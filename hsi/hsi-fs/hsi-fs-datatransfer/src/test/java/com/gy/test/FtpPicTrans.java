package com.gy.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

import com.gy.hsi.fs.common.utils.FtpUtils;

public class FtpPicTrans {
	
	private static final Logger logger = Logger.getLogger(FtpPicTrans.class);
	public static int count = 0;

	public static File targetFile = new File("C:/FS-TEMP-DATA/ftp");
	
	public static List<String> list = new ArrayList<String>();
	
	public static void main(String[] args) {
		
//		try {
////			FtpUtils.download("192.168.228.71", 21, "ftpuser", "ftpuser", "/company/cardstyles/00000000156/2013-09-19/", "27b7052f-dcf2-4aef-baf5-fcc24318a419.jpg", "C:/FS-TEMP-DATA/ftp/aa");
//		
//			// FTPFile[] files = FtpUtils.listFiles("192.168.228.71", 21, "ftpuser", "ftpuser", "/company/cardstyles/00000000156");
//		
//		} catch (IOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		try {
			recurFiles("/");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(count);
		
//		File d = new File("C:/ftppic");
//		
//		recurFiles(d);
//		
//		System.out.println("总数： " + count);
	}
	
	public static void recurFiles(String directory) throws IOException {

		FTPFile[] files = FtpUtils.listFiles("192.168.228.71", 21, "ftpuser",
				"ftpuser", directory);

		if (null == files || (0 >= files.length)) {
			return;
		}

		for (FTPFile f : files) {
			String fileName = f.getName();

			if (f.isFile()) {
				logger.info(fileName);
				System.out.println(fileName);

				if (!list.contains(fileName)) {
					list.add(fileName);
				} else {
					continue;
				}

				count++;

				FtpUtils.download("192.168.228.71", 21, "ftpuser", "ftpuser",
						directory, f.getName(),
						"C:/FS-TEMP-DATA/ftp/" + f.getName());

				continue;
			}

			recurFiles(directory + "/" + f.getName());
		}
	}
	
	public static void recurFiles(File currDir) {
		
		String fileName = currDir.getName();

		if (currDir.isFile()) {
			logger.info(fileName);
			System.out.println(fileName);
			
			if(!list.contains(fileName)) {
				list.add(fileName);
			} else {
				return;
			}
			
			count ++;
			
			try {
				org.apache.commons.io.FileUtils.copyFileToDirectory(currDir,
						targetFile);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return;
		}

		File[] files = currDir.listFiles();

		if (null == files || (0 >= files.length)) {
			return;
		}

		for (File f : files) {
			recurFiles(f);
		}
	}
}
