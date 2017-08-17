package com.gy.hsxt.rp.common.bean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

/**
 * 发票金额统计写文件信息类
 * 
 * @Package: com.gy.hsxt.rp.common.bean  
 * @ClassName: ReportsInvoiceValueStatisticsExportFile 
 * @Description: 发票金额统计写文件信息
 *
 * @author: maocan 
 * @date: 2015-9-17 上午9:47:29
 * @version V1.0
 */
public class ReportsInvoiceValueStatisticsExportFile implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8995201085455089434L;

	
	/**
	 * 当前写文件名称
	 */
	private String fileName;
	
	/**
	 * 当前文件数量
	 */
	private int fileNum;
	
	/**
	 * 执行月
	 */
	private String newMonth;
	
	/**
	 * 执行日期
	 */
	private String newDate;
	
	/**
	 * 文件路径
	 */
	private String filePath;
	

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
	 * @return the 执行月
	 */
	public String getNewMonth() {
		return newMonth;
	}



	/**
	 * @param 执行月 the newMonth to set
	 */
	public void setNewMonth(String newMonth) {
		this.newMonth = newMonth;
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
			this.fileName = newDate+"_"+this.fileNum+".TXT";
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
