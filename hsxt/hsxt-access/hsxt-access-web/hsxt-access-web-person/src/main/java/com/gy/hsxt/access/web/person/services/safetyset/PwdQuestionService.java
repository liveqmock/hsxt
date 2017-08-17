/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.person.services.safetyset;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdQuestionService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;

/***
 * 密保信息管理服务类
 * 
 * @Package: com.gy.hsxt.access.web.person.services.safetyset
 * @ClassName: ReserveInfoService
 * @Description: TODO
 * 
 * @author: lizhi peter.li
 * @date: 2015-11-10 下午3:38:07
 * @version V1.0
 */
@Service
public class PwdQuestionService extends BaseServiceImpl implements IPwdQuestionService {

    @Autowired
    private IUCAsPwdQuestionService ucAsPwdQuestionService;  // 密码服务接口类 dubbo
    /**  
     * 获取密保问题
     * @param pointNo 持卡人是互生号，非持卡人是手机号
     * @param usertype 持卡人还是非持卡人
     * @return 密保问题列表
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.safetyset.IPwdQuestionService#findPwdQuestionByCustId(java.lang.String, java.lang.String) 
     */
    @Override
    public List<AsPwdQuestion> findPwdQuestionByCustId(String pointNo, UserTypeEnum usertype) throws HsException {
        List<AsPwdQuestion> list = new ArrayList<AsPwdQuestion>();
       return this.ucAsPwdQuestionService.listDefaultPwdQuestions();
    }

    /**  
     * 密保问题验证
     * @param pwdQuestion 密保问题
     * @param pointNo     持卡人是互生号，非持卡人是手机号
     * @param usertype    持卡人还是非持卡人
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.safetyset.IPwdQuestionService#checkPwdQuestion(com.gy.hsxt.uc.as.bean.common.AsPwdQuestion, java.lang.String, java.lang.String) 
     */
    @Override
    public void checkPwdQuestion(AsPwdQuestion pwdQuestion, String perCustId, String usertype) throws HsException {
//        this.ucAsPwdQuestionService.checkPwdQuestion(pointNo ,usertype, null, pwdQuestion);
    	ucAsPwdQuestionService.checkPwdQuestion(perCustId, usertype, pwdQuestion);
    }

    /**
     * 保存密保问题
     * @param pwdQuestion 密保问题
     * @param custId 客户号
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.safetyset.IPwdQuestionService#savePwdQuestion(com.gy.hsxt.uc.as.bean.common.AsPwdQuestion, java.lang.String)
     */
    @Override
    public void savePwdQuestion(AsPwdQuestion pwdQuestion, String custId) throws HsException {
        this.ucAsPwdQuestionService.updatePwdQuestion(custId, pwdQuestion);
    }

    

}
