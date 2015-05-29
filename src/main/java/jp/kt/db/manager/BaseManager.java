package jp.kt.db.manager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.kt.db.entity.BaseEntity;
import jp.kt.logger.ApplicationLogger;
import jp.kt.prop.KtProperties;
import jp.kt.tool.StringUtil;
import jp.kt.tool.Validator;

/**
 * Managerの基底クラス.
 *
 * @author tatsuya.kumon
 */
public abstract class BaseManager<T extends BaseEntity> {
	/**
	 * NULL値をプレースホルダーにセットするための定数.
	 *
	 * @author tatsuya.kumon
	 */
	public static enum NullData {
		/** 文字列型 */
		STRING,
		/** 文字型 */
		CHAR,
		/** 日時型 */
		TIMESTAMP,
		/** 整数型 */
		INTEGER,
		/** 小数型 */
		DOUBLE,
		/** DECIMAL型 */
		DECIMAL;
	}

	/** DB接続 */
	private Connection con;

	/** SELECT時のフェッチサイズ */
	private Integer fetchSize;

	/** SQL文のデバッグ出力するためのLogger */
	private ApplicationLogger sqlDebugLogger;

	/** SQLスローログのためのLogger */
	private ApplicationLogger sqlSlowLogger;

	/** SQLデバッグログの出力可否 */
	private boolean isOutputSqlDebuglog = KtProperties.getInstance()
			.getBoolean("kt.core.sql.debuglog.enabled");

	/** 時間がかかるSQLを検知するための閾値（ミリ秒） */
	private long borderMillisec = KtProperties.getInstance().getInt(
			"kt.core.sql.slowlog.border.millisec");

	/** SQLデバッグログ出力用Logger名 */
	private static final String SQL_DEBUG_LOGGER_NAME = "sqlDebug";

	/** SQLスローログ出力用Logger名 */
	private static final String SQL_SLOW_LOGGER_NAME = "sqlSlow";

	/**
	 * コンストラクタ.
	 *
	 * @param con
	 *            DB接続
	 */
	public BaseManager(Connection con) {
		this.con = con;
		// SQLをデバッグ出力するためのLogger
		if (isOutputSqlDebuglog) {
			sqlDebugLogger = new ApplicationLogger(SQL_DEBUG_LOGGER_NAME,
					this.getClass());
		}
		// SQLスローログのためのLogger
		// 設定値（閾値）がマイナスの場合はログ出力しない
		if (borderMillisec >= 0) {
			sqlSlowLogger = new ApplicationLogger(SQL_SLOW_LOGGER_NAME,
					this.getClass());
		}
		// フェッチサイズをプロパティファイルから取得
		if (KtProperties.getInstance().existKey("kt.core.sql.fetchsize")) {
			String fetchSizeStr = KtProperties.getInstance().getString(
					"kt.core.sql.fetchsize");
			if (!Validator.isEmpty(fetchSizeStr.trim())) {
				fetchSize = Integer.parseInt(fetchSizeStr);
			}
		}
	}

	/**
	 * Connectionオブジェクトを取得する.
	 *
	 * @return {@link Connection}オブジェクト
	 */
	protected Connection getConnection() {
		return this.con;
	}

	/**
	 * 共通SELECT処理.
	 *
	 * @param sql
	 *            SQL文
	 * @return SELECTした結果のList
	 * @throws Exception
	 */
	protected List<T> executeQuery(CharSequence sql) throws Exception {
		return executeQuery(sql, new Object[0]);
	}

	/**
	 * 共通SELECT処理.
	 *
	 * @param sql
	 *            SQL文
	 * @param timeoutSec
	 *            タイムアウト値（秒）
	 * @return SELECTした結果のList
	 * @throws Exception
	 */
	protected List<T> executeQuery(CharSequence sql, int timeoutSec)
			throws Exception {
		return executeQuery(sql, new Object[0], timeoutSec);
	}

	/**
	 * 共通SELECT処理.
	 *
	 * @param sql
	 *            SQL文
	 * @param values
	 *            プレースホルダーの値
	 * @return SELECTした結果のList
	 * @throws Exception
	 */
	protected List<T> executeQuery(CharSequence sql, Object[] values)
			throws Exception {
		return executeQuery(sql, values, 0);
	}

