package jp.kt.mail;

/**
 * メール用文字列ユーティリティ.
 *
 * @author tatsuya.kumon
 */
class StringUtilForMail {
	/** ～ */
	private static final char FULLWIDTH_TILDE = '\uff5e';

	private static final char WAVE_DASH = '\u301c';

	/** ∥ */
	private static final char PARALLEL_TO = '\u2225';

	private static final char DOUBLE_VERTICAL_LINE = '\u2016';

	/** － */
	private static final char FULLWIDTH_HYPHEN_MINUS = '\uff0d';

	private static final char MINUS_SIGN = '\u2212';

	/** ￠ */
	private static final char FULLWIDTH_CENT_SIGN = '\uffe0';

	private static final char CENT_SIGN = '\u00a2';

	/** ￡ */
	private static final char FULLWIDTH_POUND_SIGN = '\uffe1';

	private static final char POUND_SIGN = '\u00a3';

	/** ￢ */
	private static final char FULLWIDTH_NOT_SIGN = '\uffe2';

	private static final char NOT_SIGN = '\u00ac';

	/** ― */
	private static final char FULLWIDTH_HORIZONTAL_BAR = '\u2015';

	private static final char HORIZONTAL_BAR = '\u2500';

	/** はしごたか */
	private static final char HASHIGO_TAKA = '\u9ad9';

	private static final char TAKA = '\u9ad8';

	/** 崎 */
	private static final char SAKI_ANOTHER_STYLE = '\ufa11';

	private static final char SAKI = '\u5d0e';

	/** 隆 */
	private static final char RYU_ANOTHER_STYLE = '\uf9dc';

	private static final char RYU = '\u9686';

	/** 薫 */
	private static final char KUN_ANOTHER_STYLE = '\u85b0';

	private static final char KUN = '\u85ab';

	/** 徳 */
	private static final char TOKU_ANOTHER_STYLE = '\u5fb7';

	private static final char TOKU = '\u5fb3';

	/** 丸数字1～20 */
	private static final char ENCLOSED_ONE = '\u2460';

	private static final char ENCLOSED_TWO = '\u2461';

	private static final char ENCLOSED_THREE = '\u2462';

	private static final char ENCLOSED_FOUR = '\u2463';

	private static final char ENCLOSED_FIVE = '\u2464';

	private static final char ENCLOSED_SIX = '\u2465';

	private static final char ENCLOSED_SEVEN = '\u2466';

	private static final char ENCLOSED_EIGHT = '\u2467';

	private static final char ENCLOSED_NINE = '\u2468';

	private static final char ENCLOSED_TEN = '\u2469';

	private static final char ENCLOSED_ELEVEN = '\u246a';

	private static final char ENCLOSED_TWELVE = '\u246b';

	private static final char ENCLOSED_THIRTEEN = '\u246c';

	private static final char ENCLOSED_FOURTEEN = '\u246d';

	private static final char ENCLOSED_FIFTEEN = '\u246e';

	private static final char ENCLOSED_SIXTEEN = '\u246f';

	private static final char ENCLOSED_SEVENTEEN = '\u2470';

	private static final char ENCLOSED_EIGHTEEN = '\u2471';

	private static final char ENCLOSED_NINETEEN = '\u2472';

	private static final char ENCLOSED_TWENTY = '\u2473';

	/** 大文字ローマ数字I～X */
	private static final char ROMAN_NUMERAL_ONE = '\u2160';

	private static final char ROMAN_NUMERAL_TWO = '\u2161';

	private static final char ROMAN_NUMERAL_THREE = '\u2162';

	private static final char ROMAN_NUMERAL_FOUR = '\u2163';

	private static final char ROMAN_NUMERAL_FIVE = '\u2164';

	private static final char ROMAN_NUMERAL_SIX = '\u2165';

	private static final char ROMAN_NUMERAL_SEVEN = '\u2166';

	private static final char ROMAN_NUMERAL_EIGHT = '\u2167';

	private static final char ROMAN_NUMERAL_NINE = '\u2168';

