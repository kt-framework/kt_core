package jp.kt.text;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import jp.kt.tool.DateUtil;
import jp.kt.tool.StringUtil;
import jp.kt.tool.Validator;

/**
 * CSV形式のテキスト.
 * <p>
 * 改行コードは除去されます.
 * </p>
 *
 * @author tatsuya.kumon
 */
abstract class CsvBaseText extends BaseText {
	/** 区切り文字 */
	private static final String DELIM = ",";

	/** ダブルクォート（エスケープ処理のための定数） */
	private static final String DOUBLE_QUOTE = "\"";

	/**
	 * コンストラクタ.
	 */
	public CsvBaseText() {
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
	public CsvBaseText(String allText) throws IOException {
		super(allText);
	}

	/**
	 * Excelモードを返す.
	 *
	 * @return Excelモードの場合はtrue
	 */
	abstract boolean isExcelMode();

	@Override
	public final String getLineText(int lineIndex) {
		// 指定行のデータを取得
		String[] lineData = super.getLineData(lineIndex);
		// データを1つの文字列に変換
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < lineData.length; i++) {
			// ダブルクォーテーションがあれば二重にする
			String data = StringUtil.replaceAll(lineData[i], DOUBLE_QUOTE,
					DOUBLE_QUOTE + DOUBLE_QUOTE);
			// 改行コードを除去する
			data = StringUtil.removeLine(data);
			// Excel用出力設定がONの場合のみ、数値（全角含む）と日付文字列には頭にイコールを付ける
			// 数値と日付文字列以外に付けてしまうとデータ内に半角カンマがあった場合、区切り文字と判別されてしまう
			if (isExcelMode()) {
				if (!Validator.isEmpty(data)) {
					// 半角に変換した上で判定
					String temp = StringUtil.zenToHan(data);
					/*
					 * 数値もしくは日付文字列の場合は、頭にイコールを付加する
					 */
					if (Validator.isNumber(temp) || this.isDateText(temp)) {
						sb.append("=");
					}
				}
			}
			// ダブルクォーテーションで囲む
			sb.append(DOUBLE_QUOTE);
			sb.append(data);
			sb.append(DOUBLE_QUOTE);
			// 最後でなければカンマをつける
			if (i < lineData.length - 1) {
				sb.append(DELIM);
			}
		}
		return sb.toString();
	}

	/**
	 * 日付文字列判定.
	 *
	 * @param text
	 *            文字列
	 * @return 日付文字列ならtrue
	 */
	private boolean isDateText(String text) {
		final String[] datePatterns = new String[] { "yyyy/M/d", "yyyy/M",
				"M/d", "yyyy年M月d日", "yyyy年M月", "M月d日" };
		boolean isDateText = false;
		for (String pattern : datePatterns) {
			try {
				DateUtil.getDate(text, pattern);
				// ParseExceptionが出なければ日付文字列
				isDateText = true;
				break;
			} catch (ParseException e) {
			}
		}
		return isDateText;
	}

	@Override
	public void addLineText(String lineText) {
		try {
			// カンマ区切りでString配列にする
			String[] lineData = lineText.split(DELIM, -1);
			// ダブルクォートを考慮して再度配列を作り直す
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < lineData.length; i++) {
				if (lineData[i].startsWith(DOUBLE_QUOTE)) {
					// ダブルクォートで始まっている場合
					StringBuffer sb = new StringBuffer(lineData[i]);
					// ダブルクォートが奇数の場合は次以降の単語とカンマで連結
					if (!isDoubleQuoteCountEven(lineData[i])) {
						sb.append(DELIM);
						sb.append(lineData[++i]);
						// これ以降はダブルクォートが偶数なら連結
						while (isDoubleQuoteCountEven(lineData[i])) {
							sb.append(DELIM);
							sb.append(lineData[++i]);
						}
					}
					// 前後のダブルクォートを除去
					String word = sb.substring(1, sb.length() - 1);
					// 連続ダブルクォートを1つにする
					word = StringUtil.replaceAll(word, DOUBLE_QUOTE
							+ DOUBLE_QUOTE, DOUBLE_QUOTE);
					// リストにセット
					list.add(word);
				} else {
					// ダブルクォートで始まっていない場合はそのままセット
					list.add(lineData[i]);
				}
			}
			// 配列に変換
			lineData = new String[list.size()];
			list.toArray(lineData);
			// 行データとしてセットする
			super.addLineData(lineData);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new TextFormatException(e);
		}
	}

	/**
	 * ダブルクォートの数が偶数個ならtrueを返す.
	 *
	 * @param text
	 *            テキスト
	 * @return ダブルクォートの数が偶数個ならtrue
	 */
	private boolean isDoubleQuoteCountEven(String text) {
		// ダブルクォートの数をカウント
		char[] cArray = text.toCharArray();
		int count = 0;
		for (int i = 0; i < cArray.length; i++) {
			if (String.valueOf(cArray[i]).equals(DOUBLE_QUOTE)) {
				count++;
			}
		}
		// ダブルクォートが偶数ならtrue
		return (count % 2 == 0);
	}
}
