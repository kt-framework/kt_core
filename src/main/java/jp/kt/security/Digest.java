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
	/** 暗号化アルゴリズムMD5 */
	private static final String ALGORITHM_MD5 = "MD5";

	/** 暗号化アルゴリズムSHA-1 */
	private static final String ALGORITHM_SHA1 = "SHA-1";

	/** 暗号化アルゴリズムHMAC-SHA1 */
	private static final String ALGORITHM_HMAC_SHA1 = "HmacSHA1";

	/**
	 * インスタンス化されないための内部コンストラクタ.
	 */
	private Digest() {
	}

	/**
	 * MD5での暗号化.
	 * <p>
	 * 16進文字列で返す.
	 * </p>
	 * 
	 * @param text
	 *            暗号化前のテキスト
	 * @return 暗号化されたテキスト
	 * @throws Exception
	 */
	public static String md5Hex(String text) throws Exception {
		// 変換
		byte[] digested = digest(text, ALGORITHM_MD5);
		// 16進文字列に変換
		return new String(Hex.encode(digested));
	}

	/**
	 * MD5での暗号化.
	 * <p>
	 * Base64文字列で返す.
	 * </p>
	 * 
	 * @param text
	 *            暗号化前のテキスト
	 * @return 暗号化されたテキスト
	 * @throws Exception
	 */
	public static String md5Base64(String text) throws Exception {
		// 変換
		byte[] digested = digest(text, ALGORITHM_MD5);
		// Base64文字列に変換
		return new String(Base64.encode(digested));
	}

	/**
	 * SHA-1での暗号化.
	 * <p>
	 * 16進文字列で返す.
	 * </p>
	 * 
	 * @param text
	 *            暗号化前のテキスト
	 * @return 暗号化されたテキスト
	 * @throws Exception
	 */
	public static String sha1Hex(String text) throws Exception {
		// 変換
		byte[] digested = digest(text, ALGORITHM_SHA1);
		// 16進文字列に変換
		return new String(Hex.encode(digested));
	}

	/**
	 * SHA-1での暗号化.
	 * <p>
	 * Base64文字列で返す.
	 * </p>
	 * 
	 * @param text
	 *            暗号化前のテキスト
	 * @return 暗号化されたテキスト
	 * @throws Exception
	 */
	public static String sha1Base64(String text) throws Exception {
		// 変換
		byte[] digested = digest(text, ALGORITHM_SHA1);
		// Base64文字列に変換
		return new String(Base64.encode(digested));
	}

	/**
	 * 暗号化.
	 * 
	 * @param text
	 *            暗号化前のテキスト
	 * @param algorithm
	 *            アルゴリズム
	 * @return 暗号化されたbyte配列
	 * @throws Exception
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
	 * HMAC-SHA1での暗号化.
	 * <p>
	 * Base64文字列で返す.
	 * </p>
	 * 
	 * @param text
	 *            暗号化前のテキスト
	 * @param key
	 *            秘密鍵
	 * @return 暗号化されたテキスト
	 * @throws Exception
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
