/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.infomanage.impl;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.infomanage.RelateConsumerManageService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.entstatus.IBSChangeInfoService;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsQueryConsumerCondition;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;


@SuppressWarnings("rawtypes")
@Service
public class RelateConsumerManageServiceImpl extends BaseServiceImpl implements RelateConsumerManageService {

    @Autowired
    private IUCAsEntService iuCAsEntService;
    
    @Autowired
    private IUCAsCardHolderService iUCAsCardHolderService;
    
    @Autowired
    private IBSChangeInfoService  ibSChangeInfoService;
    
    @Autowired
    private IUCAsPwdService  asPwdService;
    
    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        String perResNo = (String) filterMap.get("belongPerResNo");//消费者互生号
        String realName = (String) filterMap.get("belongPerName");//消费者姓名
        AsQueryConsumerCondition condition = new AsQueryConsumerCondition();
        condition.setPerResNo(perResNo);
        condition.setRealName(realName);
        PageData pd = iUCAsCardHolderService.listAllConsumerInfo(condition, page);
        return pd;
    }
    
    /**
     * 分页查看消费者的修改记录信息
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException 
     */
    @Override
    public PageData queryConsumberInfoBakList(Map filterMap, Map sortMap, Page page) throws HsException {
        String custId = (String)filterMap.get("belongCustId");
        String updateFieldName = (String)filterMap.get("updateFieldName");
        PageData pd = iUCAsCardHolderService.listCustUpdateLog(custId,updateFieldName, page);
        return pd;
    }
    
    /**
     * 消费者信息的修改
     * @param conditoanMap
     * @throws HsException
     */
    @Override
    public void UpdateConsumerAndLog(Map conditoanMap) throws HsException {
        
        AsRealNameAuth  asRealNameAuth = (AsRealNameAuth )conditoanMap.get("updateLog");//修改对象
        ArrayList logList = (ArrayList)conditoanMap.get("logList");
        String operCustId = (String)conditoanMap.get("operCustId");//操作员ID
        String perCustId = (String)conditoanMap.get("perCustId");
        String regionalResNo = (String)conditoanMap.get("regionalResNo");//操作员互生号
        String userName = (String)conditoanMap.get("userName");//复核员用户名
        String loginPwd = (String)conditoanMap.get("loginPwd");//复核员密码
        String secretKey = (String)conditoanMap.get("secretKey");//秘钥
        String mobile = (String)conditoanMap.get("mobile");//手机号码
        String confirmId = asPwdService.checkApsReCheckerLoginPwd(regionalResNo, userName,loginPwd, secretKey, operCustId);
        iUCAsCardHolderService.updateCustAndLog(asRealNameAuth, logList, operCustId, confirmId,mobile,perCustId);
    }
    
    
    /**
     * 查看消费者信息
     * @param filterMap
     * @param sortMap
     * @param page
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.common.service.BaseServiceImpl#findScrollResult(java.util.Map, java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public AsCardHolderAllInfo searchAllInfo(String perCustId) throws HsException {
        AsCardHolderAllInfo allInfo = iUCAsCardHolderService.searchAllInfo(perCustId);
        return allInfo;
    }

}
