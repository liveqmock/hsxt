/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.Language;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : ILanguageService.java
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
     * 插入或更新语言
     * 
     * @param list
     *            新增或者更新的语言列表
     * @param version
     * @return
     */
    public int addOrUpdateLanguage(List<Language> list, Long version);

    /**
     * 查询所有语言信息
     * 
     * @return
     */
    public List<Language> queryLanguageAll();
}
