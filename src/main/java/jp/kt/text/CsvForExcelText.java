package jp.kt.text;

import java.io.IOException;

/**
 * Excelで開いても数値や日付が崩れないCSV形式のテキスト.
 * <p>
 * 改行コードは除去されます.
 * </p>
 *
 * @author tatsuya.kumon
 */
public final class CsvForExcelText extends CsvBaseText {
	/**
	 * コンストラクタ.
	 */
	public CsvForExcelText() {
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
	public CsvForExcelText(String allText) throws IOException {
		super(allText);
	}

	@Override
	final boolean isExcelMode() {
		return true;
	}
}
