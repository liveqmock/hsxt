/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 
 * 记账分解数据老化 mapper dao映射类
 * 
 * @Package: com.gy.hsxt.ao.mapper
 * @ClassName: AccountingHisMapper
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-11 下午7:18:53
 * @version V1.0
 */
public interface AccountingHisMapper {

	/**
	 * 插入 上一个月的终端批上传数据到历史表
	 * 
	 * @return
	 */
	public int insertAccountingHisLastMath(@Param("month") Integer month);

}
