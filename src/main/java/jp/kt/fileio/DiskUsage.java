package jp.kt.fileio;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import jp.kt.exception.KtException;

/**
 * 指定したディレクトリ配下全体のディレクトリ数、ファイル数、合計容量を取得するためのクラス.
 *
 * @author tatsuya.kumon
 */
public final class DiskUsage extends SimpleFileVisitor<Path> {
	/** ディスク情報（集計結果） */
	private DiskInfo diskInfo;

	/**
	 * コンストラクタ.
	 *
	 * @param dir
	 *            対象のディレクトリ
	 */
	private DiskUsage(String dir) {
		FileUtil f = new FileUtil(dir);
		if (!f.isDirectory()) {
			throw new KtException("A015", "ディレクトリではありません[" + dir + "]");
		}
		this.diskInfo = new DiskInfo();
	}

	/**
	 * ディレクトリを再帰的に巡回し、集計する.
	 *
	 * @param dir
	 *            対象のディレクトリ
	 * @return 集計されたディスク情報
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public static DiskInfo execute(String dir) throws IOException {
		// 検索実行
		DiskUsage directoryInfo = new DiskUsage(dir);
		Files.walkFileTree(Paths.get(dir), directoryInfo);
		return directoryInfo.diskInfo;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
			throws IOException {
		// ディレクトリ数インクリメント
		this.diskInfo.addDirectoryCounnt();
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
			throws IOException {
		if (attrs.isDirectory()) {
			// ディレクトリ数インクリメント
			this.diskInfo.addDirectoryCounnt();
		} else {
			// ファイル数インクリメント
			this.diskInfo.addFileCounnt();
			// サイズ加算
			this.diskInfo.addSize(attrs.size());
		}
		return FileVisitResult.CONTINUE;
	}

	/**
	 * 集計結果であるディスク情報.
	 *
	 * @author tatsuya.kumon
	 */
	public final class DiskInfo implements Serializable {
		/** 全ディレクトリ数 */
		private int directoryCount;

		/** 全ファイル数 */
		private int fileCount;

		/** 合計容量（バイト） */
		private long size;

		/**
		 * ディレクトリ数を加算.
		 */
		private void addDirectoryCounnt() {
			this.directoryCount++;
		}

		/**
		 * ファイル数を加算.
		 */
		private void addFileCounnt() {
			this.fileCount++;
		}

		/**
		 * 合計容量を加算.
		 */
		private void addSize(long size) {
			this.size += size;
		}

		/**
		 * ディレクトリ数を返す.
		 *
		 * @return ディレクトリ数
		 */
		public int getDirectoryCount() {
			return directoryCount;
		}

		/**
		 * ファイル数を返す.
		 *
		 * @return ファイル数
		 */
		public int getFileCount() {
			return fileCount;
		}

		/**
		 * 合計容量を返す.
		 *
		 * @return 合計容量（バイト）
		 */
		public long getSize() {
			return size;
		}
	}
}
