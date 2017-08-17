package com.gy.hsxt.lcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.lcs.bean.Bank;
import com.gy.hsxt.lcs.interfaces.IBankService;
import com.gy.hsxt.lcs.mapper.BankMapper;

@Service
public class BankService implements IBankService {

    @Autowired
    private BankMapper bankMapper;

    @Override
    public String getBankName(String bankNo) {
        return bankMapper.getBankName(bankNo);
    }

    @Override
    public List<Bank> getBankList() {
        return bankMapper.getBankList();
    }

}
