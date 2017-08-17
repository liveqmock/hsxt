/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.entstatus.interfaces;

import java.util.List;

import com.gy.hsxt.bs.entstatus.bean.CancelAccountParam;

/**
 * 
 * @Package: com.gy.hsxt.bs.entstatus.interfaces
 * @ClassName: IMemberQuitService
 * @Description: 成员企业销户
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午4:25:57
 * @version V1.0
 */
public interface IMemberQuitService {

    /**
     * 查询未完成的销户（STATUS=4,PROGRESS<>5）
     * 
     * @return 未完成的销户列表
     */
    public List<CancelAccountParam> getUnCompletedCancelAcList();

}
