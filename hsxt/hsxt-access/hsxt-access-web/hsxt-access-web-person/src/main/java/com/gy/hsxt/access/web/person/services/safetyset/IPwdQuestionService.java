/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.safetyset;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.bean.safetySet.DealPwdReset;
import com.gy.hsxt.access.web.person.bean.safetySet.DealPwdResetCheck;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;

/***
 * 密保答案服务类
 * 
 * @Package: com.gy.hsxt.access.web.person.services.safetyset
 * @ClassName: IPwdSetService
 * @Description: TODO
 * 
 * @author: lizhi peter。li
 * @date: 2015-11-10 下午3:29:52
 * @version V1.0
 */
@Service
public interface IPwdQuestionService extends IBaseService {

    /**
     * 获取密保问题
     * @param pointNo 持卡人是互生号，非持卡人是手机号
     * @param usertype 持卡人还是非持卡人
     * @return
     * @throws HsException
     */
    public List<AsPwdQuestion> findPwdQuestionByCustId(String pointNo,UserTypeEnum usertype)throws HsException;
    
    /**
     * 密保问题验证
     * @param pwdQuestion 密保问题
     * @param pointNo 持卡人是互生号，非持卡人是手机号
     * @param usertype 持卡人还是非持卡人
     * @return
     * @throws HsException
     */
    public void checkPwdQuestion(AsPwdQuestion pwdQuestion,String pointNo,String usertype)throws HsException;
    
    /**
     * 保存密保问题
     * @param pwdQuestion 密保问题
     * @param custId 客户号
     * @throws HsException 
     */
    public void savePwdQuestion(AsPwdQuestion pwdQuestion,String custId)throws HsException;

}
