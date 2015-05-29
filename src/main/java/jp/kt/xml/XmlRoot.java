package jp.kt.xml;

import jp.kt.prop.KtProperties;

/**
 * XMLルート.
 *
 * @author tatsuya.kumon
 */
public class XmlRoot extends XmlBaseElement {
	private static final long serialVersionUID = 1L;

	/** 文字コード */
	private String charset;

	/**
	 * コンストラクタ.
	 * <p>
	 * 文字コードはデフォルト文字コードとなります.
	 * </p>
	 *
	 * @param tagName
	 *            タグ名
	 */
	public XmlRoot(String tagName) {
		this(tagName, KtProperties.getInstance().getDefaultCharset());
	}

	/**
	 * コンストラクタ.
	 *
	 * @param tagName
	 *            タグ名
	 * @param charset
	 *            文字コード
	 */
	public XmlRoot(String tagName, String charset) {
		super(tagName);
		this.charset = charset;
	}

	/**
	 * 文字コードを取得する.
	 *
	 * @return charset
	 */
	String getCharset() {
		return charset;
	}
}
