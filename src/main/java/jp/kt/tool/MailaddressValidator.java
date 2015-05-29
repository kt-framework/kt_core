package jp.kt.tool;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

import jp.kt.tool.Command.Result;

/**
 * メールアドレスチェック.
 * <p>
 * 形式チェックとドメイン存在チェック.
 * </p>
 *
 * @author tatsuya.kumon
 */
public class MailaddressValidator {
	/** メールアドレスパターン */
	private static final Pattern PATTERN_MAIL_ADDRESS = Pattern
			.compile("^[a-zA-Z0-9!$&*.=^`|~#%\\\'+\\/?_{}-]+@([a-zA-Z0-9_-]+\\.)+[a-zA-Z]{2,4}$");

	/** メールアドレス最大長 */
	private static final int MAX_LENGTH_MAIL_ADDRESS = 128;

	/**
	 * インスタンス化されないための内部コンストラクタ.
	 */
	private MailaddressValidator() {
	}

	/**
	 * メールアドレス形式チェック.
	 * <p>
	 * 形式のみのチェックなので、ドメイン存在チェックは行っていません.
	 * </p>
	 *
	 * @param mailaddress
	 *            メールアドレス
	 * @return メールアドレス形式の場合はtrue
	 */
	public static boolean isMailaddress(String mailaddress) {
		boolean isValid = false;
		// パターンマッチング、長さチェック
		if (PATTERN_MAIL_ADDRESS.matcher(mailaddress).matches()
				&& mailaddress.length() <= MAX_LENGTH_MAIL_ADDRESS) {
			isValid = true;
		}
		return isValid;
	}

	/**
	 * メールアドレスのドメインが存在していることをチェック.
	 * <p>
	 * <b>{@link InetAddress} クラスでIPが引ける</b><br>
	 * もしくは、<br>
	 * <b>nslookup -type=mx コマンドで正常に返ってきた</b>場合、<br>
	 * メールアドレスとして存在するドメインであると判定します.
	 * </p>
	 *
	 * @param mailaddress
	 *            メールアドレス
	 * @return 存在するドメインの場合はtrue
	 */
	public static boolean isValidDomain(String mailaddress) {
		/*
		 * 形式チェック
		 */
		if (!isMailaddress(mailaddress)) {
			return false;
		}
		/*
		 * ドメイン分割
		 */
		String domain = mailaddress.substring(mailaddress.indexOf("@") + 1);
		/*
		 * ハイフンチェック
		 */
		// ハイフンから始まっている場合はnslookupコマンドが対話モードになるのでNGとする
		// そもそもRFCでもハイフンで始まるドメインはNG
		if (domain.startsWith("-")) {
			return false;
		}
		/*
		 * ドメイン存在チェック
		 */
		boolean isValid = false;
		try {
			// InetAddressを使用してネットワークに存在するか調べます
			InetAddress.getByName(domain);
			isValid = true;
		} catch (UnknownHostException e) {
			// nslookupコマンドで調べます
			isValid = checkNslookup(domain);
		}
		return isValid;
	}

	/**
	 * nslookupコマンドでメールアドレスドメインの存在確認.
	 *
	 * @param domain
	 *            ドメイン
	 * @return OKの場合はtrue
	 */
	private static boolean checkNslookup(String domain) {
		// DNSサーバータイムアウト時にリトライする回数
		final int MAX_RETRY_COUNT = 3;
		// mxレコードを検索するコマンド
		final String CONSOLE_COMMAND = "nslookup -type=mx ";
		// コマンド結果　Unknown
		final String SERVER_RESPONSE_UNKOWN = "UnKnown";
		// コマンド結果　OK
		final String SERVER_RESPONSE_OK = "mail exchanger";
		boolean isValid = false;
		try {
			for (int i = 0; i < MAX_RETRY_COUNT; i++) {
				// コマンド実行
				Result result = Command.executeSynchronous(CONSOLE_COMMAND
						+ domain, MailaddressValidator.class);
				// コマンド実行結果
				String output = result.getOutput();
				// mxレコードが見つかったらOK
				if (output.indexOf(SERVER_RESPONSE_OK) >= 0) {
					isValid = true;
					break;
				}
				// DNSネームサーバーがUnknownのときはリトライ
				if (output.indexOf(SERVER_RESPONSE_UNKOWN) >= 0) {
					Thread.sleep(100);
					continue;
				}
				// ここまできたらmxレコードが見つからなかったということなので終了
				break;
			}
		} catch (Exception e) {
			// Exceptionは無視（falseで返る）
		}
		return isValid;
	}
}
