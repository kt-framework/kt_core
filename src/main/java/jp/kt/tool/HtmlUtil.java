package jp.kt.tool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTMLテキスト関連のユーティリティ.
 *
 * @author tatsuya.kumon
 */
public class HtmlUtil {
	/** エスケープ文字Map */
	private static final Map<Character, String> ESCAPE_MAP;

	/** 文字表記の逆エスケープMap */
	private static final Map<String, String> UNESCAPE_MAP;

	static {
		/*
		 * エスケープ文字Map
		 */
		ESCAPE_MAP = new HashMap<Character, String>();
		ESCAPE_MAP.put('&', "&amp;");
		ESCAPE_MAP.put('<', "&lt;");
		ESCAPE_MAP.put('>', "&gt;");
		ESCAPE_MAP.put('"', "&#034;");
		ESCAPE_MAP.put('\'', "&#039;");
		/*
		 * 文字表記の逆エスケープMap
		 */
		UNESCAPE_MAP = new HashMap<String, String>();
		UNESCAPE_MAP.put("&quot;", "\"");
		UNESCAPE_MAP.put("&amp;", "&");
		UNESCAPE_MAP.put("&lt;", "<");
		UNESCAPE_MAP.put("&gt;", ">");
		UNESCAPE_MAP.put("&nbsp;", " ");
		UNESCAPE_MAP.put("&sect;", "§");
		UNESCAPE_MAP.put("&uml;", "¨");
		UNESCAPE_MAP.put("&deg;", "°");
		UNESCAPE_MAP.put("&plusmn;", "±");
		UNESCAPE_MAP.put("&acute;", "´");
		UNESCAPE_MAP.put("&para;", "¶");
		UNESCAPE_MAP.put("&times;", "×");
		UNESCAPE_MAP.put("&divide;", "÷");
		UNESCAPE_MAP.put("&Alpha;", "Α");
		UNESCAPE_MAP.put("&Beta;", "Β");
		UNESCAPE_MAP.put("&Gamma;", "Γ");
		UNESCAPE_MAP.put("&Delta;", "Δ");
		UNESCAPE_MAP.put("&Epsilon;", "Ε");
		UNESCAPE_MAP.put("&Zeta;", "Ζ");
		UNESCAPE_MAP.put("&Eta;", "Η");
		UNESCAPE_MAP.put("&Theta;", "Θ");
		UNESCAPE_MAP.put("&Iota;", "Ι");
		UNESCAPE_MAP.put("&Kappa;", "Κ");
		UNESCAPE_MAP.put("&Lambda;", "Λ");
		UNESCAPE_MAP.put("&Mu;", "Μ");
		UNESCAPE_MAP.put("&Nu;", "Ν");
		UNESCAPE_MAP.put("&#xi;", "Ξ");
		UNESCAPE_MAP.put("&Omicron;", "Ο");
		UNESCAPE_MAP.put("&Pi;", "Π");
		UNESCAPE_MAP.put("&Rho;", "Ρ");
		UNESCAPE_MAP.put("&Sigma;", "Σ");
		UNESCAPE_MAP.put("&Tau;", "Τ");
		UNESCAPE_MAP.put("&Upsilon;", "Υ");
		UNESCAPE_MAP.put("&Phi;", "Φ");
		UNESCAPE_MAP.put("&Chi;", "Χ");
		UNESCAPE_MAP.put("&Psi;", "Ψ");
		UNESCAPE_MAP.put("&Omega;", "Ω");
		UNESCAPE_MAP.put("&alpha;", "α");
		UNESCAPE_MAP.put("&beta;", "β");
		UNESCAPE_MAP.put("&gamma;", "γ");
		UNESCAPE_MAP.put("&delta;", "δ");
		UNESCAPE_MAP.put("&epsilon;", "ε");
		UNESCAPE_MAP.put("&zeta;", "ζ");
		UNESCAPE_MAP.put("&eta;", "η");
		UNESCAPE_MAP.put("&theta;", "θ");
		UNESCAPE_MAP.put("&iota;", "ι");
		UNESCAPE_MAP.put("&kappa;", "κ");
		UNESCAPE_MAP.put("&lambda;", "λ");
		UNESCAPE_MAP.put("&mu;", "μ");
		UNESCAPE_MAP.put("&nu;", "ν");
		UNESCAPE_MAP.put("&#xi;", "ξ");
		UNESCAPE_MAP.put("&omicron;", "ο");
		UNESCAPE_MAP.put("&pi;", "π");
		UNESCAPE_MAP.put("&rho;", "ρ");
		UNESCAPE_MAP.put("&sigma;", "σ");
		UNESCAPE_MAP.put("&tau;", "τ");
		UNESCAPE_MAP.put("&upsilon;", "υ");
		UNESCAPE_MAP.put("&phi;", "φ");
		UNESCAPE_MAP.put("&chi;", "χ");
		UNESCAPE_MAP.put("&psi;", "ψ");
		UNESCAPE_MAP.put("&omega;", "ω");
		UNESCAPE_MAP.put("&hellip;", "…");
		UNESCAPE_MAP.put("&prime;", "′");
		UNESCAPE_MAP.put("&Prime;", "″");
		UNESCAPE_MAP.put("&larr;", "←");
		UNESCAPE_MAP.put("&uarr;", "↑");
		UNESCAPE_MAP.put("&rarr;", "→");
		UNESCAPE_MAP.put("&darr;", "↓");
		UNESCAPE_MAP.put("&rArr;", "⇒");
		UNESCAPE_MAP.put("&hArr;", "⇔");
		UNESCAPE_MAP.put("&forall;", "∀");
		UNESCAPE_MAP.put("&part;", "∂");
		UNESCAPE_MAP.put("&exist;", "∃");
		UNESCAPE_MAP.put("&nabla;", "∇");
		UNESCAPE_MAP.put("&isin;", "∈");
		UNESCAPE_MAP.put("&ni;", "∋");
		UNESCAPE_MAP.put("&sum;", "∑");
		UNESCAPE_MAP.put("&radic;", "√");
		UNESCAPE_MAP.put("&prop;", "∝");
		UNESCAPE_MAP.put("&infin;", "∞");
		UNESCAPE_MAP.put("&ang;", "∠");
		UNESCAPE_MAP.put("&and;", "∧");
		UNESCAPE_MAP.put("&or;", "∨");
		UNESCAPE_MAP.put("&cap;", "∩");
		UNESCAPE_MAP.put("&cup;", "∪");
		UNESCAPE_MAP.put("&int;", "∫");
		UNESCAPE_MAP.put("&there4;", "∴");
		UNESCAPE_MAP.put("&ne;", "≠");
		UNESCAPE_MAP.put("&equiv;", "≡");
		UNESCAPE_MAP.put("&sub;", "⊂");
		UNESCAPE_MAP.put("&sup;", "⊃");
		UNESCAPE_MAP.put("&sube;", "⊆");
		UNESCAPE_MAP.put("&supe;", "⊇");
		UNESCAPE_MAP.put("&perp;", "⊥");
		UNESCAPE_MAP.put("&lsquo;", "‘");
		UNESCAPE_MAP.put("&rsquo;", "’");
		UNESCAPE_MAP.put("&ldquo;", "“");
		UNESCAPE_MAP.put("&rdquo;", "”");
		UNESCAPE_MAP.put("&dagger;", "†");
		UNESCAPE_MAP.put("&Dagger;", "‡");
		UNESCAPE_MAP.put("&permil;", "‰");
	}

