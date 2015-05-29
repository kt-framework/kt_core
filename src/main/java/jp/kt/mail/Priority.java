package jp.kt.mail;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 重要度.
 *
 * @author tatsuya.kumon
 */
public class Priority implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ヘッダ値 */
	private Map<String, String> header;

	/** 重要度高 */
	public static final Priority HIGH;

	/** 重要度低 */
	public static final Priority LOW;

	static {
		// 重要度高
		HIGH = new Priority();
		HIGH.addHeader("Proprity", "urgent");
		HIGH.addHeader("X-Priority", "1");
		HIGH.addHeader("X-MSMail-Priority", "High");
		HIGH.addHeader("Importance", "High");
		// 重要度低
		LOW = new Priority();
		LOW.addHeader("Proprity", "non-urgent");
		LOW.addHeader("X-Priority", "5");
		LOW.addHeader("X-MSMail-Priority", "Low");
		LOW.addHeader("Importance", "Low");
	}

	/**
	 * 内部コンストラクタ.
	 *
	 * @param priority
	 *            Proprityヘッダ値
	 * @param xPriority
	 *            X-Priorityヘッダ値
	 * @param xMsMailPriority
	 *            X-MSMail-Priorityヘッダ値
	 * @param importance
	 *            Importanceヘッダ値
	 */
	private Priority() {
		header = new HashMap<String, String>();
	}

	/**
	 * ヘッダ値を追加.
	 *
	 * @param name
	 *            ヘッダ名
	 * @param value
	 *            ヘッダ値
	 */
	private void addHeader(String name, String value) {
		header.put(name, value);
	}

	/**
	 * ヘッダ値のMapを取得する
	 *
	 * @return ヘッダ値のMap
	 */
	Map<String, String> getHeader() {
		return header;
	}
}
