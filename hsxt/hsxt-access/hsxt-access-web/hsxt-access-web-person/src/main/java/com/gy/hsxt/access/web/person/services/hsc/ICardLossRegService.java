package com.gy.hsxt.access.web.person.services.hsc;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;

@Service
public interface ICardLossRegService extends IBaseService {
	
    /**
     * 获取互生卡挂失状态
     * @param custId 客户号
     * @return Map  key="perResNo"获取互生号，key="cardStatus"获取互生卡状态
     * @throws HsException
     */
    public Map<String,String> findHsCardStatusInfoBycustId (String custId) throws HsException;
	
	
    /**
     * 修改互生卡挂失状态
     * @param custId            客户编号
     * @param loginPassword     登录密码
     * @param randomToken       随机token
     * @param status            状态信息
     * @param lossReason        原因信息
     * @throws HsException
     */
    public void modifyCardLossStatus(String custId, String loginPassword, String randomToken, int status, String lossReason) throws HsException;
	
}
