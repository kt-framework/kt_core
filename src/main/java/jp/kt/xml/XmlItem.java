package jp.kt.xml;

/**
 * XML要素.
 *
 * @author tatsuya.kumon
 */
public class XmlItem extends XmlBaseElement {
	private static final long serialVersionUID = 1L;

	/** 内容 */
	private String value;

	/** 内容をCDATA形式とするか */
	private boolean isCdata;

	/**
	 * コンストラクタ
	 *
	 * @param tagName
	 *            タグ名
	 */
	public XmlItem(String tagName) {
		super(tagName);
	}

	/**
	 * コンストラクタ
	 *
	 * @param tagName
	 *            タグ名
	 * @param value
	 *            内容
	 */
	public XmlItem(String tagName, Object value) {
		this(tagName, value, false);
	}

	/**
	 * コンストラクタ
	 *
	 * @param tagName
	 *            タグ名
	 * @param value
	 *            内容
	 * @param isCdata
	 *            CDATA形式にする場合はtrue
	 */
	public XmlItem(String tagName, Object value, boolean isCdata) {
		super(tagName);
		if (value != null) {
			this.value = value.toString();
		}
		this.isCdata = isCdata;
	}

	/**
	 * 内容を取得する.
	 *
	 * @return 内容
	 */
	public String getValue() {
		return (value == null ? "" : value);
	}

	/**
	 * 内容をセットする.
	 *
	 * @param value
	 *            内容
	 */
	public void setValue(Object value) {
		if (value != null) {
			this.value = value.toString();
		}
		this.isCdata = false;
	}

	/**
	 * 内容をCDATA形式でセットする.
	 *
	 * @param value
	 *            CDATA形式でセットする内容
	 */
	public void setCdataValue(Object value) {
		if (value != null) {
			this.value = value.toString();
		}
		this.isCdata = true;
	}

	/**
	 * 内容をCDATA形式とするかどうかのフラグ.
	 *
	 * @return trueならCDATA形式
	 */
	boolean isCdata() {
		return isCdata;
	}
}
