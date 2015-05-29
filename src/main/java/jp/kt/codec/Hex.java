package jp.kt.codec;

/**
 * 16進文字列とのエンコード、デコードクラス.
 * 
 * @author tatsuya.kumon
 */
public final class Hex {
	/**
	 * バイト配列を16進文字列にエンコードする.
	 * 
	 * @param bytes
	 *            バイト配列
	 * @return 16進文字列
	 */
	public static String encode(byte[] bytes) {
		// バイト配列の２倍の長さの文字列バッファを生成
		StringBuilder strbuf = new StringBuilder(bytes.length * 2);
		// バイト配列の要素数分、処理を繰り返す
		for (int index = 0; index < bytes.length; index++) {
			// バイト値を自然数に変換
			int bt = bytes[index] & 0xff;
			// バイト値が0x10以下か判定
			if (bt < 0x10) {
				// 0x10以下の場合、文字列バッファに0を追加
				strbuf.append("0");
			}
			// バイト値を16進数の文字列に変換して、文字列バッファに追加
			strbuf.append(Integer.toHexString(bt));
		}
		// 16進数の文字列を返す
		return strbuf.toString();
	}

	/**
	 * 16進文字列をバイト配列にデコードする.
	 * 
	 * @param enc
	 *            16進文字列
	 * @return バイト配列
	 */
	public static byte[] decode(String enc) {
		// 文字列長の1/2の長さのバイト配列を生成
		byte[] bytes = new byte[enc.length() / 2];
		// バイト配列の要素数分、処理を繰り返す
		for (int index = 0; index < bytes.length; index++) {
			// 16進数文字列をバイトに変換して配列に格納
			bytes[index] = (byte) Integer.parseInt(
					enc.substring(index * 2, (index + 1) * 2), 16);
		}
		// バイト配列を返す
		return bytes;
	}
}
