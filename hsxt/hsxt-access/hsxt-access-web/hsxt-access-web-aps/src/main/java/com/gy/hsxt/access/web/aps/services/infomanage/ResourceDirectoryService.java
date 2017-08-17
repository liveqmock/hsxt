/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.infomanage;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bp.bean.BusinessSysBoSetting;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsCardholderResource;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;

/**
 * 资源名录服务
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.infomanage
 * @ClassName: ResourceDirectoryService
 * @Description: TODO
 * 
 * @author: zhangcy
 * @date: 2015-12-9 下午8:31:58
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
@Service
public interface ResourceDirectoryService extends IBaseService {
	/**
	 * 查询消费者资源
	 * 
	 * @param condition
	 *            查询条件
	 * @param page
	 *            分页条件
	 * @return
	 * @throws HsException
	 */
    public PageData<ReportsCardholderResource> listConsumerInfo(Map filterMap, Map sortMap, Page page)
			throws HsException;

	/**
	 * 查询消费者所有信息
	 * 
	 * @param custID
	 *            消费者客户号
	 * @return
	 * @throws HsException
	 */
	public AsCardHolderAllInfo queryConsumerAllInfo(String custID) throws HsException;

	/**
	 * 查询绑定银行卡列表
	 * 
	 * @param custID
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            1：非持卡人 2：持卡人 4：企业
	 * @return
	 * @throws HsException
	 */
	public List<AsBankAcctInfo> listBanksByCustId(String custID, String userType)
			throws HsException;

	/**
	 * 查询绑定快捷卡列表
	 * 
	 * @param custID
	 *            客户号（持卡人、非持卡人、企业）
	 * @param userType
	 *            1：非持卡人 2：持卡人 4：企业
	 * @return
	 * @throws HsException
	 */
	public List<AsQkBank> listQkBanksByCustId(String custID, String userType) throws HsException;
	
	/**
     * 查询业务操作许可
     * 
     * @param custID
     *            客户号（持卡人、非持卡人、企业）
     * @return
     * @throws HsException
     */
    public Map<String, BusinessSysBoSetting> findBusinessPmInfo(BusinessSysBoSetting businessBo) throws HsException;
    
    /**
     * 设置业务操作许可
     * 
     * @param custID
     *            客户号（持卡人、非持卡人、企业）
     * @return
     * @throws HsException
     */
    public void setBusinessPmInfo(String custId, String operCustId, List<BusinessSysBoSetting> sysBoSettingList) throws HsException;
    
    /**
     * 获取消费者资源文件下载路径
     * @param rcr
     * @return
     */
    public String personResourceDowloadPath(Map filterMap) throws HsException;
    
    /**
     * 获取企业资源文件下载路径
     * @param rcr
     * @return
     */
    public String entResourceDowloadPath(Map filterMap) throws HsException;
    
}
