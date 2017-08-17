package com.gy.hsxt.ac.common.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * 日积分活动资源信息类
 * 
 * @Package: com.gy.hsxt.ac.common.bean  
 * @ClassName: AccountTransSystem 
 * @Description: 企业月送积分信息
 *
 * @author: maocan 
 * @date: 2015-9-17 上午9:47:29
 * @version V1.0
 */
public class AccountIntegralActive implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5011774467464404176L;

	/**
	 * 系统信息名称
	 */
	private String transSys;
	
	/**
	 * 当前写文件名称
	 */
	private String fileName;
	
	/**
	 * 当前文件数量
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
	 * @return the 系统信息名称
	 */
	public String getTransSys() {
		return transSys;
	}


	/**
	 * @param 系统信息名称 the transSys to set
	 */
	public void setTransSys(String transSys) {
		this.transSys = transSys;
	}


	/**
	 * @return the 当前写文件名称
	 */
	public String getFileName() {
		return fileName;
	}


	/**
	 * @param 当前写文件名称 the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	/**
	 * @return the 当前文件数量
	 */
	public int getFileNum() {
		return fileNum;
	}


	/**
	 * @param 当前文件数量 the fileNum to set
	 */
	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}


	/**
	 * @return the 执行日期
	 */
	public String getNewDate() {
		return newDate;
	}


	/**
	 * @param 执行日期 the newDate to set
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
			this.fileName = "AC_"+this.newDate+"_DET_"+this.fileNum+".TXT";
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
