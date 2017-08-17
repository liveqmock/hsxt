package com.gy.hsxt.gpf.gcs.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.Province;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IProvinceService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.ProvinceMapper;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : ProvinceService.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 省份代码ProvinceService实现类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("provinceService")
@Transactional(readOnly = true)
public class ProvinceService implements IProvinceService {

	@Resource(name = "provinceMapper")
	private ProvinceMapper provinceMapper;
	@Autowired
	private IVersionService veresionService;

	/**
	 * 插入记录
	 * 
	 * @param province
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	@Transactional
	public int insert(Province province) {
		long version = veresionService
				.addOrUpdateVersion(Constant.GL_PROVINCE);
		province.setVersion(version);
		return provinceMapper.insert(province);
	}

	/**
	 * 读取某条记录
	 * 
	 * @param provinceNo
	 *            省份代码 必须，非null
	 * @return 返回实体类province
	 */
	public Province getProvince(String provinceNo) {
		return provinceMapper.getProvince(provinceNo);
	}

	/**
	 * 获取某个国家代码下的所以有省份--下拉菜单列表
	 * 
	 * @param countryNo
	 *            国家代码 必须，非null
	 * @return 返回List<Province>,数据为空，返回空List<Province>
	 */
	public List<Province> getProvinceDropdownmenu(String countryNo) {
		return provinceMapper.getProvinceDropdownmenu(countryNo);
	}

	/**
	 * 获取数据分页列表
	 * 
	 * @param province实体类
	 *            必须，非null
	 * @return 返回List<Province>,数据为空，返回空List<Province>
	 */
	public List<Province> getProvinceListPage(Province province) {
		return provinceMapper.getProvinceListPage(province);
	}

	/**
	 * 更新某条记录
	 * 
	 * @param province实体类
	 *            必须，非null
	 * @return 返回int类型 1成功，其他失败
	 */
	@Transactional
	public int update(Province province) {
		long version = veresionService
				.addOrUpdateVersion(Constant.GL_PROVINCE);
		province.setVersion(version);
		return provinceMapper.update(province);
	}

	/**
	 * 删除某条记录
	 * 
	 * @param province
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	@Transactional
	public int delete(String provinceNo) {
		long version = veresionService
				.addOrUpdateVersion(Constant.GL_PROVINCE);
		Province province = new Province();
		province.setProvinceNo(provinceNo);
		province.setVersion(version);
		province.setDelFlag(1);
		return provinceMapper.delete(province);
	}

	/**
	 * 判断是否已存在相同
	 * 
	 * @param provinceNo
	 *            省份代码 必须，非null
	 * @return 返回String 大于等于1存在，0不存在
	 */
	public boolean existProvince(String provinceNo) {
		boolean iSExist = "1".equals(provinceMapper.existProvince(provinceNo));
		return iSExist;
	}

	/**
	 * 读取大于某个版本号的数据
	 * 
	 * @param version
	 *            版本号 必须，非null
	 * @return 返回List<Province>,数据为空，返回空List<Province>
	 */
	public List<Province> queryProvinceSyncData(long version) {
		return provinceMapper.queryProvinceSyncData(version);
	}
}
