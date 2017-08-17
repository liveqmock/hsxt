package com.gy.hsxt.lcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.lcs.bean.Province;
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
public interface ProvinceMapper {

	/**
	 * 读取某条记录
	 * @param countryNo 国家代码 非空
	 * @param provinceNo 省份代码 必须 非空
	 * @return 返回Province实体类
	 */
	public Province getProvince(@Param("countryNo")String countryNo, 
	                            @Param("provinceNo")String provinceNo);

	/**
	 * 根据国家获取下级省份列表
	 * 
	 * @param countryNo 国家代码 必须 非空
	 * @return 返回List<Province>列表，数据为空
	 */
	public List<Province> queryProvinceByParent(String countryNo);

	/**
	 * 判断平台代码是否存在
	 * @param countryNo 国家代码 非空
	 * @param provinceNo 省份代码 必须 非空
	 * @return 返回String,1已经存在，0不存在
	 */
	public String existProvince(@Param("countryNo")String countryNo, 
                                @Param("provinceNo")String provinceNo);

	/**
	 * 批量更新数据
	 * 
	 * @param provinceListUpdate
	 *            总平台城市变更的数据列表 必须 非空， 无返回值
	 */
	public void batchUpdate(List<Province> provinceListUpdate);

	/**
	 * 批量插入数据
	 * 
	 * @param provinceListAdd
	 *            总平台城市变更的数据列表 必须 非空， 无返回值
	 */
	public void batchInsert(List<Province> provinceListAdd);
}