	private static final char ROMAN_NUMERAL_TEN = '\u2169';

	/** 小文字ローマ数字i-x */
	private static final char SMALL_ROMAN_NUMERAL_ONE = '\u2170';

	private static final char SMALL_ROMAN_NUMERAL_TWO = '\u2171';

	private static final char SMALL_ROMAN_NUMERAL_THREE = '\u2172';

	private static final char SMALL_ROMAN_NUMERAL_FOUR = '\u2173';

	private static final char SMALL_ROMAN_NUMERAL_FIVE = '\u2174';

	private static final char SMALL_ROMAN_NUMERAL_SIX = '\u2175';

	private static final char SMALL_ROMAN_NUMERAL_SEVEN = '\u2176';

	private static final char SMALL_ROMAN_NUMERAL_EIGHT = '\u2177';

	private static final char SMALL_ROMAN_NUMERAL_NINE = '\u2178';

	private static final char SMALL_ROMAN_NUMERAL_TEN = '\u2179';

	/** 置換用半角アルファベット */
	private static final char ARPHABET_PARENTHESIS_START = '(';

	private static final char ARPHABET_PARENTHESIS_END = ')';

	/** (株) */
	private static final char PARENTHESIZED_IDEOGRAPH_STOCK = '\u3231';

	private static final String KAKKO_KABU = getBracketString(String
			.valueOf('\u682a'));

	/** TEL */
	private static final char TELEPHONE_SIGN = '\u2121';

	private static final String TEL = "\u0054\u0045\u004c";

	/**
	 * インスタンスが作られないようにするための内部コンストラクタ
	 */
	private StringUtilForMail() {
	}

	/**
	 * <p>
	 * 文字化けする文字のコードを置き換える。<br>
	 * 機種依存文字にも対応している。
	 * </p>
	 * <p>
	 * 機種依存文字は具体的には以下の通り。
	 * </p>
	 * <ul>
	 * <li>丸囲み数字</li>
	 * <li>ローマ数字</li>
	 * <li>(株)</li>
	 * <li>TEL</li>
	 * <li>いくつかの異字体</li>
	 * </ul>
	 * <p>
	 * "(株)"に変換する必要があるため、charからStringを返すメソッドを使用している。
	 * </p>
	 *
	 * @param str
	 *            変換対照の文字列
	 * @return 変換後文字列
	 */
	public static String convertMapping(String str) {
		StringBuffer rtn = new StringBuffer();
		char c = '\u0000';
		for (int i = 0; str.length() > i; i++) {
			c = str.charAt(i);
			rtn.append(convertSpecialCharToString(c));
		}
		return rtn.toString();
	}

	/**
	 * 文字化けする文字のコードを置き換える
	 *
	 * @param c
	 *            変換対象文字
	 * @param reverse
	 *            変換方向
	 * @return 変換後文字
	 */
	private static char convertMapping(char c, boolean reverse) {
		char rtn = c;
		if (!reverse) {
			switch (c) {
			case FULLWIDTH_TILDE:
				rtn = WAVE_DASH;
				break;
			case PARALLEL_TO:
				rtn = DOUBLE_VERTICAL_LINE;
				break;
			case FULLWIDTH_HYPHEN_MINUS:
				rtn = MINUS_SIGN;
				break;
			case FULLWIDTH_CENT_SIGN:
				rtn = CENT_SIGN;
				break;
			case FULLWIDTH_POUND_SIGN:
				rtn = POUND_SIGN;
				break;
			case FULLWIDTH_NOT_SIGN:
				rtn = NOT_SIGN;
				break;
			case HASHIGO_TAKA:
				rtn = TAKA;
				break;
			case SAKI_ANOTHER_STYLE:
				rtn = SAKI;
				break;
			case RYU_ANOTHER_STYLE:
				rtn = RYU;
				break;
			case KUN_ANOTHER_STYLE:
				rtn = KUN;
				break;
			case TOKU_ANOTHER_STYLE:
				rtn = TOKU;
				break;
			case FULLWIDTH_HORIZONTAL_BAR:
				// ―
				rtn = HORIZONTAL_BAR;
				break;
			}
		} else {
			switch (c) {
			case WAVE_DASH:
				rtn = FULLWIDTH_TILDE;
				break;
			case DOUBLE_VERTICAL_LINE:
				rtn = PARALLEL_TO;
				break;
			case MINUS_SIGN:
				rtn = FULLWIDTH_HYPHEN_MINUS;
				break;
			case CENT_SIGN:
				rtn = FULLWIDTH_CENT_SIGN;
				break;
			case POUND_SIGN:
				rtn = FULLWIDTH_POUND_SIGN;
				break;
			case NOT_SIGN:
				rtn = FULLWIDTH_NOT_SIGN;
				break;
			}
		}
		return rtn;
	}

