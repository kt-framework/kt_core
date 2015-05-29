package jp.kt.imagetool;

import java.awt.image.BufferedImage;

/**
 * gif animation のフレームのイメージと設定値を格納するオブジェクトです。<br>
 * このオブジェクトにより1フレーム毎の表示位置、インターレス処理の有無、表示秒数、disposal Method(イメージの重ね方)の設定ができます。
 *
 * @author aiura
 */
class GifAnimationFrame {
	private BufferedImage img;

	private boolean interlace = false;

	private int delayTime = 0;

	/**
	 * ImageオブジェクトからGifAnimationFrameオブジェクトを構築します。<br>
	 * あらかじめ、表示位置は(0,0)、インターレスは無し、表示秒数は0秒、 disposal Methodは無指定に設定されます。
	 *
	 * @param img
	 *            Imageオブジェクト
	 */
	public GifAnimationFrame(BufferedImage img) {
		this.img = img;
	}

	/**
	 * ImageオブジェクトからGifAnimationFrameオブジェクトを構築します。<br>
	 * 表示秒数とdisposal Methodは引数の値に 表示位置は(0,0)、インターレスは無しに設定されます。
	 *
	 * @param img
	 *            Imageオブジェクト
	 * @param delayTime
	 *            1フレームあたりの表示秒数（ミリ秒）
	 */
	public GifAnimationFrame(BufferedImage img, int delayTime) {
		this.img = img;
		this.delayTime = delayTime;
	}

	/**
	 * 1フレームあたりの表示秒数を取得します。
	 *
	 * @return 1フレームあたりの表示秒数
	 */
	public int getDelayTime() {
		return delayTime / 10;
	}

	/**
	 * 1フレームあたりの表示秒数を設定します。
	 *
	 * @param delayTime
	 *            1フレームあたりの表示秒数
	 */
	public void setDelayTime(int delayTime) {
		this.delayTime = delayTime;
	}

	/**
	 * インターレス処理の有無を取得します。
	 *
	 * @return インターレス処理の有無(true:有)
	 */
	public boolean isInterlace() {
		return interlace;
	}

	/**
	 * インターレス処理の有無を設定します。
	 *
	 * @param interlace
	 *            インターレス処理の有無(true:有)
	 */
	public void setInterlace(boolean interlace) {
		this.interlace = interlace;
	}

	/**
	 * 設定したBufferedImageオブジェクトを取得します。
	 *
	 * @return BufferedImageオブジェクト
	 */
	public BufferedImage getImg() {
		return img;
	}
}
