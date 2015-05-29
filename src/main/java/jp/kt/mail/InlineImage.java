package jp.kt.mail;

import java.io.IOException;

import jp.kt.tool.StringUtil;

/**
 * インライン画像クラス.
 *
 * @author tatsuya.kumon
 */
public class InlineImage extends AttachmentFile {
	private static final long serialVersionUID = 1L;

	/** Content-IDの後置詞 */
	private static final String CID_POSTFIX = "@mailx.fujitv.co.jp";

	/** Content-ID */
	private String cid;

	/**
	 * コンストラクタ.
	 *
	 * @param filePath
	 *            物理ファイルのパス
	 * @throws IOException
	 */
	public InlineImage(String filePath) throws IOException {
		super(filePath);
		// cidの生成
		createCid();
	}

	/**
	 * コンストラクタ.
	 *
	 * @param fileData
	 *            ファイルデータ
	 * @param fileName
	 *            ファイル名
	 */
	public InlineImage(byte[] fileData, String fileName) {
		super(fileData, fileName);
		// cidの生成
		createCid();
	}

	/**
	 * cidの生成.
	 */
	private void createCid() {
		StringBuilder cid = new StringBuilder();
		cid.append(System.currentTimeMillis());
		cid.append(StringUtil.createRandomText(5));
		cid.append(CID_POSTFIX);
		this.cid = cid.toString();
	}

	/**
	 * cidを返す.
	 *
	 * @return cid
	 */
	public String getCid() {
		return this.cid;
	}
}
