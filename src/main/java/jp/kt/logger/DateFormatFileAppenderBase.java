package jp.kt.logger;

import java.io.IOException;
import java.util.Date;

import jp.kt.tool.TextFormat;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.ErrorCode;

/**
 * 日時フォーマットファイル名でログ出力するFileAppenderの基底クラス.
 * <p>
 * FilePatternで指定したパターンでファイル名を生成します.<br>
 * ファイルのローテーションは行いません.
 * </p>
 * <p>
 * ※参考ページ<br>
 * http://tips.crosslaboratory.com/post/log4j_daily_file_appender/
 * </p>
 *
 * @author tatsuya.kumon
 */
public abstract class DateFormatFileAppenderBase extends FileAppender {
	/** ファイル名のパターン */
	private String filePattern;

	/** クラスロード日時 */
	private static Date loadDate = new Date();

	/**
	 * コンストラクタ.
	 */
	public DateFormatFileAppenderBase() {
	}

	/**
	 * コンストラクタ.
	 *
	 * @param layout
	 *            レイアウト
	 * @param filePattern
	 *            ファイル名のパターン
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public DateFormatFileAppenderBase(Layout layout, String filePattern)
			throws IOException {
		this(layout, filePattern, true);
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
	 *             入出力エラーが発生した場合
	 */
	public DateFormatFileAppenderBase(Layout layout, String filePattern,
			boolean append) throws IOException {
		this(layout, filePattern, append, append, 8 * 1024);
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
	 *             入出力エラーが発生した場合
	 */
	public DateFormatFileAppenderBase(Layout layout, String filePattern,
			boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		super.layout = layout;
		this.filePattern = filePattern;
		super.setFile(this.generateFileName(), append, bufferedIO, bufferSize);
	}

	/**
	 * ファイル名のパターンを取得する.
	 *
	 * @return ファイル名のパターン
	 */
	public final String getFilePattern() {
		return filePattern;
	}

	/**
	 * ファイル名のパターンをセットする.
	 *
	 * @param filePattern
	 *            ファイル名のパターン
	 */
	public final void setFilePattern(String filePattern) {
		this.filePattern = filePattern.trim();
	}

	/**
	 * システム日付からファイル名を生成.
	 *
	 * @return ファイル名
	 */
	protected final String generateFileName() {
		return TextFormat.formatDate(loadDate, this.filePattern);
	}

	@Override
	public final void activateOptions() {
		if (this.filePattern != null) {
			try {
				super.fileName = this.generateFileName();
				super.setFile(super.fileName, super.fileAppend,
						super.bufferedIO, super.bufferSize);
			} catch (java.io.IOException e) {
				errorHandler.error("setFile(" + fileName + "," + fileAppend
						+ ") call failed.", e, ErrorCode.FILE_OPEN_FAILURE);
			}
		} else {
			LogLog.error("File option not set for appender [" + name + "].");
		}
	}
}
