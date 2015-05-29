package jp.kt.text;

import java.io.IOException;

/**
 * プレーンテキスト形式.
 * 
 * @author tatsuya.kumon
 */
public final class PlainText extends BaseText {
	/**
	 * コンストラクタ.
	 */
	public PlainText() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param allText
	 *            全文
	 * @throws IOException
	 */
	public PlainText(String allText) throws IOException {
		super(allText);
	}

	@Override
	public String getLineText(int lineIndex) {
		// 指定行のデータを取得
		String[] data = super.getLineData(lineIndex);
		return data[0];
	}

	@Override
	public void addLineText(String lineText) {
		// 行データとしてセットする
		super.addLineData(new String[] { lineText });
	}
}
