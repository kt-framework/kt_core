package jp.kt.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@link BaseText}オブジェクト内のデータをソートする.<br>
 * 複数のソート条件を指定可能.
 * <hr style="border-style:dashed">
 * <p>
 * 【サンプル1：通常の配列データのソート】<br>
 * <br>
 * 以下のようなデータがある時、これを降順でソートしたい。<br>
 * くもん<br>
 * ふじた<br>
 * まえだ<br>
 * うめだ<br>
 * かとう<br>
 * <blockquote>
 *
 * <pre style="font-size:small;">
 * // PlainTextオブジェクトに変換
 * PlainText text = new PlainText();
 * text.addLineText(&quot;くもん&quot;);
 * text.addLineText(&quot;ふじた&quot;);
 * text.addLineText(&quot;まえだ&quot;);
 * text.addLineText(&quot;うめだ&quot;);
 * text.addLineText(&quot;かとう&quot;);
 * // 文字列降順でソートする
 * // addOrderの第一引数は1番目を表すインデックス値なので0を指定
 * Sort sort = new Sort(text);
 * sort.addCondition(0, SortType.STRING, SortOrder.DESC);
 * sort.sort();
 * </pre>
 *
 * </blockquote> ソート処理後のPlainTextの内容は次のようになります。<br>
 * まえだ<br>
 * ふじた<br>
 * くもん<br>
 * かとう<br>
 * うめだ<br>
 * <hr style="border-style:dashed">
 * <p>
 * 【サンプル2：2次元配列データの複合キーによるソート】<br>
 * <br>
 * CSVファイル/var/sample/sample.csvに以下の内容がある時、1列目を昇順、3番目を降順でソートしたい。<br>
 * "net","くもん","40"<br>
 * "sys","ふじた","70"<br>
 * "net","まえだ","90"<br>
 * "net","うめだ","89"<br>
 * "sys","かとう","75"<br>
 * <blockquote>
 *
 * <pre style="font-size:small;">
 * // ファイルを読み込んで、CsvTextオブジェクトに変換
 * FileUtil fileUtil = new FileUtil(&quot;/var/sample/sample.csv&quot;);
 * CsvText text = new CsvText();
 * text.setAllText(fileUtil.getFileContent());
 * // 1番目の項目を文字列昇順、3番目の項目を整数降順でソートする
 * // addOrderの第一引数はインデックス値なので0や2になることに注意
 * Sort sort = new Sort(text);
 * sort.addCondition(0, SortType.STRING, SortOrder.ASC);
 * sort.addCondition(2, SortType.NUMBER, SortOrder.DESC);
 * sort.sort();
 * </pre>
 *
 * </blockquote> ソート処理後のCsvTextの内容は次のようになります。<br>
 * "net","まえだ","90"<br>
 * "net","うめだ","89"<br>
 * "net","くもん","40"<br>
 * "sys","かとう","75"<br>
 * "sys","ふじた","70"<br>
 *
 * @author tatsuya.kumon
 */
public class Sort {
	/** ソート対象データ */
	private BaseText text;

	/** ソート条件リスト */
	private List<SortCondition> conditionList;

	/**
	 * コンストラクタ.
	 *
	 * @param text
	 *            ソート対象データ
	 */
	public Sort(BaseText text) {
		this.text = text;
		this.conditionList = new ArrayList<SortCondition>();
	}

	/**
	 * ソート条件を追加.
	 * <p>
	 * このメソッドを実行した順番がソートの優先順位となります.
	 * </p>
	 *
	 * @param index
	 *            ソート対象の項目インデックス（最初は0）
	 * @param type
	 *            ソートするデータ型
	 * @param order
	 *            ソート順（昇順/降順）
	 */
	public void addCondition(int index, SortType type, SortOrder order) {
		SortCondition condition = new SortCondition(index, type, order);
		// ソート順リストに追加
		conditionList.add(condition);
	}

	/**
	 * ソート実行.
	 */
	public void sort() {
		StringArrayComparator comparator = new StringArrayComparator(
				conditionList);
		Collections.sort(text.getContent(), comparator);
	}
}
