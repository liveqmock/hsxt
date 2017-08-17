/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.services.safeset;

import java.util.List;

import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.bean.safeset.PwdQuestionBean;
import com.gy.hsxt.access.web.bean.safeset.ReserveInfoBaen;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;

/***
 * 系统安全设置
 * 
 * @Package: com.gy.hsxt.access.web.company.services.safeSet
 * @ClassName: ISecuritySetService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-10-30 下午3:35:34
 * @version V1.0
 */
public interface ISecuritySetService extends IBaseService {

    /**
     * 获取密保问题列表
     * @param companyBase
     * @return
     * @throws HsException
     */
    public List<AsPwdQuestion> getQuestionList(MCSBase mcsBase) throws HsException;

    /**
     * 设置密保问题答案
     * 
     * @param pqb
     */
    public void setQuestion(PwdQuestionBean pqb) throws HsException;

    /**
     * 设置预留信息
     * 
     * @param rib
     * @throws HsException
     */
    public void setReserveInfo(ReserveInfoBaen rib) throws HsException;

    /**
     * 修改预留信息
     * 
     * @param rib
     * @throws HsException
     */
    public void updateReserveInfo(ReserveInfoBaen rib) throws HsException;

    /**
     * 查询用户预留信息
     * 
     * @param companyBase
     * @return
     * @throws HsException
     */
    public String getReserveInfo(MCSBase mcsBase) throws HsException;

}
