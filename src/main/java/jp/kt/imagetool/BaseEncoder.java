package jp.kt.imagetool;

import java.io.IOException;

import jp.kt.exception.KtException;
import jp.kt.fileio.FileUtil;

/**
 * 画像変換ツールの基底クラス.
 *
 * @author tatsuya.kumon
 */
abstract class BaseEncoder {
	/**
	 * 入力画像パスチェック.
	 *
	 * @param inputImagePath
	 *            入力元画像パス
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	void inputImagePathCheck(String inputImagePath) throws IOException {
		// 入力のファイルが存在するかチェック
		FileUtil fileUtil = new FileUtil(inputImagePath);
		if (!fileUtil.isFile()) {
			throw new KtException("A014", "元画像のパスは存在しない、もしくはファイルではありません:"
					+ fileUtil.getPath());
		}
	}

	/**
	 * 出力画像パスチェック.
	 *
	 * @param outputImagePath
	 *            出力画像パス
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	void outputImagePathCheck(String outputImagePath) throws IOException {
		// 出力先ディレクトリが存在するかチェック
		FileUtil fileUtil = new FileUtil(outputImagePath);
		fileUtil.setParentPath();
		if (!fileUtil.isDirectory()) {
			throw new KtException("A015",
					"出力先ディレクトリのパスは存在しない、もしくはディレクトリではありません:"
							+ fileUtil.getPath());
		}
	}
}
