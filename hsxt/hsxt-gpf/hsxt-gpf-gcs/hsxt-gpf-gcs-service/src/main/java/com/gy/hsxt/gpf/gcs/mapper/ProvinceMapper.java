package com.gy.hsxt.gpf.gcs.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gy.hsxt.gpf.gcs.bean.Province;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapperl
 * 
 *  File Name       : ProvinceMapper.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 省份代码ProvinceMapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Repository("provinceMapper")
public interface ProvinceMapper {
	/**
	 * 插入记录
	 * 
	 * @param province
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	public int insert(Province province);

	/**
	 * 读取某条记录
	 * 
	 * @param provinceNo
	 *            省份代码 必须，非null
	 * @return 返回实体类province
	 */
	public Province getProvince(String provinceNo);

	/**
	 * 获取数据分页列表
	 * 
	 * @param province实体类
	 *            必须，非null
	 * @return 返回List<Province>,数据为空，返回空List<Province>
	 */
	public List<Province> getProvinceListPage(Province province);

	/**
	 * 获取某个国家代码下的所以有省份--下拉菜单列表
	 * 
	 * @param countryNo
	 *            国家代码 必须，非null
	 * @return 返回List<Province>,数据为空，返回空List<Province>
	 */
	public List<Province> getProvinceDropdownmenu(String countryNo);

	/**
	 * 更新某条记录
	 * 
	 * @param province实体类
	 *            必须，非null
	 * @return 返回int类型 1成功，其他失败
	 */
	public int update(Province province);

	/**
	 * 删除某条记录
	 * 
	 * @param province
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	public int delete(Province province);

	/**
	 * 判断是否已存在相同
	 * 
	 * @param provinceNo
	 *            省份代码 必须，非null
	 * @return 返回String 大于等于1存在，0不存在
	 */
	public String existProvince(String provinceNo);

	/**
	 * 读取大于某个版本号的数据
	 * 
	 * @param version
	 *            版本号 必须，非null
	 * @return 返回List<Province>,数据为空，返回空List<Province>
	 */
	public List<Province> queryProvinceSyncData(long version);

}
