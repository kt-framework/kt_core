package jp.kt.text;

import java.io.Serializable;

/**
 * ソート順（昇順/降順）の定義クラス.
 *
 * @author tatsuya.kumon
 */
public class SortOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 昇順 */
	public static final SortOrder ASC = new SortOrder(true);

	/** 降順 */
	public static final SortOrder DESC = new SortOrder(false);

	private boolean asc;

	private SortOrder(boolean asc) {
		this.asc = asc;
	}

	boolean isAsk() {
		return asc;
	}
}
