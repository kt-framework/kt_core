package jp.kt.text;

import java.io.IOException;

import jp.kt.tool.StringUtil;

/**
 * TSV形式のテキスト.
 * <p>
 * 改行コードとタブ文字は除去されます.
 * </p>
 *
 * @author tatsuya.kumon
 */
public final class TsvText extends BaseText {
	/** 区切り文字 */
	private static final String DELIM = "\t";

	/**
	 * コンストラクタ.
	 */
	public TsvText() {
		super();
	}

	/**
	 * コンストラクタ.
	 *
	 * @param allText
	 *            全文
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public TsvText(String allText) throws IOException {
		super(allText);
	}

	@Override
	public String getLineText(int lineIndex) {
		// 指定行のデータを取得
		String[] data = super.getLineData(lineIndex);
		// データを1つの文字列に変換
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length; i++) {
			// 改行コードを除去する
			String temp = StringUtil.removeLine(data[i]);
			// タブを除去する
			temp = StringUtil.replaceAll(temp, "\t", "");
			// データを連結
			sb.append(temp);
			// 最後でなければ区切り文字を付加する
			if (i < data.length - 1) {
				sb.append(DELIM);
			}
		}
		return sb.toString();
	}

	@Override
	public void addLineText(String lineText) {
		// タブ区切りでString配列にする
		String[] lineData = lineText.split(DELIM, -1);
		// 行データとしてセットする
		super.addLineData(lineData);
	}
}
