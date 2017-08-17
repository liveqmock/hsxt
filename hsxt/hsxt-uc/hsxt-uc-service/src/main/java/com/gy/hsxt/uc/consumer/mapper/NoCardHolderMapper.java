package com.gy.hsxt.uc.consumer.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.uc.consumer.bean.CardHolder;
import com.gy.hsxt.uc.consumer.bean.NoCardHolder;

/**
 * 
 * 
 * @Package: com.gy.hsxt.uc.consumer.mapper
 * @ClassName: NoCardHolderMapper
 * @Description: 非持卡人信息Mapper类
 * 
 * @author: tianxh
 * @date: 2015-12-15 下午2:28:12
 * @version V1.0
 */
public interface NoCardHolderMapper {
	/**
	 * 插入非持卡人信息
	 * 
	 * @param record
	 *            持卡人信息
	 * @return
	 */
	int insertSelective(NoCardHolder record);

	/**
	 * 查询非持卡人信息
	 * 
	 * @param perCustId
	 *            客户号
	 * @return
	 */
	NoCardHolder selectByPrimaryKey(String perCustId);

	/**
	 * 通过手机号码查询客户号
	 * 
	 * @param mobile
	 *            手机号码
	 * @param isactive
	 *            标记此条记录的状态Y:可用 N:不可用
	 * @return
	 */
	String findCustIdByMobile(@Param("mobile") String mobile,
			@Param("isactive") String isactive);

	/**
	 * 更新非持卡人信息
	 * 
	 * @param record
	 *            非持卡人信息
	 * @return
	 */
	int updateByPrimaryKeySelective(NoCardHolder record);
	
	/**
	 * 
	 * @param list
	 * @return
	 */
	List<NoCardHolder> listAuthStatus(List<String> list);

}