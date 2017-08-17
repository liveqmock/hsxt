/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.apply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.bs.api.apply.IBSDebtOrderService;
import com.gy.hsxt.bs.apply.mapper.DeclareMapper;
import com.gy.hsxt.bs.bean.apply.DebtOrderParam;
import com.gy.hsxt.bs.bean.apply.ResFeeDebtOrder;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @Package: com.gy.hsxt.bs.apply.service
 * @ClassName: DebtOrderService
 * @Description: 申报欠款管理
 * 
 * @author: xiaofl
 * @date: 2015-12-16 下午4:06:49
 * @version V1.0
 */
@Service
public class DebtOrderService implements IBSDebtOrderService {

    @Autowired
    private DeclareMapper declareMapper;

    /**
     * 查询系统销售费欠款单
     * 
     * @param param
     *            查询参数
     * @return 系统销售费欠款单列表
     * @throws HsException
     */
    @Override
    public PageData<ResFeeDebtOrder> queryDebtOrder(DebtOrderParam param, Page page) throws HsException {
        PageContext.setPage(page);
        PageData<ResFeeDebtOrder> pageData = null;
        List<ResFeeDebtOrder> list = declareMapper.queryResFeeDebtOrderListPage(param);
        if (null != list && list.size() > 0)
        {
            pageData = new PageData<ResFeeDebtOrder>();
            pageData.setResult(list);
            pageData.setCount(PageContext.getPage().getCount());
        }
        return pageData;
    }
}
