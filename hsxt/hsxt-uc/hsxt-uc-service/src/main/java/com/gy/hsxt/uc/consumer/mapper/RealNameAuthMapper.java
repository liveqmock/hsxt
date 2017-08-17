package com.gy.hsxt.uc.consumer.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.uc.consumer.bean.RealNameAuth;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.consumer.mapper
 * @ClassName: RealNameAuthMapper
 * @Description: 实名认证信息Mapper类
 * 
 * @author: tianxh
 * @date: 2015-12-15 下午2:28:25
 * @version V1.0
 */
public interface RealNameAuthMapper {

	/**
	 * 插入实名认证信息
	 * 
	 * @param record
	 *            实名认证信息
	 * @return
	 */
	int insertSelective(RealNameAuth record);

	/**
	 * 通过客户号查询实名认证信息
	 * 
	 * @param perCustId
	 * @return
	 */
	RealNameAuth selectByPrimaryKey(String perCustId);

	/**
	 * 更新实名认证信息
	 * 
	 * @param record
	 *            实名认证信息
	 * @return
	 */
	int updateByPrimaryKeySelective(RealNameAuth record);

	/**
	 * 通过真实姓名、证件类型、证件号码查询记录数
	 * 
	 * @param realName
	 *            真实姓名
	 * @param cerType
	 *            证件类型
	 * @param cerNo
	 *            证件号码
	 * @return
	 */
	int getIdCardNumer(@Param("idType") Integer cerType,
			@Param("idNo") String cerNo, @Param("isactive") String isactive,
			@Param("perCustId") String perCustId,
			@Param("perName") String perName);

	/**
	 * 批量删除实名认证信息
	 * 
	 * @param isactive
	 *            标记此条记录的状态Y:可用 N:不可用
	 * @param updateDate
	 *            更新日期
	 * @param updatedby
	 *            更新人
	 * @param list
	 *            待更新的实名认证信息列表
	 * @return
	 */
	int batchDeleteByPrimaryKey(@Param("isactive") String isactive,
			@Param("updateDate") Timestamp updateDate,
			@Param("updatedby") String updatedby,
			@Param("list") List<String> list);
}