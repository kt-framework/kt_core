package jp.kt.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.activation.DataSource;

/**
 * バイトデータをメールの添付ファイルとして扱うためのクラス. <br>
 * javax.activation.DataSourceをimplementsしています.
 *
 * @author tatsuya.kumon
 */
class ByteArrayDataSource implements DataSource {
	/** バイトデータ */
	private byte[] data;

	/** コンテントタイプ */
	private String type;

	/**
	 * コンストラクタ.
	 *
	 * @param data
	 *            バイトデータ
	 * @param type
	 *            コンテントタイプ
	 */
	public ByteArrayDataSource(byte[] data, String type) {
		this.data = data;
		this.type = type;
	}

	/**
	 * データのInputStreamを返す.
	 *
	 * @return InputStream
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public InputStream getInputStream() throws IOException {
		if (data == null)
			throw new IOException("no data");
		return new ByteArrayInputStream(data);
	}

	/**
	 * コンテントタイプを返す.
	 *
	 * @return String
	 */
	public String getContentType() {
		return type;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		throw new IOException("cannot do this");
	}

	@Override
	public String getName() {
		return "dummy";
	}
}
