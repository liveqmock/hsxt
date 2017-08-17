package com.gy.hsxt.gpf.gcs.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.Subsys;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.ISubsysService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.SubsysMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : SubsysService.java
 * 
 *  Creation Date   : 2015-7-15
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 系统代码SubsysService实现类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

@Service("subsysService")
@Transactional(readOnly = true)
public class SubsysService implements ISubsysService {

	@Resource(name = "subsysMapper")
	private SubsysMapper subsysMapper;
	@Autowired
	private IVersionService veresionService;

	/**
	 * 插入记录
	 * 
	 * @param subsys
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	@Transactional
	public int insert(Subsys subsys) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_SUBSYS);
		subsys.setVersion(version);
		return subsysMapper.insert(subsys);
	}

	/**
	 * 读取某条记录
	 * 
	 * @param subsysCode
	 *            系统代码 必须，非null
	 * @return 返回实体类Subsys
	 */
	public Subsys getSubsys(String subsysCode) {
		return subsysMapper.getSubsys(subsysCode);
	}

	/**
	 * 获取数据分页列表
	 * 
	 * @param subsys实体类
	 *            必须，非null
	 * @return 返回List<Subsys>,数据为空，返回空List<Subsys>
	 */
	public List<Subsys> getSubsysListPage(Subsys subsys) {
		return subsysMapper.getSubsysListPage(subsys);
	}

	/**
	 * 获取下拉菜单列表
	 * 
	 * @return 返回List<Subsys>,数据为空，返回空List<Subsys>
	 */
	public List<Subsys> getSubsysDropdownmenu() {
		return subsysMapper.getSubsysDropdownmenu();
	}

	/**
	 * 更新某条记录
	 * 
	 * @param subsys实体类
	 *            必须，非null
	 * @return 返回int类型 1成功，其他失败
	 */
	@Transactional
	public int update(Subsys subsys) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_SUBSYS);
		subsys.setVersion(version);
		return subsysMapper.update(subsys);
	}

	/**
	 * 删除某条记录
	 * 
	 * @param subsys
	 *            实体类 必须，非null
	 * @return 返int类型 1成功，其他失败
	 */
	@Transactional
	public int delete(String subsysNo) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_SUBSYS);
		Subsys subsys = new Subsys();
		subsys.setSubsysCode(subsysNo);
		subsys.setVersion(version);
		subsys.setDelFlag(1);
		return subsysMapper.delete(subsys);
	}

	/**
	 * 判断是否已存在相同
	 * 
	 * @param subsysCode
	 *            系统代码 必须，非null
	 * @return 返回String 大于等于1存在，0不存在
	 */
	public boolean existSubsys(String subsysCode) {
		boolean iSExist = "1".equals(subsysMapper.existSubsys(subsysCode));
		return iSExist;
	}

	/**
	 * 读取大于某个版本号的数据
	 * 
	 * @param version
	 *            版本号 必须，非null
	 * @return 返回List<Subsys>,数据为空，返回空List<Subsys>
	 */
	public List<Subsys> querySubsysSyncData(Long version) {
		return subsysMapper.querySubsysSyncData(version);
	}
}
