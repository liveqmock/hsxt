package com.gy.hsxt.gp.mapper;

import com.gy.hsxt.gp.mapper.vo.TGpBankAccount;

public interface TGpBankAccountMapper {
	
    int deleteByPrimaryKey(Integer bankAccId);

    int insert(TGpBankAccount record);

    int insertSelective(TGpBankAccount record);

    TGpBankAccount selectByPrimaryKey(Integer bankAccId);
    
    TGpBankAccount selectByBankAccType(Integer bankAccType);

    int updateByPrimaryKeySelective(TGpBankAccount record);

    int updateByPrimaryKey(TGpBankAccount record);
}