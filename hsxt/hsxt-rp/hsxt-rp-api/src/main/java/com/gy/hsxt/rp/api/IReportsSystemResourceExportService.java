/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.rp.api;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsCardholderResource;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;

/**
 * 
 * 资源导出接口
 * @Package: com.gy.hsxt.rp.api  
 * @ClassName: IReportsSystemResourceExportService 
 * @Description: TODO
 *
 * @author: maocan 
 * @date: 2015-12-28 下午6:14:32 
 * @version V1.0
 */
public interface IReportsSystemResourceExportService {
	
	/**
	 * 托管企业资源导出
	 * @param rpEnterprisResource 企业查询条件
	 * @return String fileAddress 资源文件下载路径
	 * @throws HsException
	 */
	public String exportTrusEntResource(ReportsEnterprisResource rpEnterprisResource) throws HsException;
	
	/**
	 * 成员企业资源导出
	 * @param rpEnterprisResource 企业查询条件
	 * @return String fileAddress 资源文件下载路径
	 * @throws HsException
	 */
	public String exportMemberEntResource(ReportsEnterprisResource rpEnterprisResource) throws HsException;
	
	/**
	 * 服务公司资源导出
	 * @param rpEnterprisResource 企业查询条件
	 * @return String fileAddress 资源文件下载路径
	 * @throws HsException
	 */
	public String exportServiceEntResource(ReportsEnterprisResource rpEnterprisResource) throws HsException;
	
	
	/**
	 * 消费者资源导出
	 * @param rpCardholderResource 消费者查询条件
	 * @return String fileAddress 资源文件下载路径
	 * @throws HsException
	 */
	public String exportCardholderResource(ReportsCardholderResource rpCardholderResource) throws HsException;
	
}