	/**
	 * 共通SELECT処理.
	 *
	 * @param sql
	 *            SQL文
	 * @param values
	 *            プレースホルダーの値
	 * @param timeoutSec
	 *            タイムアウト値（秒）
	 * @return SELECTした結果のList
	 * @throws Exception
	 */
	protected List<T> executeQuery(CharSequence sql, Object[] values,
			int timeoutSec) throws Exception {
		List<T> list = new ArrayList<T>();
		long start = 0;
		long end = 0;
		try (PreparedStatement pstmt = con.prepareStatement(sql.toString())) {
			// タイムアウト値の設定
			pstmt.setQueryTimeout(timeoutSec);
			if (sqlDebugLogger != null) {
				sqlDebugLogger.debugLog("[SQL]"
						+ createSqlForLog(sql.toString(), values));
			}
			// プレースホルダーのセット
			setPlaceHolder(pstmt, values);
			// フェッチサイズのセット
			if (fetchSize != null) {
				pstmt.setFetchSize(fetchSize);
			}
			// SQL実行
			start = System.nanoTime();
			try (ResultSet rs = pstmt.executeQuery()) {
				end = System.nanoTime();
				// entityクラスに格納
				while (rs.next()) {
					list.add(createEntity(rs));
				}
			}
			// SQLスローログ出力
			outputSqlSlowLog(sql.toString(), values, start, end);
		}
		return list;
	}

	/**
	 * 共通更新（INSERT、UPDATE、DELETE）処理.
	 *
	 * @param sql
	 *            SQL文
	 * @return 更新したレコード数
	 * @throws Exception
	 */
	protected int executeUpdate(CharSequence sql) throws Exception {
		return executeUpdate(sql, new Object[0]);
	}

	/**
	 * 共通更新（INSERT、UPDATE、DELETE）処理.
	 *
	 * @param sql
	 *            SQL文
	 * @param timeoutSec
	 *            タイムアウト値（秒）
	 * @return 更新したレコード数
	 * @throws Exception
	 */
	protected int executeUpdate(CharSequence sql, int timeoutSec)
			throws Exception {
		return executeUpdate(sql, new Object[0], timeoutSec);
	}

	/**
	 * 共通更新（INSERT、UPDATE、DELETE）処理.
	 *
	 * @param sql
	 *            SQL文
	 * @param values
	 *            プレースホルダーの値
	 * @return 更新したレコード数
	 * @throws Exception
	 */
	protected int executeUpdate(CharSequence sql, Object[] values)
			throws Exception {
		return executeUpdate(sql, values, 0);
	}

	/**
	 * 共通更新（INSERT、UPDATE、DELETE）処理.
	 *
	 * @param sql
	 *            SQL文
	 * @param values
	 *            プレースホルダーの値
	 * @param timeoutSec
	 *            タイムアウト値（秒）
	 * @return 更新したレコード数
	 * @throws Exception
	 */
	protected int executeUpdate(CharSequence sql, Object[] values,
			int timeoutSec) throws Exception {
		int updateCnt = 0;
		try (PreparedStatement pstmt = con.prepareStatement(sql.toString())) {
			// タイムアウト値の設定
			pstmt.setQueryTimeout(timeoutSec);
			if (sqlDebugLogger != null) {
				sqlDebugLogger.debugLog("[SQL]"
						+ createSqlForLog(sql.toString(), values));
			}
			// プレースホルダーのセット
			setPlaceHolder(pstmt, values);
			// SQL実行
			long start = System.nanoTime();
			updateCnt = pstmt.executeUpdate();
			long end = System.nanoTime();
			// SQLスローログ出力
			outputSqlSlowLog(sql.toString(), values, start, end);
		}
		return updateCnt;
	}

	/**
	 * 共通更新（INSERT、UPDATE、DELETE）処理.
	 * <p>
	 * 同じSQLで複数のプレースホルダーのパターンを実行する.
	 * </p>
	 *
	 * @param sql
	 *            SQL文
	 * @param valuesList
	 *            プレースホルダーの値のリスト
	 * @return 更新したレコード数
	 * @throws Exception
	 */
	protected int executeUpdate(CharSequence sql, List<Object[]> valuesList)
			throws Exception {
		return executeUpdate(sql, valuesList, 0);
	}

	/**
	 * 共通更新（INSERT、UPDATE、DELETE）処理.
	 * <p>
	 * 同じSQLで複数のプレースホルダーのパターンを実行する.
	 * </p>
	 *
	 * @param sql
	 *            SQL文
	 * @param valuesList
	 *            プレースホルダーの値のリスト
	 * @param timeoutSec
	 *            タイムアウト値（秒）
	 * @return 更新したレコード数
	 * @throws Exception
	 */
	protected int executeUpdate(CharSequence sql, List<Object[]> valuesList,
			int timeoutSec) throws Exception {
		int updateCnt;
		long start = 0;
		long end = 0;
		try (PreparedStatement pstmt = con.prepareStatement(sql.toString())) {
			// タイムアウト値の設定
			pstmt.setQueryTimeout(timeoutSec);
			for (Object[] values : valuesList) {
				if (sqlDebugLogger != null) {
					sqlDebugLogger.debugLog("[SQL]"
							+ createSqlForLog(sql.toString(), values));
				}
				// プレースホルダーのセット
				setPlaceHolder(pstmt, values);
				// バッチ処理にパラメータセットを追加
				pstmt.addBatch();
			}
			// SQL実行
			start = System.nanoTime();
			pstmt.executeBatch();
			end = System.nanoTime();
			updateCnt = pstmt.getUpdateCount();
			// SQLスローログ出力
			outputSqlSlowLog(sql.toString(), null, start, end);
		}
		return updateCnt;
	}

