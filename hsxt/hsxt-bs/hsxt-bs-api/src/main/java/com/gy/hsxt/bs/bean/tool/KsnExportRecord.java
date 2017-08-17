/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.tool;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.Base;

/**
 * 刷卡器导出记录
 * 
 * @Package: com.gy.hsxt.bs.bean.tool
 * @ClassName: KsnExportRecord
 * @Description:
 * @author: likui
 * @date: 2016年3月12日 下午2:43:55
 * @company: gyist
 * @version V3.0.0
 */
public class KsnExportRecord extends Base implements Serializable {

	private static final long serialVersionUID = 7888428694672027844L;

	/** 导出编号 **/
	private String exportId;

	/** 导出批次号 **/
	private String bahctNo;

	/** 导出说明 **/
	private String remark;

	public String getExportId()
	{
		return exportId;
	}

	public void setExportId(String exportId)
	{
		this.exportId = exportId;
	}

	public String getBahctNo()
	{
		return bahctNo;
	}

	public void setBahctNo(String bahctNo)
	{
		this.bahctNo = bahctNo;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
