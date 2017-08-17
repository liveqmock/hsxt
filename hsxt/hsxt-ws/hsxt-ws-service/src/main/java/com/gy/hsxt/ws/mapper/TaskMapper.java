package com.gy.hsxt.ws.mapper;

import com.gy.hsxt.ws.bean.Task;

/**
 * 工单信息 Mapper
 * 
 * @Package: com.gy.hsxt.ws.mapper
 * @ClassName: TaskMapper
 * @Description: TODO
 * 
 * @author: huanggy
 * @date: 2015-12-24 下午2:36:58
 * @version V1.0
 */
public interface TaskMapper {
	/**
	 * 插入工单记录
	 * 
	 * @param record
	 *            工单记录
	 * @return
	 */
	int insertSelective(Task record);

	/**
	 * 查询工单记录
	 * 
	 * @param taskId
	 *            工单编号（主键）
	 * @return
	 * 
	 * 
	 * 
	 * 
	 */
	Task selectByPrimaryKey(String taskId);

	/**
	 * 更新工单记录
	 * 
	 * @param record
	 *            工单记录
	 * @return
	 */
	int updateByPrimaryKeySelective(Task record);

}