package jp.kt.text;

import java.io.Serializable;

/**
 * ソート条件の定義クラス.
 *
 * @author tatsuya.kumon
 */
class SortCondition implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ソート対象の項目インデックス（最初は0） */
	private int index;

	/** ソートするデータ型 */
	private SortType type;

	/**
	 * ソート順（昇順/降順）.<br>
	 * true:昇順、false:降順
	 */
	private SortOrder order;

	/**
	 * コンストラクタ.
	 *
	 * @param index
	 *            ソート対象の項目インデックス（最初は0）
	 * @param type
	 *            ソートするデータ型
	 * @param asc
	 *            ソート順（昇順/降順）
	 */
	SortCondition(int index, SortType type, SortOrder order) {
		this.index = index;
		this.type = type;
		this.order = order;
	}

	/**
	 * 昇順/降順区分を返す.
	 *
	 * @return type
	 */
	SortType getType() {
		return type;
	}

	/**
	 * インデックスの取得.
	 *
	 * @return index
	 */
	int getIndex() {
		return index;
	}

	/**
	 * 昇順/降順区分を返す.
	 *
	 * @return order
	 */
	SortOrder getOrder() {
		return order;
	}
}
