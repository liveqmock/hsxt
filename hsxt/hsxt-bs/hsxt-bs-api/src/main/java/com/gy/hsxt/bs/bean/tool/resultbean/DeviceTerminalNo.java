/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.bean.tool.resultbean;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 查询设备终端编号类别Bean
 * 
 * @Package: com.gy.hsxt.bs.bean.tool.resultbean
 * @ClassName: DeviceTerminalNo
 * @Description:
 * @author: likui
 * @date: 2015年10月29日 下午12:00:58
 * @company: gyist
 * @version V3.0.0
 */
public class DeviceTerminalNo implements Serializable {

	private static final long serialVersionUID = 6820989604919965908L;

	/** 客户号 **/
	private String entCustId;

	/** 配置单号 **/
	private String confNo;

	/** 已经配置的终端编号 **/
	private List<String> confTerminalNos;

	/** 终端编号 **/
	private List<String> terminalNos;

	public DeviceTerminalNo()
	{
		super();
	}

	public DeviceTerminalNo(String entCustId, String confNo,
			List<String> confTerminalNos, List<String> terminalNos)
	{
		super();
		this.entCustId = entCustId;
		this.confNo = confNo;
		this.confTerminalNos = confTerminalNos;
		this.terminalNos = terminalNos;
	}

	public String getEntCustId()
	{
		return entCustId;
	}

	public void setEntCustId(String entCustId)
	{
		this.entCustId = entCustId;
	}

	public String getConfNo()
	{
		return confNo;
	}

	public void setConfNo(String confNo)
	{
		this.confNo = confNo;
	}

	public List<String> getConfTerminalNos()
	{
		return confTerminalNos;
	}

	public void setConfTerminalNos(List<String> confTerminalNos)
	{
		this.confTerminalNos = confTerminalNos;
	}

	public List<String> getTerminalNos()
	{
		return terminalNos;
	}

	public void setTerminalNos(List<String> terminalNos)
	{
		this.terminalNos = terminalNos;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}

}