	/**
	 * 文字化けする文字を文字化けしない文字列に置き換える。
	 * <p>
	 * 置換対象は以下の機種依存文字。
	 * </p>
	 * <ul>
	 * <li>丸囲み数字</li>
	 * <li>ローマ数字</li>
	 * <li>(株)</li>
	 * <li>TEL</li>
	 * </ul>
	 * <p>
	 * "(株)"に変換している。 また、このメソッド中でconvertMapping(char c, false)も実行している。
	 * </p>
	 *
	 * @param c
	 *            変換対象文字
	 * @param reverse
	 *            変換方向
	 * @return 変換後文字列
	 */
	private static String convertSpecialCharToString(char c) {
		String ret;
		if (c >= StringUtilForMail.ROMAN_NUMERAL_ONE
				&& c <= StringUtilForMail.SMALL_ROMAN_NUMERAL_TEN) {
			// ローマ数字の変換
			ret = convertRomanNumeral(c);
		} else if (c >= StringUtilForMail.ENCLOSED_ONE
				&& c <= StringUtilForMail.ENCLOSED_TWENTY) {
			// 丸囲み数字の変換
			ret = convertEnclosedNumeral(c);
		} else {
			switch (c) {
			case StringUtilForMail.PARENTHESIZED_IDEOGRAPH_STOCK:
				// (株)
				ret = StringUtilForMail.KAKKO_KABU;
				break;
			case StringUtilForMail.TELEPHONE_SIGN:
				// TEL
				ret = StringUtilForMail.TEL;
				break;
			default:
				// ～、－などの変換
				ret = String
						.valueOf(StringUtilForMail.convertMapping(c, false));
			}
		}
		return ret;
	}

	/**
	 * ローマ数字をアルファベット形式の表現に変換します。
	 *
	 * @param c
	 * @return
	 */
	private static String convertRomanNumeral(char c) {
		String ret;
		switch (c) {
		// ローマ数字大文字
		case StringUtilForMail.ROMAN_NUMERAL_ONE:
			ret = getBracketString("I");
			break;
		case StringUtilForMail.ROMAN_NUMERAL_TWO:
			ret = getBracketString("II");
			break;
		case StringUtilForMail.ROMAN_NUMERAL_THREE:
			ret = getBracketString("III");
			break;
		case StringUtilForMail.ROMAN_NUMERAL_FOUR:
			ret = getBracketString("IV");
			break;
		case StringUtilForMail.ROMAN_NUMERAL_FIVE:
			ret = getBracketString("V");
			break;
		case StringUtilForMail.ROMAN_NUMERAL_SIX:
			ret = getBracketString("VI");
			break;
		case StringUtilForMail.ROMAN_NUMERAL_SEVEN:
			ret = getBracketString("VII");
			break;
		case StringUtilForMail.ROMAN_NUMERAL_EIGHT:
			ret = getBracketString("VIII");
			break;
		case StringUtilForMail.ROMAN_NUMERAL_NINE:
			ret = getBracketString("IX");
			break;
		case StringUtilForMail.ROMAN_NUMERAL_TEN:
			ret = getBracketString("X");
			break;
		// ローマ数字小文字
		case StringUtilForMail.SMALL_ROMAN_NUMERAL_ONE:
			ret = getBracketString("i");
			break;
		case StringUtilForMail.SMALL_ROMAN_NUMERAL_TWO:
			ret = getBracketString("ii");
			break;
		case StringUtilForMail.SMALL_ROMAN_NUMERAL_THREE:
			ret = getBracketString("iii");
			break;
		case StringUtilForMail.SMALL_ROMAN_NUMERAL_FOUR:
			ret = getBracketString("iv");
			break;
		case StringUtilForMail.SMALL_ROMAN_NUMERAL_FIVE:
			ret = getBracketString("v");
			break;
		case StringUtilForMail.SMALL_ROMAN_NUMERAL_SIX:
			ret = getBracketString("vi");
			break;
		case StringUtilForMail.SMALL_ROMAN_NUMERAL_SEVEN:
			ret = getBracketString("vii");
			break;
		case StringUtilForMail.SMALL_ROMAN_NUMERAL_EIGHT:
			ret = getBracketString("viii");
			break;
		case StringUtilForMail.SMALL_ROMAN_NUMERAL_NINE:
			ret = getBracketString("ix");
			break;
		case StringUtilForMail.SMALL_ROMAN_NUMERAL_TEN:
			ret = getBracketString("x");
			break;
		default:
			ret = String.valueOf(c);
		}
		return ret;
	}

