package org.json;

/**
 * The JSONException is thrown by the JSON.org classes when things are amiss.
 *
 * @author JSON.org
 * @version 2010-12-24
 */
public class JSONException extends Exception {
	private static final long serialVersionUID = 0;
	private Throwable cause;

	/**
	 * Constructs a JSONException with an explanatory message.
	 *
	 * @param message
	 *            Detail about the reason for the exception.
	 */
	public JSONException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param cause
	 *            元の例外
	 */
	public JSONException(Throwable cause) {
		super(cause.getMessage());
		this.cause = cause;
	}

	/**
	 * 元の例外を取得する.
	 *
	 * @return 元の例外
	 */
	public Throwable getCause() {
		return this.cause;
	}
}
