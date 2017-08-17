/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.company.services.common;

import java.util.List;
import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bs.bean.bizfile.BizDoc;
import com.gy.hsxt.bs.bean.bizfile.NormalDoc;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.Bank;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/***
 * 公共接口类
 * 
 * @Package: com.gy.hsxt.access.web.company.services.common
 * @ClassName: ICommService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-1 下午3:50:07
 * @version V1.0
 */
public interface ICommService extends IBaseService {
	/**
	 * 获取随机token 未登录使用
	 * 
	 * @return
	 */
	public String getRandomToken();
	
	/***
     * 依据互生号获取地区平台信息
     * @param platEntResNo
     * @return
     * @throws HsException
     */
    public AsEntMainInfo findMainInfoByResNo(String  platEntResNo) throws HsException;

	/**
	 * 获取随机token 用户已登录使用
	 * 
	 * @param custId
	 * @return
	 */
	public String getRandomToken(String custId);

	/**
	 * 验证tooken
	 * 
	 * @param custId
	 * @param random
	 * @return
	 */
	public boolean checkRandomToken(String custId, String random);

	/**
	 * 验证交易密码
	 * 
	 * @param custId
	 *            客户号或企业客户号
	 * @param pwd
	 *            加密后的密码
	 * @param randomToken
	 *            加密时的随机token
	 * @throws HsException
	 */
	public void checkTradePwd(String custId, String pwd, String entTYpe, String randomToken) throws HsException;

	/**
	 * 获取本平台的信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 上午10:02:28
	 * @return
	 * @return : LocalInfo
	 * @version V3.0.0
	 */
	public LocalInfo getLocalInfo();

	/**
	 * 获取所有国家列表
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 上午10:03:22
	 * @return
	 * @return : List<Country>
	 * @version V3.0.0
	 */
	public List<Country> getCountryAll();

	/**
	 * 获取国家下的所有省
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 上午10:04:43
	 * @param countryNo
	 * @return
	 * @return : List<Province>
	 * @version V3.0.0
	 */
	public List<Province> getProvinceByCountry(String countryNo);

	/**
	 * 获取省下的所有城市
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 上午10:05:45
	 * @param countryNo
	 * @param provinceNo
	 * @return
	 * @return : List<City>
	 * @version V3.0.0
	 */
	public List<City> getCityByProvince(String countryNo, String provinceNo);

	/**
	 * 根据城市编号查询城市信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 上午11:22:27
	 * @param countryNo
	 * @param provinceNo
	 * @param cityNo
	 * @return
	 * @return : City
	 * @version V3.0.0
	 */
	public City getCityById(String countryNo, String provinceNo, String cityNo);

	/**
	 * 获取所有银行列表
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 上午10:07:23
	 * @return
	 * @return : List<Bank>
	 * @version V3.0.0
	 */
	public List<Bank> getBankAll();
	
	
	
	/**
     * 查询用户拥有的权限 ,排除权限记录状态=N的记录，可附加过滤条件：平台，子系统,权限类型
     * 
     * @param platformCode 平台代码
     *            null则忽略该条件
     * @param subSystemCode 子系统代码
     *            null则忽略该条件
     * @param permType 权限类型 0：菜单  1：功能
     *            null则忽略该条件
     * @param custId 客户编号
     * @param permCode 权限代码，null则忽略该条件
     * @return
     * @throws HsException
     */
	public List<AsPermission> findPermByCustId(String platformCode,String subSystemCode,String parentId,  Short permType, String custId,String permCode) throws HsException ;
	
	/**
     * 
     * 方法名称：获取示例图片管理列表
     * 方法描述：获取示例图片管理列表
     * @return
     * @throws HsException
     */
	public Map<String, String> findImageDocList() throws HsException;
	
	/**
     * 方法名称：查询办理业务文档列表
     * 方法描述：查询办理业务文档列表
     * @return
     * @throws HsException
     */
    public Map<String, BizDoc> findBizDocList() throws HsException;
    
    /**
     * 方法名称：查询常用业务文档列表
     * 方法描述：查询常用业务文档列表
     * @return
     * @throws HsException
     */
    public Map<String, NormalDoc> findNormalDocList() throws HsException;
    
    /**
     * 方法名称：依据客户号获取操作员信息
     * 方法描述：依据客户号获取操作员信息
     * @param custId
     * @return
     * @throws HsException
     */
    public AsOperator searchOperByCustId(String custId);
    
    /**
     * 获取企业务操作限制
     * 
     * @param custId
     * @param bp
     *            BusinessParam.CASH_TO_BANK：货币转银行、
     *            BusinessParam.PV_TO_HSB：积分转换互生币、
     *            BusinessParam.BUY_HSB：兑换互生币、
     *            BusinessParam.HSB_TO_CASH：互生币转货币、
     *            BusinessParam.PV_INVEST：积分投资
     */
    public Map<String, String> getBusinessRestrict(String custId, BusinessParam bp) throws HsException;

    /**
     * 获取客户私有参数项
     * 
     * @param custId
     *            客户号
     * @param businessParam
     *            参数枚举值
     * @return
     */
    public BusinessCustParamItemsRedis searchCustParamItemsByIdKey(String custId, String businessParam)
            throws HsException;
}
