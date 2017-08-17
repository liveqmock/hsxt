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

import com.gy.hsxt.gpf.gcs.bean.Language;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.ILanguageService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.LanguageMapper;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
 * 
 *  File Name       : LanguageService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 语言接口实现
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Service("languageService")
public class LanguageService implements ILanguageService {
	
	@Autowired
	private LanguageMapper languageMapper;
	
	@Autowired
	private IVersionService veresionService;
	
	@Override
	public int addLanguage(Language language) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_LANGUAGE);
		language.setVersion(version);
		
		return languageMapper.addLanguage(language);
	}

	@Override
	public boolean deleteLanguage(String languageCode) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_LANGUAGE);
		Language language = new Language(languageCode);
		language.setVersion(version);
		
		return languageMapper.deleteLanguage(language);
	}

	@Override
	public boolean updateLanguage(Language language) {
		long version = veresionService.addOrUpdateVersion(Constant.GL_LANGUAGE);
		language.setVersion(version);
		return languageMapper.updateLanguage(language);
	}

	@Override
	public List<Language> queryLanguage(Language language) {
		String nameCn =language.getChineseName();
		if(null != nameCn && !"".equals(nameCn)){
			nameCn = nameCn.replaceAll("_", "/_").replaceAll("%", "/%");
			language.setChineseName(nameCn);
		}
		String nameLocal =language.getLocalName();
		if(null != nameLocal && !"".equals(nameLocal)){
			nameLocal = nameLocal.replaceAll("_", "/_").replaceAll("%", "/%");
			language.setLocalName(nameLocal);
		}
		return languageMapper.queryLanguageListPage(language);
	}
	

	@Override
	public boolean existLanguage(String languageCode) {
		boolean isExist = "1".equals(languageMapper.existLanguage(languageCode));
		return isExist;
	}

	@Override
	public List<Language> language4DropDownList() {
		return languageMapper.language4DropDownList();
	}

	@Override
	public List<Language> queryLanguage4SyncData(Long version) {
		return languageMapper.queryLanguage4SyncData(version);
	}
}
