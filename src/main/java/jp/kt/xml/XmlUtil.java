package jp.kt.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import jp.kt.prop.KtProperties;
import jp.kt.tool.Validator;

import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

/**
 * XML解析・生成処理クラス.
 * 
 * @author tatsuya.kumon
 */
public class XmlUtil {
	/** XMLの文字コードは基本的にUTF-8で固定 */
	private static String CHARSET = "UTF-8";

	/** Shift_JISの場合のxmlタグのencoding属性 */
	private static String CHARSET_SJIS_ON_XML = "Shift_JIS";

	/**
	 * インスタンス化されないようにするための内部コンストラクタ.
	 */
	private XmlUtil() {
	}

	/**
	 * {@link XmlRoot} オブジェクトから、UTF-8のXML文書を生成する.
	 * 
	 * @param root
	 *            {@link XmlRoot} オブジェクト
	 * @return XML文書
	 * @throws Exception
	 */
	public static String create(XmlRoot root) throws Exception {
		// XmlRootオブジェクトからDocumentオブジェクトに変換
		DocumentBuilder builder = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		Document document = builder.newDocument();
		// ルートElement
		Element rootEle = document.createElement(root.getTagName());
		// 属性のセット
		Map<String, String> attMap = root.getAttributeMap();
		if (attMap != null) {
			Set<String> keySet = attMap.keySet();
			for (String key : keySet) {
				rootEle.setAttribute(key, attMap.get(key));
			}
		}
		document.appendChild(rootEle);
		// 子要素を再帰的にセット
		setDomElement(root.getItemList(), document, rootEle);
		// テキスト出力
		return outputText(document);
	}

	/**
	 * {@link XmlRoot} オブジェクトから、Shift_JISのXML文書を生成する.
	 * 
	 * @param root
	 *            {@link XmlRoot} オブジェクト
	 * @return XML文書
	 * @throws Exception
	 */
	public static String createSjis(XmlRoot root) throws Exception {
		// UTF-8で作成
		String xmlText = create(root);
		// xmlタグのencoding属性を置換
		return xmlText.replaceFirst(CHARSET, CHARSET_SJIS_ON_XML);
	}