	/**
	 * プレースホルダーへの値のセット.
	 *
	 * @param pstmt
	 *            {@link PreparedStatement}オブジェクト
	 * @param values
	 *            セットする値
	 * @throws SQLException
	 */
	private void setPlaceHolder(PreparedStatement pstmt, Object[] values)
			throws SQLException {
		int parameterIndex = 0;
		for (Object value : values) {
			parameterIndex++;
			if (value instanceof NullData) {
				// NullDataによりnull値が指定された場合
				NullData nullData = (NullData) value;
				switch (nullData) {
				case STRING:
				case CHAR:
					pstmt.setString(parameterIndex, null);
					break;
				case INTEGER:
					pstmt.setNull(parameterIndex, Types.INTEGER);
					break;
				case DOUBLE:
					pstmt.setNull(parameterIndex, Types.DOUBLE);
					break;
				case DECIMAL:
					pstmt.setNull(parameterIndex, Types.DECIMAL);
					break;
				case TIMESTAMP:
					pstmt.setTimestamp(parameterIndex, null);
					break;
				}
			} else if (value instanceof String) {
				// 文字列
				pstmt.setString(parameterIndex, (String) value);
			} else if (value instanceof Character) {
				// 文字
				pstmt.setString(parameterIndex,
						String.valueOf((Character) value));
			} else if (value instanceof Integer) {
				// 整数値
				pstmt.setInt(parameterIndex, (Integer) value);
			} else if (value instanceof Double) {
				// 小数
				pstmt.setDouble(parameterIndex, (Integer) value);
			} else if (value instanceof BigDecimal) {
				// DECIMAL
				pstmt.setBigDecimal(parameterIndex, (BigDecimal) value);
			} else if (value instanceof Date) {
				// 日時
				Timestamp timestamp = new Timestamp(((Date) value).getTime());
				pstmt.setTimestamp(parameterIndex, timestamp);
			}
		}
	}

	/**
	 * ログ出力用のSQL文作成.
	 *
	 * @param sql
	 *            SQL文
	 * @param values
	 *            プレースホルダーの値
	 * @return ログ出力用のSQL文
	 */
	private String createSqlForLog(String sql, Object[] values) {
		// SQL文に含まれる改行を半角スペースに変換
		sql = StringUtil.replaceAll(sql, "\r\n", " ");
		sql = StringUtil.replaceAll(sql, "\n", " ");
		sql = StringUtil.replaceAll(sql, "\r", " ");
		// SQL文に含まれるタブを半角スペースに変換
		sql = StringUtil.replaceAll(sql, "\t", " ");
		// SQL文の連続したスペースを除去（無限ループにすると怖いのでMAX10回）
		for (int i = 0; i < 10; i++) {
			String tmp = sql;
			sql = StringUtil.replaceAll(sql, "  ", " ");
			if (sql.equals(tmp)) {
				// 変化無ければ終わり
				break;
			}
		}
		// ログメッセージ作成
		StringBuilder msg = new StringBuilder();
		msg.append(sql);
		if (values != null && values.length > 0) {
			msg.append(" ");
		}
		for (int i = 0; values != null && i < values.length; i++) {
			if (i == 0) {
				msg.append("[");
			}
			msg.append(i + 1);
			msg.append("=");
			msg.append(values[i] == null ? "[null]" : values[i].toString());
			if (i != values.length - 1) {
				msg.append(",");
			} else {
				msg.append("]");
			}
		}
		return msg.toString();
	}

	/**
	 * SQLスローログ出力.
	 *
	 * @param sql
	 *            SQL文
	 * @param values
	 *            プレースホルダーの値
	 * @param start
	 *            開始したシステム時刻
	 * @param end
	 *            終了したシステム時刻
	 */
	private void outputSqlSlowLog(String sql, Object[] values, long start,
			long end) {
		if (sqlSlowLogger == null) {
			return;
		}
		long time = (end - start) / 1000000;
		if (time >= borderMillisec) {
			// ログメッセージ作成
			StringBuilder msg = new StringBuilder();
			msg.append("(");
			msg.append(new DecimalFormat().format(time));
			msg.append("ms)");
			msg.append(createSqlForLog(sql, values));
			sqlSlowLogger.warnLog("A012", msg.toString());
		}
	}

