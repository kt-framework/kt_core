package jp.kt.exception;

/**
 * アプリケーションエラーをあらわすException.
 *
 * @author tatsuya.kumon
 */
public class KtException extends RuntimeException {
	/** メッセージコード */
	private String code;

	/**
	 * コンストラクタ.
	 *
	 * @param code
	 *            メッセージコード
	 * @param message
	 *            メッセージ
	 */
	public KtException(String code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * コンストラクタ.
	 *
	 * @param code
	 *            メッセージコード
	 * @param message
	 *            メッセージ
	 * @param cause
	 *            原因となるThrowableオブジェクト
	 */
	public KtException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	/**
	 * メッセージコードを取得.
	 *
	 * @return メッセージコード
	 */
	public String getCode() {
		return code;
	}
}
