package jp.kt.text;

import java.util.Comparator;
import java.util.List;

/**
 * String配列のソートComparator.
 *
 * @author tatsuya.kumon
 */
class StringArrayComparator implements Comparator<String[]> {
	/** ソート順リスト */
	private List<SortCondition> conditionList;

	/**
	 * コンストラクタ.
	 *
	 * @param orderList
	 *            {@link SortCondition}のList
	 */
	StringArrayComparator(List<SortCondition> conditionList) {
		this.conditionList = conditionList;
	}

	/**
	 * 大小比較.
	 *
	 * @param o1
	 *            比較対象の最初のオブジェクト
	 * @param o2
	 *            比較対象の 2 番目のオブジェクト
	 * @return 最初の引数が 2 番目の引数より小さい場合は負の整数、両方が等しい場合は 0、最初の引数が 2 番目の引数より大きい場合は正の整数
	 */
	public int compare(String[] o1, String[] o2) {
		int result = 0;
		for (int i = 0; result == 0 && i < conditionList.size(); i++) {
			SortCondition c = conditionList.get(i);
			if (c.getType().equals(SortType.STRING)) {
				// 文字列ソート
				result = o1[c.getIndex()].compareTo(o2[c.getIndex()]);
			} else if (c.getType().equals(SortType.NUMBER)) {
				// 数値ソート
				result = new Double(o1[c.getIndex()]).compareTo(new Double(o2[c
						.getIndex()]));
			}
			result = (c.getOrder().isAsk() ? result : result * -1);
		}
		return result;
	}
}
