package com.gy.hsxt.uc.consumer.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.uc.as.bean.consumer.AsConsumerInfo;
import com.gy.hsxt.uc.as.bean.consumer.AsQueryConsumerCondition;
import com.gy.hsxt.uc.consumer.bean.CardHolder;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.consumer.mapper
 * @ClassName: CardHolderMapper
 * @Description: 持卡人信息Mapper类
 * 
 * @author: tianxh
 * @date: 2015-12-15 下午2:27:23
 * @version V1.0
 */
public interface CardHolderMapper {
	/**
	 * 插入持卡人信息
	 * 
	 * @param record
	 *            持卡人信息
	 * @return
	 */
	int insertSelective(CardHolder record);

	/**
	 * 查询持卡人信息
	 * 
	 * @param perCustId
	 *            客户号
	 * @return
	 */
	CardHolder selectByPrimaryKey(String perCustId);

	/**
	 * 更新持卡人信息
	 * 
	 * @param record
	 *            持卡人信息
	 * @return
	 */
	int updateByPrimaryKeySelective(CardHolder record);

	/**
	 * 批量插入持卡人信息
	 * 
	 * @param list
	 * @return
	 */
	int batchInsertSelective(@Param("list") List<CardHolder> list);

	/**
	 * 批量删除持卡人信息
	 * 
	 * @param isactive
	 *            标记此条记录的状态Y:可用 N:不可用
	 * @param updateDate
	 *            更新日期
	 * @param updatedby
	 *            更新人
	 * @param list
	 *            待更新的持卡人信息列表
	 * @return
	 */
	int batchDeleteByPrimaryKey(@Param("isactive") String isactive,
			@Param("updateDate") Timestamp updateDate, @Param("updatedby") String updatedby,
			@Param("list") List<String> list);

	/**
	 * 
	 * @param list
	 * @return
	 */
	List<CardHolder> listAuthStatus(List<String> list);

	/**
	 * 分页查询消费者信息
	 * 
	 * @param condition
	 *            分页查询条件
	 * @param page
	 *            分页参数
	 * @return
	 */
	List<AsConsumerInfo> pageConsumerInfo(@Param("condition") AsQueryConsumerCondition condition,
			@Param("page") Page page);
	/**
	 * 分页查询所有消费者信息
	 * @param condition
	 * @param page
	 * @return
	 */
	List<AsConsumerInfo> listAllConsumerInfo(@Param("condition") AsQueryConsumerCondition condition,
			@Param("page") Page page);
	
	/**
	 * 统计消费者总记录数
	 * 
	 * @param condition
	 *            分页查询条件
	 * @return
	 */
	int countConsumerInfo(@Param("condition") AsQueryConsumerCondition condition);
	
	
	/**
	 * 统计所有消费者总记录数
	 * 
	 * @param condition
	 *            分页查询条件
	 * @return
	 */
	int countAllConsumerInfo(@Param("condition") AsQueryConsumerCondition condition);
	
	
}