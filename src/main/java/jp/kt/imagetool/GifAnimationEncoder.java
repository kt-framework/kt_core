package jp.kt.imagetool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

import javax.imageio.ImageIO;

import jp.kt.exception.KtException;

/**
 * 複数の静止画像ファイルを結合して、アニメーションGIFを生成するクラスです.
 * <p>
 * 生成元の画像は、GIFでなくても構いません.
 * </p>
 *
 * @author tatsuya.kumon
 */
public class GifAnimationEncoder extends BaseEncoder {
	/** gifImageFrameを入れる可変配列 */
	private Vector<GifAnimationFrame> gifAnimationFrames = new Vector<GifAnimationFrame>();

	/** 1フレームあたりのデフォルト表示秒数（ミリ秒） */
	private int defaultDelay;

	/** ループ回数（0なら無限回） */
	private int loopNumber;

	/** 画像の横幅 */
	private int width = -1;

	/** 画像の高さ */
	private int height = -1;

	/**
	 * コンストラクタ.
	 * <p>
	 * デフォルトでは、ループ回数は無限回、1フレームあたりの表示秒数は0秒に設定されます.
	 * </p>
	 */
	public GifAnimationEncoder() {
		this.defaultDelay = 0;
		this.loopNumber = 0;
	}

	/**
	 * エンコードをし、結果をOutputStreamに出力します.
	 *
	 * @param outputImagePath
	 *            出力先の画像パス
	 * @throws IOException
	 *             出力エラー
	 * @throws KtException
	 *             イメージが存在しないとき
	 */
	public void encode(String outputImagePath) throws IOException, KtException {
		// 出力先ディレクトリが存在するかチェック
		super.outputImagePathCheck(outputImagePath);
		FileOutputStream outs = null;
		try {
			outs = new FileOutputStream(outputImagePath);

			// イメージがない場合はエラー
			if (gifAnimationFrames.size() < 1) {
				throw new KtException("A024",
						"アニメーションGIF作成しようとしましたが、画像が1枚もセットされていません");
			}

			// gifデータを書き込む
			// ここから先はJef PoskanzerさんのGifEncoderのソースを参考にしました.
			// Write the Magic header
			writeString(outs, "GIF89a");

			// Write out the screen width and height
			putWord(width, outs);
			putWord(height, outs);

			// グローバルカラーはなし
			putByte((byte) 0x00, outs);

			// Write out the Background colour
			// グローバルカラーはないのでBackground colourの指定はしない
			putByte((byte) 0, outs);

			// Pixel aspect ratio - 1:1.
			// Putbyte( (byte) 49, outs );
			// Java's GIF reader currently has a bug, if the aspect ratio byte
			// is
			// not zero it throws an ImageFormatException. It doesn't know that
			// 49 means a 1:1 aspect ratio. Well, whatever, zero works with all
			// the other decoders I've tried so it probably doesn't hurt.
			putByte((byte) 0, outs);

			// 繰り返しの制御を行う
			if (loopNumber != 1) {
				putByte((byte) '!', outs);
				putByte((byte) 0xff, outs);
				putByte((byte) 11, outs);
				writeString(outs, "NETSCAPE2.0");
				putByte((byte) 3, outs);
				putByte((byte) 1, outs);
				if (loopNumber > 1) {
					putByte((byte) (0xff & (loopNumber - 1)), outs);
					putByte((byte) (0xff & ((loopNumber - 1) >> 8)), outs);
				} else {
					putByte((byte) 0, outs);
					putByte((byte) 0, outs);
				}
				putByte((byte) 0, outs);
			}

			// イメージをくっつける
			for (int i = 0; i < gifAnimationFrames.size(); i++) {
				GifAnimationFrame myGifAnimationFrame = gifAnimationFrames
						.get(i);

				GifImageDescriptorEncoder gifEncoder = null;
				if (myGifAnimationFrame.getImg() != null) {
					gifEncoder = new GifImageDescriptorEncoder(
							myGifAnimationFrame.getImg(), outs,
							myGifAnimationFrame.isInterlace());
				}
				gifEncoder.setDelayTime(myGifAnimationFrame.getDelayTime());
				gifEncoder.encode();
			}

			// Write the GIF file terminator
			putByte((byte) ';', outs);
		} finally {
			if (outs != null)
				outs.close();
		}
	}

	/**
	 * 1フレームあたりの表示秒数の規定値を設定します.<br>
	 *
	 * @param delayTime
	 *            1フレームあたりの表示秒数（ミリ秒で指定）.<br>
	 *            ただし、1桁目は切り捨てられます.
	 */
	public void setDefaultDelay(int delayTime) {
		// 1/100秒単位にする
		defaultDelay = delayTime;
	}

	/**
	 * ループする回数を指定します.<br>
	 *
	 * @param num
	 *            ループ回数.<br>
	 *            0は無制限.<br>
	 *            一部のブラウザ(Operaなど)では、引数を2以上にした場合にループ回数が(引数-1)になることがあります.
	 */
	public void setLoopNumber(int num) {
		loopNumber = num;
	}

	/**
	 * エンコードするイメージを追加します.<br>
	 * 追加した順にイメージが表示されます.<br>
	 * 1フレームあたりの表示秒数（ミリ秒）デフォルト値が適用されます.
	 *
	 * @param imgPath
	 *            画像ファイルのパス
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public void addImage(String imgPath) throws IOException {
		this.addImage(imgPath, this.defaultDelay);
	}

	/**
	 * エンコードするイメージを追加します.<br>
	 * 追加した順にイメージが表示されます.<br>
	 * 第二引数が1フレームあたりの表示秒数（ミリ秒）となります.
	 *
	 * @param imgPath
	 *            画像ファイルのパス
	 * @param delayTime
	 *            表示時間（ミリ秒）
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public void addImage(String imgPath, int delayTime) throws IOException {
		// 出力先ディレクトリが存在するかチェック
		super.inputImagePathCheck(imgPath);
		BufferedImage image = ImageIO.read(new File(imgPath));
		// BufferedImageをフレームに追加
		addImage(image, delayTime);
	}

	/**
	 * エンコードするイメージを追加します.<br>
	 * 追加した順にイメージが表示されます.<br>
	 * 第二引数が1フレームあたりの表示秒数（ミリ秒）となります.
	 *
	 * @param image
	 *            画像のBufferedImageオブジェクト
	 * @param delayTime
	 *            表示時間（ミリ秒）
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	void addImage(BufferedImage image, int delayTime) throws IOException {
		gifAnimationFrames.add(new GifAnimationFrame(image, delayTime));
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
	}

	/**
	 * エンコードするイメージの枚数を返します.
	 *
	 * @return エンコードするイメージの枚数
	 */
	public int getImageSize() {
		return gifAnimationFrames.size();
	}

	private void writeString(OutputStream out, String str) throws IOException {
		byte[] buf = str.getBytes();
		out.write(buf);
	}

	// Write out a word to the GIF file
	private void putWord(int w, OutputStream outs) throws IOException {
		putByte((byte) (w & 0xff), outs);
		putByte((byte) ((w >> 8) & 0xff), outs);
	}

	// Write out a byte to the GIF file
	private void putByte(byte b, OutputStream outs) throws IOException {
		outs.write(b);
	}
}