	/**
	 * インスタンス化されないようにするための内部コンストラクタ.
	 */
	private HtmlUtil() {
	}

	/**
	 * HTML出力用にエスケープする.
	 *
	 * @param text
	 *            対象のテキスト
	 * @return HTMLエスケープされたテキスト
	 */
	public static String escape(String text) {
		if (text == null) {
			return null;
		}
		StringBuffer escapedBuffer = new StringBuffer();
		char[] charArray = text.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			if (ESCAPE_MAP.containsKey(charArray[i])) {
				// 変換対象文字ならば変換実行して連結
				escapedBuffer.append(ESCAPE_MAP.get(charArray[i]));
			} else {
				// 変換対象文字でなければそのまま連結
				escapedBuffer.append(charArray[i]);
			}
		}
		return escapedBuffer.toString();
	}

	/**
	 * HTMLのエスケープされている箇所を逆エスケープする.
	 *
	 * @param html
	 *            エスケープされているHTML
	 * @return 逆エスケープしたHTML
	 */
	public static String unescape(String html) {
		// 文字表記の逆エスケープ
		String result = unescape1(html);
		// 10進表記の逆エスケープ
		result = unescape2(result);
		return result;
	}

	/**
	 * 文字表記の逆エスケープ.
	 *
	 * @param html
	 *            エスケープされているHTML
	 * @return 逆エスケープしたHTML
	 */
	private static String unescape1(String html) {
		String result = html;
		Iterator<String> keyIt = UNESCAPE_MAP.keySet().iterator();
		while (keyIt.hasNext()) {
			String key = keyIt.next();
			if (html.indexOf(key) >= 0) {
				result = StringUtil.replaceAll(result, key, UNESCAPE_MAP
						.get(key));
			}
		}
		return result;
	}

	/**
	 * 10進表記の逆エスケープ.
	 *
	 * @param html
	 *            エスケープされているHTML
	 * @return 逆エスケープしたHTML
	 */
	private static String unescape2(String html) {
		Pattern pattern = Pattern.compile("&#([0-9]*);");
		Matcher matcher = pattern.matcher(html);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			// 置換文字
			String replaceStr = String.valueOf((char) Integer.parseInt(matcher
					.group(1)));
			// 置換文字が $ である場合は、appendReplacementメソッドで
			// Exception が発生しないようにエスケープする
			if (replaceStr.equals("$")) {
				replaceStr = "\\$";
			}
			matcher.appendReplacement(sb, replaceStr);
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	/**
	 * タグを除去したテキストに変換する.
	 *
	 * @param html
	 *            対象のHTMLテキスト
	 * @return HTMLエスケープされたテキスト
	 */
	public static String removeHtmlTag(String html) {
		Matcher matcher = Pattern.compile("<[^<>]+>").matcher(html);
		return matcher.replaceAll("");
	}

	/**
	 * 改行コードを&lt;br&gt;タグに変換する.
	 *
	 * @param text
	 *            変換対象文字列
	 * @return &lt;br&gt;タグに変換した文字列を返す.
	 */
	public static String replaceLineToBrtag(String text) {
		String brtag = "<br>";
		String newStr = text;
		newStr = StringUtil.replaceAll(newStr, "\r\n", brtag);
		newStr = StringUtil.replaceAll(newStr, "\n", brtag);
		newStr = StringUtil.replaceAll(newStr, "\r", brtag);
		return newStr;
	}

	/**
	 * &lt;br&gt;タグを改行コード（CR+LF）に変換する.
	 *
	 * @param text
	 *            変換対象文字列
	 * @return &lt;br&gt;タグに変換した文字列.
	 */
	public static String replaceBrtagToLineCrlf(String text) {
		return replaceBrtagToLine(text, "\r\n");
	}

	/**
	 * &lt;br&gt;タグを改行コード（CR）に変換する.
	 *
	 * @param text
	 *            変換対象文字列
	 * @return &lt;br&gt;タグに変換した文字列.
	 */
	public static String replaceBrtagToLineCr(String text) {
		return replaceBrtagToLine(text, "\r");
	}

	/**
	 * &lt;br&gt;タグを改行コード（LF）に変換する.
	 *
	 * @param text
	 *            変換対象文字列
	 * @return &lt;br&gt;タグに変換した文字列.
	 */
	public static String replaceBrtagToLineLf(String text) {
		return replaceBrtagToLine(text, "\n");
	}

	/**
	 * &lt;br&gt;タグを改行コードに変換する.
	 *
	 * @param text
	 *            変換対象文字列
	 * @param returnCode
	 *            改行コード
	 * @return &lt;br&gt;タグを改行コードに変換した文字列.
	 */
	private static String replaceBrtagToLine(String text, String returnCode) {
		Matcher matcher = Pattern.compile("<br\\s*/*>",
				Pattern.CASE_INSENSITIVE).matcher(text);
		return matcher.replaceAll(returnCode);
	}
}
