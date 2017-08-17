/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.ao.AOErrorCode;
import com.gy.hsxt.bp.bean.BusinessCustParamItemsRedis;
import com.gy.hsxt.bp.client.api.BusinessParamSearch;
import com.gy.hsxt.bs.api.bizfile.IBSBizFileService;
import com.gy.hsxt.bs.bean.bizfile.BizDoc;
import com.gy.hsxt.bs.bean.bizfile.ImageDoc;
import com.gy.hsxt.bs.bean.bizfile.NormalDoc;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.BusinessParam;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.auth.IUCAsPermissionService;
import com.gy.hsxt.uc.as.api.common.IUCAsTokenService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.operator.IUCAsOperatorService;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;

/***
 * 公共接口实现类
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.common  
 * @ClassName: CommServiceImpl 
 * @Description: TODO
 *
 * @author: wangjg 
 * @date: 2015-12-3 上午11:20:43 
 * @version V1.0
 */
@Service("commService")
public class CommServiceImpl extends BaseServiceImpl implements ICommService {

    @Resource
    private IUCAsTokenService ucAsTokenService;
    
    @Resource
	private IUCAsEntService ucAsEntService;
    
    @Autowired
    private IBSBizFileService bsService;//BS服务类
    
    @Resource
    private IUCAsOperatorService operatorService;
    
    /**
	 * 调用互生权限接口
	 */
	@Autowired
	private IUCAsPermissionService iucAsPermissionService;
	
	@Autowired
    private BusinessParamSearch businessParamSearch;// 业务参数服务接口

    /**
     * 获取随机token 未登录使用
     * 
     * @return
     * @see com.gy.hsxt.access.web.company.services.common.ICommService#getRandomToken()
     */
    @Override
    public String getRandomToken() {
        return this.getRandomToken("");
    }
    
    /***
     * 依据互生号获取地区平台信息
     * @param platEntResNo
     * @return
     * @throws HsException
     */
    public AsEntMainInfo findMainInfoByResNo(String  platEntResNo) throws HsException {
    	return this.ucAsEntService.getMainInfoByResNo(platEntResNo);
    }

    /**
     * 获取随机token 用户已登录使用
     * 
     * @param custId
     * @return
     * @see com.gy.hsxt.access.web.company.services.common.ICommService#getRandomToken(java.lang.String)
     */
    @Override
    public String getRandomToken(String custId) {
        return ucAsTokenService.getRandomToken(custId);
    }

    /**
     * token验证
     * 
     * @param custId
     * @param random
     * @return
     * @see com.gy.hsxt.access.web.company.services.common.ICommService#checkRandomToken(java.lang.String,
     *      java.lang.String)
     */
    @Override
    public boolean checkRandomToken(String custId, String random) {
        return ucAsTokenService.checkRandomToken(custId, random);
    }
    
	/**
     * 
     * 方法名称：获取示例图片管理列表
     * 方法描述：获取示例图片管理列表
     * @return
     * @throws HsException
     */
	public HashMap<String, String> findImageDocList() throws HsException {
		List<ImageDoc> list = this.bsService.getImageDocList(2);
		if(list == null){
			return null;
		}
		HashMap<String, String> map = new HashMap<String, String>();
		for(ImageDoc obj : list){
			map.put(obj.getDocCode(), obj.getFileId());
		}
    	return map;
	}
	
	/**
     * 方法名称：查询办理业务文档列表
     * 方法描述：查询办理业务文档列表
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException
     */
    public HashMap<String, BizDoc> findBizDocList() throws HsException {
    	PageData<BizDoc> pd = this.bsService.getBizDocList(null, 2, new Page(0, 100));
    	if(pd == null){
    		return null;
    	}
    	HashMap<String, BizDoc> map = new HashMap<String, BizDoc>();
		for(BizDoc obj : pd.getResult()){
			map.put(obj.getDocCode(), obj);
		}
    	return map;
	}
    
