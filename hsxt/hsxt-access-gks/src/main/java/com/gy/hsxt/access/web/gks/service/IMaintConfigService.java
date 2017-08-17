/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.gks.service;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.gks.bean.GenPmkResult;
import com.gy.hsxt.access.web.gks.bean.PmkSecretKeyParam;
import com.gy.hsxt.access.web.gks.bean.UpdateDeviceInfoParam;
import com.gy.hsxt.bs.bean.tool.AfterServiceConfig;
import com.gy.hsxt.bs.bean.tool.resultbean.AfterDeviceDetail;
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
 
/***
 * POS 维护配置接口
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.posInterface
 * @ClassName: IMaintConfigService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-23 下午6:09:56
 * @version V1.0
 */
public interface IMaintConfigService extends IBaseService {
    /**
     * 分页查询POS机维护配置单（BS）
     * 
     * @param bean
     * @param page
     * @return
     */
    public PageData<SecretKeyOrderInfoPage> queryAfterSecretKeyConfigByPage(String resNo, String custName, String custId,
			Page page) throws HsException; 

    /**
     * 分页查询POS机维护配置单详细清单（BS）
     * 
     * @param confNo
     * @return
     */
    public List<AfterDeviceDetail> queryAfterSecretKeyConfigDetail(String confNo) throws HsException;

    /**
     * 验证设备（BS）
     * 
     * @param deviceSeqNo
     * @return
     */
    public void vaildSecretKeyDeviceAfter(String deviceSeqNo) throws HsException;

    /**
     * 获取PMK秘钥（UC）
     * 
     * @param pskb
     * @return
     * @throws HsException
     */
    public GenPmkResult reCreatePosPMK(PmkSecretKeyParam pskb) throws HsException;

    /**
     * 配置POS机设备与企业关联关系（BS）
     * 
     * @param bean
     */
    public void configSecretKeyDeviceAfter(UpdateDeviceInfoParam param) throws HsException;

}
