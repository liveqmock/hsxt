package com.gy.hsxt.gp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.gp.mapper.vo.TGpCustidBankcardMap;

public interface TGpCustidBankcardMapMapper {

	/**
	 * 新增
	 * 
	 * @param custidBankcardMap
	 * @return
	 */
	int insert(TGpCustidBankcardMap custidBankcardMap);

	/**
	 * 根据银行卡号和支付号查询
	 * 
	 * @param bankOrderNo
	 * @param bankCardNo
	 * @return
	 */
	TGpCustidBankcardMap selectByBankcardnoBankorderno(
			@Param("bankOrderNo") String bankOrderNo,
			@Param("bankCardNo") String bankCardNo);

	/**
	 * 根据银行卡号和支付号查询
	 * 
	 * @param bankCardNo
	 * @return
	 */
	List<TGpCustidBankcardMap> selectByBankcardno(
			@Param("bankCardNo") String bankCardNo);

	/**
	 * 更新绑定状态
	 * 
	 * @param bankCardNo
	 * @param bindingOk
	 * @return
	 */
	int updateBindingOK(@Param("bankCardNo") String bankCardNo,
			@Param("bindingOk") boolean bindingOk);
}