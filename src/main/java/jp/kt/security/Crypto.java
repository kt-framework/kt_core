package jp.kt.security;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import jp.kt.codec.Base64;
import jp.kt.exception.KtException;

/**
 * 文字列の暗号化・複合化.
 * <p>
 * AESアルゴリズムで暗号化します.<br>
 * 鍵長は128ビットのみ指定可能です.
 * </p>
 *
 * @author tatsuya.kumon
 */
public class Crypto {
	/** 暗号化アルゴリズム */
	private static final String ALGORITHM = "AES";

	/** キーのビット長 */
	private static final int KEY_BIT_LENGTH = 128;

	/**
	 * インスタンス化されないための内部コンストラクタ.
	 */
	private Crypto() {
	}

	/**
	 * 暗号化.
	 * <p>
	 * 暗号化文字列はBase64文字列となります.
	 * </p>
	 *
	 * @param src
	 *            元のバイトデータ
	 * @param key
	 *            キー
	 * @return 暗号化されたBase64文字列
	 * @throws Exception
	 *             暗号化処理時に例外発生した場合
	 */
	public static String encodeBase64(byte[] src, byte[] key) throws Exception {
		// 暗号化
		byte[] encrypted = encode(src, key);
		// Base64文字列に変換
		return new String(Base64.encode(encrypted));
	}

	/**
	 * 暗号化.
	 *
	 * @param src
	 *            元のバイトデータ
	 * @param key
	 *            キー
	 * @return 暗号化されたバイト配列
	 * @throws Exception
	 *             暗号化処理時に例外発生した場合
	 */
	public static byte[] encode(byte[] src, byte[] key) throws Exception {
		// キーの生成
		Key skey = createKey(key);
		// Cipher生成
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, skey);
		// 暗号化
		byte[] encrypted = cipher.doFinal(src);
		return encrypted;
	}

	/**
	 * 複合化.
	 *
	 * @param enc
	 *            暗号化されたBase64文字列.
	 * @param key
	 *            キー
	 * @return 複合化されたバイト配列
	 * @throws Exception
	 *             複合化処理時に例外発生した場合
	 */
	public static byte[] decodeBase64(String enc, byte[] key) throws Exception {
		// Base64文字列をbyte配列に変換
		byte[] encByte = Base64.decode(enc);
		// 複合化
		return decode(encByte, key);
	}

	/**
	 * 複合化.
	 *
	 * @param enc
	 *            暗号化されたバイト配列.
	 * @param key
	 *            キー
	 * @return 複合化されたバイト配列
	 * @throws Exception
	 *             複合化処理時に例外発生した場合
	 */
	public static byte[] decode(byte[] enc, byte[] key) throws Exception {
		// キーの生成
		Key skey = createKey(key);
		// Cipher生成
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, skey);
		// 複合化
		byte[] decByte = cipher.doFinal(enc);
		return decByte;
	}

	/**
	 * キー生成.
	 *
	 * @param key
	 *            キー
	 * @return Key
	 */
	private static Key createKey(byte[] key) {
		// 128ビットでない場合は、0埋めもしくはカットして合わせる
		int keyBitLength = key.length * 8;
		if (keyBitLength != KEY_BIT_LENGTH) {
			throw new KtException("A063", "暗号化キーの鍵長は" + KEY_BIT_LENGTH
					+ "ビットのみ指定可能です[" + keyBitLength + "bit]");
		}
		// キー生成
		Key skey = new SecretKeySpec(key, ALGORITHM);
		return skey;
	}
}
