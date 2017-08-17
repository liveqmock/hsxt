package com.gy.hsxt.gpf.gcs.service;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    :com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : PlatService.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 平台代码PlatService实现类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.Plat;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IPlatService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.PlatMapper;

@Service("platService")
public class PlatService implements IPlatService {

	@Resource(name = "platMapper")
	private PlatMapper platMapper;
	@Autowired
	private IVersionService veresionService;

	/**
	 * 插入记录
	 * 
	 * @param plat
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	@Transactional
	public int insert(Plat plat) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_PLAT);
		plat.setVersion(version);
		return platMapper.insert(plat);
	}

	/**
	 * 读取某条记录
	 * 
	 * @param platNo
	 *            平台代码 必须，非null
	 * @return 返回实体类Plat
	 */
	public Plat getPlat(String platNo) {
		return platMapper.getPlat(platNo);
	}

	/**
	 * 根据国家代码获取平台信息
	 * 
	 * @param countryNo
	 *            国家代码 必须，非null
	 * @return 返回实体类Plat
	 */
	public Plat getPlatByCountryNo(String countryNo) {
		return platMapper.getPlatByCountryNo(countryNo);
	}

	/**
	 * 获取数据分页列表
	 * 
	 * @param plat实体类
	 *            必须，非null
	 * @return 返回List<Plat>,数据为空，返回空List<Plat>
	 */
	public List<Plat> getPlatListPage(Plat plat) {
		return platMapper.getPlatListPage(plat);
	}

	/**
	 * 获取下拉菜单列表
	 * 
	 * @return 返回List<Plat>,数据为空，返回空List<Plat>
	 */
	public List<Plat> findAllPlat() {
		return platMapper.findAllPlat();
	}

	/**
	 * 更新某条记录
	 * 
	 * @param plat实体类
	 *            必须，非null
	 * @return 返回int类型 1成功，其他失败
	 */
	@Transactional
	public int update(Plat plat) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_PLAT);
		plat.setVersion(version);
		return platMapper.update(plat);
	}

	/**
	 * 删除某条记录
	 * 
	 * @param plat
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	@Transactional
	public int delete(String platNo) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_PLAT);
		Plat plat = new Plat();
		plat.setPlatNo(platNo);
		plat.setVersion(version);
		plat.setDelFlag(true);
		return platMapper.delete(plat);
	}

	/**
	 * 判断是否已存在相同
	 * 
	 * @param platNo
	 *            平台代码 必须，非null
	 * @return 返回String 大于等于1存在，0不存在
	 */
	public boolean isExistIP(String ip) {
		boolean isExistIP = "1".equals(platMapper.existPlatIP(ip));
		return isExistIP;
	}

	/**
	 * 判断平台IP是否存在
	 * 
	 * @param platIP
	 *            ip地址 必须，非null
	 * @return 返回String 大于等于1存在，0不存在
	 */
	public boolean existPlat(String platNo) {
		boolean existPlat = "1".equals(platMapper.existPlat(platNo));
		return existPlat;
	}

	/**
	 * 读取大于某个版本号的数据
	 * 
	 * @param version
	 *            版本号 必须，非null
	 * @return 返回List<Plat>,数据为空，返回空List<Plat>
	 */
	public List<Plat> queryPlatSyncData(long version) {
		return platMapper.queryPlatSyncData(version);
	}
}
