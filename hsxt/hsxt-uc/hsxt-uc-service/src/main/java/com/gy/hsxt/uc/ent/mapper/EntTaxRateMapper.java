package com.gy.hsxt.uc.ent.mapper;

import com.gy.hsxt.uc.ent.bean.EntTaxRate;

public interface EntTaxRateMapper {

	/**
	 * 根据企业客户号删除企业税率
	 * @param entCustId
	 * @return
	 */
    int deleteByPrimaryKey(String entCustId);
    
    /**
     * 插入企业税率
     * @param record
     * @return
     */
    int insertSelective(EntTaxRate record);

    /**
     * 查询企业税率信息
     * @param entCustId
     * @return
     */
    EntTaxRate selectByPrimaryKey(String entCustId);

    /**
     * 更新企业税率信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(EntTaxRate record);
    
    /**
     * 查询企业税率数量
     * @param entCustId
     * @return
     */
    int selectCounts(String entCustId);
}