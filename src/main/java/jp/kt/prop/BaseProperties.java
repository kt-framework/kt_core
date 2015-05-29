package jp.kt.prop;

import java.util.Enumeration;
import java.util.ResourceBundle;

import jp.kt.tool.Validator;

/**
 * プロパティファイル読み込みの共通処理クラス.
 * 
 * @author tatsuya.kumon
 */
public abstract class BaseProperties {
	private ResourceBundle rb;

	/**
	 * コンストラクタ.
	 * 
	 * @param baseName
	 *            プロパティファイルの拡張子を除いた名称
	 */
	protected BaseProperties(String baseName) {
		this.rb = ResourceBundle.getBundle(baseName);
	}

	/**
	 * プロパティファイルから指定キーの値を取得する.
	 * 
	 * @param key
	 *            キー
	 * @return キーにマッピングされたString値
	 */
	public final String getString(String key) {
		return rb.getString(key);
	}

	/**
	 * プロパティファイルから指定キーの値をintで取得する.
	 * 
	 * @param key
	 *            キー
	 * @return キーにマッピングされたint値
	 */
	public final int getInt(String key) {
		return Integer.parseInt(getString(key));
	}

	/**
	 * プロパティファイルから指定キーの値をbooleanで取得する.
	 * 
	 * @param key
	 *            キー
	 * @return キーにマッピングされたboolean値
	 */
	public final boolean getBoolean(String key) {
		return Boolean.parseBoolean(getString(key));
	}

	/**
	 * キーが存在するか判定する.
	 * <p>
	 * getStringやgetIntを呼ぶと、キーが存在しない場合はExceptionとなってしまうため、<br>
	 * キーが存在しない可能性があって、ハンドリングが必要なアプリケーションの場合は<br>
	 * このメソッドを使ってハンドリングする.
	 * </p>
	 * 
	 * @param key
	 *            キー
	 * @return キーが存在する場合はtrue
	 */
	public final boolean existKey(String key) {
		if (Validator.isEmpty(key)) {
			return false;
		}
		boolean exist = false;
		// 全キー名を取得
		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String k = keys.nextElement();
			if (k != null && k.equals(key)) {
				// 存在している場合はtrueを返す
				exist = true;
				break;
			}
		}
		return exist;
	}
}
