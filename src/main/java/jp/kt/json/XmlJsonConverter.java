package jp.kt.json;

import jp.kt.tool.HtmlUtil;
import jp.kt.tool.Validator;
import jp.kt.xml.XmlBaseElement;
import jp.kt.xml.XmlItem;
import jp.kt.xml.XmlRoot;

import org.json.JSONObject;

/**
 * XMLからJSONへの変換処理.
 * 
 * @author tatsuya.kumon
 */
public class XmlJsonConverter {
	/** 変換元データとなるXmlRootオブジェクト */
	private XmlRoot root;

	/** HTMLエスケープするかどうかのフラグ */
	private boolean isHtmlEscape;

	/** JavaScript用の変数名 */
	private String jsVarName;

	/** 変数定義文にするフラグ（前にvarを付加する） */
	private boolean isDefinition;

	/**
	 * JSON本来の形式で出力する変換メソッド.
	 * <p>
	 * 下記注意点があります.<br>
	 * <ul>
	 * <li>ルートタグ名は消失します.</li>
	 * <li>タグ属性は全て消失します.</li>
	 * <li>全体を1行のテキストとして出力されます.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param root
	 *            変換元のXMLデータ
	 * @param isHtmlEscape
	 *            valueをHTMLエスケープする場合はtrueを指定する
	 * @throws Exception
	 */
	public static String convert(XmlRoot root, boolean isHtmlEscape)
			throws Exception {
		return convert(root, isHtmlEscape, null);
	}

	/**
	 * JavaScript用で使用する形式で出力する変換メソッド.
	 * <p>
	 * Javascriptでそのまま使えるように、<br>
	 * <br>
	 * 　　（JavaScript変数名） = [ （JSONテキスト） ]<br>
	 * <br>
	 * という形式となります.<br>
	 * 先頭行のJavaScript変数名は引数で指定します.<br>
	 * <br>
	 * なお、下記注意点があります.<br>
	 * <ul>
	 * <li>ルートタグ名は消失します.</li>
	 * <li>タグ属性は全て消失します.</li>
	 * <li>全体を1行のテキストとして出力されます.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param root
	 *            変換元のXMLデータ
	 * @param isHtmlEscape
	 *            valueをHTMLエスケープする場合はtrueを指定する
	 * @param jsVarName
	 *            JavaScript用の変数名
	 * @throws Exception
	 */
	public static String convert(XmlRoot root, boolean isHtmlEscape,
			String jsVarName) throws Exception {
		return convert(root, isHtmlEscape, jsVarName, false);
	}

	/**
	 * JavaScript用で使用する形式で出力する変換メソッド.
	 * <p>
	 * Javascriptでそのまま使えるように、<br>
	 * <br>
	 * 　・変数定義文（isDefinition=true）の場合<br>
	 * 　　var （JavaScript変数名） = [ （JSONテキスト） ]<br>
	 * 　・変数定義文にしない（isDefinition=false）場合<br>
	 * 　　（JavaScript変数名） = [ （JSONテキスト） ]<br>
	 * <br>
	 * という形式となります.<br>
	 * 先頭行のJavaScript変数名は引数で指定します.<br>
	 * <br>
	 * なお、下記注意点があります.<br>
	 * <ul>
	 * <li>ルートタグ名は消失します.</li>
	 * <li>タグ属性は全て消失します.</li>
	 * <li>全体を1行のテキストとして出力されます.</li>
	 * </ul>
	 * </p>
	 * 
	 * @param root
	 *            変換元のXMLデータ
	 * @param isHtmlEscape
	 *            valueをHTMLエスケープする場合はtrueを指定する
	 * @param jsVarName
	 *            JavaScript用の変数名
	 * @param isDefinition
	 *            変数定義文にするかどうかのフラグ
	 * @throws Exception
	 */
	public static String convert(XmlRoot root, boolean isHtmlEscape,
			String jsVarName, boolean isDefinition) throws Exception {
		return new XmlJsonConverter(root, isHtmlEscape, jsVarName, isDefinition)
				.convert();
	}

	/**
	 * コンストラクタ.
	 * <p>
	 * Javascriptでそのまま使えるように、<br>
	 * <br>
	 * 　　var （JavaScript変数名） = [ （JSONテキスト） ]<br>
	 * <br>
	 * という形式となります.<br>
	 * 先頭行のJavaScript変数名は引数で指定します.
	 * </p>
	 * 
	 * @param root
	 *            変換元のXMLデータ
	 * @param isHtmlEscape
	 *            valueをHTMLエスケープする場合はtrueを指定する
	 * @param jsVarName
	 *            JavaScript用の変数名<br>
	 *            nullもしくは空文字の場合はJavaScript用の形式ではなく、本来のJSON形式で出力します.
	 * @param isDefinition
	 *            変数定義文にするかどうかのフラグ
	 */
	private XmlJsonConverter(XmlRoot root, boolean isHtmlEscape,
			String jsVarName, boolean isDefinition) {
		this.root = root;
		this.isHtmlEscape = isHtmlEscape;
		this.jsVarName = jsVarName;
		this.isDefinition = isDefinition;
	}

	/**
	 * XMLデータをJSONに変換します.
	 * 
	 * @return JavaScript用JSONテキスト
	 * @throws Exception
	 */
	private String convert() throws Exception {
		JSONObject jsonRoot = new JSONObject();
		// rootのJSONObjectに再帰的にオブジェクトや値をセットする
		setJsonObject(jsonRoot, this.root);
		// JSONテキストを生成
		StringBuilder text = new StringBuilder();
		if (!Validator.isEmpty(this.jsVarName)) {
			if (this.isDefinition) {
				text.append("var ");
			}
			text.append(this.jsVarName);
			text.append(" = [ ");
		}
		text.append(jsonRoot.toString());
		if (!Validator.isEmpty(this.jsVarName)) {
			text.append(" ];");
		}
		return text.toString();
	}

	/**
	 * JSONObjectにXMLデータをセットする.
	 * 
	 * @param obj
	 *            JSONのオブジェクト
	 * @param ele
	 *            XMLのオブジェクト
	 * @throws Exception
	 */
	private void setJsonObject(JSONObject obj, XmlBaseElement ele)
			throws Exception {
		// 配列かそうでないかを判定
		// 複数の要素に同じタグが1つでも存在すれば配列と判定する
		// 逆に言うと、全部タグ名が異なれば配列ではないと判定する
		boolean isArray = false;
		for (int i = 1; i < ele.getItemSize(); i++) {
			if (ele.getItem(i).getTagName()
					.equals(ele.getItem(i - 1).getTagName())) {
				isArray = true;
				break;
			}
		}
		for (int i = 0; i < ele.getItemSize(); i++) {
			XmlItem childItem = ele.getItem(i);
			if (childItem.getItemSize() == 0) {
				String value = childItem.getValue();
				// HTMLエスケープする場合はここで実行する
				if (this.isHtmlEscape) {
					value = HtmlUtil.escape(value);
				}
				obj.put(childItem.getTagName(), value);
				continue;
			}
			JSONObject childObj = new JSONObject();
			if (isArray) {
				obj.append(childItem.getTagName(), childObj);
			} else {
				obj.put(childItem.getTagName(), childObj);
			}
			// 再帰呼び出し
			setJsonObject(childObj, childItem);
		}
	}
}
