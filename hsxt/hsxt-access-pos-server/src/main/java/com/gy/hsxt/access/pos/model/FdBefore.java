/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.model;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
		
/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.model  
 * @ClassName: FdBefore 
 * @Description: 初始化  pos 位元表
 *
 * @author: wucl 
 * @date: 2015-11-10 下午3:07:55 
 * @version V1.0
 */
@Component("bigMap")
public class FdBefore {

	@PostConstruct
	public void init(){
		BitMap.init();
	}
}

	