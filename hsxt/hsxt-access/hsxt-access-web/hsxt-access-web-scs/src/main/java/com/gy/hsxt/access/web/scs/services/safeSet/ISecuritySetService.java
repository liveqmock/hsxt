/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.safeSet;

import java.util.List;

import com.gy.hsxt.access.web.bean.SCSBase;
import com.gy.hsxt.access.web.bean.safeSet.PwdQuestionBean;
import com.gy.hsxt.access.web.bean.safeSet.ReserveInfoBaen;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;

/***
 * 系统安全设置
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.safeSet
 * @ClassName: ISecuritySetService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-2 下午8:00:29
 * @version V1.0
 */
public interface ISecuritySetService extends IBaseService {

    /**
     * 获取密保问题列表
     * @param scsBase
     * @return
     * @throws HsException
     */
    public List<AsPwdQuestion> getQuestionList(SCSBase scsBase) throws HsException;

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
     * @param scsBase
     * @return
     * @throws HsException
     */
    public String getReserveInfo(SCSBase scsBase) throws HsException;

}
