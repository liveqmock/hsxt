package com.gy.hsxt.access.web.person.services.hsc;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderStatusInfoService;

@Service
public class CardLossRegService extends BaseServiceImpl implements
		ICardLossRegService {
    @Autowired
    private IUCAsCardHolderStatusInfoService ucAsCardHolderStatusInfoService;   //互生卡挂失服务类
    /**
     * 获取互生卡挂失状态
     * @param custId 客户号
     * @return Map  key="perResNo"获取互生号，key="cardStatus"获取互生卡状态
     * @throws HsException
     */
    @Override
    public Map<String,String> findHsCardStatusInfoBycustId(String custId) throws HsException {
        return this.ucAsCardHolderStatusInfoService.searchHsCardStatusInfoBycustId(custId);
    }
    
    /**
     * 修改互生卡挂失状态
     * @param custId            客户编号
     * @param loginPassword     登录密码
     * @param randomToken       随机token
     * @param status            状态信息
     * @param lossReason        原因信息
     * @throws HsException
     */
    @Override
    public void modifyCardLossStatus(String custId, String loginPassword, String randomToken, int status,
            String lossReason) throws HsException {
        this.ucAsCardHolderStatusInfoService.updateHsCardStatus(custId, loginPassword, randomToken, status, lossReason);
    }
   
    
}
