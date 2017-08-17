/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.bs.bean.quota;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 查询市级配额分配记录时的查询参数对象
 * 
 * @Package: com.gy.hsxt.bs.bean.quota
 * @ClassName: CityQuotaQueryParam
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-10-30 下午4:55:35
 * @version V1.0
 */
public class CityQuotaQueryParam implements Serializable {

	private static final long serialVersionUID = -2903090937863580741L;

	/** 开始日期 **/
	private String startDate;

	/** 结束日期 **/
	private String endDate;

	/** 城市代码 **/
	private String cityNo;
	
	/** 城市名称 **/
    private String cityName;

	/** 申请类型 **/
	private Integer applyType;

	/** 状态 **/
	private Integer status;

	/** 经办人客户号 **/
	private String exeCustId;

	public CityQuotaQueryParam()
	{
	}

	public CityQuotaQueryParam(String startDate, String endDate, String cityNo,
			Integer applyType, Integer status)
	{
		this.startDate = startDate;
		this.endDate = endDate;
		this.cityNo = cityNo;
		this.applyType = applyType;
		this.status = status;
	}

	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public String getCityNo()
	{
		return cityNo;
	}

	public void setCityNo(String cityNo)
	{
		this.cityNo = cityNo;
	}

	public Integer getApplyType()
	{
		return applyType;
	}

	public void setApplyType(Integer applyType)
	{
		this.applyType = applyType;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getExeCustId()
	{
		return exeCustId;
	}

	public void setExeCustId(String exeCustId)
	{
		this.exeCustId = exeCustId;
	}

	public void setQueryDate()
	{
		if (StringUtils.isNotBlank(getStartDate()))
		{
			setStartDate(DateUtil.getMinDateOfDayStr(getStartDate()));
		}
		if (StringUtils.isNotBlank(getEndDate()))
		{
			setEndDate(DateUtil.getMaxDateOfDayStr(getEndDate()));
		}
	}

    /**
     * @return the cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param cityName the cityName to set
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
