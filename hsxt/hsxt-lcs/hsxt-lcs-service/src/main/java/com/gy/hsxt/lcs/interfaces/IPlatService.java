package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.Plat;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : IPlatService.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 平台代码IPlatService接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IPlatService {

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
    public boolean isExistIP(String ip);

    /**
     * 批量更新数据
     * 
     * @param list
     *            获取变的版本号的数据表示 必须
     * @param version
     *            版本号
     * @return 返回int 1成功，其他失败
     */
    public int addOrUpdatePlat(List<Plat> list, Long version);

    /**
     * 获取本平台信息
     * 
     * @return 返回本平台信息
     */
    public Plat getLocalPlat();

    /**
     * 获取所有有效平台信息
     * 
     * @return
     */
    public List<Plat> queryPlatAll();

    /**
     * 获取本平台详细信息
     * 
     * @return 返回本平台信息
     */
    public LocalInfo getLocalInfo();
}
