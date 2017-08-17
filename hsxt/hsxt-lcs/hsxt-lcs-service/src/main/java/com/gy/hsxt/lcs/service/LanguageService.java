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

import com.gy.hsxt.lcs.bean.Language;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.ILanguageService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.LanguageMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
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
    @Transactional
    public int addOrUpdateLanguage(List<Language> list, Long version) {
        veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_LANGUAGE, version, true));
        List<Language> languageListAdd = new ArrayList<Language>();
        List<Language> languageListUpdate = new ArrayList<Language>();
        for (Language obj : list)
        {
            if ("1".equals(languageMapper.existLanguage(obj.getLanguageCode())))
            {
                languageListUpdate.add(obj);
            }
            else
            {
                languageListAdd.add(obj);
            }
        }

        if (languageListUpdate.size() > 0)
        {
            languageMapper.batchUpdate(languageListUpdate);
        }

        if (languageListAdd.size() > 0)
        {
            languageMapper.batchInsert(languageListAdd);
        }
        return 1;
    }

    /**
     * 查询有效语言列表
     * 
     * @return
     * @see com.gy.hsxt.lcs.interfaces.ILanguageService#queryLanguageAll()
     */
    @Override
    public List<Language> queryLanguageAll() {
        return languageMapper.queryLanguageAll();
    }
}
