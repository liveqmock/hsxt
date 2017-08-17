/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.common.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.uc.bs.bean.common.BsBankAcctInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntBaseInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntMainInfo;
import com.gy.hsxt.uc.bs.bean.ent.BsEntStatusInfo;

/**
 * 公共Service接口
 * 
 * @Package: com.gy.hsxt.bs.common.interfaces
 * @ClassName: ICommonService
 * @Description: TODO
 * @author: likui
 * @date: 2015年11月11日 上午10:00:46
 * @company: gyist
 * @version V3.0.0
 */
public interface ICommonService {

	/**
	 * 获取本地区平台信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月11日 上午10:08:37
	 * @return
	 * @return : LocalInfo
	 * @version V3.0.0
	 */
	public LocalInfo getAreaPlatInfo();

	/**
	 * 获取地区平台客户号
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月11日 下午5:11:22
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public String getAreaPlatCustId();

	/**
	 * 获取本地区平台的省份
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月18日 下午6:31:35
	 * @return
	 * @return : List<Province>
	 * @version V3.0.0
	 */
	public List<Province> getAreaPlatProvince();

	/**
	 * 获取本地区平台的省份编号
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月19日 下午2:45:42
	 * @return
	 * @return : List<String>
	 * @version V3.0.0
	 */
	public List<String> getAreaPlatProvinceNo();

	/**
	 * 根据省编号获取省信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月24日 下午12:26:37
	 * @param provinceNo
	 * @return
	 * @return : Province
	 * @version V3.0.0
	 */
	public Province getProvinceByNo(String provinceNo);

	/**
	 * 获取省下所有城市
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月24日 下午12:29:15
	 * @param provinceNo
	 * @return
	 * @return : List<City>
	 * @version V3.0.0
	 */
	public List<City> getCityByProvinceNo(String provinceNo);

	/**
	 * 根据企业互生号或客户号查询企业全部信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月27日 下午3:49:30
	 * @param entResNo
	 * @param entCustId
	 * @return
	 * @return : BsEntAllInfo
	 * @version V3.0.0
	 */
	public BsEntAllInfo getEntAllInfoToUc(String entResNo, String entCustId);

	/**
	 * 根据企业互生号或客户号查询企业主要信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年11月27日 下午3:49:53
	 * @param entResNo
	 * @param entCustId
	 * @return
	 * @return : BsEntMainInfo
	 * @version V3.0.0
	 */
	public BsEntMainInfo getEntMainInfoToUc(String entResNo, String entCustId);

	/**
	 * 根据企业互生号或客户号查询企业基本信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月2日 上午9:45:19
	 * @param entResNo
	 * @param entCustId
	 * @return
	 * @return : BsEntBaseInfo
	 * @version V3.0.0
	 */
	public BsEntBaseInfo getEntBaseInfoToUc(String entResNo, String entCustId);

	/**
	 * 根据企业互生号或客户号查询企业状态信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月2日 上午9:45:49
	 * @param entResNo
	 * @param entCustId
	 * @return
	 * @return : BsEntStatusInfo
	 * @version V3.0.0
	 */
	public BsEntStatusInfo getEntStatusInfoToUc(String entResNo, String entCustId);

	/**
	 * 根据客户号获取默认银行账户信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:27:36
	 * @param hsCustId
	 * @return
	 * @return : BsBankAcctInfo
	 * @version V3.0.0
	 */
	public BsBankAcctInfo getDefaultEntBankAcctByCustId(String hsCustId);

	/**
	 * 根据互生号获取默认银行账户信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:27:55
	 * @param hsResNo
	 * @return
	 * @return : BsBankAcctInfo
	 * @version V3.0.0
	 */
	public BsBankAcctInfo getDefaultEntBankAcctByNo(String hsResNo);
}
