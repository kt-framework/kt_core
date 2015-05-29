package jp.kt.prop;

/**
 * 全アプリケーション（Webアプリ、バッチ）の共通設定を管理するプロパティファイルの読み込み.
 *
 * @author tatsuya.kumon
 */
public class KtProperties extends BaseProperties {
	private static KtProperties p;

	/**
	 * 内部コンストラクタ.
	 */
	private KtProperties() {
		super("kt");
	}

	/**
	 * インスタンス生成メソッド.
	 *
	 * @return {@link KtProperties}オブジェクト
	 */
	public static KtProperties getInstance() {
		if (p == null) {
			p = new KtProperties();
		}
		return p;
	}

	/**
	 * デフォルト文字コードを取得する.
	 *
	 * @return デフォルト文字コード
	 */
	public String getDefaultCharset() {
		return getString("kt.core.charset.default");
	}

	/**
	 * 文字コードShift_JISを取得する.
	 *
	 * @return 文字コードShift_JIS
	 */
	public String getSjisCharset() {
		return getString("kt.core.charset.sjis");
	}
}
