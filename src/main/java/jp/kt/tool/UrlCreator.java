package jp.kt.tool;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jp.kt.prop.KtProperties;

/**
 * URL生成クラス.
 *
 * @author tatsuya.kumon
 */
public class UrlCreator extends UrlBase {
	/** セットするパラメータ群 */
	private Map<String, String> params;

	/** URLエンコードで使用する文字コード */
	private String charsetName;

	/**
	 * コンストラクタ.
	 * <p>
	 * パラメータのURLエンコードにはデフォルト文字コードを使用します.
	 * </p>
	 *
	 * @param url
	 *            URL
	 * @throws URISyntaxException
	 *             URIの書式が誤っている場合
	 */
	public UrlCreator(String url) throws URISyntaxException {
		this(url, KtProperties.getInstance().getDefaultCharset());
	}

	/**
	 * コンストラクタ.
	 * <p>
	 * パラメータのURLエンコードで使用する文字コードを明示的に指定します.
	 * </p>
	 *
	 * @param url
	 *            URL
	 * @param charsetName
	 *            文字コード
	 * @throws URISyntaxException
	 *             URIの書式が誤っている場合
	 */
	public UrlCreator(String url, String charsetName) throws URISyntaxException {
		super(url);
		this.charsetName = charsetName;
	}

	/**
	 * パラメータの追加.
	 * <p>
	 * 元のURLにクエリストリングが付加されている場合は、それに追加する.
	 * </p>
	 *
	 * @param name
	 *            パラメータ名
	 * @param value
	 *            パラメータ値
	 */
	public void addParam(String name, Object value) {
		if (params == null) {
			params = new HashMap<String, String>();
		}
		String valueText;
		if (value == null) {
			valueText = "";
		} else {
			valueText = value.toString();
		}
		// Mapにセット
		params.put(name, valueText);
	}

	/**
	 * フラグメント（ページ内リンク）をセットする.
	 *
	 * @param fragment
	 *            フラグメント（ページ内リンク）の名称
	 */
	public void setFragment(String fragment) {
		super.setFragment(fragment);
	}

	/**
	 * サブパス（一階層下）に移動する.
	 *
	 * @param subPath
	 *            サブパス
	 */
	public void moveSubPath(Object subPath) {
		StringBuilder temp = new StringBuilder(super.getPath());
		if (!super.getPath().endsWith("/")
				&& !subPath.toString().startsWith("/")) {
			// 連結部にスラッシュがなければ追加
			temp.append("/");
		}
		// サブパスを追加
		temp.append(subPath);
		// スラッシュが両方ともついていた場合は1つにする
		super.setPath(StringUtil.replaceAll(temp.toString(), "//", "/"));
	}

	/**
	 * 親パス（一階層上）に移動する.
	 */
	public void moveParentPath() {
		if (Validator.isEmpty(super.getPath()) || super.getPath().equals("/")) {
			// ルートなので何もしない
			return;
		}
		// 後ろからスラッシュを検索して
		int index = super.getPath().lastIndexOf("/");
		if (index == 0) {
			// スラッシュが先頭ならルートに移動する
			super.setPath("/");
		} else {
			// スラッシュが先頭以外で見つかった場合はそれ以降を削除する
			super.setPath(getPath().substring(0, index));
		}
	}

	/**
	 * URL生成.
	 *
	 * @return 生成されたURL
	 * @throws UnsupportedEncodingException
	 *             指定されたエンコーディングがサポートされていない場合
	 */
	public String create() throws UnsupportedEncodingException {
		StringBuilder fullUrl = new StringBuilder(createWithoutParam());
		// クエリストリング
		StringBuilder query = new StringBuilder();
		// まずは元のクエリ文字列を付加
		if (!Validator.isEmpty(super.getQuery())) {
			query.append("?");
			query.append(super.getQuery());
		}
		// 次にパラメータ群を追加
		if (params != null) {
			Set<String> nameSet = params.keySet();
			for (String name : nameSet) {
				String value = params.get(name);
				value = (value == null) ? "" : value;
				// 最初なら?、2番目以降なら&を連結
				if (query.length() == 0) {
					query.append("?");
				} else {
					query.append("&");
				}
				// 文字コード名を取得
				// name=value形式で連結
				// ただしvalueはURLEncodeする
				query.append(name);
				query.append("=");
				query.append(URLEncoder.encode(value, this.charsetName));
			}
		}
		// URLとクエリストリングを連結する
		fullUrl.append(query);
		// ページ内リンクが設定されている場合はURLに追加する
		if (!Validator.isEmpty(super.getFragment())) {
			fullUrl.append("#");
			fullUrl.append(super.getFragment());
		}
		return fullUrl.toString();
	}

	/**
	 * パラメータやページ内リンクを除いたURLを生成する.
	 *
	 * @return 生成されたURL
	 */
	public String createWithoutParam() {
		StringBuilder fullUrl = new StringBuilder();
		// ドメイン
		fullUrl.append(super.getDomain() == null ? "" : super.getDomain());
		// パス
		fullUrl.append(super.getPath() == null ? "" : super.getPath());
		return fullUrl.toString();
	}
}