	/**
	 * 再帰的にDocumentオブジェクトにセットする.
	 * 
	 * @param itemList
	 *            子要素リスト
	 * @param document
	 *            Documentオブジェクト
	 * @param parentEle
	 *            セットする先の親要素
	 */
	private static void setDomElement(List<XmlItem> itemList,
			Document document, Element parentEle) {
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			XmlItem item = itemList.get(i);
			/*
			 * Elementの生成
			 */
			Element element = document.createElement(item.getTagName());
			// 値のセット
			if (!Validator.isEmpty(item.getValue())) {
				if (item.isCdata()) {
					// CDATA形式
					element.appendChild(document.createCDATASection(item
							.getValue()));
				} else {
					// 通常形式
					element.appendChild(document.createTextNode(item.getValue()));
				}
			}
			// 属性のセット
			Map<String, String> attMap = item.getAttributeMap();
			if (attMap != null) {
				Set<String> keySet = attMap.keySet();
				for (String key : keySet) {
					element.setAttribute(key, attMap.get(key));
				}
			}
			// 子要素のセット
			if (item.getItemList() != null) {
				// 再帰呼び出し
				setDomElement(item.getItemList(), document, element);
			}
			/*
			 * 生成されたElementを親にセット
			 */
			parentEle.appendChild(element);
		}
	}

	/**
	 * DocumentオブジェクトからXML文書生成.
	 * 
	 * @param document
	 *            Documentオブジェクト
	 * @return XML文書
	 * @throws TransformerException
	 */
	private static String outputText(Document document)
			throws TransformerException {
		// Transformer生成
		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		// 文字コード設定
		transformer.setOutputProperty(OutputKeys.ENCODING, CHARSET);
		// メソッド
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		// インデント設定
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		// String書き出し
		StringWriter sw = new StringWriter();
		transformer.transform(new DOMSource(document), new StreamResult(sw));
		return sw.toString();
	}

	/**
	 * UTF-8で書かれているXML文書のパース.
	 * <p>
	 * XML形式エラーの場合はnullを返します.
	 * </p>
	 * 
	 * @param xmlText
	 *            XML文書
	 * @return {@link XmlRoot} オブジェクト
	 * @throws Exception
	 */
	public static XmlRoot parse(String xmlText) throws Exception {
		return parse(xmlText, CHARSET);
	}

	/**
	 * Shift_JISで書かれているXML文書のパース.
	 * <p>
	 * XML形式エラーの場合はnullを返します.
	 * </p>
	 * 
	 * @param xmlText
	 *            XML文書
	 * @return {@link XmlRoot} オブジェクト
	 * @throws Exception
	 */
	public static XmlRoot parseSjis(String xmlText) throws Exception {
		String charsetSjis = KtProperties.getInstance().getSjisCharset();
		// xmlタグのencoding属性の置換
		xmlText = xmlText.replaceFirst(CHARSET_SJIS_ON_XML, charsetSjis);
		return parse(xmlText, charsetSjis);
	}

	/**
	 * UTF-8で書かれているXML文書のパース.
	 * <p>
	 * XML形式エラーの場合はnullを返します.
	 * </p>
	 * 
	 * @param xmlText
	 *            XML文書
	 * @param charset
	 *            文字コード
	 * @return {@link XmlRoot} オブジェクト
	 * @throws Exception
	 */
	private static XmlRoot parse(String xmlText, String charset)
			throws Exception {
		// parserを生成
		DocumentBuilder parser = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder();
		// parseしてDocumentオブジェクトを取得
		InputStream is = new ByteArrayInputStream(xmlText.getBytes(charset));
		Document doc = null;
		try {
			doc = parser.parse(is);
		} catch (SAXParseException e) {
			// XML形式エラーの場合はnullを返す
			return null;
		}
		// ルート要素を取得
		Element rootEle = doc.getDocumentElement();
		XmlRoot root = new XmlRoot(rootEle.getTagName());
		NamedNodeMap attrMap = rootEle.getAttributes();
		for (int j = 0; j < attrMap.getLength(); j++) {
			Attr attr = (Attr) attrMap.item(j);
			root.addAttribute(attr.getName(), attr.getValue());
		}
		// 子要素を再帰的にセット
		setKtElement(rootEle.getChildNodes(), root);
		return root;
	}

	/**
	 * 再帰的に {@link XmlBaseElement} オブジェクトにセットする.
	 * 
	 * @param nodeList
	 *            ノードリスト
	 * @param parentEle
	 *            セットする先の親要素
	 */
	private static void setKtElement(NodeList nodeList, XmlBaseElement parentEle) {
		for (int i = 0; i < nodeList.getLength(); i++) {
			if (!(nodeList.item(i) instanceof Element)) {
				continue;
			}
			Element element = (Element) nodeList.item(i);
			XmlItem item = new XmlItem(element.getTagName());
			// 内容
			if (element.getFirstChild() != null) {
				if (element.getFirstChild() instanceof CDATASection) {
					// CDATA形式
					item.setCdataValue(element.getFirstChild().getNodeValue());
				} else {
					// 通常形式
					item.setValue(element.getFirstChild().getNodeValue());
				}
			}
			// 属性
			NamedNodeMap attrMap = element.getAttributes();
			for (int j = 0; j < attrMap.getLength(); j++) {
				Attr attr = (Attr) attrMap.item(j);
				item.addAttribute(attr.getName(), attr.getValue());
			}
			// 再帰呼び出し
			setKtElement(element.getChildNodes(), item);
			// 親に追加
			parentEle.addItem(item);
		}
	}
}
