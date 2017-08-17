/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.safeSet;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.bean.SCSBase;
import com.gy.hsxt.access.web.bean.safeSet.PwdQuestionBean;
import com.gy.hsxt.access.web.bean.safeSet.ReserveInfoBaen;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdQuestionService;
import com.gy.hsxt.uc.as.api.common.IUCAsReserveInfoService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;

/***
 * 系统安全设置
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.safeSet
 * @ClassName: SecuritySetService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-2 下午8:18:17
 * @version V1.0
 */
@Service(value = "securitySetService")
public class SecuritySetService extends BaseServiceImpl implements ISecuritySetService {


    @Resource
    private IUCAsPwdQuestionService ucAsPwdQuestionService;

    @Resource
    private IUCAsReserveInfoService ucAsReserveInfoService;

    /**
     * 获取密保问题
     * 
     * @param scsBase
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.scs.services.safeSet.ISecuritySetService#getQuestionList(com.gy.hsxt.access.web.bean.SCSBase)
     */
    @Override
    public List<AsPwdQuestion> getQuestionList(SCSBase scsBase) throws HsException {
        // 获取密保问题
        return ucAsPwdQuestionService.listDefaultPwdQuestions();
    }

    /**
     * 设置密保问题答案
     * 
     * @param pqb
     * @throws HsException
     * @see com.gy.hsxt.access.web.ISecuritySetService.service.ISafeSetService#setQuestion(com.gy.hsxt.access.web.bean.safeSet.PwdQuestionBean)
     */
    @Override
    public void setQuestion(PwdQuestionBean pqb) throws HsException {
        // 修改密保
        ucAsPwdQuestionService.updatePwdQuestion(pqb.getEntCustId(), pqb);
    }

    /**
     * 设置预留信息
     * 
     * @param rib
     * @throws HsException
     * @see com.gy.hsxt.access.web.ISecuritySetService.service.ISafeSetService#setReserveInfo(com.gy.hsxt.access.web.bean.safeSet.ReserveInfoBaen)
     */
    @Override
    public void setReserveInfo(ReserveInfoBaen rib) throws HsException {
        // 新增预留信息
        ucAsReserveInfoService
                .setReserveInfo(rib.getEntCustId(), rib.getReserveInfo(), UserTypeEnum.ENT.getType());

    }

    /**
     * 修改预留信息
     * 
     * @param rib
     * @throws HsException
     * @see com.gy.hsxt.access.web.ISecuritySetService.service.ISafeSetService#updateReserveInfo(com.gy.hsxt.access.web.bean.safeSet.ReserveInfoBaen)
     */
    @Override
    public void updateReserveInfo(ReserveInfoBaen rib) throws HsException {
        // 修改预留信息
        ucAsReserveInfoService
                .setReserveInfo(rib.getEntCustId(), rib.getReserveInfo(), UserTypeEnum.ENT.getType());
    }

    /**
     * 查询预留信息
     * 
     * @param scsBase
     * @return
     * @throws HsException
     * @see com.gy.hsxt.access.web.scs.services.safeSet.ISecuritySetService#getReserveInfo(com.gy.hsxt.access.web.bean.SCSBase)
     */
    @Override
    public String getReserveInfo(SCSBase scsBase) throws HsException {
        // 调用查询预留信息
        return ucAsReserveInfoService.findReserveInfoByCustId(scsBase.getEntCustId(), UserTypeEnum.ENT.getType());
    }
}
