package jp.kt.archiver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.tools.tar.TarEntry;
import org.apache.tools.tar.TarInputStream;
import org.apache.tools.tar.TarOutputStream;

/**
 * tar.gz圧縮・解凍ツール.
 * <p>
 * 日本語ファイル名には対応していません.
 * </p>
 * 
 * @author tatsuya.kumon
 */
public final class TarGzArchiver extends BaseArchiver {
	@Override
	public void compress(String baseDirPath, String compFilePath)
			throws Exception {
		// パスチェック
		checkCompressPath(baseDirPath, compFilePath);
		// 出力先ファイル
		try (TarOutputStream tos = new TarOutputStream(new GZIPOutputStream(
				Files.newOutputStream(Paths.get(compFilePath))))) {
			// 出力先 OutputStream を生成
			outputToTarGzStream(tos, Paths.get(baseDirPath),
					Paths.get(baseDirPath));
		}
	}

	/**
	 * 入力ファイルをtar.gzファイル出力ストリームに出力.
	 * 
	 * @param tos
	 *            tar.gzファイル出力ストリーム
	 * @param inputFile
	 *            入力ファイル
	 * @param baseDir
	 *            圧縮対象のベースディレクトリ
	 * @throws IOException
	 */
	private void outputToTarGzStream(TarOutputStream tos, Path inputFile,
			Path baseDir) throws IOException {
		if (Files.isDirectory(inputFile)) {
			/*
			 * ディレクトリの場合
			 */
			// Entry名の生成
			String entryName = createEntryName(inputFile, baseDir);
			// ルートディレクトリを除いてEntryに追加する
			if (!entryName.isEmpty()) {
				// ディレクトリは末尾にスラッシュを付加する
				entryName = entryName + "/";
				// 出力先Entryを設定
				TarEntry entry = new TarEntry(entryName);
				entry.setModTime(Files.getLastModifiedTime(inputFile)
						.toMillis());
				tos.putNextEntry(entry);
				// 書き込んだらEntryをcloseする
				tos.closeEntry();
			}
			// ディレクトリに含まれるファイル分、再帰呼び出し
			try (DirectoryStream<Path> stream = Files
					.newDirectoryStream(inputFile)) {
				for (Path entry : stream) {
					outputToTarGzStream(tos, entry, baseDir);
				}
			}
		} else {
			/*
			 * ファイルの場合
			 */
			try (BufferedInputStream fis = new BufferedInputStream(
					Files.newInputStream(inputFile))) {
				// Entry名称を取得
				String entryName = createEntryName(inputFile, baseDir);
				// 出力先Entryを設定
				TarEntry entry = new TarEntry(entryName);
				entry.setSize(Files.size(inputFile));
				entry.setModTime(Files.getLastModifiedTime(inputFile)
						.toMillis());
				tos.putNextEntry(entry);
				// 入力ファイルを読み込み出力ストリームに書き込んでいく
				int ava = 0;
				while ((ava = fis.available()) > 0) {
					byte[] bs = new byte[ava];
					fis.read(bs);
					tos.write(bs);
				}
				// 書き込んだらEntryをclose
				tos.closeEntry();
			}
		}
	}

	@Override
	public void decompress(String compFilePath, String outputDirPath)
			throws Exception {
		// パスチェック
		checkDecompressPath(compFilePath, outputDirPath);
		// tar.gzファイルからZipEntryを一つずつ取り出し、ファイルに保存していく
		try (TarInputStream tis = new TarInputStream(new GZIPInputStream(
				Files.newInputStream(Paths.get(compFilePath))))) {
			TarEntry entry;
			while ((entry = tis.getNextEntry()) != null) {
				// 出力先パス
				Path outPath = Paths.get(outputDirPath, entry.getName());
				if (entry.isDirectory()) {
					// Entryがディレクトリの場合はディレクトリを作成
					Files.createDirectories(outPath);
				} else {
					// 出力先ファイルの保存先ディレクトリが存在しない場合は、
					// ディレクトリを作成しておく
					if (!Files.exists(outPath.getParent())) {
						Files.createDirectories(outPath.getParent());
					}
					// ファイル出力
					try (BufferedOutputStream bos = new BufferedOutputStream(
							Files.newOutputStream(outPath))) {
						tis.copyEntryContents(bos);
					}
				}
				// 最終更新日時をセット
				Files.setLastModifiedTime(outPath,
						FileTime.fromMillis(entry.getModTime().getTime()));
			}
		}
	}
}
