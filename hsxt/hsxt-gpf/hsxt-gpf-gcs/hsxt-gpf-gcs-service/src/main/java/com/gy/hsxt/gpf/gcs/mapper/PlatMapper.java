package com.gy.hsxt.gpf.gcs.mapper;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.gy.hsxt.gpf.gcs.bean.Plat;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapperl
 * 
 *  File Name       : PlatMapper.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 平台代码PlatMapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Repository("platMapper")
public interface PlatMapper {
	/**
	 * 插入记录
	 * 
	 * @param plat
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	public int insert(Plat plat);

	/**
	 * 读取某条记录
	 * 
	 * @param platNo
	 *            平台代码 必须，非null
	 * @return 返回实体类Plat
	 */
	public Plat getPlat(String platNo);

	/**
	 * 根据国家代码获取平台信息
	 * 
	 * @param countryNo
	 *            国家代码 必须，非null
	 * @return 返回实体类Plat
	 */
	public Plat getPlatByCountryNo(String countryNo);

	/**
	 * 获取数据分页列表
	 * 
	 * @param plat实体类
	 *            必须，非null
	 * @return 返回List<Plat>,数据为空，返回空List<Plat>
	 */
	public List<Plat> getPlatListPage(Plat plat);

	/**
	 * 获取有效平台列表
	 * 
	 * @return 返回List<Plat>,数据为空，返回空List<Plat>
	 */
	public List<Plat> findAllPlat();

	/**
	 * 更新某条记录
	 * 
	 * @param plat实体类
	 *            必须，非null
	 * @return 返回int类型 1成功，其他失败
	 */
	public int update(Plat plat);

	/**
	 * 删除某条记录
	 * 
	 * @param plat
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	public int delete(Plat plat);

	/**
	 * 判断是否已存在相同
	 * 
	 * @param platNo
	 *            平台代码 必须，非null
	 * @return 返回String 大于等于1存在，0不存在
	 */
	public String existPlat(String platNo);

	/**
	 * 判断平台IP是否存在
	 * 
	 * @param platIP
	 *            ip地址 必须，非null
	 * @return 返回String 大于等于1存在，0不存在
	 */
	public String existPlatIP(String platIP);

	/**
	 * 读取大于某个版本号的数据
	 * 
	 * @param version
	 *            版本号 必须，非null
	 * @return 返回List<Plat>,数据为空，返回空List<Plat>
	 */
	public List<Plat> queryPlatSyncData(long version);
}
