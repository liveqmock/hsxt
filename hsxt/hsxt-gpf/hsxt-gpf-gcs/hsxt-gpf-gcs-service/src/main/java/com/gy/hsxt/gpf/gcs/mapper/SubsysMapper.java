package com.gy.hsxt.gpf.gcs.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gy.hsxt.gpf.gcs.bean.Subsys;

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
@Repository("subsysMapper")
public interface SubsysMapper {
	/**
	 * 插入记录
	 * 
	 * @param subsys
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	public int insert(Subsys subsys);

	/**
	 * 读取某条记录
	 * 
	 * @param subsysCode
	 *            系统代码 必须，非null
	 * @return 返回实体类Subsys
	 */
	public Subsys getSubsys(String subsysCode);

	/**
	 * 获取数据分页列表
	 * 
	 * @param subsys实体类
	 *            必须，非null
	 * @return 返回List<Subsys>,数据为空，返回空List<Subsys>
	 */
	public List<Subsys> getSubsysListPage(Subsys subsys);

	/**
	 * 获取下拉菜单列表
	 * 
	 * @return 返回List<Subsys>,数据为空，返回空List<Subsys>
	 */
	public List<Subsys> getSubsysDropdownmenu();

	/**
	 * 更新某条记录
	 * 
	 * @param subsys实体类
	 *            必须，非null
	 * @return 返回int类型 1成功，其他失败
	 */
	public int update(Subsys subsys);

	/**
	 * 删除某条记录
	 * 
	 * @param subsys
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	public int delete(Subsys subsys);

	/**
	 * 判断是否已存在相同
	 * 
	 * @param subsysCode
	 *            系统代码 必须，非null
	 * @return 返回String 大于等于1存在，0不存在
	 */
	public String existSubsys(String subsysCode);

	/**
	 * 读取大于某个版本号的数据
	 * 
	 * @param version
	 *            版本号 必须，非null
	 * @return 返回List<Subsys>,数据为空，返回空List<Subsys>
	 */
	public List<Subsys> querySubsysSyncData(long version);
}
