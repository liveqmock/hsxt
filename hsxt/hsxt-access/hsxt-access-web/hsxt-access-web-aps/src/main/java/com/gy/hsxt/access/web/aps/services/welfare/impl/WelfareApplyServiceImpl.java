package com.gy.hsxt.access.web.aps.services.welfare.impl;


import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.welfare.IWelfareApplyService;
import com.gy.hsxt.access.web.common.utils.CommonUtils;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.ws.api.IWsApplyService;
import com.gy.hsxt.ws.bean.WelfareApplyRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 *
 * 积分福利福利资格接口
 * @category      积分福利福利资格
 * @projectName   hsxt-access-web-aps
 * @package       com.gy.hsxt.access.web.aps.services.welfare.IWelfareQualifyService.java
 * @className     IWelfareQualifyService
 * @description   积分福利福利资格
 * @author        leiyt
 * @createDate    2015-12-31 下午5:03:46
 * @updateUser    leiyt
 * @updateDate    2015-12-31 下午5:03:46
 * @updateRemark 	说明本次修改内容
 * @version       v0.0.1
 */
@Service
public class WelfareApplyServiceImpl implements IWelfareApplyService {

    @Autowired
    IWsApplyService wsApplyService;
    @Autowired
    IUCAsCardHolderService asCardHolderService;

    /**
     * 分页查询积分福利申请记录
     * @param filterMap
     * @param sortMap
     * @param page            分页参数
     * @return
     * @throws HsException
     */
    @Override
    public PageData<WelfareApplyRecord> listWelfareApply(Map filterMap, Map sortMap, Page page) throws HsException {
        String hsResNo = (String) filterMap.get("resNo");
        String custId = asCardHolderService.findCustIdByResNo(hsResNo);
        Integer welfareType = CommonUtils.toInteger(filterMap.get("welfareType"));
        Integer  approvalStatus=CommonUtils.toInteger(filterMap.get("approvalStatus"));
        try
		{
        	return wsApplyService.listWelfareApply(custId,welfareType,approvalStatus,page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "listWelfareApply", "分页查询积分福利申请记录失败", ex);
			return null;
		}
    }


    @Override
    public PageData<?> findScrollResult(Map map, Map map1, Page page) throws HsException {
        return null;
    }

    @Override
    public Object findById(Object o) throws HsException {
        return null;
    }

    @Override
    public String save(String s) throws HsException {
        return null;
    }

    @Override
    public void checkVerifiedCode(String s, String s1) throws HsException {

    }
}

