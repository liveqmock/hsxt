package com.gy.hsxt.bs.bean.quota;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.bs.bean.base.ApprBase;

/**
 * 
 * @className:CityQuotaApp
 * @author:likui
 * @date:2015年9月2日
 * @desc:市级配额申请Bean
 * @company:gyist
 */
public class CityQuotaApp extends ApprBase implements Serializable {

	private static final long serialVersionUID = -3574166677548429040L;
	/**
	 * 业务申请编号
	 */
	private String applyId;
	/**
	 * 管理公司互生号
	 */
	private String entResNo;
	/**
	 * 国家代码
	 */
	private String countryNo;
	/**
	 * 省代码
	 */
	private String provinceNo;
	/**
	 * 城市代码
	 */
	private String cityNo;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	/**
	 * 申请类型 1：首次配置 2：增加配额 3：减少配额
	 */
	private Integer applyType;
	/**
	 * 人口数
	 */
	private String population;
	/**
	 * 申请理由1. 配额售罄 2. 配额不足 3. 人口增加 4. 人口减少 5. 其他
	 */
	private Integer applyReason;
	/**
	 * 其他理由
	 */
	private String otherReason;
	/**
	 * 申请数量
	 */
	private Integer applyNum;
	/**
	 * 批复数量
	 */
	private Integer apprNum;
	/**
	 * 批复资源号
	 */
	private String resNoList;
	/**
	 * 状态 0：待审核 1：审核通过 2：审核驳回 
	 */
	private Integer status;

	public CityQuotaApp()
	{
		super();
	}

	public CityQuotaApp(String applyId, String entResNo, String countryNo,
			String provinceNo, String cityNo, Integer applyType,
			String population, Integer applyReason, String otherReason,
			Integer applyNum, Integer apprNum, String resNoList, Integer status)
	{
		super();
		this.applyId = applyId;
		this.entResNo = entResNo;
		this.countryNo = countryNo;
		this.provinceNo = provinceNo;
		this.cityNo = cityNo;
		this.applyType = applyType;
		this.population = population;
		this.applyReason = applyReason;
		this.otherReason = otherReason;
		this.applyNum = applyNum;
		this.apprNum = apprNum;
		this.resNoList = resNoList;
		this.status = status;
	}

	public String getApplyId()
	{
		return applyId;
	}

	public void setApplyId(String applyId)
	{
		this.applyId = applyId;
	}

	public String getEntResNo()
	{
		return entResNo;
	}

	public void setEntResNo(String entResNo)
	{
		this.entResNo = entResNo;
	}

	public String getCountryNo()
	{
		return countryNo;
	}

	public void setCountryNo(String countryNo)
	{
		this.countryNo = countryNo;
	}

	public String getProvinceNo()
	{
		return provinceNo;
	}

	public void setProvinceNo(String provinceNo)
	{
		this.provinceNo = provinceNo;
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

	public String getPopulation()
	{
		return population;
	}

	public void setPopulation(String population)
	{
		this.population = population;
	}

	public Integer getApplyReason()
	{
		return applyReason;
	}

	public void setApplyReason(Integer applyReason)
	{
		this.applyReason = applyReason;
	}

	public String getOtherReason()
	{
		return otherReason;
	}

	public void setOtherReason(String otherReason)
	{
		this.otherReason = otherReason;
	}

	public Integer getApplyNum()
	{
		return applyNum;
	}

	public void setApplyNum(Integer applyNum)
	{
		this.applyNum = applyNum;
	}

	public Integer getApprNum()
	{
		return apprNum;
	}

	public void setApprNum(Integer apprNum)
	{
		this.apprNum = apprNum;
	}

	public String getResNoList()
	{
		return resNoList;
	}

	public void setResNoList(String resNoList)
	{
		this.resNoList = resNoList;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}
	
	public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
