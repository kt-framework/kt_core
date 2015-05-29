package jp.kt.tool;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * URL解析、生成の基底クラス.
 *
 * @author tatsuya.kumon
 */
abstract class UrlBase {
	/** スキーマ */
	private String scheme;

	/** ホスト名 */
	private String host;

	/** ポート番号 */
	private int port;

	/** パス */
	private String path;

	/** クエリストリング */
	private String query;

	/** フラグメント（ページ内リンク） */
	private String fragment;

	/**
	 * コンストラクタ.
	 *
	 * @param url
	 *            URL
	 * @throws URISyntaxException
	 */
	UrlBase(String url) throws URISyntaxException {
		// クエリ文字列がある場合は切り出してセットする
		URI uri = new URI(url);
		this.scheme = uri.getScheme();
		this.host = uri.getHost();
		this.port = uri.getPort();
		this.path = uri.getRawPath();
		this.query = uri.getRawQuery();
		this.fragment = uri.getRawFragment();
	}

	/**
	 * ドメイン名部分を返す.
	 * <p>
	 * スキーマ、ホスト名、ポート番号が連結された文字列を返します.
	 * </p>
	 *
	 * @return ドメイン
	 */
	String getDomain() {
		if (this.scheme == null) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(scheme);
		sb.append("://");
		sb.append(host);
		if (port > 1) {
			sb.append(":");
			sb.append(port);
		}
		return sb.toString();
	}

	/**
	 * パスを返す.
	 *
	 * @return パス
	 */
	String getPath() {
		return this.path;
	}

	/**
	 * クエリストリングを返す.
	 *
	 * @return クエリストリング
	 */
	String getQuery() {
		return this.query;
	}

	/**
	 * フラグメント（ページ内リンク）を返す.
	 *
	 * @return フラグメント（ページ内リンク）
	 */
	String getFragment() {
		return this.fragment;
	}

	/**
	 * フラグメント（ページ内リンク）をセットする.
	 *
	 * @param fragment
	 *            フラグメント（ページ内リンク）の名称
	 */
	void setFragment(String fragment) {
		this.fragment = fragment;
	}

	/**
	 * パスをセット.
	 *
	 * @param path
	 *            パス
	 */
	void setPath(String path) {
		this.path = path;
	}
}
