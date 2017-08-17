package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.Bank;

public interface IBankService {
	
	
	/**
	 * 读取银行名字
	 * 
	 * */
	public String getBankName(String bankNo);
	
	/**
	 * 读取银行列表
	 * 
	 * */
	public List<Bank> getBankList();
	
}
