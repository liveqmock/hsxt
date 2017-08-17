/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ao.bean.BatchUpload;

/**
 * 
 * 终端批上传 mapper dao映射类
 * 
 * @Package: com.gy.hsxt.ao.mapper
 * @ClassName: BatchUploadMapper
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-8 下午5:24:26
 * @version V1.0
 */
public interface BatchUploadMapper {

	/**
	 * 插入 终端批上传
	 * 
	 * @param batchUpload
	 * @return
	 */
	public int insertBatchUpload(
			@Param("batchUpload") List<BatchUpload> batchUpload);

	/**
	 * 删除前几个月的批上传数据
	 * 
	 * @param month
	 *            前几个月
	 */
	public void deleteBatchUploadLastMath(@Param("month") Integer month);

}
