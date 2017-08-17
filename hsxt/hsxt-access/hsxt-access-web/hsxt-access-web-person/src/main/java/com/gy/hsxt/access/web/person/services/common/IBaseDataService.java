/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.person.services.common;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.Bank;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.bean.Province;


/***************************************************************************
 * <PRE>
 * Description 		: 地区配置服务类
 *
 * Project Name   	: hsxt-access-web-person 
 *
 * Package Name     : com.gy.hsxt.access.web.person.services.common  
 *
 * File Name        : IBaseDataService 
 * 
 * Author           : LiZhi Peter
 *
 * Create Date      : 2015-12-3 下午8:43:48
 *
 * Update User      : LiZhi Peter
 *
 * Update Date      : 2015-12-3 下午8:43:48
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v1.0
 *
 * </PRE>
 ***************************************************************************/
@Service
public interface IBaseDataService {
    
    
    /**
     * 查询国家列表
     * 
     * @return
     * @exception HsException
     */
    public List<Country> findContryAll() throws HsException;

    /**
     * 根据国家代码获取国家信息
     * 
     * @param countryNo
     *            国家代码
     * @return
     * @throws HsException
     */
    public Country getContryById(String countryNo) throws HsException;

    /**
     * 查询国家查询下级省列表
     * 
     * @param countryNo
     *            国家代码
     * @return
     * @throws HsException
     */
    public List<Province> findProvinceByParent(String countryNo) throws HsException;

    /**
     * 根据国家代码 +省代码查询省信息
     * @param countryNo
     * @param provinceNo
     *            省代码
     * @return
     * @throws HsException
     */
    public Province getProvinceById(String countryNo, String provinceNo) throws HsException;

    /**
     * 查询省下级城市列表
     * @param countryNo 国家代码
     * @param provinceNo 省代码
     * @return
     * @throws HsException
     */
    public List<City> queryCityByParent(String countryNo, String provinceNo) throws HsException;

    /**
     * 根据国家代码+省代码+城市代码查询城市信息
     * @param countryNo 国家代码
     * @param provinceNo 省代码
     * @param cityNo 城市代码
     * @return
     * @throws HsException
     */
    public City getCityById(String countryNo, String privinceNo, String cityNo) throws HsException;
    
    /**
     * 查询转账银行列表
     * @return 返回全部转账银行列表
     */
    public List<Bank> findBankAll()throws HsException;
    
}
