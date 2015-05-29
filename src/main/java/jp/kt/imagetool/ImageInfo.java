package jp.kt.imagetool;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import jp.kt.exception.KtException;
import jp.kt.fileio.FileUtil;

/**
 * 画像情報を取得するクラス.
 * 
 * @author tatsuya.kumon
 */
public class ImageInfo implements Serializable {
	/** 画像の幅 */
	private int width;

	/** 画像の高さ */
	private int height;

	/**
	 * コンストラクタ.
	 * 
	 * @param imagePath
	 *            画像パス
	 * @throws IOException
	 */
	public ImageInfo(String imagePath) throws IOException {
		FileUtil f = new FileUtil(imagePath);
		if (!f.isFile()) {
			// 画像ファイルが存在しない
			throw new KtException("A014",
					"指定されたパスは存在しない、もしくはファイルではありません:" + imagePath);
		}
		// 画像のロード
		Iterator<ImageReader> readerIt = ImageIO
				.getImageReadersBySuffix(getExtension(imagePath));
		if (readerIt.hasNext()) {
			ImageReader ir = readerIt.next();
			ImageInputStream iis = null;
			try {
				iis = ImageIO.createImageInputStream(new File(imagePath));
				ir.setInput(iis, true, true);
				// 画像のサイズを取得（ヘッダ情報を見ているだけで画像はロードしていない）
				this.width = ir.getWidth(0);
				this.height = ir.getHeight(0);
			} finally {
				if (iis != null) {
					iis.close();
				}
				if (ir != null) {
					ir.dispose();
				}
			}
		} else {
			// サポートされていない画像形式である
			throw new KtException("A036", "サポートされていない画像形式です:"
					+ imagePath);
		}
	}

	/**
	 * ファイル名の拡張子を抜き出す.
	 * 
	 * @param fileName
	 *            ファイル名
	 * @return 拡張子
	 */
	private String getExtension(String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index < 0) {
			return "";
		} else {
			return fileName.substring(index + 1);
		}
	}

	/**
	 * 画像の幅（px）を取得.
	 * 
	 * @return 画像の幅
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * 画像の高さ（px）を取得.
	 * 
	 * @return 画像の高さ
	 */
	public int getHeight() {
		return this.height;
	}
}
