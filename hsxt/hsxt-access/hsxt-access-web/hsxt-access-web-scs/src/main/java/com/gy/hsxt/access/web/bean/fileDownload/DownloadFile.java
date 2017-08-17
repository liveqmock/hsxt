/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.fileDownload;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DownloadFile implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fileType;
	private String fileName;
	private String desc;
	private String fileAddress;
	private String platName;

	public String getFileAddress()
	{
		return fileAddress;
	}

	public void setFileAddress(String fileAddress)
	{
		this.fileAddress = fileAddress;
	}

	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

	public String getFileType()
	{
		return fileType;
	}

	public void setFileType(String fileType)
	{
		this.fileType = fileType;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public String getPlatName()
	{
		return platName;
	}

	public void setPlatName(String platName)
	{
		this.platName = platName;
	}

	public static List<DownloadFile> bulidDownloadFileList()
	{
		List<DownloadFile> list = new ArrayList<DownloadFile>();
		DownloadFile df1 = new DownloadFile();
		df1.setFileName("互生系统应用合同(成员企业)");
		df1.setDesc("成员企业应用系统使用");
		df1.setFileAddress("MemberContact.docx");
		DownloadFile df2 = new DownloadFile();
		df2.setFileName("互生系统应用合同(服务公司)");
		df2.setDesc("服务公司应用系统使用");
		df2.setFileAddress("ServiceContact.docx");
		DownloadFile df3 = new DownloadFile();
		df3.setFileName("互生系统应用合同(托管企业)");
		df3.setDesc("托管企业应用系统使用");
		df3.setFileAddress("TrustedContact.docx");
		DownloadFile df4 = new DownloadFile();
		df4.setFileName("企业参与“消费100以上送5000消费抵扣券”活动协议书");
		df4.setDesc("互生托管企业系统免费使用协议");
		df4.setFileAddress("ConsumptionTicketProtocol.docx");
		DownloadFile df5 = new DownloadFile();
		df5.setFileName("企业应用互生系统申报信息登记表");
		df5.setDesc("所有应用系统企业使用");
		df5.setFileAddress("CompanyDeclareInfoReg.docx");
		DownloadFile df6 = new DownloadFile();
		df6.setFileName("互生系统区域服务公司与创业人员(创业帮扶)协议");
		df6.setDesc("所有应用系统企业使用");
		df6.setFileAddress("InfoApplicationA.docx");
		list.add(df1);
		list.add(df2);
		list.add(df3);
		list.add(df4);
		list.add(df5);
		list.add(df6);
		return list;
	}
}
