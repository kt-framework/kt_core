package jp.kt.text;

import java.io.Serializable;

/**
 * ソートするデータ型の定義クラス.
 *
 * @author tatsuya.kumon
 */
public class SortType implements Serializable {
	private static final long serialVersionUID = 1L;

	/** 文字列ソート */
	public static final SortType STRING = new SortType(1);

	/** 数値ソート.整数でも小数でも可. */
	public static final SortType NUMBER = new SortType(2);

	private int type;

	private SortType(int type) {
		this.type = type;
	}

	/*
	 * (非 Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof SortType && this.type == ((SortType) obj).type) {
			return true;
		}
		return false;
	}
}
