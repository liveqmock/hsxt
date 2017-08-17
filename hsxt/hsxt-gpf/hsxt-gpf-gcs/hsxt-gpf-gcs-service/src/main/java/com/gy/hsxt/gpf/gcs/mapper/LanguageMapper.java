/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gy.hsxt.gpf.gcs.bean.Language;
/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapper
 * 
 *  File Name       : LanguageMapper.java
 * 
 *  Creation Date   : 2015-7-1
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 语言mapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Repository("LanguageMapper")
public interface LanguageMapper {
	
	public int addLanguage(Language language);

	public boolean deleteLanguage(Language language);

	public boolean updateLanguage(Language language);

	public String existLanguage(@Param("languageCode")String languageCode);

	public List<Language> queryLanguageListPage(Language language);
	
	public Language queryLanguageWithPK(@Param("languageCode")String languageCode);
	
	public List<Language> language4DropDownList();
	
	public List<Language> queryLanguage4SyncData(@Param("version")long version);
	
}
