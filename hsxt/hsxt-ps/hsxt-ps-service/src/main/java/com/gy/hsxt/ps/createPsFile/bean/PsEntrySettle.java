package com.gy.hsxt.ps.createPsFile.bean;

import java.io.*;
import java.nio.charset.Charset;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.gpf.fss.constant.FssPurpose;
import com.gy.hsxt.gpf.fss.utils.FileNameBuilder;
import com.gy.hsxt.ps.common.PsException;

/**
 * 批生成互商结算信息类
 * 
 * @Package: com.gy.hsxt.ps.creatPsFile.bean  
 * @ClassName: PsEntrySettle 
 * @Description: 批生成互商结算信息
 *
 * @author: weixz 
 * @date: 2016-3-21 下午14:44:29
 * @version V1.0
 */
public class PsEntrySettle implements Serializable{
	
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
	
	private String sysName;
	
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

	/**
	 * @return the sysName
	 */
	public String getSysName() {
		return sysName;
	}


	/**
	 * @param sysName the sysName to set
	 */
	public void setSysName(String sysName) {
		this.sysName = sysName;
	}


	public synchronized void write(StringBuilder writeFileData){
		String fileAdder = this.filePath+File.separator+this.fileName;
		if(writeFileData!=null&&writeFileData.length()>0){
			try {
				Writer fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileAdder), Charset.defaultCharset()));
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(writeFileData.toString());
				bw.flush();
				fw.close();
				bw.close();
			} catch (IOException e) {
				PsException.psExceptionNotThrow(new Throwable().getStackTrace()[0], RespCode.FAIL.getCode(),e.toString());
			}
		}
	}
}
