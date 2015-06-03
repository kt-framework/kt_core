package jp.kt.tool;

import java.net.URISyntaxException;

/**
 * URL解析クラス.
 *
 * @author tatsuya.kumon
 */
public class UrlParser extends UrlBase {
	/**
	 * コンストラクタ.
	 *
	 * @param url
	 *            URL
	 * @throws URISyntaxException
	 *             URIの書式が誤っている場合
	 */
	public UrlParser(String url) throws URISyntaxException {
		super(url);
	}

	/**
	 * ドメイン部分を返す.
	 * <p>
	 * スキーマ、ホスト名、ポート番号が連結された文字列を返します.<br>
	 * （例）<br>
	 * http://blog.fujitv.co.jp<br>
	 * https://secadm.fujitv.co.jp:8080<br>
	 * </p>
	 *
	 * @return ドメイン
	 */
	public String getDomain() {
		return super.getDomain();
	}

	/**
	 * パスを返す.
	 *
	 * @return パス
	 */
	public String getPath() {
		return super.getPath();
	}

	/**
	 * クエリストリングを返す.
	 *
	 * @return クエリストリング
	 */
	public String getQuery() {
		return super.getQuery();
	}

	/**
	 * フラグメント（ページ内リンク）を返す.
	 *
	 * @return フラグメント（ページ内リンク）
	 */
	public String getFragment() {
		return super.getFragment();
	}
}
