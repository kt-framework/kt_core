package jp.kt.text;

import java.io.IOException;

/**
 * CSV形式のテキスト.
 * <p>
 * 改行コードは除去されます.
 * </p>
 *
 * @author tatsuya.kumon
 */
public final class CsvText extends CsvBaseText {
	/**
	 * コンストラクタ.
	 */
	public CsvText() {
		super();
	}

	/**
	 * コンストラクタ.
	 *
	 * @param allText
	 *            全文
	 * @throws IOException
	 */
	public CsvText(String allText) throws IOException {
		super(allText);
	}

	@Override
	final boolean isExcelMode() {
		return false;
	}
}
