/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.tool.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.bs.bean.tool.DeviceUseRecord;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceInfoPage;

/**
 * 设备使用记录Mapper
 * 
 * @Package: com.hsxt.bs.btool.mapper
 * @ClassName: ProductUseMapper
 * @Description: TODO
 * @author: likui
 * @date: 2015年9月30日 下午4:53:32
 * @company: gyist
 * @version V3.0.0
 */
@Repository(value = "deviceUseRecordMapper")
public interface DeviceUseRecordMapper {

	/**
	 * 插入设备使用记录
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月26日 下午3:53:39
	 * @param entity
	 * @return : void
	 * @version V3.0.0
	 */
	public void insertDeviceUseRecord(DeviceUseRecord entity);

	/**
	 * 分页查询工具设备使用
	 * 
	 * @Desc:
	 * @author: likui
	 * @created: 2015年10月26日 下午2:00:42
	 * @param deviceSeqNo
	 * @param useStatus
	 * @param batchNo
	 * @return
	 * @return : List<DeviceInfoPage>
	 * @version V3.0.0
	 */
	public List<DeviceInfoPage> selectToolDeviceUseListPage(@Param("deviceSeqNo") String deviceSeqNo,
			@Param("useStatus") Integer useStatus, @Param("batchNo") String batchNo);
}
