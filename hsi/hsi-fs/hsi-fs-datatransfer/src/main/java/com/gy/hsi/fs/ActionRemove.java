package com.gy.hsi.fs;

import java.io.FileNotFoundException;

import org.springframework.util.Log4jConfigurer;

import com.gy.hsi.fs.common.spring.SpringContextLoader;
import com.gy.hsi.fs.worker.fileupload.RemoveTfsFilesWorker;

public class ActionRemove {
	
	/**
	 * 只要是文件系统里的文件全部删除
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Log4jConfigurer.initLogging("file:${user.dir}/conf/fs/log4j.xml");
		} catch (FileNotFoundException e) {
		}

		SpringContextLoader.getAppContext().start();
		
		RemoveTfsFilesWorker.batchDeleteAllInDB();
	}
	
	/**
	 * 仅删除迁移的文件
	 * 
	 * @param args
	 */
//	public static void main2(String[] args) {
//		try {
//			Log4jConfigurer.initLogging("file:${user.dir}/conf/fs/log4j.xml");
//		} catch (FileNotFoundException e) {
//		}
//
//		SpringContextLoader.getAppContext().start();
//
//		RemoveTfsFilesWorker.batchDeleteAllUploaded();
//	}
}
