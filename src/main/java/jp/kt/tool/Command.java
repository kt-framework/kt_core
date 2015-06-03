package jp.kt.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import jp.kt.logger.ApplicationLogger;
import jp.kt.prop.KtProperties;
import jp.kt.text.PlainText;

/**
 * ネイティブコマンドの実行クラス.
 *
 * @author tatsuya.kumon
 */
public class Command extends Thread {
	/** logger名 */
	private static final String LOGGER_NAME = "ktCommand";

	/** 独自ログのLogger */
	private ApplicationLogger logger;

	/** コマンド */
	private String command;

	/** 文字コード */
	private String charset;

	/**
	 * コンストラクタ.
	 *
	 * @param command
	 *            コマンド
	 * @param orgClass
	 *            呼び出し元クラス
	 */
	private Command(String command, Class<?> orgClass) {
		this.command = command;
		this.charset = KtProperties.getInstance().getString(
				"kt.core.command.output.charset");
		this.logger = new ApplicationLogger(LOGGER_NAME, orgClass);
	}

	/**
	 * 実行メソッド.
	 */
	public void run() {
		try {
			execute();
		} catch (IOException e) {
			e.printStackTrace();
			logger.errorLog("A032", "コマンド実行エラー[コマンド=" + command + "]", e);
		}
	}

	/**
	 * コマンド実行
	 *
	 * @return 出力と実行時間を示すテキスト
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	private Result execute() throws IOException {
		/*
		 * コマンド実行
		 */
		long start = System.currentTimeMillis();
		Process process = Runtime.getRuntime().exec(command);
		long end = System.currentTimeMillis();
		/*
		 * 標準出力取得
		 */
		String output = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					process.getInputStream(), charset));
			// 標準出力を１行づつ取り出します
			String line;
			PlainText text = new PlainText();
			while ((line = br.readLine()) != null) {
				text.addLineText(line);
			}
			output = text.getAllText();
		} finally {
			br.close();
		}
		/*
		 * エラー出力取得
		 */
		String errOutput = null;
		try {
			br = new BufferedReader(new InputStreamReader(
					process.getErrorStream(), charset));
			// 標準出力を１行づつ取り出します
			String line;
			PlainText text = new PlainText();
			while ((line = br.readLine()) != null) {
				text.addLineText(line);
			}
			errOutput = text.getAllText();
		} finally {
			br.close();
		}

		// 実行結果クラスの生成
		Result result = new Result(command, end - start, output, errOutput);
		// 独自ログ出力
		outputLog(result);
		return result;
	}

	/**
	 * 実行結果ログ出力
	 *
	 * @param result
	 *            実行結果
	 */
	private void outputLog(Result result) {
		long threadId = super.getId();
		logger.infoLog("A033",
				"[ThreadId=" + threadId + "][コマンド=" + result.getCommand() + "]");
		logger.infoLog("A033",
				"[ThreadId=" + threadId + "][実行時間=" + result.getTime() + "ms]");
		logger.infoLog("A033",
				"[ThreadId=" + threadId + "][出力結果]\n" + result.getOutput());
		logger.infoLog("A033",
				"[ThreadId=" + threadId + "][エラー出力]\n" + result.getErrOutput());
	}

	/**
	 * 同期実行.
	 * <p>
	 * 文字コード未指定版.<br>
	 * 文字コードはapplication.propertiesで指定したデフォルト文字コードになります.<br>
	 * <br>
	 * 実行結果は、返り値である {@link Result}オブジェクト、ならびに log4j.propertiesにて指定された logger =
	 * ktCommandLog に対して出力されます.
	 * </p>
	 *
	 * @param command
	 *            コマンド
	 * @param orgClass
	 *            呼び出し元クラス
	 * @return 実行結果
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public static Result executeSynchronous(String command, Class<?> orgClass)
			throws IOException {
		return new Command(command, orgClass).execute();
	}

	/**
	 * 非同期実行.
	 * <p>
	 * 文字コード未指定版.<br>
	 * 文字コードはapplication.propertiesで指定したデフォルト文字コードになります.<br>
	 * <br>
	 * 実行結果は、log4j.propertiesにて指定された logger = ktCommandLog に対して出力されます.
	 * </p>
	 *
	 * @param command
	 *            コマンド
	 * @param orgClass
	 *            呼び出し元クラス
	 */
	public static void executeAsynchronous(String command, Class<?> orgClass) {
		new Command(command, orgClass).start();
	}

	/**
	 * コマンド実行結果.
	 *
	 * @author tatsuya.kumon
	 */
	public class Result implements Serializable {
		private static final long serialVersionUID = 1L;

		private String command;
		private long time;
		private String output;
		private String errOutput;

		/**
		 * コンストラクタ.
		 *
		 * @param command
		 *            コマンド
		 * @param time
		 *            実行時間
		 * @param output
		 *            出力
		 * @param errOutput
		 *            エラー出力
		 */
		private Result(String command, long time, String output,
				String errOutput) {
			this.command = command;
			this.time = time;
			this.output = output;
			this.errOutput = errOutput;
		}

		/**
		 * 実行したコマンドを取得する.
		 *
		 * @return command
		 */
		public String getCommand() {
			return command;
		}

		/**
		 * 実行時間（ms）を取得する.
		 *
		 * @return time
		 */
		public long getTime() {
			return time;
		}

		/**
		 * 出力結果を取得する.
		 *
		 * @return output
		 */
		public String getOutput() {
			return output;
		}

		/**
		 * エラー出力を取得する.
		 *
		 * @return errOutput
		 */
		public String getErrOutput() {
			return errOutput;
		}
	}
}
