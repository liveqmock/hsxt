package com.gy.hsxt.uc.operator.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.as.bean.operator.AsQueryOperatorCondition;
import com.gy.hsxt.uc.operator.bean.Operator;

/**
 * 操作员 Mapper
 * 
 * @Package: com.gy.hsxt.uc.operator.mapper
 * @ClassName: OperatorMapper
 * @Description: TODO
 * 
 * @author: huanggaoyang
 * @date: 2015-11-11 下午2:27:05
 * @version V1.0
 */
public interface OperatorMapper {

	/**
	 * 删除操作员
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @return
	 */
	int deleteByPrimaryKey(String operCustId);

	/**
	 * 插入操作员
	 * 
	 * @param record
	 *            操作员信息
	 * @return
	 */
	int insert(Operator record);

	/**
	 * 插入操作员（有值的属性插入）
	 * 
	 * @param record
	 *            操作员信息
	 * @return
	 */
	int insertSelective(Operator record);

	int insertPwd2(Operator record);

	/**
	 * 查询操作员信息
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @return
	 */
	Operator selectByPrimaryKey(String operCustId);

	/**
	 * 查询操作员密码2信息
	 * 
	 * @param operCustId
	 * @return
	 */
	HashMap<String, Object> selectOperatorSecondPwd(String operCustId);

	/**
	 * 修改操作员信息（有值的属性更新）
	 * 
	 * @param record
	 *            操作员信息
	 * @return
	 */
	int updateByPrimaryKeySelective(Operator record);

	/**
	 * 注销所有的操作员（账户状态更改为2：已删除，删除标识更改为N：不可用）
	 * @param entCustId
	 * @return
	 */
	int cancelAllOper(String entCustId);
	/**
	 * 修改操作员信息
	 * 
	 * @param record
	 *            操作员信息
	 * @return
	 */
	int updateByPrimaryKey(Operator record);

	/**
	 * 查询操作员信息通过用户名
	 * 
	 * @param entResNo
	 *            企业客户号
	 * @param userName
	 *            用户名（操作员账户名）
	 * @return
	 */
	Operator selectByUserName(String entResNo, String userName);

	/**
	 * 查询企业操作员列表
	 * 
	 * @param entCustId
	 *            企业客户号
	 * @return
	 */
	List<Operator> listOperByEntCustId(String entCustId);

	/**
	 * 查询操作员客户号通过操作员互生号
	 * 
	 * @param operResNo
	 *            操作员客户号
	 * @return
	 */
	String findOperCustIdByOperResNo(String operResNo);

	/**
	 * 查询操作员列表通过 企业用户组编号
	 * 
	 * @param entGroupId
	 * @return
	 */
	List<Operator> listOperByEntGroupId(Long entGroupId);

	/**
	 * 统计操作员记录数
	 * 
	 * @param condition
	 * @return
	 */
	int countOperator(@Param("condition") AsQueryOperatorCondition condition);

	/**
	 * 分页查询操作员信息
	 * 
	 * @param condition
	 * @param page
	 * @return
	 */
	List<AsOperator> pageOperators(@Param("condition") AsQueryOperatorCondition condition,
			@Param("page") Page page);
	
	int countOperCustId(String operCustId);
	
	String selectMaxSeqNo(String entResNo,String operNo);

	/**
	 * 根据多个客户号查询
	 * @param custIds 客户号，中间使用逗号分隔
	 * @return
	 */
	List<Operator> selectByIds(Map <String ,Object> map);
}