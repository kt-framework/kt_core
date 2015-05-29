package jp.kt.mail;

import java.io.IOException;
import java.io.Serializable;

import jp.kt.fileio.FileUtil;

/**
 * 添付ファイルクラス.
 * 
 * @author tatsuya.kumon
 */
class AttachmentFile implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ファイルデータ */
	private byte[] fileData;

	/** ファイル名 */
	private String fileName;

	/**
	 * コンストラクタ.
	 * 
	 * @param filePath
	 *            物理ファイルのパス
	 * @throws IOException
	 */
	public AttachmentFile(String filePath) throws IOException {
		FileUtil f = new FileUtil(filePath);
		this.fileData = f.readAllBytes();
		this.fileName = f.getName();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param fileData
	 *            ファイルデータ
	 * @param fileName
	 *            ファイル名
	 */
	public AttachmentFile(byte[] fileData, String fileName) {
		this.fileData = fileData;
		this.fileName = fileName;
	}

	/**
	 * ファイルデータを返す.
	 * 
	 * @return ファイルデータ
	 */
	public byte[] getFileData() {
		return fileData;
	}

	/**
	 * ファイル名を返す.
	 * 
	 * @return ファイル名
	 */
	public String getFileName() {
		return fileName;
	}
}
