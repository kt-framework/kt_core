package jp.kt.db;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * DB接続クラス.
 *
 * @author tatsuya.kumon
 */
public final class DbConnectManager {
	/**
	 * DataSourceを使ったConnectionの取得.
	 * <p>
	 * 主にWebアプリ用.<br>
	 * オートコミットはOFFになります.
	 * </p>
	 *
	 * @param jndiName
	 *            接続先JNDI名
	 * @return Connectionオブジェクト
	 * @throws Exception
	 *             DB接続時に例外発生した場合
	 */
	public static Connection createConnection(String jndiName) throws Exception {
		// JNDI名からConnectionを生成
		DataSource ds = InitialContext.doLookup(jndiName);
		Connection con = ds.getConnection();
		// オートコミットをOFFにする
		con.setAutoCommit(false);
		return con;
	}

	/**
	 * DriverManagerを使ったConnectionの取得.
	 * <p>
	 * 主にバッチ用.<br>
	 * オートコミットはOFFになります.
	 * </p>
	 *
	 * @param driver
	 *            接続Driver
	 * @param url
	 *            接続URL
	 * @param user
	 *            ログインユーザ
	 * @param password
	 *            ログインパスワード
	 * @return {@link Connection}オブジェクト
	 * @throws Exception
	 *             DB接続時に例外発生した場合
	 */
	public static Connection createConnection(String driver, String url,
			String user, String password) throws Exception {
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, user, password);
		con.setAutoCommit(false);
		return con;
	}
}
