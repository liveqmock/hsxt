package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.Province;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : IProvinceService.java
 * 
 *  Creation Date   : 2015-7-10
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 省份代码IProvinceService接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface IProvinceService {

    /**
     * 读取某条记录
     * @param countryNo 国家代码
     * @param provinceNo
     *            省份代码 必须 非空
     * @return 返回Province实体类
     */
    public Province getProvince(String countryNo, String provinceNo);

	/**
	 * 根据国家获取下级省份列表
	 * 
	 * @param countryNo
	 *            国家代码 必须 非空
	 * @return 返回List<Province>列表，数据为空，返回空List<Province>
	 */
	public List<Province> findProvinceByParent(String countryNo);

	/**
	 * 批量更新数据
	 * 
	 * @param list
	 *            获取变的版本号的数据表示 必须
	 * @param version
	 *            版本号
	 * @return 返回int 1成功，其他失败
	 */
	public int addOrUpdateProvince(List<Province> list, Long version);
}
