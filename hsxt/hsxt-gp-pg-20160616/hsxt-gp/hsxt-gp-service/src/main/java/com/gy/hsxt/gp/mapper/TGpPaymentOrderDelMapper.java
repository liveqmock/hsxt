package com.gy.hsxt.gp.mapper;

import com.gy.hsxt.gp.bean.PaymentOrderState;
import com.gy.hsxt.gp.mapper.vo.TGpPaymentOrder;
import com.gy.hsxt.gp.mapper.vo.TGpPaymentOrderDel;

public interface TGpPaymentOrderDelMapper {
	
    int deleteByPrimaryKey(String transSeqId);

    int insert(TGpPaymentOrderDel record);

    int insertSelective(TGpPaymentOrderDel record);

    TGpPaymentOrderDel selectByPrimaryKey(String transSeqId);
    
    PaymentOrderState selectOneBySelective(TGpPaymentOrder paymentOrder);

    int updateByPrimaryKeySelective(TGpPaymentOrderDel record);

    int updateByPrimaryKey(TGpPaymentOrderDel record);
}