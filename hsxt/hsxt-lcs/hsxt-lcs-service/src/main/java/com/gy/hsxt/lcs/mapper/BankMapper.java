package com.gy.hsxt.lcs.mapper;

import java.util.List;

import com.gy.hsxt.lcs.bean.Bank;
/**
 * 银行转账银行列表接口
 * @Package: com.gy.hsxt.lcs.mapper  
 * @ClassName: BankMapper 
 * @Description: TODO
 *
 * @author: yangjianguo 
 * @date: 2015-12-15 下午2:56:32 
 * @version V1.0
 */
public interface BankMapper {
	
	
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
