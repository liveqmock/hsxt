package com.gy.hsxt.uc.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.uc.common.bean.CustAccount;

/**
 * 消费者银行账户 Mapper
 * 
 * @Package: com.gy.hsxt.uc.common.mapper
 * @ClassName: CustAccountMapper
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-27 下午4:47:30
 * @version V1.0
 */
public interface CustAccountMapper {
	/**
	 * 删除银行账户
	 * 
	 * @param accId
	 *            银行账户主键编号
	 * @return
	 */
	int deleteByPrimaryKey(Long accId);

	/**
	 * 插入银行账户
	 * 
	 * @param record
	 *            消费者银行账户信息
	 * @return
	 */
	int insertSelective(CustAccount record);

	/**
	 * 通过消费者银行账户主键ID查询
	 * 
	 * @param accId
	 *            银行账户主键编号
	 * @return
	 */
	CustAccount selectByPrimaryKey(Long accId);

	/**
	 * 更新银行账户信息
	 * 
	 * @param record
	 *            消费者银行账户信息
	 * @return
	 */
	int updateByPrimaryKeySelective(CustAccount record);

	/**
	 * 查询消费者银行账户列表
	 * 
	 * @param custId
	 *            企业客户号
	 * @return
	 */
	List<CustAccount> listAccountByCustId(String custId);

	/**
	 * 设置默认账户为非默认
	 * 
	 * @param custId
	 *            企业客户号
	 * @return
	 */
	int setDefaultAccToGeneral(String custId);
	
	/**
	 * 通过客户号和银行账号更新消费者银行账户信息
	 * @param record
	 * @return
	 */
	int updateByUniqueKeySelective(CustAccount record);
	
	/**
	 * 根据客户号和银行账户查询记录数
	 * @param perCustId	客户号
	 * @param bankAccNo		银行账户
	 * @return
	 */
	int selectCounts(@Param("perCustId") String perCustId,@Param("bankAccNo")String bankAccNo);

}