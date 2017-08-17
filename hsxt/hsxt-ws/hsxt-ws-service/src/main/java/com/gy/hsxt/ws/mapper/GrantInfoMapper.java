package com.gy.hsxt.ws.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.ws.bean.GrantInfo;
import com.gy.hsxt.ws.bean.GrantQueryCondition;

/**
 * 发放信息 Mapper
 * 
 * @Package: com.gy.hsxt.ws.mapper
 * @ClassName: GrantInfoMapper
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午12:08:52
 * @version V1.0
 */
public interface GrantInfoMapper {

	/**
	 * 生成发放记录
	 * 
	 * @param record
	 *            发放记录
	 * @return
	 */
	int insertSelective(GrantInfo record);

	/**
	 * 查询发放记录 通过发放编号（主键）
	 * 
	 * @param givingId
	 *            发放编号
	 * @return
	 */
	GrantInfo selectByPrimaryKey(String givingId);

	/**
	 * 更新发放记录
	 * 
	 * @param record
	 *            发放记录
	 * @return
	 */
	int updateByPrimaryKeySelective(GrantInfo record);

	/**
	 * 查询发放记录 通过任务编号
	 * 
	 * @param taskId
	 *            任务编号
	 * @return
	 */
	GrantInfo selectByTaskId(String taskId);

	/**
	 * 分页查询发放记录 统计查询总条数
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @param grantStatus
	 *            发放状态 0 未发放 1已发放
	 * @return
	 */
	int countGrantInfo(@Param("condition") GrantQueryCondition queryCondition,
			@Param("grantStatus") Integer grantStatus);

	/**
	 * 分页查询 发放记录
	 * 
	 * @param queryCondition
	 *            查询条件
	 * @param page
	 *            分页参数
	 * @param grantStatus
	 *            发放状态 0 未发放 1已发放
	 * @return
	 */
	List<GrantInfo> pageGrantInfo(
			@Param("condition") GrantQueryCondition queryCondition,
			@Param("page") Page page, @Param("grantStatus") Integer grantStatus);

}