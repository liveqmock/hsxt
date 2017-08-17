/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.ps.api;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.bean.BatSettle;
import com.gy.hsxt.ps.bean.BatUpload;

import java.util.List;

/**
 * @Package :com.gy.hsxt.ps.api
 * @ClassName : IPsSettleService
 * @Description : 对账(批结算，批上送)
 * @Author : guopf
 * @Date : 2015/11/2 16:17
 * @Version V3.0.0.0
 */
public interface IPsSettleService {

    //  批结算
    void batSettle(BatSettle batSettle)throws HsException;

    //  批上送
    void batUpload(List<BatUpload> batUpload)throws HsException;

}
