package com.gy.hsxt.lcs.mapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapperl
 * 
 *  File Name       : PlatMapper.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 平台代码PlatMapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
import java.util.List;

import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.Plat;

public interface PlatMapper {

    /**
     * 读取某条记录
     * 
     * @param platNo
     *            国家代码 必须 非空
     * @return 返回Plat实体类
     */
    public Plat getPlat(String platNo);

    /**
     * 根据国家代码获取平台信息
     * 
     * @param countryNo
     *            国家代码 必须 非空
     * @return 返回Plat实体类
     */
    public Plat getPlatByCountryNo(String countryNo);

    /**
     * 判断平台代码是否存在
     * 
     * @param platNo
     *            国家代码 必须 非空
     * @return 返回String,1已经存在，0不存在
     */
    public String existPlat(String platNo);

    /**
     * 判断平台IP是否存在
     * 
     * @param platIP
     * @return
     */
    public String existPlatIP(String platIP);

    /**
     * 批量更新数据
     * 
     * @param platListUpdate
     *            总平台城市变更的数据列表 必须 非空， 无返回值
     */
    public void batchUpdate(List<Plat> platListUpdate);

    /**
     * 批量插入数据
     * 
     * @param platListAdd
     *            总平台城市变更的数据列表 必须 非空， 无返回值
     */
    public void batchInsert(List<Plat> platListAdd);

    /**
     * 获取本平台信息
     * 
     * @return 返回本平台信息
     */
    public Plat getLocalPlat();

    /**
     * 获取本平台详细信息
     * 
     * @return 返回本平台信息
     */
    public LocalInfo getLocalInfo();

    /**
     * 获取所有有效平台信息
     * 
     * @return
     */
    public List<Plat> queryPlatAll();

}
