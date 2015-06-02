package jp.kt.fileio;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;
import java.util.regex.Pattern;

import jp.kt.fileio.FindCondition.Type;

/**
 * Find処理クラス.
 * <p>
 * 対象ディレクトリ以下を再帰的に検索します.<br>
 * ファイル名やディレクトリ名は正規表現で指定する.
 * </p>
 *
 * @author tatsuya.kumon
 */
public class Find extends SimpleFileVisitor<Path> {
	/** 検索タイプ */
	private Type type;

	/** 検索対象の名前の正規表現 */
	private String nameRegex;

	/** 検索結果のパスリスト */
	private List<String> pathList;

	/** 検索対象の最小ファイルサイズ */
	private Long minSize;

	/** 検索対象の最大ファイルサイズ */
	private Long maxSize;

	/** 検索対象の最小日時 */
	private Date minDate;

	/** 検索対象の最大日時 */
	private Date maxDate;

	/**
	 * コンストラクタ.
	 *
	 * @param type
	 *            検索タイプ
	 * @param nameRegex
	 *            検索対象の名前を正規表現で指定する.<br>
	 *            正規表現の書式は {@link Pattern} を参照のこと.
	 * @param minSize
	 *            最小サイズ
	 * @param maxSize
	 *            最大サイズ
	 * @param minDate
	 *            最小日時
	 * @param maxDate
	 *            最大日時
	 */
	private Find(Type type, String nameRegex, Long minSize, Long maxSize,
			Date minDate, Date maxDate) {
		this.type = type;
		this.nameRegex = nameRegex;
		this.minSize = minSize;
		this.maxSize = maxSize;
		this.minDate = minDate;
		this.maxDate = maxDate;
		this.pathList = new ArrayList<>();
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException {
		// マッチング処理
		this.matching(dir, attrs);
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
		// マッチング処理
		this.matching(file, attrs);
		return FileVisitResult.CONTINUE;
	}

	/**
	 * マッチング処理.
	 *
	 * @param path
	 *            対象パス
	 * @param attrs
	 *            対象パスの属性
	 * @throws IOException
	 */
	private void matching(Path path, BasicFileAttributes attrs)
			throws IOException {
		if (this.type == Type.ONLY_DIRECTORY && !attrs.isDirectory()) {
			// 検索タイプがディレクトリのみだが、ディレクトリでない場合はスキップ
			return;
		} else if (this.type == Type.ONLY_FILE && !attrs.isRegularFile()) {
			// 検索タイプがファイルのみだが、ファイルでない場合はスキップ
			return;
		}
		/*
		 * キーワードマッチング
		 */
		boolean isMatchKeyword = false;
		if (Pattern.matches(nameRegex, path.getFileName().toString())) {
			isMatchKeyword = true;
		}
		/*
		 * ファイルサイズマッチング
		 */
		boolean isMatchSize = false;
		long fileSize = attrs.size();
		if (attrs.isDirectory()) {
			// ディレクトリの場合はサイズマッチングしない
			isMatchSize = true;
		} else if (minSize == null && maxSize == null) {
			// 条件未設定
			isMatchSize = true;
		} else if (minSize != null && maxSize == null) {
			// 最小サイズのみ設定
			isMatchSize = (minSize <= fileSize);
		} else if (minSize == null && maxSize != null) {
			// 最大サイズのみ設定
			isMatchSize = (fileSize <= maxSize);
		} else {
			// 両方設定
			isMatchSize = (minSize <= fileSize && fileSize <= maxSize);
		}
		/*
		 * 最終更新日時マッチング
		 */
		boolean isMatchDate = false;
		long lastModified = attrs.lastModifiedTime().toMillis();
		if (minDate == null && maxDate == null) {
			// 条件未設定
			isMatchDate = true;
		} else if (minDate != null && maxDate == null) {
			// 最小日時のみ設定
			isMatchDate = (minDate.getTime() <= lastModified);
		} else if (minDate == null && maxDate != null) {
			// 最大日時のみ設定
			isMatchDate = (lastModified <= maxDate.getTime());
		} else {
			// 両方設定
			isMatchDate = (minDate.getTime() <= lastModified && lastModified <= maxDate
					.getTime());
		}
		/*
		 * 全ての判定がtrueならリストに追加
		 */
		if (isMatchKeyword && isMatchSize && isMatchDate) {
			pathList.add(path.toAbsolutePath().toString());
		}
	}

	/**
	 * 検索結果を返す.
	 *
	 * @return 検索結果のパスリスト
	 */
	private List<String> getResult() {
		return this.pathList;
	}

	/**
	 * Find処理実行.
	 *
	 * @param cond
	 *            Find条件
	 * @return 検索されたディレクトリもしくはファイルの絶対パスのリスト
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public static List<String> execute(FindCondition cond) throws IOException {
		// visitorクラス生成
		Find visitor = new Find(cond.getType(), cond.getNameRegex(),
				cond.getMinSize(), cond.getMaxSize(), cond.getMinDate(),
				cond.getMaxDate());
		// 検索実行
		Files.walkFileTree(Paths.get(cond.getDir()),
				EnumSet.noneOf(FileVisitOption.class), cond.getMaxDepth(),
				visitor);
		// 検索結果を返す
		return visitor.getResult();
	}
}
