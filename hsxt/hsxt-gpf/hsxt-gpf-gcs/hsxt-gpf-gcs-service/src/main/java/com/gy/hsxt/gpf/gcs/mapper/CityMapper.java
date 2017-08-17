package com.gy.hsxt.gpf.gcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.gpf.gcs.bean.City;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapperl
 * 
 *  File Name       : CityMapper.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 城市代码Mapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Repository("cityMapper")
public interface CityMapper {
	/**
	 * 插入记录
	 * 
	 * @param City
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	public int insert(City city);

	/**
	 * 读取某条记录
	 * 
	 * @param cityNo
	 *            城市代码 必须，非null
	 * @return 返回实体类City
	 */
	public City getCity(String cityNo);

	/**
	 * 读取某个省份的所以城市记录
	 * 
	 * @param provinceNo
	 *            省份代码 必须，非null
	 * 
	 * @return 返回List<City>,数据为空，返回空List<City>
	 */
	public List<City> queryCityByParent(String provinceNo);

	/**
	 * 获取数据分页列表
	 * 
	 * @param City实体类
	 *            必须，非null
	 * @return 返回List<City>,数据为空，返回空List<City>
	 */
	public List<City> getCityListPage(City city);

	/**
	 * 更新某一条记录
	 * 
	 * @param City实体类
	 *            必须，非null
	 * @return 返回int类型 1成功，其他失败
	 */
	public int update(City city);

	/**
	 * 删除某条记录
	 * 
	 * @param City
	 *            实体类 必须，非null
	 * @return 返回int类型 1成功，其他失败
	 */
	public int delete(City city);

	/**
	 * 判断是否已存在相同
	 * 
	 * @param cityNo
	 *            城市代码 必须，非null
	 * @param countryNo
	 *            国家代码 必须，非null
	 * @param provinceNo
	 *            省份代码 必须，非null
	 * @return 返回String 大于等于1存在，0不存在
	 */
	public String existCity(@Param("cityNo") String cityNo,
			@Param("countryNo") String countryNo,
			@Param("provinceNo") String provinceNo);

	/**
	 * 读取大于某个版本号的数据
	 * 
	 * @param version
	 *            版本号 必须，非null
	 * @return 返回List<City>,数据为空，返回空List<City>
	 */
	public List<City> queryCitySyncData(Long version);

}
