/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.Bank;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.lcs.client.ProvinceTree;


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
public class BaseDataService implements IBaseDataService{
	
    //地区配置 dubbo
    @Autowired 
    private LcsClient lcsClient;

    /**  
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.common.IBaseDataService#findContryAll() 
     */
    @Override
    public List<Country> findContryAll() throws HsException {
        return this.lcsClient.findContryAll();
    }

    /**  
     * @param countryNo
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.common.IBaseDataService#getContryById(java.lang.String) 
     */
    @Override
    public Country getContryById(String countryNo) throws HsException {
        return this.lcsClient.getContryById(countryNo);
    }

    /**  
     * @param countryNo
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.common.IBaseDataService#queryProvinceByParent(java.lang.String) 
     */
    @Override
    public List<Province> findProvinceByParent(String countryNo) throws HsException {
        return this.lcsClient.queryProvinceByParent(countryNo);
    }

    /**  
     * @param countryNo
     * @param provinceNo
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.common.IBaseDataService#getProvinceById(java.lang.String, java.lang.String) 
     */
    @Override
    public Province getProvinceById(String countryNo, String provinceNo) throws HsException {
        return this.lcsClient.getProvinceById(countryNo,provinceNo);
    }

    /**  
     * @param countryNo
     * @param provinceNo
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.common.IBaseDataService#queryCityByParent(java.lang.String, java.lang.String) 
     */
    @Override
    public List<City> queryCityByParent(String countryNo, String provinceNo) throws HsException {
        return this.lcsClient.queryCityByParent(countryNo,provinceNo);
    }

    /**  
     * @param countryNo
     * @param privinceNo
     * @param cityNo
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.common.IBaseDataService#getCityById(java.lang.String, java.lang.String, java.lang.String) 
     */
    @Override
    public City getCityById(String countryNo, String privinceNo, String cityNo) throws HsException {
        return this.lcsClient.getCityById(countryNo, privinceNo, cityNo);
    }

    /**  
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.person.services.common.IBaseDataService#queryBankAll() 
     */
    @Override
    public List<Bank> findBankAll() throws HsException {
        return this.lcsClient.queryBankAll();
    }
    
    /**
     * 依据国家获取省份及城市列表
     * @param countryCode 国家代码
     * @return 省份及城市列表
     * @throws HsException
     */
    public List<ProvinceTree> findProvinceTreeOfCountry(String countryCode)throws HsException{
    	return this.lcsClient.queryProvinceTreeOfCountry(countryCode);
    }
    
}
