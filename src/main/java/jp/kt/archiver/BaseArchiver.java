package jp.kt.archiver;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import jp.kt.exception.KtException;
import jp.kt.fileio.FileUtil;
import jp.kt.tool.StringUtil;

/**
 * 圧縮・解凍ツールの基底クラス.
 *
 * @author tatsuya.kumon
 */
abstract class BaseArchiver {
	/**
	 * ディレクトリ以下全てをファイルに圧縮する.
	 *
	 * @param baseDirPath
	 *            圧縮対象のディレクトリパス
	 * @param compFilePath
	 *            圧縮ファイルパス
	 * @throws Exception
	 *             圧縮時に例外発生した場合
	 */
	public abstract void compress(String baseDirPath, String compFilePath)
			throws Exception;

	/**
	 * 圧縮ファイルを解凍する.
	 *
	 * @param compFilePath
	 *            圧縮ファイルのパス
	 * @param outputDirPath
	 *            解凍先ディレクトリパス
	 * @throws Exception
	 *             解凍時に例外発生した場合
	 */
	public abstract void decompress(String compFilePath, String outputDirPath)
			throws Exception;

	/**
	 * Entry名を生成する.
	 *
	 * @param inputFile
	 *            入力ファイル
	 * @param baseDir
	 *            圧縮対象のベースディレクトリ
	 * @return Entry名
	 */
	final String createEntryName(Path inputFile, Path baseDir) {
		// 相対パスにする
		String entryName = inputFile.toAbsolutePath().toString()
				.replace(baseDir.toAbsolutePath().toString(), "");
		// バックスラッシュをスラッシュに変換する
		entryName = StringUtil.replaceAll(entryName, "\\", "/");
		// 先頭のスラッシュをカット
		if (!entryName.isEmpty()) {
			entryName = entryName.substring(1);
		}
		return entryName;
	}

	/**
	 * 圧縮時のパスチェック.
	 * <p>
	 * エラーの場合は {@link KtException} がthrowされる.
	 * </p>
	 *
	 * @param baseDirPath
	 *            圧縮対象のディレクトリパス
	 * @param compFilePath
	 *            圧縮ファイルパス
	 */
	final void checkCompressPath(String baseDirPath, String compFilePath) {
		// 圧縮対象ディレクトリ
		Path d = Paths.get(baseDirPath);
		if (!Files.isDirectory(d)) {
			// 圧縮対象がディレクトリではありません
			throw new KtException("A015", "圧縮対象がディレクトリではありません["
					+ d.toAbsolutePath() + "]");
		}
		// 圧縮ファイルパス
		Path f = Paths.get(compFilePath);
		if (Files.isDirectory(f)) {
			// 圧縮ファイルパスがディレクトリとして存在している
			throw new KtException("A014", "圧縮ファイルのパスがディレクトリとして存在しています["
					+ f.toAbsolutePath() + "]");
		}
	}

	/**
	 * 解凍時のパスチェック.
	 * <p>
	 * エラーの場合は {@link KtException} がthrowされる.
	 * </p>
	 *
	 * @param compFilePath
	 *            圧縮ファイルのパス
	 * @param outputDirPath
	 *            解凍先ディレクトリパス
	 */
	final void checkDecompressPath(String compFilePath, String outputDirPath) {
		// 圧縮ファイルの存在チェック
		FileUtil f = new FileUtil(compFilePath);
		if (!f.isFile()) {
			// 圧縮ファイルが存在しない
			throw new KtException("A014", "圧縮ファイルのパスは存在しない、もしくはファイルではありません["
					+ f.getPath() + "]");
		}
		// 解凍先ディレクトリ存在チェック
		FileUtil d = new FileUtil(outputDirPath);
		if (!d.isDirectory()) {
			// 解凍先ディレクトリが存在しない
			throw new KtException("A015", "解凍先ディレクトリが存在しません[" + d.getPath()
					+ "]");
		}
	}
}
