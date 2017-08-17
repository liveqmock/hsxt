/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.baidu.disconf.client.common.annotations.DisconfFileItem;
import com.baidu.disconf.client.common.update.IDisconfUpdate;
import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsxt.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 * Description 		: 企业web规则约束条件配置文件
 * 
 * Project Name   	: hsxt-access-web-company 
 * 
 * Package Name     : com.gy.hsxt.access.web.company.services.common  
 * 
 * File Name        : CompanyConfigService 
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-10-9 下午4:42:32
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-10-9 下午4:42:32
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0
 * 
 * </PRE>
 ***************************************************************************/
@Service
@Scope("singleton")
public class ApsConfigService implements IDisconfUpdate {

	@Autowired
	private HsPropertiesConfigurer propertyConfigurer;

	/**
	 * @return the 管理员帐号
	 */
	public String getAdminUser() {
		return this.propertyConfigurer.getProperty("admin.user");
	}

	/**
	 * 交易密码长度
	 * 
	 * @return
	 */
	public int getTradingPasswordLength() {
		String value = this.propertyConfigurer
				.getProperty("trading.password.length");
		// 获取不到时候默认8位
		if (StringUtils.isBlank(value)) {
			value = "8";
		}
		return Integer.parseInt(value);
	}

	/**
	 * 登录密码长度
	 * 
	 * @return
	 */
	public int getLoginPasswordLength() {
		String value = this.propertyConfigurer
				.getProperty("login.passWord.length");
		// 获取不到时候默认6位
		if (StringUtils.isBlank(value)) {
			value = "6";
		}
		return Integer.parseInt(value);
	}

	/**
	 * @return the 验证码是否为固定值(1111)
	 */
	@DisconfFileItem(name = "img.code.isFixed", associateField = "imgCodeFixed")
	public boolean isImgCodeFixed() {

		String value = this.propertyConfigurer.getProperty("img.code.isFixed");

		// 获取不到时候默认false
		if (StringUtils.isBlank(value)) {
			value = "false";
		}
		return Boolean.parseBoolean(value);
	}
	
	/**
	 * 获取缓存图片保存时间 
	 * @return
	 */
	@DisconfFileItem(name = "img.code.overdueTime", associateField = "imgCodeOverdueTime")
	public int imgCodeOverdueTime(){
	    //缓存时间
	    String value =  this.propertyConfigurer.getProperty("img.code.overdueTime");
	    
	    if(StringUtils.isBlank(value)){
	        return 1200;
	    }
	    
	    return Integer.parseInt(value);
	}

	/**
	 * @throws Exception
	 * @see com.baidu.disconf.client.common.update.IDisconfUpdate#reload()
	 */
	@Override
	public void reload() throws Exception {
		// TODO Auto-generated method stub

	}

}
