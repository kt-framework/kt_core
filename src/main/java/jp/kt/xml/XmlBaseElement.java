package jp.kt.xml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * XmlRootとXmlItemの基底クラス.
 *
 * @author tatsuya.kumon
 */
public abstract class XmlBaseElement implements Serializable {
	private static final long serialVersionUID = 1L;

	/** タグ名 */
	private String tagName;

	/** 属性 */
	private Map<String, String> attributeMap;

	/** 子要素のリスト */
	private List<XmlItem> itemList;

	/**
	 * コンストラクタ.
	 *
	 * @param tagName
	 *            タグ名
	 */
	XmlBaseElement(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * タグ名を取得する.
	 *
	 * @return タグ名
	 */
	public String getTagName() {
		return this.tagName;
	}

	/**
	 * 属性を追加する.
	 * <p>
	 * 既に同一キーが存在する場合は上書きされます.
	 * </p>
	 *
	 * @param name
	 *            属性名
	 * @param value
	 *            属性値
	 */
	public void addAttribute(String name, Object value) {
		if (value != null) {
			if (this.attributeMap == null) {
				this.attributeMap = new HashMap<String, String>();
			}
			this.attributeMap.put(name, value.toString());
		}
	}

	/**
	 * 属性Mapを取得.
	 *
	 * @return attributeMap
	 */
	Map<String, String> getAttributeMap() {
		return attributeMap;
	}

	/**
	 * 属性値を取得.
	 *
	 * @param name
	 *            属性名
	 * @return 属性値
	 */
	public String getAttribute(String name) {
		if (this.attributeMap == null) {
			return null;
		}
		return this.attributeMap.get(name);
	}

	/**
	 * 要素を追加します.
	 *
	 * @param item
	 *            要素
	 */
	public final void addItem(XmlItem item) {
		if (this.itemList == null) {
			this.itemList = new ArrayList<XmlItem>();
		}
		this.itemList.add(item);
	}

	/**
	 * 要素リストを取得.
	 *
	 * @return 要素リスト
	 */
	final List<XmlItem> getItemList() {
		return this.itemList;
	}

	/**
	 * 子要素数を取得.
	 *
	 * @return 子要素数
	 */
	public int getItemSize() {
		if (this.itemList == null) {
			return 0;
		}
		return this.itemList.size();
	}

	/**
	 * 子要素を取得.
	 *
	 * @param index
	 *            インデックス番号
	 * @return 指定インデックスの子要素
	 */
	public XmlItem getItem(int index) {
		return this.itemList.get(index);
	}
}
