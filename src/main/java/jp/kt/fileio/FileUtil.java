package jp.kt.fileio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.kt.exception.KtException;
import jp.kt.prop.KtProperties;
import jp.kt.tool.Command;
import jp.kt.tool.Command.Result;
import jp.kt.tool.Validator;

/**
 * ファイルやディレクトリを操作するためのユーティリティ.
 *
 * @author tatsuya.kumon
 */
public class FileUtil {
	/**
	 * NFSモードの際にファイル存在確認を行う最大ミリ秒数.
	 */
	private static final long MAX_WAIT_MILLISEC = 2000;

	/** Pathオブジェクト */
	private Path path;

	/** NFSモード */
	private boolean isNfsMode;

	/**
	 * コンストラクタ.
	 * <p>
	 * NFSモードはOFFになります.
	 * </p>
	 *
	 * @param pathText
	 *            ディレクトリ、もしくはファイルへのパス
	 * @param nextPaths
	 *            現在のパス配下に連結するディレクトリ名もしくはファイル名
	 */
	public FileUtil(String pathText, String... nextPaths) {
		this(pathText, false, nextPaths);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param pathText
	 *            ディレクトリ、もしくはファイルへのパス
	 * @param isNfsMode
	 *            NFSモードの場合はtrue.<br>
	 *            パスの伝播に時間がかかる時のためのモード.<br>
	 *            NFSモードにすると、パスの存在確認に最大2秒程度かかる.
	 * @param nextPaths
	 *            現在のパス配下に連結するディレクトリ名もしくはファイル名
	 */
	public FileUtil(String pathText, boolean isNfsMode, String... nextPaths) {
		this(Paths.get(pathText, nextPaths), isNfsMode);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param path
	 *            {@link Path}オブジェクト
	 * @param isNfsMode
	 *            NFSモードの場合はtrue.<br>
	 *            パスの伝播に時間がかかる時のためのモード.<br>
	 *            NFSモードにすると、パスの存在確認に最大2秒程度かかる.
	 */
	private FileUtil(Path path, boolean isNfsMode) {
		this.path = path.toAbsolutePath();
		this.isNfsMode = isNfsMode;
	}

	/**
	 * パスを返す.
	 *
	 * @return ファイルの絶対パス
	 */
	public String getPath() {
		return this.path.toString();
	}

	/**
	 * パスを親ディレクトリに変更.
	 * <p>
	 * 親ディレクトリへ移動できない（親ディレクトリが存在しない）場合は何もしません.
	 * </p>
	 */
	public void setParentPath() {
		Path p = path.getParent();
		if (p != null) {
			this.path = p;
		}
	}

	/**
	 * パスをサブディレクトリもしくはファイルに変更.
	 *
	 * @param nextPaths
	 *            現在のパス配下に連結するディレクトリ名もしくはファイル名
	 */
	public void setNextPath(String... nextPaths) {
		this.path = Paths.get(this.path.toString(), nextPaths);
	}

	/**
	 * ファイル名もしくはディレクトリ名のみを返します.
	 *
	 * @return ファイル名もしくはディレクトリ名
	 */
	public String getName() {
		Path fileName = this.path.getFileName();
		if (fileName == null) {
			return null;
		} else {
			return fileName.toString();
		}
	}

	/**
	 * ファイルやディレクトリのコピー.
	 * <p>
	 * ファイルの場合は上書きします.<br>
	 * ディレクトリの場合は上書きします.
	 * </p>
	 *
	 * @param target
	 *            コピー先ファイル
	 * @param isUpdateLastModified
	 *            ファイルの最終更新日時を現在日時で更新するかどうかのフラグ
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public void copy(final FileUtil target, final boolean isUpdateLastModified)
			throws IOException {
		if (!exists(this.path, this.isNfsMode)) {
			throw new KtException("A014", "コピー元が存在しません [" + this.path + "]");
		} else if (isDirectory(this.path, this.isNfsMode)) {
			// コピー元がディレクトリの場合
			this.copyDirectory(target.path, isUpdateLastModified);
		} else if (isFile(this.path, this.isNfsMode)) {
			// コピー元がファイルの場合
			this.copyFile(target.path, isUpdateLastModified);
		}
	}

	/**
	 * ファイルコピー.
	 * <p>
	 * コピー先が既に存在していても上書きします.
	 * </p>
	 *
	 * @param targetFile
	 *            コピー先ファイル
	 * @param isUpdateLastModified
	 *            ファイルの最終更新日時を現在日時で更新するかどうかのフラグ
	 * @throws IOException
	 */
	private void copyFile(final Path targetFile,
			final boolean isUpdateLastModified) throws IOException {
		// コピー元とコピー先のパスが同じ場合は何もしない
		if (this.path.equals(targetFile)) {
			return;
		}
		// コピー先がディレクトリとして存在していないかどうかチェック
		// 存在しないのが正なので、this.isDirectory(Path)は使わない
		if (Files.isDirectory(targetFile)) {
			throw new KtException("A031", "コピー先のパスがディレクトリとして存在しているのでコピーできません ["
					+ this.path + "]");
		}
		// 上書きモードでコピー実行
		Files.copy(this.path, targetFile, StandardCopyOption.REPLACE_EXISTING,
				StandardCopyOption.COPY_ATTRIBUTES);
		if (isUpdateLastModified) {
			// 最終更新日時を現在日時に更新
			Files.setLastModifiedTime(targetFile,
					FileTime.fromMillis(new Date().getTime()));
		}
	}

	/**
	 * ディレクトリのコピー.
	 *
	 * @param targetDir
	 *            コピー先ディレクトリ
	 * @param isUpdateLastModified
	 *            ファイルの最終更新日時を現在日時で更新するかどうかのフラグ
	 * @throws IOException
	 */
	private void copyDirectory(final Path targetDir,
			final boolean isUpdateLastModified) throws IOException {
		// コピー元とコピー先が同じ場合は何もしない
		if (this.path.equals(targetDir)) {
			return;
		}
		// コピー先が既に存在していないかどうかチェック
		// 存在しないのが正なので、this.exists(Path)は使わない
		if (Files.exists(targetDir)) {
			throw new KtException("A031", "コピー先のパスが存在しているのでコピーできません ["
					+ targetDir + "]");
		}
		// コピー処理するvisitor作成
		FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir,
					BasicFileAttributes attrs) throws IOException {
				Path targetD = targetDir.resolve(path.relativize(dir));
				// ディレクトリコピー実行
				Files.copy(dir, targetD, StandardCopyOption.REPLACE_EXISTING,
						StandardCopyOption.COPY_ATTRIBUTES);
				if (isUpdateLastModified) {
					// 最終更新日時を現在日時に更新
					Files.setLastModifiedTime(targetD,
							FileTime.fromMillis(new Date().getTime()));
				}
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult visitFile(Path file,
					BasicFileAttributes attrs) throws IOException {
				Path targetF = targetDir.resolve(path.relativize(file));
				// ファイルコピー実行
				Files.copy(file, targetF, StandardCopyOption.REPLACE_EXISTING,
						StandardCopyOption.COPY_ATTRIBUTES);
				if (isUpdateLastModified) {
					// 最終更新日時を現在日時に更新
					Files.setLastModifiedTime(targetF,
							FileTime.fromMillis(new Date().getTime()));
				}
				return FileVisitResult.CONTINUE;
			}
		};
		// 再帰的にコピー実行
		Files.walkFileTree(this.path, visitor);
	}

	/**
	 * ファイルもしくはディレクトリの削除.
	 * <p>
	 * ディレクトリの場合は、再帰的に削除します.<br>
	 * 削除対象のディレクトリもしくはファイルが別プロセスにより使用中の場合はExceptionが発生します.<br>
	 * 既に削除対象が存在していない場合は何もせず正常終了します.
	 * </p>
	 *
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public void delete() throws IOException {
		if (isFile(this.path, this.isNfsMode)) {
			/*
			 * ファイルの場合
			 */
			Files.delete(this.path);
		} else if (isDirectory(this.path, this.isNfsMode)) {
			/*
			 * ディレクトリの場合
			 */
			// 削除処理するvisitor作成
			FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file,
						BasicFileAttributes attrs) throws IOException {
					// ファイル削除
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir,
						IOException exception) throws IOException {
					if (exception == null) {
						// ディレクトリ削除
						Files.delete(dir);
						return FileVisitResult.CONTINUE;
					} else {
						throw exception;
					}
				}
			};
			// 再帰的に削除実行
			Files.walkFileTree(this.path, visitor);
		}
	}

	/**
	 * ファイルもしくはディレクトリの移動もしくはリネーム.
	 *
	 * @param target
	 *            移動先
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public void move(FileUtil target) throws IOException {
		// 変更元のパス存在チェック
		if (!exists(this.path, this.isNfsMode)) {
			throw new KtException("A013", "変更元のパスが存在しません [" + this.path + "]");
		}
		// 移動
		Files.move(this.path, target.path);
	}

	/**
	 * サーバ上のテキストファイルの内容を取得する.
	 *
	 * @param charset
	 *            文字コード
	 * @return ファイルの全内容を全てStringで返す.
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public String readAllString(String charset) throws IOException {
		// ファイルかどうかチェック
		if (!isFile(this.path, this.isNfsMode)) {
			throw new KtException("A014", "指定されたパスは存在しない、もしくはファイルではありません ["
					+ this.path + "]");
		}
		// ファイル内容の取得
		byte[] bytes = Files.readAllBytes(this.path);
		return new String(bytes, charset);
	}

	/**
	 * サーバ上のテキストファイルの内容を取得する.<br>
	 * デフォルトの文字コードで取得します.
	 *
	 * @return ファイルの全内容を全てStringで返す.
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public String readAllString() throws IOException {
		return readAllString(KtProperties.getInstance().getDefaultCharset());
	}

	/**
	 * サーバ上のバイナリファイルの内容を取得する.
	 *
	 * @return ファイルの全内容を全てbyte配列で返す.
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public byte[] readAllBytes() throws IOException {
		// ファイルかどうかチェック
		if (!isFile(this.path, this.isNfsMode)) {
			throw new KtException("A014", "指定されたパスは存在しない、もしくはファイルではありません ["
					+ this.path + "]");
		}
		// ファイル内容の取得
		return Files.readAllBytes(this.path);
	}

	/**
	 * ファイルとして存在するか調べる.
	 * <p>
	 * 存在しない、もしくはディレクトリの場合はfalseを返す.
	 * </p>
	 *
	 * @return ファイルとして存在する場合はtrue
	 */
	public boolean isFile() {
		return isFile(this.path, this.isNfsMode);
	}

	/**
	 * ディレクトリとして存在するか調べる.
	 * <p>
	 * 存在しない、もしくはファイルの場合はfalseを返す.
	 * </p>
	 *
	 * @return ディレクトリとして存在する場合はtrue
	 */
	public boolean isDirectory() {
		return isDirectory(this.path, this.isNfsMode);
	}

	/**
	 * ディレクトリを作成します.
	 * <p>
	 * 親ディレクトリが存在しなくても強制的に作成します.
	 * </p>
	 *
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public void makeDirectory() throws IOException {
		Files.createDirectories(this.path);
	}

	/**
	 * 指定した文字列をファイル出力.<br>
	 * 文字コードはデフォルトの設定となります.
	 *
	 * @param text
	 *            ファイルに出力するテキスト
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public void write(String text) throws IOException {
		String charset = KtProperties.getInstance().getDefaultCharset();
		this.write(text, charset);
	}

	/**
	 * 指定した文字列をファイル出力.<br>
	 * 文字コード指定版.
	 *
	 * @param text
	 *            ファイルに出力するテキスト
	 * @param charset
	 *            文字コード
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public void write(String text, String charset) throws IOException {
		// 文字コード
		if (Validator.isEmpty(charset)) {
			// 引数が空の場合はデフォルト文字コードをセットする
			charset = KtProperties.getInstance().getDefaultCharset();
		}
		// 書き出し実行
		this.write(text.getBytes(charset));
	}

	/**
	 * バイトデータをファイル出力する.
	 *
	 * @param outputData
	 *            出力データ
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public void write(byte[] outputData) throws IOException {
		// 親パスがディレクトリであることをチェック
		if (!isDirectory(this.path.getParent(), this.isNfsMode)) {
			throw new KtException("A015", "親ディレクトリが存在しません [" + this.path + "]");
		}
		// 書き出し実行
		Files.write(this.path, outputData);
	}

	/**
	 * 空のファイルを作成します.
	 * <p>
	 * 既に存在している場合は、最終更新日時を現在日時にするのみで、ファイルの内容は変えません.
	 * </p>
	 *
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public void touch() throws IOException {
		// 親パスがディレクトリであることをチェック
		if (!isDirectory(this.path.getParent(), this.isNfsMode)) {
			throw new KtException("A015", "親ディレクトリが存在しません [" + this.path + "]");
		}
		// 空のファイル作成
		if (exists(this.path, this.isNfsMode)) {
			// 既に存在しいていれば最終更新日時を現在日時に更新
			Files.setLastModifiedTime(this.path,
					FileTime.fromMillis(new Date().getTime()));
		} else {
			// 存在していなければ作成
			Files.createFile(this.path);
		}
	}

	/**
	 * 指定ディレクトリに存在するファイルリストを取得.
	 *
	 * @return ファイルリスト
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public List<FileUtil> getFileList() throws IOException {
		return getList(true);
	}

	/**
	 * 指定ディレクトリに存在するディレクトリリストを取得.
	 *
	 * @return ファイルリスト
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public List<FileUtil> getDirectoryList() throws IOException {
		return getList(false);
	}

	/**
	 * ディレクトリに存在するファイルもしくはディレクトリのリストを返す.
	 *
	 * @param isFile
	 *            ファイルリストの場合はtrue、ディレクトリリストの場合はfalseを指定する.
	 * @return ファイルもしくはディレクトリのリスト
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	private List<FileUtil> getList(final boolean isFile) throws IOException {
		if (!isDirectory(this.path, this.isNfsMode)) {
			throw new KtException("A015", "指定されたパスは存在しない、もしくはディレクトリではありません ["
					+ this.path + "]");
		}
		// ファイル名がキーワードに合致するファイルを抽出するフィルター
		DirectoryStream.Filter<Path> filter = new DirectoryStream.Filter<Path>() {
			public boolean accept(Path p) throws IOException {
				if (isFile) {
					// ファイル
					return Files.isRegularFile(p);
				} else {
					// ディレクトリ
					return Files.isDirectory(p);
				}
			}
		};
		// フィルターで抽出されたファイルが1つでも存在すればtrue
		List<FileUtil> fileList = new ArrayList<FileUtil>();
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(this.path,
				filter)) {
			for (Path p : ds) {
				fileList.add(new FileUtil(p, this.isNfsMode));
			}
		}
		return fileList;
	}

	/**
	 * ファイルもしくはディレクトリの最終更新日時を取得する.
	 *
	 * @return 最終更新日時
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public Date getLastModifiedDate() throws IOException {
		if (!exists(this.path, this.isNfsMode)) {
			throw new KtException("A013", "指定されたパスは存在しません [" + this.path + "]");
		}
		return new Date(Files.getLastModifiedTime(this.path).toMillis());
	}

	/**
	 * ファイルサイズを取得する.
	 * <p>
	 * ディレクトリ内のファイルサイズを取得したい場合は {@link DiskUsage} クラスを使用してください.
	 * </p>
	 *
	 * @return ファイルサイズ（バイト）
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public long getFileSize() throws IOException {
		// ファイルかどうかチェック
		if (!isFile(this.path, this.isNfsMode)) {
			throw new KtException("A014", "指定されたパスは存在しない、もしくはファイルではありません ["
					+ this.path + "]");
		}
		// ファイルサイズの取得
		return Files.size(this.path);
	}

	/**
	 * パスの存在確認.
	 * <p>
	 * NFSモードの場合は、最大で2秒間検索する.<br/>
	 * パスが存在していれば通常は瞬時に返ってくる.
	 * </p>
	 *
	 * @param p
	 *            対象{@link Path}オブジェクト
	 * @param isNfsMode
	 *            NFSモードフラグ
	 * @return パスが存在する場合はtrue
	 */
	private static boolean exists(Path p, boolean isNfsMode) {
		// 存在確認
		boolean exists = Files.exists(p);
		if (!exists && isNfsMode) {
			// 存在せず、NFSモードの場合
			try {
				// 100ミリ秒毎に存在確認する
				final long interval = 100;
				for (long milli = 0; milli < MAX_WAIT_MILLISEC; milli += interval) {
					Thread.sleep(interval);
					// 存在確認
					exists = Files.exists(p);
					if (exists) {
						break;
					}
				}
			} catch (Exception e) {
			}
		}
		return exists;
	}

	/**
	 * ディレクトリとしての存在判定.
	 * <p>
	 * NFS対策込.
	 * </p>
	 *
	 * @param p
	 *            対象{@link Path}オブジェクト
	 * @param isNfsMode
	 *            NFSモードフラグ
	 * @return ディレクトリとして存在する場合はtrue
	 */
	private static boolean isDirectory(Path p, boolean isNfsMode) {
		return (exists(p, isNfsMode) && Files.isDirectory(p));
	}

	/**
	 * ファイルとしての存在判定.
	 * <p>
	 * NFS対策込.
	 * </p>
	 *
	 * @param p
	 *            対象{@link Path}オブジェクト
	 * @param isNfsMode
	 *            NFSモードフラグ
	 * @return ファイルとして存在する場合はtrue
	 */
	private static boolean isFile(Path p, boolean isNfsMode) {
		return (exists(p, isNfsMode) && Files.isRegularFile(p));
	}

	/**
	 * パーミッション変更.
	 * <p>
	 * ディレクトリもしくはファイルのパーミッションを変更します.
	 * </p>
	 *
	 * @param permission
	 *            パーミッション（3ケタの半角数字で指定すること）
	 * @throws Exception
	 *             パラメータ不正の場合<br>
	 *             コマンド実行時に例外発生した場合
	 *
	 */
	public void chmod(String permission) throws Exception {
		// パスの存在確認
		if (!exists(this.path, this.isNfsMode)) {
			throw new KtException("A013", "指定されたパスは存在しません [" + this.path + "]");
		}
		// パーミッションチェック
		if (Validator.isEmpty(permission)) {
			// パーミッション未指定
			throw new KtException("A037", "パーミッションが指定されていません");
		} else if (Validator.isEmpty(permission) || permission.length() != 3
				|| !Validator.isNumber(permission)) {
			// 3ケタの半角数字でない
			throw new KtException("A037", "指定されたパーミッションは不正です [" + permission
					+ "]");
		} else {
			// パーミッションが000～777でない
			int p = Integer.parseInt(permission);
			if (p < 0 || 777 < p) {
				throw new KtException("A037", "指定されたパーミッションは不正です ["
						+ permission + "]");
			}
		}
		// コマンド生成
		StringBuilder command = new StringBuilder();
		String chmod = KtProperties.getInstance().getString(
				"kt.core.fileutil.chmod");
		command.append(chmod);
		command.append(" ");
		command.append(permission);
		command.append(" ");
		command.append(getPath());
		// コマンド実行
		Result result = Command.executeSynchronous(command.toString(),
				getClass());
		// エラーチェック
		if (!Validator.isEmpty(result.getErrOutput())) {
			throw new KtException("A038", "chmodコマンドの実行に失敗しました");
		}
	}

	/**
	 * 同一内容のファイルか判定.
	 * <p>
	 * 比較対象は両方ともファイルであること.<br>
	 * いずれかがファイルでなければfalseが返る.<br>
	 * テキストファイルでもバイナリファイルでも可.
	 * </p>
	 *
	 * @param f
	 *            比較対象のファイル
	 * @return 同一内容のファイルであればtrue
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public boolean isSameContent(FileUtil f) throws IOException {
		boolean isSame = false;
		if (isFile(this.path, this.isNfsMode) && isFile(f.path, f.isNfsMode)) {
			// 両方ともファイルである
			if (this.getFileSize() == f.getFileSize()) {
				// ファイルサイズが同じ
				try (InputStream is1 = Files.newInputStream(this.path);
						InputStream is2 = Files.newInputStream(f.path)) {
					// 少しずつファイルを読み込みながら差分チェック
					byte[] buffer1 = new byte[4096];
					byte[] buffer2 = new byte[4096];
					while (true) {
						// ファイル1を読み込み
						if (is1.read(buffer1) == -1) {
							// 異なる個所が無くファイル終わりまで来たら同じということ
							isSame = true;
							break;
						}
						// ファイル2を読み込み
						is2.read(buffer2);
						// 差分チェック
						boolean isSamePart = true;
						for (int i = 0; i < buffer1.length; i++) {
							if (buffer1[i] != buffer2[i]) {
								// 異なる個所があった
								isSamePart = false;
								break;
							}
						}
						if (!isSamePart) {
							// 異なる個所があったらそこで終わり
							break;
						}
					}
				}
			}
		}
		return isSame;
	}
}
