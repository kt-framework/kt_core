package jp.kt.security;

import java.security.MessageDigest;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import jp.kt.codec.Base64;
import jp.kt.codec.Hex;
import jp.kt.prop.KtProperties;

/**
 * 文字列のハッシュ化.
 *
 * @author tatsuya.kumon
 */
public class Digest {
	/** ハッシュ化アルゴリズムMD5 */
	private static final String ALGORITHM_MD5 = "MD5";

	/** ハッシュ化アルゴリズムSHA-1 */
	private static final String ALGORITHM_SHA1 = "SHA-1";

	/** ハッシュ化アルゴリズムHMAC-SHA1 */
	private static final String ALGORITHM_HMAC_SHA1 = "HmacSHA1";

	/**
	 * インスタンス化されないための内部コンストラクタ.
	 */
	private Digest() {
	}

	/**
	 * MD5でのハッシュ化.
	 * <p>
	 * 16進文字列で返す.
	 * </p>
	 *
	 * @param text
	 *            ハッシュ化前のテキスト
	 * @return ハッシュ化されたテキスト
	 * @throws Exception
	 *             ハッシュ化処理時に例外発生した場合
	 */
	public static String md5Hex(String text) throws Exception {
		// 変換
		byte[] digested = digest(text, ALGORITHM_MD5);
		// 16進文字列に変換
		return new String(Hex.encode(digested));
	}

	/**
	 * MD5でのハッシュ化.
	 * <p>
	 * Base64文字列で返す.
	 * </p>
	 *
	 * @param text
	 *            ハッシュ化前のテキスト
	 * @return ハッシュ化されたテキスト
	 * @throws Exception
	 *             ハッシュ化処理時に例外発生した場合
	 */
	public static String md5Base64(String text) throws Exception {
		// 変換
		byte[] digested = digest(text, ALGORITHM_MD5);
		// Base64文字列に変換
		return new String(Base64.encode(digested));
	}

	/**
	 * SHA-1でのハッシュ化.
	 * <p>
	 * 16進文字列で返す.
	 * </p>
	 *
	 * @param text
	 *            ハッシュ化前のテキスト
	 * @return ハッシュ化されたテキスト
	 * @throws Exception
	 *             ハッシュ化処理時に例外発生した場合
	 */
	public static String sha1Hex(String text) throws Exception {
		// 変換
		byte[] digested = digest(text, ALGORITHM_SHA1);
		// 16進文字列に変換
		return new String(Hex.encode(digested));
	}

	/**
	 * SHA-1でのハッシュ化.
	 * <p>
	 * Base64文字列で返す.
	 * </p>
	 *
	 * @param text
	 *            ハッシュ化前のテキスト
	 * @return ハッシュ化されたテキスト
	 * @throws Exception
	 *             ハッシュ化処理時に例外発生した場合
	 */
	public static String sha1Base64(String text) throws Exception {
		// 変換
		byte[] digested = digest(text, ALGORITHM_SHA1);
		// Base64文字列に変換
		return new String(Base64.encode(digested));
	}

	/**
	 * ハッシュ化.
	 *
	 * @param text
	 *            ハッシュ化前のテキスト
	 * @param algorithm
	 *            アルゴリズム
	 * @return ハッシュ化されたbyte配列
	 * @throws Exception
	 *             ハッシュ化処理時に例外発生した場合
	 */
	private static byte[] digest(String text, String algorithm)
			throws Exception {
		// デフォルト文字コードを取得
		String charsetName = KtProperties.getInstance().getDefaultCharset();
		// ハッシュ化
		MessageDigest md = MessageDigest.getInstance(algorithm);
		byte[] digested = md.digest(text.getBytes(charsetName));
		return digested;
	}

	/**
	 * HMAC-SHA1でのハッシュ化.
	 * <p>
	 * Base64文字列で返す.
	 * </p>
	 *
	 * @param text
	 *            ハッシュ化前のテキスト
	 * @param key
	 *            秘密鍵
	 * @return ハッシュ化されたテキスト
	 * @throws Exception
	 *             ハッシュ化処理時に例外発生した場合
	 */
	public static String hmacSha1Base64(String text, byte[] key)
			throws Exception {
		SecretKeySpec sk = new SecretKeySpec(key, ALGORITHM_HMAC_SHA1);
		Mac mac = Mac.getInstance(ALGORITHM_HMAC_SHA1);
		mac.init(sk);
		byte[] digested = mac.doFinal(text.getBytes());
		// Base64文字列に変換
		return new String(Base64.encode(digested));
	}
}
