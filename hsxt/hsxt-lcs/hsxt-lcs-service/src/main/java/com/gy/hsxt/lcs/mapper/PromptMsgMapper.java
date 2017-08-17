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

import com.gy.hsxt.lcs.bean.PromptMsg;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.mapper
 * 
 *  File Name       : PromptMsgMapper.java
 * 
 *  Creation Date   : 2015-7-1
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 提示信息mapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface PromptMsgMapper {

    /**
     * 根据主键获取提示信息
     * @param languageCode
     * @param promptCode
     * @return
     */
    public PromptMsg queryPromptMsgWithPK(@Param("languageCode") String languageCode,
            @Param("promptCode") String promptCode);

    /**
     * 判断提示信息是否已经存在
     * @param languageCode
     * @param promptCode
     * @return
     */
    public String existPromptMsg(@Param("languageCode") String languageCode, @Param("promptCode") String promptCode);

    /**
     * 批量更新提示信息
     * @param promptMsgListUpdate
     */
    public void batchUpdate(List<PromptMsg> promptMsgListUpdate);

    /**
     * 批量查询提示信息
     * @param promptMsgListAdd
     */
    public void batchInsert(List<PromptMsg> promptMsgListAdd);
}
