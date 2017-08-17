/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.Unit;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.IUnitService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.UnitMapper;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : UnitService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 计量单位接口实现
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("unitService")
public class UnitService implements IUnitService {

	@Autowired
	private UnitMapper unitMapper;

	@Autowired
	private IVersionService veresionService;

	@Override
	@Transactional
	public int addUnit(Unit unit) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_UNIT);
		unit.setVersion(version);

		return unitMapper.addUnit(unit);
	}

	@Override
	@Transactional
	public boolean deleteUnit(String languageCode,String unitCode) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_UNIT);
		Unit unit = new Unit(languageCode,unitCode);
		unit.setVersion(version);
		return unitMapper.deleteUnit(unit);
	}

	@Override
	@Transactional
	public boolean updateUnit(Unit unit) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_UNIT);
		unit.setVersion(version);
		return unitMapper.updateUnit(unit);
	}

	@Override
	public Unit queryUnitWithPK(String languageCode,String unitCode) {
		return unitMapper.queryUnitWithPK(languageCode,unitCode);
	}

	@Override
	public List<Unit> queryUnit(Unit unit) {
		String unitName =unit.getUnitName();
		if(null != unitName && !"".equals(unitName)){
			unitName = unitName.replaceAll("_", "/_").replaceAll("%", "/%");
			unit.setUnitName(unitName);
		}
		return unitMapper.queryUnitListPage(unit);
	}

	@Override
	public boolean existUnit(String languageCode,String unitCode) {
		boolean isExit = "1".equals(unitMapper.existUnit(languageCode,unitCode));
		return isExit;
	}

	@Override
	public List<Unit> queryUnitByLanguage(String languageCode) {
		return unitMapper.queryUnitByLanguage(languageCode);
	}

	@Override
	public List<Unit> queryUnit4SyncData(Long version) {
		return unitMapper.queryUnit4SyncData(version);
	}
}