	/**
	 * 検索結果1件をentityクラスにセットして返す.
	 *
	 * @param rs
	 *            {@link ResultSet}オブジェクト
	 * @throws SQLException
	 * @return BaseEntityオブジェクト
	 */
	protected abstract T createEntity(ResultSet rs) throws SQLException;

	/**
	 * ResultSet中に指定カラムがあるか判定.
	 *
	 * @param rs
	 *            {@link ResultSet}オブジェクト
	 * @param columnName
	 *            カラム名
	 * @return 指定カラムがあればtrue
	 */
	private boolean existColumn(ResultSet rs, String columnName) {
		boolean result = false;
		try {
			// 指定名称のカラムがなかった場合はSQLException発生
			rs.findColumn(columnName);
			result = true;
		} catch (SQLException e) {
			result = false;
		}
		return result;
	}

	/**
	 * ResultSetから指定カラムの値を取得.<br>
	 * 整数値の場合.
	 *
	 * @param rs
	 *            {@link ResultSet}オブジェクト
	 * @param columnName
	 *            カラム名
	 * @return ResultSetから取得した値
	 * @throws SQLException
	 */
	protected Integer getInt(ResultSet rs, String columnName)
			throws SQLException {
		Integer result = null;
		if (existColumn(rs, columnName)) {
			// カラム名が存在した場合は値取得
			result = rs.getInt(columnName);
			// 取得した値がnullか判定
			if (rs.wasNull()) {
				result = null;
			}
		}
		return result;
	}

	/**
	 * ResultSetから指定カラムの値を取得.<br>
	 * 小数値の場合.
	 *
	 * @param rs
	 *            {@link ResultSet}オブジェクト
	 * @param columnName
	 *            カラム名
	 * @return ResultSetから取得した値
	 * @throws SQLException
	 */
	protected Double getDouble(ResultSet rs, String columnName)
			throws SQLException {
		Double result = null;
		if (existColumn(rs, columnName)) {
			// カラム名が存在した場合は値取得
			result = rs.getDouble(columnName);
			// 取得した値がnullか判定
			if (rs.wasNull()) {
				result = null;
			}
		}
		return result;
	}

	/**
	 * ResultSetから指定カラムの値を取得.<br>
	 * DECIMAL値の場合.
	 *
	 * @param rs
	 *            {@link ResultSet}オブジェクト
	 * @param columnName
	 *            カラム名
	 * @return ResultSetから取得した値
	 * @throws SQLException
	 */
	protected BigDecimal getBigDecimal(ResultSet rs, String columnName)
			throws SQLException {
		BigDecimal result = null;
		if (existColumn(rs, columnName)) {
			// カラム名が存在した場合は値取得
			result = rs.getBigDecimal(columnName);
		}
		return result;
	}

	/**
	 * ResultSetから指定カラムの値を取得.<br>
	 * 文字列の場合.
	 *
	 * @param rs
	 *            {@link ResultSet}オブジェクト
	 * @param columnName
	 *            カラム名
	 * @return ResultSetから取得した値
	 * @throws SQLException
	 */
	protected String getString(ResultSet rs, String columnName)
			throws SQLException {
		String result = null;
		if (existColumn(rs, columnName)) {
			// カラム名が存在した場合は値取得
			result = rs.getString(columnName);
		}
		return result;
	}

	/**
	 * ResultSetから指定カラムの値を取得.<br>
	 * 文字の場合.
	 *
	 * @param rs
	 *            {@link ResultSet}オブジェクト
	 * @param columnName
	 *            カラム名
	 * @return ResultSetから取得した値
	 * @throws SQLException
	 */
	protected Character getChar(ResultSet rs, String columnName)
			throws SQLException {
		Character result = null;
		if (existColumn(rs, columnName)) {
			// カラム名が存在した場合は値取得
			String str = rs.getString(columnName);
			if (!Validator.isEmpty(str)) {
				result = str.charAt(0);
			}
		}
		return result;
	}

	/**
	 * ResultSetから指定カラムの値を取得.<br>
	 * 日時の場合.
	 *
	 * @param rs
	 *            {@link ResultSet}オブジェクト
	 * @param columnName
	 *            カラム名
	 * @return ResultSetから取得した値
	 * @throws SQLException
	 */
	protected Timestamp getTimestamp(ResultSet rs, String columnName)
			throws SQLException {
		Timestamp result = null;
		if (existColumn(rs, columnName)) {
			// カラム名が存在した場合は値取得
			result = rs.getTimestamp(columnName);
		}
		return result;
	}
}
