/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.gks.service;

import com.gy.hsxt.access.web.gks.bean.GenPmkResult;
import com.gy.hsxt.access.web.gks.bean.PmkSecretKeyParam;
import com.gy.hsxt.access.web.gks.bean.UpdateDeviceStatusParam;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceTerminalNo;
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/***
 * POS 申购配置接口类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.posInterface
 * @ClassName: IPurchaseConfigService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-23 下午6:27:28
 * @version V1.0
 */
public interface IPurchaseConfigService {

    /**
     * 分页查询POS机申购配置单（BS）
     * 
     * @param resNo 资源号
     * @param custName 客户名称
     * @param custId 客户号
     * @param page 页码
     * @return 
     */
    public PageData<SecretKeyOrderInfoPage> querySecretKeyConfigByPage(String resNo, String custName, String custId,
			Page page);

    /**
     * 查询设备的终端编号列表(BS)
     * 
     * @param entCustId 客户号
     * @param confNo 配置ID
     * @param categoryCode 分类code
     * @return
     */
    public DeviceTerminalNo queryConifgDeviceTerminalNo(String entCustId, String confNo, String categoryCode)
            throws HsException;


    /**
     * 获取PMK秘钥（UC）
     * @param pskb
     * @return
     */
    public GenPmkResult createPosPMK(PmkSecretKeyParam pskb);

    /**
     * 修改POS机启用状态（UC）
     * 
     * @param param
     * @return
     */
    public void updateDeviceStatus(UpdateDeviceStatusParam param);

}
