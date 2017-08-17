/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.lcs.bean.Unit;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.IUnitService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.UnitMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
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
	public Unit queryUnitWithPK(String languageCode,String unitCode) {
		return unitMapper.queryUnitWithPK(languageCode,unitCode);
	}

	@Override
	public List<Unit> queryUnitByLanguage(String languageCode) {
		return unitMapper.queryUnitByLanguage(languageCode);
	}

	@Override
	@Transactional
	public int addOrUpdateUnit(List<Unit> list, Long version) {
		veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_UNIT,version,true));
		List<Unit> unitListAdd = new ArrayList<Unit>();
		List<Unit> unitListUpdate = new ArrayList<Unit>();
		
		for(Unit obj : list){
			if("1".equals(unitMapper.existUnit(obj.getLanguageCode(), obj.getUnitCode()))){
				unitListUpdate.add(obj);
			}else{
				unitListAdd.add(obj);
			}
		}
		
		if(unitListUpdate.size()>0){
			unitMapper.batchUpdate(unitListUpdate);
		}
		
		if(unitListAdd.size()>0){
			unitMapper.batchInsert(unitListAdd);
		}
		return 1;
	}
}
