package jp.kt.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * テキストデータを保持するベースクラス.
 * 
 * @author tatsuya.kumon
 */
public abstract class BaseText {
	/**
	 * テキスト内容を保持する変数
	 */
	private List<String[]> content;

	/**
	 * コンストラクタ.
	 */
	BaseText() {
		// Listをインスタンス化
		this.content = new ArrayList<String[]>();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param allText
	 *            全文
	 * @throws IOException
	 */
	BaseText(String allText) throws IOException {
		this.setAllText(allText);
	}

	/**
	 * 行データを追加する.
	 * 
	 * @param lineData
	 *            セットするデータ
	 */
	public final void addLineData(Object[] lineData) {
		String[] lineDataArray = new String[lineData.length];
		// nullの場合は空文字に変換してセット
		for (int i = 0; i < lineData.length; i++) {
			lineDataArray[i] = (lineData[i] == null ? "" : lineData[i]
					.toString());
		}
		content.add(lineDataArray);
	}

	/**
	 * 行テキストを適正に変換してデータとしてセットする.
	 * <p>
	 * テキスト形式によっては、不正なテキストをこのメソッドに渡すと<br>
	 * {@link TextFormatException} が発生する可能性があります.
	 * </p>
	 * 
	 * @param lineText
	 *            行全体のテキスト
	 */
	public abstract void addLineText(String lineText);

	/**
	 * 現在の行数を返す.
	 * 
	 * @return 現在の行数
	 */
	public final int getLineCount() {
		return content.size();
	}

	/**
	 * 指定行のテキストを出力.
	 * 
	 * @param rowIndex
	 *            指定行番号
	 * @return 指定行の出力テキスト
	 */
	public abstract String getLineText(int rowIndex);

	/**
	 * 指定行のデータを返す.
	 * 
	 * @param lineIndex
	 *            指定行番号
	 * @return 指定行のデータ
	 */
	public final String[] getLineData(int lineIndex) {
		return content.get(lineIndex);
	}

	/**
	 * テキスト全文を返す.
	 * <p>
	 * 最終行には改行は付きません.
	 * </p>
	 * 
	 * @return テキスト全文
	 */
	public final String getAllText() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < getLineCount(); i++) {
			// 1行分のテキストと改行をappend
			sb.append(getLineText(i));
			if (i < getLineCount() - 1) {
				// 最終行以外は改行を付ける
				sb.append(System.getProperty("line.separator"));
			}
		}
		return sb.toString();
	}

	/**
	 * テキスト全文をセットする.
	 * <p>
	 * テキスト形式によっては、不正なテキストをこのメソッドに渡すと<br>
	 * {@link TextFormatException} が発生する可能性があります.<br>
	 * <br>
	 * ===========================<br>
	 * 2012/10/17追記<br>
	 * このメソッドは使わないでください.<br>
	 * String引数付きのコンストラクタを使ってください.<br>
	 * いずれprivateメソッドにします.
	 * </p>
	 * 
	 * @param allText
	 *            全文
	 * @throws IOException
	 */
	public final void setAllText(String allText) throws IOException {
		// ファイル内容をクリア
		this.content = new ArrayList<String[]>();
		// 1行ごとに分割してセット
		StringReader sr = null;
		BufferedReader br = null;
		try {
			sr = new StringReader(allText);
			br = new BufferedReader(sr);
			String line;
			while ((line = br.readLine()) != null) {
				// 1行分のデータを追加
				this.addLineText(line);
			}
		} finally {
			// 各close処理
			if (br != null)
				br.close();
			if (sr != null)
				sr.close();
		}
	}

	/**
	 * コンテンツの空判定.
	 * 
	 * @return コンテンツが空ならtrue.
	 */
	public final boolean isEmpty() {
		if (this.content == null || this.content.size() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * テキスト内容を取得.
	 * <p>
	 * {@link Sort}クラスのためのメソッド.
	 * </p>
	 * 
	 * @return テキスト内容
	 */
	final List<String[]> getContent() {
		return this.content;
	}
}
