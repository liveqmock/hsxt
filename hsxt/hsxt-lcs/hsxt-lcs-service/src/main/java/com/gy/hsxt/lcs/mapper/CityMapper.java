package com.gy.hsxt.lcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.lcs.bean.City;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.mapper
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
public interface CityMapper {
	/**
	 * 读取某条记录
	 * @param countryNo 国家代码，非null
     * @param provinceNo 省代码，非null 
	 * @param cityNo 城市代码 必须 非空
	 * @return 返回City实体类
	 */
	public City getCity(@Param("countryNo")String countryNo, 
	                    @Param("provinceNo")String provinceNo, 
	                    @Param("cityNo")String cityNo);

	/**
	 * 读取某个省份的所以城市记录
	 * @param countryNo 国家代码，非null
	 * @param provinceNo 省份代码 必须 非空
	 * @return 返回List<City>列表，数据为空，返回空List<City>
	 */
	public List<City> queryCityByParent(@Param("countryNo")String countryNo, 
                                        @Param("provinceNo")String provinceNo);

	/**
	 * 判断是否已存在相同的城市代码
	 * 
	 * @param cityNo
	 *            城市代码 必须 非空
	 * @param countryNo
	 *            国家代码 必须 非空
	 * @param provinceNo
	 *            省级代码 必须 非空
	 * @return 返回String,1已经存在，0不存在
	 */
	public String existCity(@Param("countryNo") String countryNo,
			                @Param("provinceNo") String provinceNo,
			                @Param("cityNo") String cityNo);

	/**
	 * 批量更新数据
	 * 
	 * @param cityListUpdate
	 *            总平台城市变更的数据列表 必须 非空， 无返回值
	 */
	public void batchUpdate(List<City> cityListUpdate);

	/**
	 * 批量插入数据
	 * 
	 * @param cityListAdd
	 *            总平台城市变更的数据列表 必须 非空， 无返回值
	 */
	public void batchInsert(List<City> cityListAdd);

}
