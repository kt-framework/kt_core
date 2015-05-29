package jp.kt.tool;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * メールアドレスプロバイダ判定.
 *
 * @author tatsuya.kumon
 */
public class MailaddressProvider {
	/** docomoのメールアドレスのパターンリスト */
	private static final List<String> DOCOMO;

	/** auのメールアドレスのパターンリスト */
	private static final List<String> AU;

	/** softbankのメールアドレスのパターンリスト */
	private static final List<String> SOFTBANK;

	/** willcomのメールアドレスのパターンリスト */
	private static final List<String> WILLCOM;

	static {
		/*
		 * docomo
		 */
		DOCOMO = new ArrayList<String>();
		DOCOMO.add(".+@docomo\\.ne\\.jp$");
		/*
		 * au
		 */
		AU = new ArrayList<String>();
		AU.add(".+@ezweb\\.ne\\.jp$");
		AU.add(".+@+[a-zA-Z0-9_-]+\\.ezweb\\.ne\\.jp$");
		AU.add(".+@+[a-zA-Z0-9_-]+\\.biz\\.ezweb\\.ne\\.jp$");
		/*
		 * softbank（disney携帯含む）
		 */
		SOFTBANK = new ArrayList<String>();
		SOFTBANK.add(".+@softbank\\.ne\\.jp$");
		SOFTBANK.add(".+@i\\.softbank\\.jp$");
		SOFTBANK.add(".+@disney\\.ne\\.jp$");
		// 下2つは古いドメインです
		SOFTBANK.add(".+@+[a-zA-Z0-9_-]+\\.vodafone\\.ne\\.jp$");
		SOFTBANK.add(".+@jp-[dhtckrnsq]\\.ne\\.jp$");
		/*
		 * willcom
		 */
		WILLCOM = new ArrayList<String>();
		WILLCOM.add(".+@di\\.pdx\\.ne\\.jp$");
		WILLCOM.add(".+@dj\\.pdx\\.ne\\.jp$");
		WILLCOM.add(".+@dk\\.pdx\\.ne\\.jp$");
		WILLCOM.add(".+@wm\\.pdx\\.ne\\.jp$");
		WILLCOM.add(".+@pdx\\.ne\\.jp$");
		WILLCOM.add(".+@willcom\\.com$");
	}

	/**
	 * インスタンス化されないための内部コンストラクタ.
	 */
	private MailaddressProvider() {
	}

	/**
	 * docomoのメールアドレスであるか判定.
	 *
	 * @param mailaddress
	 *            メールアドレス
	 * @return docomoのメールアドレスならtrue
	 */
	public static boolean isDocomo(String mailaddress) {
		return match(mailaddress, DOCOMO);
	}

	/**
	 * auのメールアドレスであるか判定.
	 *
	 * @param mailaddress
	 *            メールアドレス
	 * @return auのメールアドレスならtrue
	 */
	public static boolean isAu(String mailaddress) {
		return match(mailaddress, AU);
	}

	/**
	 * softbankのメールアドレスであるか判定.
	 *
	 * @param mailaddress
	 *            メールアドレス
	 * @return softbankのメールアドレスならtrue
	 */
	public static boolean isSoftbank(String mailaddress) {
		return match(mailaddress, SOFTBANK);
	}

	/**
	 * willcomのメールアドレスであるか判定.
	 *
	 * @param mailaddress
	 *            メールアドレス
	 * @return willcomのメールアドレスならtrue
	 */
	public static boolean isWillcom(String mailaddress) {
		return match(mailaddress, WILLCOM);
	}

	/**
	 * マッチング共通ロジック.
	 *
	 * @param mailaddress
	 *            メールアドレス
	 * @param regexList
	 *            正規表現リスト
	 * @return パターンリストのいずれかにマッチすればtrue
	 */
	private static boolean match(String mailaddress, List<String> regexList) {
		boolean result = false;
		for (String regex : regexList) {
			if (Pattern.matches(regex, mailaddress)) {
				result = true;
				break;
			}
		}
		return result;
	}
}
