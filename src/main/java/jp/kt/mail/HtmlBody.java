package jp.kt.mail;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.kt.tool.StringUtil;

/**
 * HTML本文を管理するクラス.
 * <p>
 * HTMLとインライン画像を管理する.
 * </p>
 *
 * @author tatsuya.kumon
 */
public class HtmlBody implements Serializable {
	private static final long serialVersionUID = 1L;

	/** Content-IDの前置詞 */
	private static final String CID_PREFIX = "cid:";

	/** HTML本文 */
	private String html;

	/** インライン画像 */
	private Map<String, InlineImage> inlineImageMap;

	/**
	 * コンストラクタ.
	 *
	 * @param html
	 *            HTML本文.<br>
	 *            インライン画像のパスの部分には、${key}のように記述すること.<br>
	 *            例えば、&lt;img&nbsp;src=&quot;${BANNER_1}&quot;&gt;のようにHTMLに記述し、<br>
	 *            addInlineImageメソッドの引数keyに&quot;BANNER_1&quot;を指定する.
	 */
	public HtmlBody(String html) {
		if (html == null) {
			html = "";
		}
		this.html = StringUtilForMail.convertMapping(html);
		// インライン画像Mapをインスタンス化
		this.inlineImageMap = new HashMap<String, InlineImage>();
	}

	/**
	 * 添付ファイルの追加.
	 * <p>
	 * 物理ファイル用.
	 * </p>
	 *
	 * @param key
	 *            インライン画像を埋め込む場所を特定するための本文中のキー.
	 * @param filePath
	 *            ファイルのパス
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public void addInlineImage(String key, String filePath) throws IOException {
		inlineImageMap.put(key, new InlineImage(filePath));
	}

	/**
	 * 添付ファイルの追加.
	 * <p>
	 * バイトデータ用.
	 * </p>
	 *
	 * @param key
	 *            インライン画像を埋め込む場所を特定するための本文中のキー.
	 * @param fileData
	 *            ファイルのバイトデータ
	 * @param fileName
	 *            ファイル名
	 */
	public void addInlineImage(String key, byte[] fileData, String fileName) {
		inlineImageMap.put(key, new InlineImage(fileData, fileName));
	}

	/**
	 * HTML本文を返す.
	 * <p>
	 * インライン画像を埋め込むキーをcidの記述に置換した本文を返す.
	 * </p>
	 *
	 * @return HTML本文
	 */
	String getHtml() {
		String replacedHtml = this.html;
		// cid置換
		for (String key : this.inlineImageMap.keySet()) {
			replacedHtml = StringUtil.replaceAll(replacedHtml,
					"${" + key + "}", CID_PREFIX
							+ this.inlineImageMap.get(key).getCid());
		}
		return replacedHtml;
	}

	/**
	 * インライン画像のリストを返す.
	 *
	 * @return インライン画像のリスト
	 */
	List<InlineImage> getInlineImageList() {
		List<InlineImage> list = new ArrayList<InlineImage>();
		for (String key : this.inlineImageMap.keySet()) {
			list.add(this.inlineImageMap.get(key));
		}
		return list;
	}
}
