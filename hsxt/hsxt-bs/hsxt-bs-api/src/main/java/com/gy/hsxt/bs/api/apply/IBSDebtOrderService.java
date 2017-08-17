/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.api.apply;

import com.gy.hsxt.bs.bean.apply.DebtOrderParam;
import com.gy.hsxt.bs.bean.apply.ResFeeDebtOrder;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/** 
 * 
 * @Package: com.gy.hsxt.bs.api.apply  
 * @ClassName: IBSDebtOrderService 
 * @Description: 系统销售费欠款管理
 *
 * @author: xiaofl 
 * @date: 2015-12-16 上午9:36:35 
 * @version V1.0 
 

 */ 
public interface IBSDebtOrderService {
    
    /**
     * 查询系统销售费欠款单
     * @param param 查询参数
     * @return 系统销售费欠款单列表
     * @throws HsException
     */
    public PageData<ResFeeDebtOrder> queryDebtOrder(DebtOrderParam param,Page page) throws HsException; 

}
