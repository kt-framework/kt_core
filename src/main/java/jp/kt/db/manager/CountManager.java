package jp.kt.db.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import jp.kt.db.entity.BaseEntity;
import jp.kt.db.manager.CountManager.Count;

/**
 * SQLでカウントを取得するためのManagerクラス.
 * <p>
 * メソッドの引数に指定するSQL文は例えば下記のようになります.<br>
 * <br>
 * ■SELECT句<br>
 * SELECT COUNT(*)<br>
 * SELECT A.COUNT(*)<br>
 * SELECT COUNT(B.USER_ID)<br>
 * 別名指定（AS xxx）は付加してはいけません.<br>
 * <br>
 * ■FROM句＆WHERE句<br>
 * FROM ADMIN_USER<br>
 * FROM ADMIN_USER WHERE STATUS = ?
 * </p>
 * 
 * @author tatsuya.kumon
 */
public final class CountManager extends BaseManager<Count> {
	/** SELECT句のCOUNT別名 */
	private static final String COUNT_OTHER_NAME = "COUNT_RESULT";

	/**
	 * コンストラクタ.
	 * 
	 * @param con
	 */
	public CountManager(Connection con) {
		super(con);
	}

	/**
	 * count値を取得する.
	 * 
	 * @param selectBlock
	 *            SELECT句
	 * @param fromWhereBlock
	 *            FROM句とWHERE句
	 * @return カウント
	 * @throws Exception
	 */
	public int getCount(String selectBlock, String fromWhereBlock)
			throws Exception {
		return getCount(selectBlock, fromWhereBlock, null);
	}

	/**
	 * count値を取得する共通処理.
	 * 
	 * @param selectBlock
	 *            SELECT句
	 * @param fromWhereBlock
	 *            FROM句とWHERE句
	 * @param values
	 *            プレースホルダーにセットする値
	 * @return カウント
	 * @throws Exception
	 */
	public int getCount(String selectBlock, String fromWhereBlock,
			Object[] values) throws Exception {
		// SQL文生成
		StringBuilder sql = new StringBuilder();
		sql.append(selectBlock);
		sql.append(" AS ");
		sql.append(COUNT_OTHER_NAME);
		sql.append(" ");
		sql.append(fromWhereBlock);
		// SQL実行
		List<Count> list = null;
		if (values == null) {
			list = executeQuery(sql.toString());
		} else {
			list = executeQuery(sql.toString(), values);
		}
		Count entity = list.get(0);
		return entity.getCount();
	}

	@Override
	protected Count createEntity(ResultSet rs) throws SQLException {
		Count entity = new Count();
		entity.setCount(getInt(rs, COUNT_OTHER_NAME));
		return entity;
	}

	/**
	 * CountManager用Entity.
	 * 
	 * @author tatsuya.kumon
	 */
	final class Count implements BaseEntity {
		private int count;

		public int getCount() {
			return count;
		}

		public void setCount(int count) {
			this.count = count;
		}
	}
}
