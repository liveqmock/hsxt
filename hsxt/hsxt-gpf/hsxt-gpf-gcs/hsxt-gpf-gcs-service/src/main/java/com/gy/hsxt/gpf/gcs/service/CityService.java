package com.gy.hsxt.gpf.gcs.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.City;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.ICityService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.CityMapper;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : CityService.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 城市代码CityService实现类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("cityService")
@Transactional(readOnly = true)
public class CityService implements ICityService {

	@Resource(name = "cityMapper")
	private CityMapper cityMapper;
	@Autowired
	private IVersionService veresionService;

	/**
	 * 插入记录
	 * 
	 * @param City
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	@Transactional
	public int insert(City city) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_CITY);
		city.setVersion(version);
		return cityMapper.insert(city);
	}

	/**
	 * 读取某条记录
	 * 
	 * @param cityNo
	 *            城市代码 必须，非null
	 * @return 返回实体类City
	 */
	public City getCity(String cityNo) {
		return cityMapper.getCity(cityNo);
	}

	/**
	 * 获取数据分页列表
	 * 
	 * @param City实体类
	 *            必须，非null
	 * @return 返回List<City>,数据为空，返回空List<City>
	 */
	public List<City> getCityListPage(City city) {
		return cityMapper.getCityListPage(city);
	}

	/**
	 * 读取某个省份的所以城市记录
	 * 
	 * @param provinceNo
	 *            省份代码 必须，非null
	 * 
	 * @return 返回List<City>,数据为空，返回空List<City>
	 */
	public List<City> queryCityByParent(String provinceNo) {
		return cityMapper.queryCityByParent(provinceNo);
	}

	/**
	 * 更新某一条记录
	 * 
	 * @param City实体类
	 *            必须，非null
	 * @return 返回int类型 1成功，其他失败
	 */
	@Transactional
	public int update(City city) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_CITY);
		city.setVersion(version);
		return cityMapper.update(city);
	}

	/**
	 * 删除某条记录
	 * 
	 * @param City
	 *            实体类 必须，非null
	 * @return 返回int类型 1成功，其他失败
	 */
	@Transactional
	public int delete(String cityNo) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_CITY);
		City city = new City();
		city.setCityNo(cityNo);
		city.setVersion(version);
		city.setDelFlag(1);
		return cityMapper.delete(city);
	}

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
	public boolean existCity(String cityNo, String countryNo, String provinceNo) {

		boolean isExist = "1".equals(cityMapper.existCity(cityNo, countryNo,
				provinceNo));

		return isExist;
	}

	/**
	 * 读取大于某个版本号的数据
	 * 
	 * @param version
	 *            版本号 必须，非null
	 * @return 返回List<City>,数据为空，返回空List<City>
	 */
	public List<City> queryCitySyncData(Long version) {
		return cityMapper.queryCitySyncData(version);
	}

}
