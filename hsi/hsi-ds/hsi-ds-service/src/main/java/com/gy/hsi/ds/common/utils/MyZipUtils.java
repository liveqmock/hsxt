package com.gy.hsi.ds.common.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import com.github.knightliao.apollo.utils.io.OsUtil;
import com.gy.hsi.common.utils.StringUtils;

public class MyZipUtils {
	/**
	 * 压缩文件或者目录
	 * 
	 * @param baseDirName
	 *            压缩的根目录
	 * @param fileName
	 *            根目录下待压缩的文件或文件夹名， 星号*表示压缩根目录下的全部文件。
	 * @param targetFileName
	 *            目标ZIP文件
	 */
	public static void zipFile(String baseDirName, String fileName,
			String targetFileName) {
		// 检测根目录是否存在
		if (baseDirName == null) {
			System.out.println("压缩失败，根目录不存在：" + baseDirName);
			
			return;
		}
		
		File baseDir = new File(baseDirName);
		
		if (!baseDir.exists() || (!baseDir.isDirectory())) {
			System.out.println("压缩失败，根目录不存在：" + baseDirName);
			
			return;
		}
		
		// if (!baseDirName.endsWith("/")) {
		// baseDirName += "/";
		// }
		
		String baseDirPath = baseDir.getAbsolutePath();
		// 目标文件
		File targetFile = new File(targetFileName);
		
		try {
			// 创建一个zip输出流来压缩数据并写入到zip文件
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					targetFile));
			
			if ("*".equals(fileName) || StringUtils.isEmpty(fileName)) {
				// 将baseDir目录下的所有文件压缩到ZIP
				dirToZip(baseDirPath, baseDir, out);
			} else {
				File file = new File(baseDir, fileName);
				if (file.isFile()) {
					fileToZip(baseDirPath, file, out);
				} else {
					dirToZip(baseDirPath, file, out);
				}
			}
			
			out.close();
			
			System.out.println("压缩文件成功，目标文件名：" + targetFileName);
		} catch (IOException e) {
			System.out.println("压缩失败：" + e);
			e.printStackTrace();
		}
	}

	/**
	 * 解压文件
	 * 
	 * @param inPath
	 * @param outPath
	 * @return
	 */
	public static boolean unZip(String inPath, String outPath) {

		try {
			return unZip(new FileInputStream(inPath), outPath);
		} catch (FileNotFoundException e) {
		}

		return false;
	}

	/**
	 * 解压文件
	 * 
	 * @param inPath
	 * @param outPath
	 * @return
	 */
	public static boolean unZip(InputStream inputStream, String outPath) {
	
		if (!outPath.endsWith("/")) {
			outPath += "/";
		}
	
		try {
			ZipInputStream zin = new ZipInputStream(inputStream);
			ZipEntry entry;
	
			// 创建文件夹
			while ((entry = zin.getNextEntry()) != null) {
				if (entry.isDirectory()) {
					File directory = new File(outPath, entry.getName());
	
					if (!directory.exists()) {
						if (!directory.mkdirs()) {
							System.exit(0);
						}
					}
	
					zin.closeEntry();
				} else {
					File myFile = new File(entry.getName());
	
					if (null != myFile.getParentFile()) {
						OsUtil.makeDirs(outPath
								+ myFile.getParentFile().getPath());
					} else {
						OsUtil.makeDirs(outPath);
					}
	
					FileOutputStream fout = new FileOutputStream(outPath
							+ myFile.getPath());
	
					DataOutputStream dout = new DataOutputStream(fout);
	
					byte[] b = new byte[1024];
					int len = 0;
	
					while ((len = zin.read(b)) != -1) {
						dout.write(b, 0, len);
					}
	
					dout.close();
					fout.close();
					zin.closeEntry();
				}
			}
	
			zin.close();
	
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将目录压缩到ZIP输出流。
	 */
	private static void dirToZip(String baseDirPath, File dir,
			ZipOutputStream out) {
		if (dir.isDirectory()) {
			// 列出dir目录下所有文件
			File[] files = dir.listFiles();

			// 如果是空文件夹
			if (files.length == 0) {
				ZipEntry entry = new ZipEntry(getEntryName(baseDirPath, dir));

				// 存储目录信息
				try {
					out.putNextEntry(entry);
					out.closeEntry();
				} catch (IOException e) {
					e.printStackTrace();
				}

				return;
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					// 如果是文件，调用fileToZip方法
					fileToZip(baseDirPath, files[i], out);
				} else {
					// 如果是目录，递归调用
					dirToZip(baseDirPath, files[i], out);
				}
			}
		}
	}

	/**
	 * 将文件压缩到ZIP输出流
	 */
	private static void fileToZip(String baseDirPath, File file,
			ZipOutputStream out) {
		FileInputStream in = null;
		ZipEntry entry = null;

		// 创建复制缓冲区
		byte[] buffer = new byte[4096];

		int bytes_read;

		if (file.isFile()) {
			try {
				// 创建一个文件输入流
				in = new FileInputStream(file);

				// 做一个ZipEntry
				entry = new ZipEntry(getEntryName(baseDirPath, file));

				// 存储项信息到压缩文件
				out.putNextEntry(entry);

				// 复制字节到压缩文件
				while ((bytes_read = in.read(buffer)) != -1) {
					out.write(buffer, 0, bytes_read);
				}

				out.closeEntry();
				in.close();

				System.out.println("添加文件" + file.getAbsolutePath()
						+ "被到ZIP文件中！");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取待压缩文件在ZIP文件中entry的名字。即相对于跟目录的相对路径名
	 * 
	 * @param baseDirPath
	 * @param file
	 * @return
	 */
	private static String getEntryName(String baseDirPath, File file) {
		if (!baseDirPath.endsWith(File.separator)) {
			baseDirPath += File.separator;
		}

		String filePath = file.getAbsolutePath();

		// 对于目录，必须在entry名字后面加上"/"，表示它将以目录项存储。
		if (file.isDirectory()) {
			filePath += "/";
		}

		int index = filePath.indexOf(baseDirPath);

		return filePath.substring(index + baseDirPath.length());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// MyZipUtils book = new MyZipUtils();
		//
		// try {
		// book.zip("C:/zhang/8089.zip", new File("C:/zhang/8089/"));
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// try {
		// MyZipUtils.unZip("C:/zhang/8089.zip", "C:/zhang/8089");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
	}
}