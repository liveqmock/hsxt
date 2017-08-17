package com.gy.hsxt.uc.consumer.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.uc.consumer.bean.HsCard;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.consumer.mapper
 * @ClassName: HsCardMapper
 * @Description: 互生卡信息Mapper类
 * 
 * @author: tianxh
 * @date: 2015-12-15 下午2:27:49
 * @version V1.0
 */
public interface HsCardMapper {

	/**
	 * 插入互生卡信息
	 * 
	 * @param record
	 *            互生卡信息
	 * @return
	 */
	int insertSelective(HsCard record);

	/**
	 * 查询互生卡信息
	 * 
	 * @param perCustId
	 *            客户号
	 * @return
	 */
	HsCard selectByPrimaryKey(String perCustId);

	/**
	 * 更新互生卡信息
	 * 
	 * @param record
	 *            互生卡信息
	 * @return
	 */
	int updateByPrimaryKeySelective(HsCard record);

	/**
	 * 通过互生号查询客户号
	 * 
	 * @param perResNo
	 *            互生号
	 * @param isactive
	 *            标记此条记录的状态Y:可用 N:不可用
	 * @return
	 */
	String selectByResNo(@Param("perResNo") String perResNo,
			@Param("isactive") String isactive);

	/**
	 * 通过互生号查询记录数
	 * 
	 * @param perResNo
	 *            互生号
	 * @param isactive
	 *            标记此条记录的状态Y:可用 N:不可用
	 * @return
	 */
	int selectCountsByResNo(@Param("perResNo") String perResNo,
			@Param("isactive") String isactive);

	/**
	 * 批量插入互生卡信息
	 * 
	 * @param list
	 *            互生卡列表信息
	 * @return
	 */
	int batchInsertSelective(@Param("list") List<HsCard> list);

	/**
	 * 批量删除互生卡信息（逻辑删除）
	 * 
	 * @param isactive
	 *            标记此条记录的状态Y:可用 N:不可用
	 * @param updateDate
	 *            更新日期
	 * @param updatedby
	 *            更新人
	 * @param list
	 *            待更新的互生卡信息列表
	 * @return
	 */
	int batchDeleteByPrimaryKey(@Param("isactive") String isactive,
			@Param("updateDate") Timestamp updateDate,
			@Param("updatedby") String updatedby,
			@Param("list") List<String> list);
}