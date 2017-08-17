/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gpf.gcs.interfaces;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.Language;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.interfaces
 * 
 *  File Name       : LanguageService.java
 * 
 *  Creation Date   : 2015-7-6
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 语言接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface ILanguageService {

	/**
	 * 添加语言
	 * 
	 * @param language
	 * @return
	 */
	@Transactional
	public int addLanguage(Language language);

	/**
	 * 删除语言，delFlag = 1
	 * 
	 * @param language
	 * @return
	 */
	@Transactional
	public boolean deleteLanguage(String languageCode);

	/**
	 * 修改语言
	 * 
	 * @param language
	 * @return
	 */
	@Transactional
	public boolean updateLanguage(Language language);


	/**
	 * 查询语言
	 * 
	 * @param language
	 * @return
	 */
	public List<Language> queryLanguage(Language language);

	/**
	 * 判断是否存一样的语言代码
	 * 
	 * @param language
	 * @return
	 */
	public boolean existLanguage(String languageCode);
	
	/**
	 * 语言下拉列表
	 * @return
	 */
	public List<Language>  language4DropDownList();

	/**
	 * 查询出版本号大于子平台的所有语言
	 * @param version
	 * @return
	 */
	public List<Language> queryLanguage4SyncData(Long version);
	
}
