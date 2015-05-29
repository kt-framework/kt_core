package jp.kt.tool;

/**
 * カレントスレッドに関するユーティリティクラス.
 * 
 * @author tatsuya.kumon
 */
public final class ThreadUtil {
	/**
	 * このメソッドを呼び出したクラス名、メソッド名、ファイル名、行数の情報を取得します。
	 * 
	 * @return メソッド名、ファイル名、行数の情報文字列
	 */
	public static String calledAt() {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		return createText(ste);
	}

	/**
	 * このメソッドを呼び出したメソッドの呼び出し元のクラス名、メソッド名、ファイル名、行数の情報を取得します。
	 * 
	 * @return メソッド名、ファイル名、行数の情報文字列
	 */
	public static String calledFrom() {
		StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
		if (steArray.length <= 3) {
			return "";
		}
		StackTraceElement ste = steArray[3];
		return createText(ste);
	}

	/**
	 * {@link StackTraceElement}から文字列を生成する.
	 * 
	 * @param ste
	 *            {@link StackTraceElement}オブジェクト
	 * @return スレッド情報文字列
	 */
	private static String createText(StackTraceElement ste) {
		StringBuilder sb = new StringBuilder();
		sb.append("\tat ").append(ste.getClassName()).append(".") // タブ、at、半角スペース、クラス名、ドット
				.append(ste.getMethodName()) // メソッド名取得
				.append("(").append(ste.getFileName()) // ファイル名取得
				.append(":").append(ste.getLineNumber()) // 行番号取得
				.append(")");
		return sb.toString();
	}
}
