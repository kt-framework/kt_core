package jp.kt.logger;

import java.io.IOException;

import org.apache.log4j.Layout;

/**
 * インスタンス化された時点の日時フォーマットファイル名でログ出力するFileAppender.
 * <p>
 * FilePatternで指定したパターンでファイル名を生成します.<br>
 * ファイルのローテーションは行いません.
 * </p>
 *
 * @author tatsuya.kumon
 */
public final class CreateDateFormatFileAppender extends DateFormatFileAppenderBase {
	/**
	 * コンストラクタ.
	 */
	public CreateDateFormatFileAppender() {
	}

	/**
	 * コンストラクタ.
	 *
	 * @param layout
	 *            レイアウト
	 * @param filePattern
	 *            ファイル名のパターン
	 * @throws IOException
	 */
	public CreateDateFormatFileAppender(Layout layout, String filePattern)
			throws IOException {
		super(layout, filePattern);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param layout
	 *            レイアウト
	 * @param filePattern
	 *            ファイル名のパターン
	 * @param append
	 *            追加書きフラグ
	 * @throws IOException
	 */
	public CreateDateFormatFileAppender(Layout layout, String filePattern,
			boolean append) throws IOException {
		super(layout, filePattern, append);
	}

	/**
	 * コンストラクタ.
	 *
	 * @param layout
	 *            レイアウト
	 * @param filePattern
	 *            ファイル名のパターン
	 * @param append
	 *            追加書きフラグ
	 * @param bufferedIO
	 *            バッファフラグ
	 * @param bufferSize
	 *            バッファサイズ
	 * @throws IOException
	 */
	public CreateDateFormatFileAppender(Layout layout, String filePattern,
			boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		super(layout, filePattern, append,bufferedIO, bufferSize);
	}
}
