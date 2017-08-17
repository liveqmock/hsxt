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

import com.gy.hsxt.lcs.bean.Unit;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-lcs-service
 * 
 *  Package Name    : com.gy.hsxt.lcs.mapper
 * 
 *  File Name       : UnitMapper.java
 * 
 *  Creation Date   : 2015-7-1
 * 
 *  Author          : xiaofl
 * 
 *  Purpose         : 计量单位mapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface UnitMapper {

    /**
     * 根据主键获取计量单位
     * @param languageCode
     * @param unitCode
     * @return
     */
    public Unit queryUnitWithPK(@Param("languageCode") String languageCode, @Param("unitCode") String unitCode);

    /**
     * 判断数据是否存在
     * @param languageCode
     * @param unitCode
     * @return
     */
    public String existUnit(@Param("languageCode") String languageCode, @Param("unitCode") String unitCode);

    /**
     * 查询全部计量单位
     * @param languageCode
     * @return
     */
    public List<Unit> queryUnitByLanguage(@Param("languageCode") String languageCode);

    /**
     * 批量更新计量单位
     * @param unitListUpdate
     */
    public void batchUpdate(List<Unit> unitListUpdate);

    /**
     * 批量插入计量单位
     * @param unitListAdd
     */
    public void batchInsert(List<Unit> unitListAdd);
}
