package jp.kt.logger;

import jp.kt.tool.Validator;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.varia.NullAppender;

/**
 * ログ出力クラス.
 *
 * @author tatsuya.kumon
 */
public class ApplicationLogger {
	/** org.apache.log4j.Logger */
	private Logger logger;

	/** 未出力のLogger */
	public static final ApplicationLogger NULL_LOGGER;

	/** 標準出力のLogger */
	public static final ApplicationLogger CONSOLE_LOGGER;

	static {
		// NULL_LOGGER
		Logger nullLogger = Logger.getLogger(ApplicationLogger.class);
		nullLogger.addAppender(new NullAppender());
		NULL_LOGGER = new ApplicationLogger(nullLogger);
		// CONSOLE_LOGGER
		Logger consoleLogger = Logger.getLogger(ApplicationLogger.class);
		consoleLogger.addAppender(new ConsoleAppender(new PatternLayout(
				"%d %5p - %m%n")));
		CONSOLE_LOGGER = new ApplicationLogger(consoleLogger);
	}

	/**
	 * privateコンストラクタ.
	 *
	 * @param logger
	 *            Logger
	 */
	private ApplicationLogger(Logger logger) {
		this.logger = logger;
	}

	/**
	 * コンストラクタ.
	 *
	 * @param loggerName
	 *            log4j.propertiesに記載したlogger名.
	 * @param loggerClass
	 *            ログ出力クラス.
	 */
	public ApplicationLogger(String loggerName, Class<?> loggerClass) {
		logger = Logger.getLogger(loggerName + "." + loggerClass.getName());
	}

	/**
	 * log4jログ出力.<br>
	 * レベル=DEBUG.
	 *
	 * @param message
	 *            メッセージ
	 */
	public void debugLog(Object message) {
		logger.debug(message);
	}

	/**
	 * log4jログ出力.<br>
	 * レベル=INFO.
	 *
	 * @param code
	 *            メッセージコード
	 * @param message
	 *            メッセージ
	 */
	public void infoLog(String code, Object message) {
		logger.info(createLogMessage(code, message));
	}

	/**
	 * log4jログ出力.<br>
	 * レベル=WARN.
	 *
	 * @param code
	 *            メッセージコード
	 * @param message
	 *            メッセージ
	 */
	public void warnLog(String code, Object message) {
		logger.warn(createLogMessage(code, message));
	}

	/**
	 * log4jログ出力.<br>
	 * レベル=WARN.
	 *
	 * @param code
	 *            メッセージコード
	 * @param message
	 *            メッセージ
	 * @param e
	 *            Throwableオブジェクト
	 */
	public void warnLog(String code, Object message, Throwable e) {
		logger.warn(createLogMessage(code, message, e));
	}

	/**
	 * log4jログ出力.<br>
	 * レベル=ERROR.
	 *
	 * @param code
	 *            メッセージコード
	 * @param message
	 *            メッセージ
	 */
	public void errorLog(String code, Object message) {
		logger.error(createLogMessage(code, message));
	}

	/**
	 * log4jログ出力.<br>
	 * レベル=ERROR.<br>
	 * Exception付き.
	 *
	 * @param code
	 *            メッセージコード
	 * @param message
	 *            メッセージ
	 * @param e
	 *            Throwableオブジェクト
	 */
	public void errorLog(String code, Object message, Throwable e) {
		logger.error(createLogMessage(code, message, e), e);
	}

	/**
	 * ログメッセージ作成.
	 *
	 * @param code
	 *            メッセージコード
	 * @param message
	 *            メッセージ
	 * @return ログ出力メッセージ
	 */
	private String createLogMessage(String code, Object message) {
		StringBuffer msg = new StringBuffer();
		if (!Validator.isEmpty(code)) {
			msg.append("[");
			msg.append(code);
			msg.append("]");
		}
		msg.append(message);
		return msg.toString();
	}

	/**
	 * ログメッセージ作成.
	 *
	 * @param code
	 *            メッセージコード
	 * @param message
	 *            メッセージ
	 * @param e
	 *            Throwableオブジェクト
	 * @return ログ出力メッセージ
	 */
	private String createLogMessage(String code, Object message, Throwable e) {
		StringBuffer msg = new StringBuffer();
		msg.append("[");
		msg.append(e.getClass().getName());
		msg.append("]");
		msg.append(message);
		// 原因Throwableがあればメッセージを連結して出力
		Throwable cause = e;
		while (true) {
			// 原因エラーを取得
			cause = cause.getCause();
			if (cause == null) {
				break;
			}
			msg.append(" | [");
			msg.append(cause.getClass().getName());
			msg.append("]");
			msg.append(cause.getMessage());
		}
		return createLogMessage(code, msg.toString());
	}
}
