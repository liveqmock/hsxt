package com.gy.hsxt.gp.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.gp.mapper.vo.TGpCustidBankcardnoMapping;

public interface TGpCustidBankcardnoMappingMapper {

	/**
	 * 新增
	 * 
	 * @param custidBankcardnoMapping
	 * @return
	 */
	int insert(TGpCustidBankcardnoMapping custidBankcardnoMapping);

	/**
	 * 根据银行卡号和支付号查询
	 * 
	 * @param bankOrderNo
	 * @param bankCardNo
	 * @return
	 */
	TGpCustidBankcardnoMapping selectByBankcardnoBankorderno(
			@Param("bankOrderNo") String bankOrderNo,
			@Param("bankCardNo") String bankCardNo);

	/**
	 * 根据银行卡号和支付号查询
	 * 
	 * @param bankCardNo
	 * @return
	 */
	List<TGpCustidBankcardnoMapping> selectByBankcardno(
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