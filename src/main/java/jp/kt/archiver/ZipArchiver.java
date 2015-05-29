package jp.kt.archiver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * zip圧縮・解凍ツール.
 * <p>
 * 日本語ファイル名には対応していません.
 * </p>
 * 
 * @author tatsuya.kumon
 */
public final class ZipArchiver extends BaseArchiver {
	@Override
	public void compress(String baseDirPath, String compFilePath)
			throws Exception {
		// パスチェック
		checkCompressPath(baseDirPath, compFilePath);
		// 出力先ファイル
		try (ZipOutputStream zos = new ZipOutputStream(
				Files.newOutputStream(Paths.get(compFilePath)))) {
			// 出力先 OutputStream を生成
			outputToZipStream(zos, Paths.get(baseDirPath),
					Paths.get(baseDirPath));
		}
	}

	/**
	 * 入力ファイルをzipファイル出力ストリームに出力.
	 * 
	 * @param zos
	 *            zipファイル出力ストリーム
	 * @param inputFile
	 *            入力ファイル
	 * @param baseDir
	 *            圧縮対象のベースディレクトリ
	 * @throws IOException
	 */
	private void outputToZipStream(ZipOutputStream zos, Path inputFile,
			Path baseDir) throws IOException {
		if (Files.isDirectory(inputFile)) {
			/*
			 * ディレクトリの場合
			 */
			// Entry名の生成
			String entryName = createEntryName(inputFile, baseDir);
			// ルートディレクトリを除いてEntryに追加する
			if (!entryName.isEmpty()) {
				// ディレクトリは末尾にスラッシュを付加する
				entryName = entryName + "/";
				// 出力先Entryを設定
				ZipEntry entry = new ZipEntry(entryName);
				entry.setTime(Files.getLastModifiedTime(inputFile).toMillis());
				zos.putNextEntry(entry);
				// 書き込んだらEntryをcloseする
				zos.closeEntry();
			}
			// ディレクトリに含まれるファイル分、再帰呼び出し
			try (DirectoryStream<Path> stream = Files
					.newDirectoryStream(inputFile)) {
				for (Path entry : stream) {
					outputToZipStream(zos, entry, baseDir);
				}
			}
		} else {
			/*
			 * ファイルの場合
			 */
			try (BufferedInputStream fis = new BufferedInputStream(
					Files.newInputStream(inputFile))) {
				// Entry名称を取得
				String entryName = createEntryName(inputFile, baseDir);
				// 出力先Entryを設定
				ZipEntry entry = new ZipEntry(entryName);
				entry.setSize(Files.size(inputFile));
				entry.setTime(Files.getLastModifiedTime(inputFile).toMillis());
				zos.putNextEntry(entry);
				// 入力ファイルを読み込み出力ストリームに書き込んでいく
				int ava = 0;
				while ((ava = fis.available()) > 0) {
					byte[] bs = new byte[ava];
					fis.read(bs);
					zos.write(bs);
				}
				// 書き込んだらEntryをclose
				zos.closeEntry();
			}
		}
	}

	@Override
	public void decompress(String compFilePath, String outputDirPath)
			throws Exception {
		// パスチェック
		checkDecompressPath(compFilePath, outputDirPath);
		// zipファイルからZipEntryを一つずつ取り出し、ファイルに保存していく
		try (ZipFile zipFile = new ZipFile(compFilePath)) {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				// 出力先
				Path outPath = Paths.get(outputDirPath, entry.getName());
				if (entry.isDirectory()) {
					// Entryがディレクトリの場合はディレクトリを作成
					Files.createDirectories(outPath);
				} else {
					// 出力先ファイルの保存先ディレクトリが存在しない場合は、
					// ディレクトリを作成しておく
					if (!Files.exists(outPath.getParent())) {
						Files.createDirectories(outPath.getParent());
					}
					try (BufferedInputStream bis = new BufferedInputStream(
							zipFile.getInputStream(entry));
							BufferedOutputStream bos = new BufferedOutputStream(
									Files.newOutputStream(outPath))) {
						// 入力ストリームから読み込み、出力ストリームへ書き込む
						int byteCount;
						while ((byteCount = bis.available()) > 0) {
							byte[] data = new byte[byteCount];
							// 入力
							bis.read(data);
							// 出力
							bos.write(data);
						}
					}
				}
				// 最終更新日時をセット
				Files.setLastModifiedTime(outPath,
						FileTime.fromMillis(entry.getTime()));
			}
		}
	}
}
