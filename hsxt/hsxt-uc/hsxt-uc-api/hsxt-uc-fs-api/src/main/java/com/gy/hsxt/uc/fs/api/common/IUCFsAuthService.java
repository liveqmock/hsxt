/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.fs.api.common;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.fs.api.common
 * @ClassName: IUCAsAuthService
 * @Description: 鉴权（文件系统FS调用）
 * 
 * @author: tianxh
 * @date: 2015-11-24 下午5:06:46
 * @version V1.0
 */
public interface IUCFsAuthService {
    /**
     * 
     * @param custId
     *            客户号
     * @param channelType 
     *            渠道类型
     *           网页接入 WEB(1, "w_"),
     *           POS接入 POS(2, "pos_"),
     *           刷卡器 接入方MCR(3, "mcr_"),
     *           移动APP接入,MOBILE(4, "m_"),
     *           互生平板 接入,HSPAD(5, "pd_"),
     *           互生平台接入,SYS(6, "sys_"),
     *           IVR接入,IVR(7, "ivr_"),
     *           第三方接入,THIRDPART(8, "tp_");
     * @param loginToken
     *            已登录token
     * @return
     */
    public boolean isLogin(String custId, String channelType, String loginToken);
}
