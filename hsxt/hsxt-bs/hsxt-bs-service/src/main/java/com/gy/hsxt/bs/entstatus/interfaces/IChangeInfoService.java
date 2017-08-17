/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.interfaces;

/**
 * 
 * @Package: com.gy.hsxt.bs.entstatus.interfaces
 * @ClassName: IChangeInfoService
 * @Description: 重要信息变更
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午4:24:43
 * @version V1.0
 */
public interface IChangeInfoService {

    /**
     * 是否存在正在审批中的重要信息变更
     * 
     * @param entCustId
     *            企业客户号
     * @return 存在返回true 否则返回false
     */
    public Boolean isExistApplyingEntChange(String entCustId);

}
