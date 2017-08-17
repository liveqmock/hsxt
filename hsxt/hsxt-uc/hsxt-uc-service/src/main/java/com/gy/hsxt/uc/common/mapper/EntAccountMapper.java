package com.gy.hsxt.uc.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.uc.common.bean.EntAccount;

/**
 * 企业帐户数据库操作接口
 * 
 * @Package: com.gy.hsxt.uc.common.mapper
 * @ClassName: EntAccountMapper
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-15 下午12:04:44
 * @version V1.0
 */
public interface EntAccountMapper {

	/**
	 * 根据ID删除
	 * 
	 * @param accId ID
	 * @return
	 */
	int deleteByPrimaryKey(Long accId);

	/**
	 * 添加企业帐户
	 * @param record 企业帐户对象
	 * @return
	 */
	int insertSelective(EntAccount record);

	/**
	 * 根据ID查询企业帐户
	 * @param accId 企业帐户ID
	 * @return
	 */
	EntAccount selectByPrimaryKey(Long accId);

	/**
	 * 更新企业帐户
	 * @param record 企业帐户
	 * @return
	 */
	int updateByPrimaryKeySelective(EntAccount record);

	/**
	 * 设置默认的帐户
	 * @param custId 客户号
	 * @return
	 */
	int setDefaultAccToGeneral(String custId);

	/**
	 * 根据客户号查询企业帐户
	 * @param custId 企业客户号
	 * @return
	 */
	List<EntAccount> listAccountByCustId(String custId);
	
	/**
	 * 根据客户号和银行账户编号更新银行账户信息
	 * @param record
	 * @return
	 */
	int updateByUniqueKeySelective(EntAccount record);
	
	/**
	 * 根据客户号和银行账户编号查询记录数
	 * @param custId	客户号
	 * @param accId		银行账户编号
	 * @return
	 */
	int selectCounts(@Param("entCustId") String entCustId,@Param("bankAccNo")String bankAccNo);

}