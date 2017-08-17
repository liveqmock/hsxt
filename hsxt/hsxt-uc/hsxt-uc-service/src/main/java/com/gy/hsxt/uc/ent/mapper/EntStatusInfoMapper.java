package com.gy.hsxt.uc.ent.mapper;

import java.util.List;

import com.gy.hsxt.uc.ent.bean.EntStatusInfo;

public interface EntStatusInfoMapper {

	/**
	 * 查询企业客户号通过企业互生号
	 * 
	 * @param entResNo
	 * @return
	 */

	String findEntCustIdByEntResNo(String entResNo);

	/**
	 * 删除企业状态信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 */
	int deleteByPrimaryKey(String entCustId);

	/**
	 * 插入企业状态信息（有值的属性插入）
	 * 
	 * @param record
	 *            企业状态信息
	 * @return
	 */
	int insertSelective(EntStatusInfo record);

	/**
	 * 查询企业状态信息
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 */
	EntStatusInfo selectByPrimaryKey(String entCustId);

	/**
	 * 更新企业状态信息（有值的属性更新）
	 * 
	 * @param record
	 *            企业状态信息
	 * @return
	 */
	int updateByPrimaryKeySelective(EntStatusInfo record);

	/**
	 * 更新企业状态信息
	 * 
	 * @param record
	 *            企业状态信息
	 * @return
	 */
	int updateByPrimaryKey(EntStatusInfo record);
	
	/**
	 * 批量查询企业的客户号列表
	 * @param list	企业互生号列表
	 */
	List<EntStatusInfo> batchSelectEntCustId(List<String> list);
	
	
}