    /**
     * 方法名称：查询常用业务文档列表
     * 方法描述：查询常用业务文档列表
     * @return
     * @throws HsException
     */
    public HashMap<String, NormalDoc> findNormalDocList() throws HsException {
    	PageData<NormalDoc> pd = this.bsService.getNormalDocList(null, 2, new Page(0, 100));
		if(pd == null){
    		return null;
    	}
		HashMap<String, NormalDoc> map = new HashMap<String, NormalDoc>();
		for(NormalDoc obj : pd.getResult()){
			map.put(obj.getDocCode(), obj);
		}
    	return map;
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
     * @return
     * @throws HsException
     */

	@Override
	public List<AsPermission> findPermByCustId(String platformCode,String subSystemCode, Short permType, String custId) throws HsException {
		//查询用户提供dubbo服务
		return iucAsPermissionService.listPermByCustId(platformCode, subSystemCode, permType, custId);
	}
	
	/**
     * 查询权限列表，可附加过滤条件：平台，子系统 ,权限类型,父权限id
     * 
     * @param platformCode
     *            null则忽略该条件
     * @param subSystemCode
     *            null则忽略该条件
     * @param permType
     *            null则忽略该条件
     * @param parentId
     *            父权限ID null则忽略该条件
     * @return
     * @throws HsException
     */
	public List<AsPermission> findPerontIdByPermission(String platformCode,String subSystemCode, Short permType, String parentId,String custId)throws HsException {
		return iucAsPermissionService.listPermByCustId(platformCode, subSystemCode,parentId, permType, custId);
	}

	@Override
	public AsOperator searchOperByCustId(String custId) {
		AsOperator asOperator=operatorService.searchOperByCustId(custId);
		return asOperator;
	}
	
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
    @Override
    public Map<String, String> getBusinessRestrict(String custId, BusinessParam bp) throws HsException {
        Map<String, String> retMap = new HashMap<String, String>();
        
        // 获取限制数据
        BusinessCustParamItemsRedis bcpir = this.searchCustParamItemsByIdKey(custId, bp.getCode());

        if (null != bcpir)  {
            retMap.put("restrictValue", bcpir.getSysItemsValue()); // 开启状态 0：开启 1：限制
            retMap.put("restrictRemark", bcpir.getSettingRemark()); // 限制备注说明
        }

        return retMap;
    }
    
    /**
     * 获取客户私有参数项
     * 
     * 因为BP查询接口有可能返回一个空的参数项对象或者返回的参数对象属性为空， 所以统一包一下这个查询接口，对空对象或者空属性处理一下
     * 
     * @param custId
     *            客户号
     * @param businessParam
     *            参数枚举值
     * @return
     */
    @Override
    public BusinessCustParamItemsRedis searchCustParamItemsByIdKey(String custId, String businessParam)throws HsException {
        BusinessCustParamItemsRedis custParamItems = null;
        try
        {
            custParamItems = businessParamSearch.searchCustParamItemsByIdKey(custId, businessParam);
            if (custParamItems != null)
            {
                return custParamItems;
            }
            else
            {
                custParamItems = new BusinessCustParamItemsRedis();
                custParamItems.setCustId(custId);
                custParamItems.setSysItemsKey(businessParam);
            }
        }
        catch (HsException e)
        {
            throw e;
        }
        catch (Exception e)
        {

            SystemLog.error(this.getClass().getName(), Thread.currentThread().getStackTrace()[1].getMethodName(),
                    AOErrorCode.AO_TO_BP_GET_PRIVATE_PARAM_ERROR.getCode() + "获取客户私有参数项异常,params[custId:" + custId
                            + ",businessParam:" + businessParam + "]", e);
            throw new HsException(AOErrorCode.AO_TO_BP_GET_PRIVATE_PARAM_ERROR.getCode(), "获取客户私有参数项异常,params[custId:"
                    + custId + ",businessParam:" + businessParam + "]" + e);
        }
        return custParamItems;
    }

}
