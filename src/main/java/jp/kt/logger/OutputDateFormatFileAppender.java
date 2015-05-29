package jp.kt.logger;

import java.io.IOException;

import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/**
 * 出力するタイミングの日時フォーマットファイル名でログ出力するFileAppender.
 * <p>
 * FilePatternで指定したパターンでファイル名を生成します.<br>
 * ファイルのローテーションは行いません.
 * </p>
 * 
 * @author tatsuya.kumon
 */
public final class OutputDateFormatFileAppender extends
		DateFormatFileAppenderBase {
	/**
	 * コンストラクタ.
	 */
	public OutputDateFormatFileAppender() {
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
	public OutputDateFormatFileAppender(Layout layout, String filePattern)
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
	public OutputDateFormatFileAppender(Layout layout, String filePattern,
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
	public OutputDateFormatFileAppender(Layout layout, String filePattern,
			boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		super(layout, filePattern, append, bufferedIO, bufferSize);
	}

	@Override
	protected void subAppend(LoggingEvent event) {
		String fileName = this.generateFileName();
		if (!fileName.equals(super.fileName)) {
			// 前回とファイル名が異なる場合は新しいファイル名に再度セットし直す
			try {
				// 新しいファイル名をセット
				super.setFile(fileName, super.getAppend(), super.bufferedIO,
						super.bufferSize);
			} catch (IOException ioe) {
				LogLog.error("Failed open the file [" + fileName + "].", ioe);
			}
		}
		super.subAppend(event);
	}
}
