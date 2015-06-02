package jp.kt.fileio;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import jp.kt.exception.KtException;

/**
 * ファイルロック.
 * <p>
 * ファイルに対してロックをかけたいときに使います.<br>
 * {@link FileLock#lock()} メソッドを実行すると、ロック対象ファイルと同じディレクトリに、<br>
 * <blockquote> [対象ファイル名].ktlck0 </blockquote> というロックファイルを生成します.<br>
 * このロックファイルが現在処理中のロックファイルということになります.<br>
 * <br>
 * ロック中にさらに他のスレッドが {@link FileLock#lock()} メソッドを実行すると、<br>
 * <blockquote> [対象ファイル名].ktlck1 </blockquote>
 * というファイルが生成され、順次ファイル名の末尾がインクリメントされ待ち状態になります.<br>
 * <br>
 * 待ち状態のスレッドはひとつ前のファイルを監視しており、無くなったら（ロック解除されたら）リネームしていきます.
 *
 * @author tatsuya.kumon
 */
public class FileLock implements Serializable {
	private static final long serialVersionUID = 1L;

	/** ディレクトリ */
	private String dir;

	/** ファイル名 */
	private String fileName;

	/** ロックファイル番号（0が処理中、1以降が待機中） */
	private int lockFileNum = -1;

	/** ロックタイムアウト（ミリ秒） */
	private int timeoutMillis;

	/** ロックファイル名の接尾辞 */
	private static final String LOCK_FILE_SUFFIX = ".ktlck";

	/**
	 * コンストラクタ.
	 *
	 * @param filePath
	 *            ロック対象ファイルのパス
	 * @param timeoutSec
	 *            ロックタイムアウト（秒）
	 */
	public FileLock(String filePath, int timeoutSec) {
		FileUtil f = new FileUtil(filePath);
		if (!f.isFile()) {
			throw new KtException("A014", "指定されたパスは存在しない、もしくはファイルではありません ["
					+ filePath + "]");
		}
		// ファイル名と親ディレクトリパスをインスタンス変数にセット
		this.fileName = f.getName();
		f.setParentPath();
		this.dir = f.getPath();
		// ロック待ち時間はミリ秒に変換してインスタンス変数にセット
		this.timeoutMillis = timeoutSec * 1000;
	}

	/**
	 * ロック解除されるまで待つ.
	 * <p>
	 * ファイル読み込み時にロックされている場合、読込エラーになってしまう恐れがあるので、このメソッドでロック解除を待ちます.<br>
	 * ただし、タイムアウト時間を超えた場合は {@link KtException} をthrowします.
	 * </p>
	 *
	 * @throws Exception
	 *             ロックファイルが一定時間以上経過しても消えない場合<br>
	 *             sleep時に例外発生した場合
	 */
	public void waitRelease() throws Exception {
		// ロックファイルのFile作成
		File f = new File(dir, fileName + LOCK_FILE_SUFFIX + 0);
		long start = System.currentTimeMillis();
		while (f.exists()) {
			// タイムアウト設定以上経過してもロックファイルが消えない場合はException
			if (System.currentTimeMillis() - start > timeoutMillis) {
				throw new KtException("A051", "ロックファイルが" + timeoutMillis
						+ "ミリ秒以上経過しても消えません [" + f.getAbsolutePath() + "]");
			}
			// ロックファイルが存在する間は、0.1秒sleepしつつループ
			Thread.sleep(100);
		}
	}

	/**
	 * 対象ファイルをロックする.
	 * <p>
	 * 既にロックされている場合は、解除されるか最大待ち時間経過まで待つ.<br>
	 * </p>
	 *
	 * @throws IOException
	 *             入出力エラーが発生した場合
	 */
	public void lock() throws IOException {
		/*
		 * 念のためディレクトリの存在確認
		 */
		FileUtil d = new FileUtil(dir);
		if (!d.isDirectory()) {
			throw new KtException("A015", "ディレクトリではありません [" + dir + "]");
		}
		/*
		 * ロックファイルの生成
		 */
		for (int i = 0; i < 100; i++) {
			// ロックファイルのFile作成
			File f = new File(dir, fileName + LOCK_FILE_SUFFIX + i);
			// ロックファイル作成
			if (f.createNewFile()) {
				// ロックファイル作成成功
				// ロックファイル番号を保持
				this.lockFileNum = i;
				break;
			} else {
				// 既にその番号のロックファイルは存在したので次の番号でリトライ
				continue;
			}
		}
		if (lockFileNum < 0) {
			// ロックできなかったらException
			throw new KtException("A030", "ファイルロックができませんでした [" + dir + fileName
					+ "]");
		}
		/*
		 * ロックファイル番号が0になるまでループし続ける
		 */
		while (lockFileNum > 0) {
			/*
			 * ひとつ前のロックファイルが解除されるまで、もしくはロックタイムアウトまで待つ
			 */
			// ひとつ前の番号のロックファイルの作成を試みる
			File next = new File(dir, fileName + LOCK_FILE_SUFFIX
					+ (lockFileNum - 1));
			while (true) {
				try {
					if (next.createNewFile()) {
						// ひとつ前のロックファイル作成に成功したらbreak
						break;
					}
				} catch (Exception e) {
					// Exception発生はファイル作成失敗と同等とするため
					// catchして何もしない
				}
				// ひとつ前のファイルの最終更新日を元にタイムアウト判定
				long nextFileLastModified = next.lastModified();
				long now = System.currentTimeMillis();
				if (now - nextFileLastModified >= this.timeoutMillis) {
					// ロックタイムアウトしたら、強制的に前のファイルを削除する
					next.delete();
				}
				// sleep
				try {
					Thread.sleep(500);
				} catch (Exception e) {
				}
			}
			// 元のロックファイルを削除
			release();
			// ロックファイル番号をデクリメント
			this.lockFileNum--;
		}
	}

	/**
	 * ロックを解除する.
	 */
	public void release() {
		File f = new File(dir, fileName + LOCK_FILE_SUFFIX + lockFileNum);
		f.delete();
	}
}
