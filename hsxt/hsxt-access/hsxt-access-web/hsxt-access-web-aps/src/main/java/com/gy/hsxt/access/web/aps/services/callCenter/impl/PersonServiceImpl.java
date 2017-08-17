package com.gy.hsxt.access.web.aps.services.callCenter.impl;

import com.gy.hsxt.access.web.bean.callCenter.SysService;
import com.gy.hsxt.bs.api.entstatus.IBSChangeInfoService;
import com.gy.hsxt.bs.api.tool.IBSToolOrderService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.entstatus.PerChangeInfo;
import com.gy.hsxt.bs.bean.order.Order;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.callCenter.IPersonService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.bean.consumer.AsCardHolderAllInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 查询消费者信息
 * @category      查询消费者信息
 * @projectName   hsxt-access-web-aps
 * @package       com.gy.hsxt.access.web.aps.services.callCenter.impl.PersonServiceImpl.java
 * @className     PersonServiceImpl
 * @description   查询消费者信息
 * @author        leiyt
 * @createDate    2016-1-27 下午3:42:51  
 * @updateUser    leiyt
 * @updateDate    2016-1-27 下午3:42:51
 * @updateRemark 	说明本次修改内容
 * @version       v0.0.1
 */
@Service
public class PersonServiceImpl implements IPersonService {
    @Autowired
    IUCAsCardHolderService asCardHolderService;
    @Autowired
    private IBSToolOrderService orderService;//BS-订单管理

    @Autowired
    private IBSChangeInfoService ibSChangeInfoService;//重要信息变更查询

    @Override
    public String findCustIdByResNo(String resNo) throws HsException {
        return asCardHolderService.findCustIdByResNo(resNo);
    }

    @Override
    public AsCardHolderAllInfo searchAllInfo(String custId) throws HsException {
        return asCardHolderService.searchAllInfo(custId);
    }

    @Override
    public PerChangeInfo queryPerChangeByCustId(String custId) throws HsException {
        return ibSChangeInfoService.queryPerChangeByCustId(custId);
    }

    @Override
    public PageData<?> findScrollResult(Map map, Map map1, Page page) throws HsException {
        BaseParam param = new BaseParam();
        param.setStartDate((String) map.get("startDate"));
        param.setEndDate((String) map.get("endDate"));
        param.setHsCustId(findCustIdByResNo((String) map.get("hsResNo")));
        return this.orderService.queryPersonMendCardOrderPage(param,page);
    }

    @Override
    public Object findById(Object o) throws HsException {
        return null;
    }

    @Override
    public String save(String s) throws HsException {
        return null;
    }

    @Override
    public void checkVerifiedCode(String s, String s1) throws HsException {

    }

    @Override
    public PageData<SysService> findCardapplyList(Map filterMap, Map sortMap, Page page) throws HsException {
        BaseParam param = new BaseParam();
        param.setStartDate((String) filterMap.get("startDate"));
        param.setEndDate((String) filterMap.get("endDate"));
        param.setHsCustId(findCustIdByResNo((String) filterMap.get("hsResNo")));
        PageData<Order> pd = this.orderService.queryPersonMendCardOrderPage(param, page);
        if(pd == null){
            return null;
        }
        List<SysService> temp = new ArrayList<SysService>();
        for (Order order : pd.getResult()) {
            temp.add(new SysService(order.getOrderNo(), order.getOrderTime(), order.getOrderStatus()));
        }
        return new PageData<>(pd.getCount(), temp);
    }
}
