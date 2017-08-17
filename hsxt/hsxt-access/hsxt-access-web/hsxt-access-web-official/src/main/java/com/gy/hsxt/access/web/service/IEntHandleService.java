/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.service;

import com.gy.hsxt.access.web.bean.HttpResp;

/**
 * 企业办理接口
 * 
 * @Package: com.gy.hsxt.access.web.service
 * @ClassName: IEntHandleService
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月9日 下午5:36:30
 * @company: gyist
 * @version V3.0.0
 */
public interface IEntHandleService {

	/**
	 * 查询所有服务公司
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月10日 上午11:38:50
	 * @param cityName
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	public HttpResp selectAllEntInfoS(String cityName);

	/**
	 * 提交意向客户申请
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月10日 上午11:39:48
	 * @param intent
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	public HttpResp submitIntentionCustApply(String intent);

	/**
	 * 根据名称查询企业信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月10日 上午11:52:26
	 * @param entCustName
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	public HttpResp selectEntInfoByName(String entCustName);

	/**
	 * 根据授权码查询企业信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月10日 上午11:58:07
	 * @param authCode
	 * @param retData
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	public HttpResp selectEntInfoByCode(String authCode, Boolean retData);

	/**
	 * 查询公告期的企业
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月10日 上午11:58:43
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	public HttpResp selectNoticeEnt();

	/**
	 * 获取网银支付地址
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月10日 上午11:59:37
	 * @param orderNo
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	public HttpResp getEBankPayUrl(String orderNo);

	/**
	 * 提交意向客户申请
	 *
	 * @Description:
	 * @author: likui
	 * @created: 2016年06月26日 下午6:02:48
	 * @param cardProvice
	 * @return
	 * @return : HttpResp
	 * @version V3.0.0
	 */
	public HttpResp submitCardProvideApply(String cardProvice);

}
