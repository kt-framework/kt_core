package jp.kt.codec;

/**
 * Base64文字列とのエンコード、デコードクラス.
 * 
 * @author tatsuya.kumon
 */
public final class Base64 {
	private static final char[] BASE64_CHARS = { 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
			'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
			'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '+', '/' };

	/**
	 * バイト配列をBase64文字列へエンコードする.
	 * 
	 * @param bytes
	 *            バイト配列
	 * @return Base64文字列
	 */
	public static String encode(byte[] bytes) {
		int[] p = new int[bytes.length];
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] < 0) {
				p[i] = bytes[i] + 256;
			} else {
				p[i] = bytes[i];
			}
		}
		StringBuilder out = new StringBuilder();
		int i = 0;
		int j = 0;
		int bb = 0;
		for (; i < p.length; i++) {
			bb = (bb << 8) + p[i];
			if (j == 2) {
				encode24(bb, 2, out);
				j = bb = 0;
			} else {
				j++;
			}
		}
		if (j > 0) {
			encode24(bb, j - 1, out);
		}
		return out.toString();
	}

	/**
	 * エンコード.
	 * 
	 * @param bb
	 * @param srclen
	 * @param out
	 */
	private static void encode24(int bb, int srclen, StringBuilder out) {
		bb <<= 8 * (2 - srclen);
		int base = 18;
		int x = 0;
		for (; x < srclen + 2; x++, base -= 6) {
			out.append(BASE64_CHARS[(bb >> base) & 0x3F]);
		}
		for (int i = x; i < 4; i++) {
			out.append("=");
		}
	}

	/**
	 * Base64文字列をバイト配列にデコードする.
	 * 
	 * @param enc
	 *            Base64文字列
	 * @return バイト配列
	 */
	public static byte[] decode(String enc) {
		byte[] b = enc.getBytes();
		int[] p = new int[b.length];
		int[] map = new int[128];
		byte[] out = new byte[p.length * 3 / 4];
		for (int i = 0; i < b.length; i++) {
			if (b[i] < 0) {
				p[i] = b[i] + 256;
			} else {
				p[i] = b[i];
			}
		}
		map['='] = 0;
		for (int i = 0; i < BASE64_CHARS.length; i++) {
			map[(int) BASE64_CHARS[i]] = i;
		}
		int i = 0;
		int j = 0;
		int bb = 0;
		int count = 0;
		for (; i < p.length; i++) {
			bb = (bb << 6) + map[(int) p[i]];
			if (j == 3) {
				count += decode24(bb, out, count);
				j = bb = 0;
			} else {
				j++;
			}
		}
		byte[] out2 = new byte[count];
		for (i = 0; i < count; i++) {
			out2[i] = out[i];
		}
		return out2;
	}

	/**
	 * デコード.
	 * 
	 * @param bb
	 * @param out
	 * @param count
	 * @return デコード値
	 */
	private static int decode24(int bb, byte[] out, int count) {
		int i;
		for (i = 0; bb != 0; i++, bb = (bb << 8) & 0xffffff) {
			out[count++] = (byte) ((bb & 0x00ff0000) >> 16);
		}
		return i;
	}
}
