package jp.kt.tool;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 各種データのテキストフォーマット処理を行います.
 *
 * @author tatsuya.kumon
 */
public class TextFormat {
	/**
	 * インスタンス化されないための内部コンストラクタ.
	 */
	private TextFormat() {
	}

	/**
	 * 3桁ずつカンマ区切り形式に変換する.
	 *
	 * @param number
	 *            整数値
	 * @return 変換後文字列
	 */
	public static String formatNumber(long number) {
		DecimalFormat format = new DecimalFormat();
		return format.format(number);
	}

	/**
	 * 数値を先頭0埋めした形式に変換する.
	 *
	 * @param number
	 *            整数値
	 * @param length
	 *            固定長桁数
	 * @return 変換後文字列
	 */
	public static String zeroPadding(long number, int length) {
		String numStr = String.valueOf(number);
		StringBuilder sb = new StringBuilder(numStr);
		for (int i = 0; i < length - numStr.length(); i++) {
			sb.insert(0, '0');
		}
		return sb.toString();
	}

	/**
	 * 先頭を半角スペース埋めした形式に変換する.
	 *
	 * @param text
	 *            元のテキスト
	 * @param length
	 *            固定長文字数
	 * @return 変換後文字列
	 */
	public static String spacePadding(String text, int length) {
		StringBuilder sb = new StringBuilder(text);
		for (int i = 0; i < length - text.length(); i++) {
			sb.insert(0, ' ');
		}
		return sb.toString();
	}

	/**
	 * Dateオブジェクトを指定フォーマットに変換する.
	 *
	 * @param date
	 *            日時
	 * @param pattern
	 *            {@link java.text.SimpleDateFormat}にて定義されている形式.
	 * @return 変換後文字列
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 小数値を、指定した小数点以下の桁数の文字列として返す.
	 * <p>
	 * 四捨五入して返します.<br>
	 * 実際の値よりも指定された小数点以下桁数の方が多ければ0埋めされます.
	 * </p>
	 *
	 * @param num
	 *            元の小数値
	 * @param maxFractionDigits
	 *            小数点以下桁数
	 * @return 変換後文字列
	 */
	public static String roundDecimal(double num, int maxFractionDigits) {
		// 指定された小数点以下の桁数のフォーマット文字列を生成
		StringBuilder formatStr = new StringBuilder();
		formatStr.append("0");
		for (int i = 0; i < maxFractionDigits; i++) {
			if (i == 0) {
				formatStr.append(".");
			}
			formatStr.append("0");
		}
		// 指定フォーマットでの変換
		DecimalFormat format = new DecimalFormat(formatStr.toString());
		return format.format(num);
	}
}
