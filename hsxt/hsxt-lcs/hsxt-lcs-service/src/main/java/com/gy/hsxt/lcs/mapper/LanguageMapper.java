/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.lcs.bean.Language;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.mapper
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
public interface LanguageMapper {

    /**
     * 判断语言是否已存在
     * 
     * @param languageCode
     * @return
     */
    public String existLanguage(@Param("languageCode") String languageCode);

    /**
     * 语言批量更新
     * 
     * @param languageListUpdate
     */
    public void batchUpdate(List<Language> languageListUpdate);

    /**
     * 语言批量插入
     * 
     * @param languageListAdd
     */
    public void batchInsert(List<Language> languageListAdd);

    /**
     * 查询有效语言列表
     * 
     * @return
     */
    public List<Language> queryLanguageAll();
}
