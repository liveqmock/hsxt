/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.lcs.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.lcs.bean.PayBank;
import com.gy.hsxt.lcs.interfaces.IPayBankService;
import com.gy.hsxt.lcs.mapper.PayBankMapper;

/**
 * 
 * 查询银联列表接口
 * 
 * @Package: com.gy.hsxt.lcs.service
 * @ClassName: UnionListService
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-11-18 上午11:34:08
 * @version V1.0
 */
@Service("payBankService")
public class PayBankService implements IPayBankService {

    @Resource(name = "payBankMapper")
    private PayBankMapper payBankMapper;

    /**
     * 查询银联支付列表
     * 
     * @return
     * @see com.gy.hsxt.lcs.interfaces.IPayBankService#queryPayBankAll()
     */
    public List<PayBank> queryPayBankAll() {
        return payBankMapper.queryPayBankAll();
    }

    /**
     * 根据银行简码查询银行信息
     * 
     * @param bankCode
     * @return
     * @see com.gy.hsxt.lcs.interfaces.IPayBankService#queryPayBankByCode(java.lang.String)
     */
    public PayBank queryPayBankByCode(String bankCode) {
        return payBankMapper.queryPayBankByCode(bankCode);
    }

}
