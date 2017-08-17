package com.gy.hsxt.lcs.mapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapper
 * 
 *  File Name       : SubsysMapper.java
 * 
 *  Creation Date   : 2015-7-15
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 系统代码ProvinceMapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
import java.util.List;

import com.gy.hsxt.lcs.bean.Subsys;

public interface SubsysMapper {

	/**
	 * 读取某条记录
	 * 
	 * @param subsysCode
	 *            系统代码 必须 非空
	 * @return 返回Province实体类
	 */
	public String existSubsys(String subsysCode);

	/**
	 * 批量更新数据
	 * 
	 * @param subsysListUpdate
	 *            总平台城市变更的数据列表 必须 非空， 无返回值
	 */
	public void batchUpdate(List<Subsys> subsysListUpdate);

	/**
	 * 批量插入数据
	 * 
	 * @param subsysListAdd
	 *            总平台城市变更的数据列表 必须 非空， 无返回值
	 */
	public void batchInsert(List<Subsys> subsysListAdd);
}