	/**
	 * 丸囲み数字をカッコつき数字に変換します。
	 *
	 * @param c
	 * @return
	 */
	private static String convertEnclosedNumeral(char c) {
		String ret;
		switch (c) {
		case StringUtilForMail.ENCLOSED_ONE:
			ret = getBracketString("1");
			break;
		case StringUtilForMail.ENCLOSED_TWO:
			ret = getBracketString("2");
			break;
		case StringUtilForMail.ENCLOSED_THREE:
			ret = getBracketString("3");
			break;
		case StringUtilForMail.ENCLOSED_FOUR:
			ret = getBracketString("4");
			break;
		case StringUtilForMail.ENCLOSED_FIVE:
			ret = getBracketString("5");
			break;
		case StringUtilForMail.ENCLOSED_SIX:
			ret = getBracketString("6");
			break;
		case StringUtilForMail.ENCLOSED_SEVEN:
			ret = getBracketString("7");
			break;
		case StringUtilForMail.ENCLOSED_EIGHT:
			ret = getBracketString("8");
			break;
		case StringUtilForMail.ENCLOSED_NINE:
			ret = getBracketString("9");
			break;
		case StringUtilForMail.ENCLOSED_TEN:
			ret = getBracketString("10");
			break;
		case StringUtilForMail.ENCLOSED_ELEVEN:
			ret = getBracketString("11");
			break;
		case StringUtilForMail.ENCLOSED_TWELVE:
			ret = getBracketString("12");
			break;
		case StringUtilForMail.ENCLOSED_THIRTEEN:
			ret = getBracketString("13");
			break;
		case StringUtilForMail.ENCLOSED_FOURTEEN:
			ret = getBracketString("14");
			break;
		case StringUtilForMail.ENCLOSED_FIFTEEN:
			ret = getBracketString("15");
			break;
		case StringUtilForMail.ENCLOSED_SIXTEEN:
			ret = getBracketString("16");
			break;
		case StringUtilForMail.ENCLOSED_SEVENTEEN:
			ret = getBracketString("17");
			break;
		case StringUtilForMail.ENCLOSED_EIGHTEEN:
			ret = getBracketString("18");
			break;
		case StringUtilForMail.ENCLOSED_NINETEEN:
			ret = getBracketString("19");
			break;
		case StringUtilForMail.ENCLOSED_TWENTY:
			ret = getBracketString("20");
			break;
		default:
			ret = String.valueOf(c);
		}
		return ret;
	}

	/**
	 * 与えられた文字列をカッコ()で囲んだ文字列を返します。
	 *
	 * @param str
	 * @return "("+str+")"
	 */
	private static String getBracketString(String str) {
		return String.valueOf(ARPHABET_PARENTHESIS_START) + str
				+ String.valueOf(ARPHABET_PARENTHESIS_END);
	}
}
