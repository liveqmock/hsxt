package com.gy.hsxt.ac.common.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * 需要对账的系统信息类
 * 
 * @Package: com.gy.hsxt.ac.common.bean  
 * @ClassName: AccountTransSystem 
 * @Description: 对账的系统信息
 *
 * @author: maocan 
 * @date: 2015-9-17 上午9:47:29
 * @version V1.0
 */
public class AccountTransSystem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5011774467464404176L;

	/**
	 * 对账的系统信息名称
	 */
	private String transSys;
	
	/**
	 * 当前写对账文件名称
	 */
	private String fileName;
	
	/**
	 * 当前对账文件数量
	 */
	private int fileNum;
	
	/**
	 * 执行日期
	 */
	private String newDate;
	
	/**
	 * 文件路径
	 */
	private String filePath;

	/**
	 * 获取对账的系统信息名称
	 * @return transSys 对账的系统信息名称
	 */
	public String getTransSys() {
		return transSys;
	}

	/**
	 * 设置对账的系统信息名称
	 * @param transSys 对账的系统信息名称
	 */
	public void setTransSys(String transSys) {
		this.transSys = transSys;
	}

	/**
	 * 获取当前写对账文件名称
	 * @return fileName 当前写对账文件名称
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 设置当前写对账文件名称
	 * @param fileName 当前写对账文件名称
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 获取当前对账文件数量
	 * @return fileNum 当前对账文件数量
	 */
	public int getFileNum() {
		return fileNum;
	}

	/**
	 * 设置当前对账文件数量
	 * @param fileNum 当前对账文件数量
	 */
	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}
	
	/**
	 * 获取newDate
	 * @return newDate newDate
	 */
	public String getNewDate() {
		return newDate;
	}

	/**
	 * 设置newDate
	 * @param newDate newDate
	 */
	public void setNewDate(String newDate) {
		this.newDate = newDate;
	}

	/**
	 * @return the 文件路径
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param 文件路径 the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public synchronized void write(StringBuilder writeFileData){
		String fileAdder = this.filePath+this.newDate+File.separator+this.fileName;
		File sourceFile = new File(fileAdder);
		int maxFileSize = Integer.parseInt("31457280");
		if(sourceFile.exists() && sourceFile.length() >= maxFileSize){
			this.fileNum = this.fileNum+1;
			this.fileName = "AC_"+this.transSys+"_"+this.newDate+"_DET_"+this.fileNum;
			fileAdder = this.filePath+this.newDate+File.separator+this.fileName;
		}
		if(writeFileData!=null&&writeFileData.length()>0){
			FileWriter fw;
			try {
				fw = new FileWriter(fileAdder, true);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(writeFileData.toString());
				bw.flush();
				fw.close();
				bw.close();
			} catch (IOException e) {
				e.getMessage();
			}
		}
	}
}
