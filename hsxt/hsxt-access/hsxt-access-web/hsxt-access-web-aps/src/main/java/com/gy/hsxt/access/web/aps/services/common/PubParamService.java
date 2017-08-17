/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.bean.ApsDoubleOperator;
import com.gy.hsxt.access.web.bean.enumtype.EntBuyHsbLimit;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.bean.BusinessSysParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.bs.api.bizfile.IBSBizFileService;
import com.gy.hsxt.bs.bean.bizfile.BizDoc;
import com.gy.hsxt.bs.bean.bizfile.ImageDoc;
import com.gy.hsxt.bs.bean.bizfile.NormalDoc;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.auth.IUCAsPermissionService;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.common.IUCAsTokenService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/**
 * @projectName : hsxt-access-web-scs
 * @package : com.gy.hsxt.access.web.scs.services.common
 * @className : PubParamService.java
 * @description :
 * @author : maocy
 * @createDate : 2015-11-18
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Service
public class PubParamService implements IPubParamService {

	@Autowired
	private IBSBizFileService bsService;// BS服务类

	// 公共参数接口
	@Autowired
	private LcsClient lcsBaseDataService;

	// 获取登陆tonken
	@Resource
	private IUCAsTokenService ucAsTokenService;

	// 密码管理服务类
	@Resource
	private IUCAsPwdService asPwdService;
	
	@Resource
	private IUCAsEntService ucService;

	@Resource
	private IUCAsOperatorService operatorService;

	@Autowired
	private BusinessParamSearch businessParamSearch;// 业务参数服务接口
	
	/**
   	 * 调用互生权限接口
   	 */
   	@Autowired
   	private IUCAsPermissionService iucAsPermissionService;

	/**
	 * 
	 * 方法名称：获取系统信息 方法描述：获取系统配置信息
	 * 
	 * @return 系统信息
	 * @throws HsException
	 */
	public LocalInfo findSystemInfo() throws HsException
	{
		return lcsBaseDataService.getLocalInfo();
	}

	/**
	 * 取随机token 客户号为空 代表未登录的获取， 客户号不为空 用户已登录使用
	 * 
	 * @param custId
	 *            客户号
	 * @return
	 * @see com.gy.hsxt.access.web.person.services.common.IPubParamService#getRandomToken(java.lang.String)
	 */
	@Override
	public String getRandomToken(String custId)
	{
		return this.ucAsTokenService.getRandomToken(custId);
	}

	/**
	 * 
	 * 方法名称：获取示例图片管理列表 方法描述：获取示例图片管理列表
	 * 
	 * @return
	 * @throws HsException
	 */
	public HashMap<String, String> findImageDocList() throws HsException
	{
		List<ImageDoc> list = this.bsService.getImageDocList(2);
		if (list == null)
		{
			return null;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		for (ImageDoc obj : list)
		{
			map.put(obj.getDocCode(), obj.getFileId());
		}
		return map;
	}

	/**
	 * 方法名称：查询办理业务文档列表 方法描述：查询办理业务文档列表
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 */
	public HashMap<String, BizDoc> findBizDocList() throws HsException
	{
		PageData<BizDoc> pd = this.bsService.getBizDocList(null, 2, new Page(0, 100));
		if (pd == null)
		{
			return null;
		}
		HashMap<String, BizDoc> map = new HashMap<String, BizDoc>();
		for (BizDoc obj : pd.getResult())
		{
			map.put(obj.getDocCode(), obj);
		}
		return map;
	}

	/**
	 * 方法名称：查询常用业务文档列表 方法描述：查询常用业务文档列表
	 * 
	 * @return
	 * @throws HsException
	 */
	public HashMap<String, NormalDoc> findNormalDocList() throws HsException
	{
		PageData<NormalDoc> pd = this.bsService.getNormalDocList(null, 2, new Page(0, 100));
		if (pd == null)
		{
			return null;
		}
		HashMap<String, NormalDoc> map = new HashMap<String, NormalDoc>();
		for (NormalDoc obj : pd.getResult())
		{
			map.put(obj.getDocCode(), obj);
		}
		return map;
	}

	/**
	 * 验证双签用户
	 * 
	 * @param ado
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.common.IPubParamService#verifyDoublePwd(com.gy.hsxt.access.web.bean.ApsDoubleOperator)
	 */
	@Override
	public String verifyDoublePwd(ApsDoubleOperator ado) throws HsException
	{
		// 双签验证(只针对地区平台用户验证)
		return asPwdService.checkApsReCheckerLoginPwd(ado.getEntResNo(), ado.getDoubleUserName(), ado.getDoublePwd(),
				ado.getRandomToken(), ado.getCustId());
	}

	/**
	 * 方法名称：依据客户号获取操作员信息 方法描述：依据客户号获取操作员信息
	 * 
	 * @param custId
	 * @return
	 * @throws HsException
	 */
	@Override
	public AsOperator searchOperByCustId(String custId)
	{
		return this.operatorService.searchOperByCustId(custId);
	}
	
	/**
	 * 方法名称：依据企业互生号查询企业信息
	 * 方法描述：依据企业互生号查询企业信息
	 * @param companyResNo 企业互生号
	 * @return
	 * @throws HsException
	 */
	public AsEntMainInfo findMainInfoByResNo(String companyResNo) {
		return this.ucService.getMainInfoByResNo(companyResNo);
	}

	/**
	 * 获取企业的兑换互生币限制
	 * 
	 * @Description:
	 * @param entCustId
	 * @return
	 * @throws HsException
	 */
	@Override
	public Map<String, Object> getEntBuyHsbLimit(String entCustId) throws HsException
	{
		// 单笔最小值Code
		String minCode = "";
		// 单笔最大值Code
		String maxCode = "";
		// 单日最大限额Code
		String dayMaxCode = "";
		// 兑换互生币（组）
		String sysGroupCode = BusinessParam.EXCHANGE_HSB.getCode();
		int custType = HsResNoUtils.getCustTypeByHsResNo(entCustId.substring(0, 11));
		if (CustType.SERVICE_CORP.getCode() != custType && CustType.TRUSTEESHIP_ENT.getCode() != custType
				&& CustType.MEMBER_ENT.getCode() != custType)
		{
			return null;
		}
		Map<String, BusinessSysParamItemsRedis> limitMap = businessParamSearch.searchSysParamItemsByGroup(sysGroupCode);
		if (null == limitMap || limitMap.size() <= 0)
		{
			return null;
		}

		Map<String, Object> result = new HashMap<String, Object>();
		BusinessSysParamItemsRedis param = null;
		BusinessCustParamItemsRedis custParam = null;
		switch (custType)
		{
		case 2:
			minCode = BusinessParam.B_SINGLE_BUY_HSB_MIN.getCode();
			maxCode = BusinessParam.B_SINGLE_BUY_HSB_MAX.getCode();
			dayMaxCode = BusinessParam.B_SINGLE_DAY_BUY_HSB_MAX.getCode();
			break;
		case 3:
			minCode = BusinessParam.T_SINGLE_BUY_HSB_MIN.getCode();
			maxCode = BusinessParam.T_SINGLE_BUY_HSB_MAX.getCode();
			dayMaxCode = BusinessParam.T_SINGLE_DAY_BUY_HSB_MAX.getCode();
			break;
		case 4:
			minCode = BusinessParam.S_SINGLE_BUY_HSB_MIN.getCode();
			maxCode = BusinessParam.S_SINGLE_BUY_HSB_MAX.getCode();
			dayMaxCode = BusinessParam.S_SINGLE_DAY_BUY_HSB_MAX.getCode();
			break;
		default:
			break;
		}
		// 单笔最小限额
		param = limitMap.get(minCode);
		result.put(EntBuyHsbLimit.SINGLE_BUY_HSB_MIN.getCode(),
				StringUtils.isBlank(param) ? "0" : (StringUtils.isBlank(param) ? "0" : param.getSysItemsValue()));
		// 单笔最大限额
		param = limitMap.get(maxCode);
		result.put(EntBuyHsbLimit.SINGLE_BUY_HSB_MAX.getCode(),
				StringUtils.isBlank(param) ? "0" : (StringUtils.isBlank(param) ? "0" : param.getSysItemsValue()));
		// 每日最大最小限额
		param = limitMap.get(dayMaxCode);
		result.put(EntBuyHsbLimit.SINGLE_DAY_BUY_HSB_MAX.getCode(),
				StringUtils.isBlank(param) ? "0" : (StringUtils.isBlank(param) ? "0" : param.getSysItemsValue()));

		// 每日已发生数量
		custParam = businessParamSearch.searchCustParamItemsByIdKey(entCustId, sysGroupCode,
				BusinessParam.SINGLE_DAY_HAD_BUY_HSB.getCode());
		// 参数为空、日期、已发生数量、不是今天的数量
		if (StringUtils.isBlank(custParam) || StringUtils.isBlank(custParam.getDueDate())
				|| StringUtils.isBlank(custParam.getSysItemsValue())
				|| DateUtil.compare_date(DateUtil.DateToString(DateUtil.StringToDate(custParam.getDueDate())),
						DateUtil.DateToString(DateUtil.today())) != 0)
		{
			result.put(EntBuyHsbLimit.DAY_BOUGHT_HSB_COUNT.getCode(), "0");
		} else
		{
			result.put(EntBuyHsbLimit.DAY_BOUGHT_HSB_COUNT.getCode(), custParam.getSysItemsValue());
		}
		return result;
	}
	
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

	@Override
	public List<AsPermission> findPermByCustId(String platformCode,String subSystemCode,String parentId, Short permType, String custId,String permCode) throws HsException {
		//查询用户提供dubbo服务
		return iucAsPermissionService.listPermByCustId(platformCode, subSystemCode,parentId, permType, custId,permCode);
	}
}
