package jp.kt.exception;

/**
 * アプリケーション警告をあらわすException.
 * <p>
 * これをthrowすると、WARNINGレベルでログ出力されます.
 * </p>
 *
 * @author tatsuya.kumon
 */
public class KtWarningException extends KtException {
	/**
	 * コンストラクタ.
	 *
	 * @param code
	 *            メッセージコード
	 * @param message
	 *            メッセージ
	 */
	public KtWarningException(String code, String message) {
		super(code, message);
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
	public KtWarningException(String code, String message, Throwable cause) {
		super(code, message, cause);
	}
}
