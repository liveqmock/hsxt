package com.gy.hsxt.bs.bean.quota.result;

import java.io.Serializable;
import java.util.List;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @className: QuotaStatOfManager
 * @author:likui
 * @date:2015年9月2日
 * @desc:统计管理公司下资源的详情
 * @company:gyist
 */
public class QuotaStatOfManager implements Serializable {

	private static final long serialVersionUID = -6972603322987974669L;
	/**
	 * 管理公司互生号
	 */
	private String mEntResNo;
	/**
	 * 管理公司名称
	 */
	private String mCustName;
	/**
	 * 服务公司系统资源配额
	 */
	private Integer sResNum;
	/**
	 * 计划配额数量
	 */
	private Integer planNum;
	/**
	 * 计划外配额数量
	 */
	private Integer notPlanNum;
	/**
	 * 已使用配额
	 */
	private Integer useNum;
	/**
	 * 尚可用配额
	 */
	private Integer usableNum;
	/**
	 * 省级配额列表
	 */
	List<QuotaStatOfProvince> provinceList;

	public QuotaStatOfManager()
	{
		super();
	}

	public QuotaStatOfManager(String mEntResNo, String mCustName,
			Integer sResNum, Integer planNum, Integer notPlanNum,
			Integer useNum, Integer usableNum,
			List<QuotaStatOfProvince> provinceList)
	{
		super();
		this.mEntResNo = mEntResNo;
		this.mCustName = mCustName;
		this.sResNum = sResNum;
		this.planNum = planNum;
		this.notPlanNum = notPlanNum;
		this.useNum = useNum;
		this.usableNum = usableNum;
		this.provinceList = provinceList;
	}

	public String getmEntResNo()
	{
		return mEntResNo;
	}

	public void setmEntResNo(String mEntResNo)
	{
		this.mEntResNo = mEntResNo;
	}

	public String getmCustName()
	{
		return mCustName;
	}

	public void setmCustName(String mCustName)
	{
		this.mCustName = mCustName;
	}

	public Integer getsResNum()
	{
		return sResNum;
	}

	public void setsResNum(Integer sResNum)
	{
		this.sResNum = sResNum;
	}

	public Integer getPlanNum()
	{
		return planNum;
	}

	public void setPlanNum(Integer planNum)
	{
		this.planNum = planNum;
	}

	public Integer getNotPlanNum()
	{
		return notPlanNum;
	}

	public void setNotPlanNum(Integer notPlanNum)
	{
		this.notPlanNum = notPlanNum;
	}

	public Integer getUseNum()
	{
		return useNum;
	}

	public void setUseNum(Integer useNum)
	{
		this.useNum = useNum;
	}

	public Integer getUsableNum()
	{
		return usableNum;
	}

	public void setUsableNum(Integer usableNum)
	{
		this.usableNum = usableNum;
	}

	public List<QuotaStatOfProvince> getProvinceList()
	{
		return provinceList;
	}

	public void setProvinceList(List<QuotaStatOfProvince> provinceList)
	{
		this.provinceList = provinceList;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
