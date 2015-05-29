package jp.kt.tool;

import java.util.regex.Pattern;

/**
 * 入力チェック用クラス.
 * 
 * @author tatsuya.kumon
 */
public class Validator {
	/** 半角英数パターン */
	private static final Pattern PATTERN_HANKAKU_EISU = Pattern
			.compile("^[0-9A-Za-z]+$");

	/** URLパターン */
	private static final String PATTERN_URL = "^(https?|ftp)(:\\/\\/[-_.!~*\\'()a-zA-Z0-9;\\/?:\\@&=+\\$,%#]+)$";

	/**
	 * インスタンス化されないための内部コンストラクタ.
	 */
	private Validator() {
	}

	/**
	 * 必須チェック.
	 * 
	 * @param text
	 *            チェック対象の値
	 * @return 空の場合true
	 */
	public static boolean isEmpty(String text) {
		boolean result = false;
		if (text == null || text.length() == 0) {
			result = true;
		}
		return result;
	}

	/**
	 * intチェック.
	 * 
	 * @param text
	 *            チェック対象の値
	 * @return intの場合true
	 */
	public static boolean isInt(String text) {
		boolean result = false;
		// 半角チェック
		if (isHanChar(text)) {
			try {
				// Integerに変換してNumberFormatExceptionが出なければOK
				new Integer(text);
				result = true;
			} catch (NumberFormatException e) {
			}
		}
		return result;
	}

	/**
	 * 半角英数チェック.
	 * 
	 * @param text
	 *            チェック対象の値
	 * @return 半角英数の場合true
	 */
	public static boolean isHankakuEisu(String text) {
		boolean result = false;
		// パターンマッチング
		if (PATTERN_HANKAKU_EISU.matcher(text).matches()) {
			result = true;
		}
		return result;
	}

	/**
	 * 文字数チェック.
	 * <p>
	 * バイト数ではないので注意.
	 * </p>
	 * 
	 * @param text
	 *            チェック対象の値
	 * @param maxLength
	 *            許容最大文字数
	 * @return 指定文字数以下ならばtrue
	 */
	public static boolean isValidLength(String text, int maxLength) {
		boolean result = false;
		if (text.length() <= maxLength) {
			// 文字数が最大文字数以下であればOK
			result = true;
		}
		return result;
	}

	/**
	 * 半角文字チェック.
	 * <p>
	 * 対象の文字数とbyte配列に格納した対象の文字数が一緒ならtrue.
	 * </p>
	 * 
	 * @param text
	 *            チェック対象の値
	 * @return 半角文字の場合true
	 */
	public static boolean isHanChar(String text) {
		byte[] bytes = text.getBytes();
		int len = text.length();
		if (len == bytes.length) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 日付形式チェック.
	 * 
	 * @param y
	 *            年
	 * @param m
	 *            月
	 * @param d
	 *            日
	 * @param h
	 *            時
	 * @param mi
	 *            分
	 * @param s
	 *            秒
	 * @return 形式が正しければtrue
	 */
	public static boolean isDate(String y, String m, String d, String h,
			String mi, String s) {
		boolean result = false;
		try {
			DateUtil.getDate(y, m, d, h, mi, s);
			// ここでExceptionが出なければOK
			result = true;
		} catch (IllegalArgumentException e) {
		}
		return result;
	}

	/**
	 * 半角数字チェック.
	 * <p>
	 * 何桁でも対応しています.
	 * </p>
	 * 
	 * @param text
	 *            チェック対象の値
	 * @return 半角数字の場合true
	 */
	public static boolean isNumber(String text) {
		boolean result = true;
		char[] cArray = text.toCharArray();
		// 1文字ずつintチェックを行って、NGがあればNGとする
		for (int i = 0; i < cArray.length; i++) {
			if (!isInt(String.valueOf(cArray[i]))) {
				result = false;
				break;
			}
		}
		return result;
	}

	/**
	 * URL形式チェック.
	 * 
	 * @param text
	 *            チェック対象の値
	 * @return URL形式であればtrue.
	 */
	public static boolean isUrl(String text) {
		Pattern pattern = Pattern.compile(PATTERN_URL);
		return pattern.matcher(text).matches();
	}

	/**
	 * サロゲート文字が含まれているか判定する.
	 * 
	 * @param text
	 *            チェック対象の値
	 * @return サロゲート文字が含まれている場合はtrue.
	 */
	public static boolean containsSurrogate(String text) {
		boolean result = false;
		for (char c : text.toCharArray()) {
			if (Character.isHighSurrogate(c) || Character.isLowSurrogate(c)) {
				result = true;
				break;
			}
		}
		return result;
	}
}
