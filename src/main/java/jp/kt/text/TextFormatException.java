package jp.kt.text;

/**
 * テキストフォーマット不正Exception.
 * <p>
 * {@link BaseText#addLineText(String)} や {@link BaseText#setAllText(String)}
 * にて発生する可能性があります.
 * </p>
 *
 * @author tatsuya.kumon
 */
public class TextFormatException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ.
	 *
	 * @param e
	 *            元のExceptionオブジェクト
	 */
	public TextFormatException(Exception e) {
		super(e);
	}
}
