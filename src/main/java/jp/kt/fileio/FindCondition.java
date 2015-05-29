package jp.kt.fileio;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Find条件設定クラス.
 * <p>
 * {@link Find}クラスへの引数に使用する.
 * </p>
 * 
 * @author tatsuya.kumon
 */
public class FindCondition {
	/** 検索対象のディレクトリ */
	private String dir;

	/** 検索タイプ */
	private Type type;

	/** 検索対象の名前の正規表現 */
	private String nameRegex;

	/** 検索する最大階層 */
	private int maxDepth = Integer.MAX_VALUE;

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
	 * @param dir
	 *            検索対象の基準ディレクトリ
	 * @param type
	 *            検索タイプ
	 * @param nameRegex
	 *            検索キーワードを正規表現で指定する.<br>
	 *            正規表現の書式は {@link Pattern} を参照のこと.
	 */
	public FindCondition(String dir, Type type, String nameRegex) {
		this.dir = dir;
		this.type = type;
		this.nameRegex = nameRegex;
	}

	/**
	 * 基準ディレクトリを取得.
	 * 
	 * @return dir 基準ディレクトリ
	 */
	String getDir() {
		return dir;
	}

	/**
	 * 検索タイプを取得.
	 * 
	 * @return 検索タイプ
	 */
	Type getType() {
		return type;
	}

	/**
	 * 検索対象の名前の正規表現を取得.
	 * 
	 * @return 検索対象の名前の正規表現
	 */
	String getNameRegex() {
		return nameRegex;
	}

	/**
	 * 検索対象の最大階層を設定する.
	 * <p>
	 * 基準ディレクトリ直下のみの場合は 1 を設定します.<br>
	 * 無制限の場合は 0 未満の値を設定します.<br>
	 * デフォルトは無制限です.
	 * </p>
	 * 
	 * @param maxDepth
	 *            検索する最大階層
	 */
	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	/**
	 * 最大階層を取得する.
	 * 
	 * @return 最大階層
	 */
	int getMaxDepth() {
		return maxDepth;
	}

	/**
	 * 検索対象の最小ファイルサイズ設定.
	 * <p>
	 * この設定は、ファイルに対してのみ有効です.
	 * </p>
	 * 
	 * @param minSize
	 *            最小ファイルサイズ（バイト）.
	 */
	public void setMinSize(long minSize) {
		this.minSize = minSize;
	}

	/**
	 * 検索対象の最大ファイルサイズ設定.
	 * <p>
	 * この設定は、ファイルに対してのみ有効です.
	 * </p>
	 * 
	 * @param maxSize
	 *            最大ファイルサイズ（バイト）.<br>
	 */
	public void setMaxSize(long maxSize) {
		this.maxSize = maxSize;
	}

	/**
	 * 検索対象の最小更新日時設定.
	 * 
	 * @param minDate
	 *            最小日時.
	 */
	public void setMinDatetime(Date minDate) {
		this.minDate = minDate;
	}

	/**
	 * 検索対象の最大更新日時設定.
	 * 
	 * @param maxDate
	 *            最大日時.
	 */
	public void setMaxDatetime(Date maxDate) {
		this.maxDate = maxDate;
	}

	/**
	 * 検索対象の最小ファイルサイズを返す.
	 * 
	 * @return 検索対象の最小ファイルサイズ
	 */
	Long getMinSize() {
		return minSize;
	}

	/**
	 * 検索対象の最大ファイルサイズを返す.
	 * 
	 * @return 検索対象の最大ファイルサイズ
	 */
	Long getMaxSize() {
		return maxSize;
	}

	/**
	 * 検索対象の最小更新日時を返す.
	 * 
	 * @return 検索対象の最小更新日時
	 */
	Date getMinDate() {
		return minDate;
	}

	/**
	 * 検索対象の最大更新日時を返す.
	 * 
	 * @return 検索対象の最大更新日時
	 */
	Date getMaxDate() {
		return maxDate;
	}

	/**
	 * 検索対象のタイプ.
	 * 
	 * @author tatsuya.kumon
	 */
	public enum Type {
		/** ディレクトリ、ファイル全てが対象. */
		ALL,

		/** ファイルのみが対象. */
		ONLY_FILE,

		/** ディレクトリのみが対象. */
		ONLY_DIRECTORY;
	}
}
