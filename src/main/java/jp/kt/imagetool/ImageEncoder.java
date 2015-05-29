package jp.kt.imagetool;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import jp.kt.exception.KtException;

/**
 * 画像ファイルの加工を行うクラス.
 * 
 * @author tatsuya.kumon
 */
public class ImageEncoder extends BaseEncoder {
	/** 元画像のパス */
	private String inputImagePath;

	/** 出力画像のパス */
	private String outputImagePath;

	/** 出力画像のファイル形式 */
	private ImageFormat format;

	/**
	 * 
	 * @param inputImagePath
	 *            元画像のパス
	 * @param outputImagePath
	 *            出力画像のパス
	 * @param format
	 *            出力画像のファイル形式
	 * @throws KtException
	 * @throws IOException
	 */
	public ImageEncoder(String inputImagePath, String outputImagePath,
			ImageFormat format) throws KtException, IOException {
		// 入力のファイルが存在するかチェック
		super.inputImagePathCheck(inputImagePath);
		// 出力先ディレクトリが存在するかチェック
		super.outputImagePathCheck(outputImagePath);
		// インスタンス変数にセット
		this.inputImagePath = inputImagePath;
		this.outputImagePath = outputImagePath;
		this.format = format;
	}

	/**
	 * 画像フォーマット変換.
	 * 
	 * @throws KtException
	 * @throws IOException
	 */
	public void convertFileFormat() throws KtException, IOException {
		BufferedImage image = getBufferedImage();
		// ファイル出力
		outputFile(image);
	}

	/**
	 * ファイル出力.
	 * 
	 * @param image
	 *            BufferedImage
	 * @throws KtException
	 * @throws IOException
	 */
	private void outputFile(BufferedImage image) throws KtException,
			IOException {
		if (format.equals(ImageFormat.GIF)) {
			// GIFの場合
			outputGif(image);
		} else {
			// GIF以外の場合
			if (!ImageIO.write(image, format.getFormatText(), new File(
					outputImagePath))) {
				throw new KtException("A025", "画像出力に失敗しました:"
						+ outputImagePath);
			}
		}
	}

	/**
	 * GIFファイル出力.
	 * 
	 * @param image
	 * @throws KtException
	 * @throws IOException
	 */
	private void outputGif(BufferedImage image) throws KtException,
			IOException {
		GifAnimationEncoder g = new GifAnimationEncoder();
		g.addImage(image, 0);
		g.encode(outputImagePath);
	}

	/**
	 * 画像ファイルのリサイズ（倍率指定）.
	 * 
	 * @param scale
	 *            倍率（1.0だと等倍、0.5だと半分に縮小）
	 * @throws KtException
	 * @throws IOException
	 */
	public void resizeByScale(double scale) throws KtException,
			IOException {
		BufferedImage image = getBufferedImage();
		// 倍率から横幅と高さを算出
		int width = (int) (image.getWidth() * scale);
		int height = (int) (image.getHeight() * scale);
		// リサイズ
		resize(image, width, height);
	}

	/**
	 * 画像ファイルのリサイズ（横幅指定）.
	 * 
	 * @param width
	 *            横幅（ピクセル）
	 * @throws KtException
	 * @throws IOException
	 */
	public void resizeByWidth(int width) throws KtException,
			IOException {
		BufferedImage image = getBufferedImage();
		// オリジナルのサイズから高さを算出
		double scale = (double) width / image.getWidth();
		int height = (int) (image.getHeight() * scale);
		// リサイズ
		resize(image, width, height);
	}

	/**
	 * 画像ファイルのリサイズ（高さ指定）.
	 * 
	 * @param height
	 *            高さ（ピクセル）
	 * @throws KtException
	 * @throws IOException
	 */
	public void resizeByHeight(int height) throws KtException,
			IOException {
		BufferedImage image = getBufferedImage();
		// オリジナルのサイズから横幅を算出
		double scale = (double) height / image.getHeight();
		int width = (int) (image.getWidth() * scale);
		// リサイズ
		resize(image, width, height);
	}

	/**
	 * 画像ファイルのリサイズ.
	 * 
	 * @param image
	 *            BufferedImage
	 * @param width
	 *            横幅（ピクセル）
	 * @param height
	 *            高さ（ピクセル）
	 * @throws KtException
	 * @throws IOException
	 */
	private void resize(BufferedImage image, int width, int height)
			throws KtException, IOException {
		// サイズ変更実行
		ImageFilter imgfilter = new AreaAveragingScaleFilter(width, height);
		Image createdImage = new Container()
				.createImage(new FilteredImageSource(image.getSource(),
						imgfilter));
		BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setRenderingHints(new RenderingHints(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON));
		graphics2D.drawImage(createdImage, null, null);
		graphics2D.dispose();
		// ファイル出力
		outputFile(bufferedImage);
	}

	/**
	 * 元画像ファイルのBufferedImageオブジェクトを取得するメソッド.
	 * <p>
	 * ImageIO.read() でできるが、OutOfMemoryが出てしまう場合があるため、自前で作成.
	 * </p>
	 * 
	 * @return BufferedImageオブジェクト
	 * @throws IOException
	 */
	private BufferedImage getBufferedImage() throws IOException {
		BufferedImage img = null;
		Iterator<ImageReader> readerIt = ImageIO
				.getImageReadersBySuffix(getExtension(inputImagePath));
		while (readerIt.hasNext()) {
			ImageReader ir = readerIt.next();
			ImageInputStream iis = null;
			try {
				iis = ImageIO.createImageInputStream(new File(inputImagePath));
				ir.setInput(iis, true, true);
				// 画像のロード
				img = ir.read(0);
				if (img != null) {
					break;
				}
			} finally {
				if (iis != null) {
					iis.close();
				}
				if (ir != null) {
					ir.dispose();
				}
			}
		}
		return img;
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
	 * 画像ファイル形式を定義するクラス.
	 * 
	 * @author tatsuya.kumon
	 */
	public final static class ImageFormat implements Serializable {
		private static final long serialVersionUID = -6741939454153547270L;

		/** JPEG形式 */
		public static final ImageFormat JPEG = new ImageFormat("JPEG");

		/** GIF形式 */
		public static final ImageFormat GIF = new ImageFormat("GIF");

		/** PNG形式 */
		public static final ImageFormat PNG = new ImageFormat("PNG");

		/** 画像ファイル形式名 */
		private String formatText;

		/**
		 * privateコンストラクタ.
		 * 
		 * @param formatText
		 *            画像ファイル形式名
		 */
		private ImageFormat(String formatText) {
			this.formatText = formatText;
		}

		/**
		 * 画像ファイル形式名を返す.
		 * 
		 * @return 画像ファイル形式名
		 */
		private String getFormatText() {
			return this.formatText;
		}

		/**
		 * 等価判定.
		 * 
		 * @param format
		 *            ImageFormatオブジェクト
		 * @return 等しければtrue.
		 */
		private boolean equals(ImageFormat format) {
			boolean result = false;
			if (format != null
					&& format.getFormatText().equals(this.formatText)) {
				result = true;
			}
			return result;
		}
	}
}
