/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ws.api;

import java.util.List;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ws.bean.WelfareQualify;

/**
 * 提供福利资格服务
 * 
 * @Package: com.gy.hsxt.ws.api
 * @ClassName: IWsApplyService
 * @Description: TODO
 * 
 * @author: chenhongzhi
 * @date: 2015-11-11 下午7:54:45
 * @version V3.0
 */
public interface IWsWelfareService {

	/**
	 * 地区平台查询积分福利资格
	 * 
	 * @param hsResNo
	 *            互生号 选传
	 * @param welfareType
	 *            福利类型 0 意外伤害(包含身故)、1 医疗保障
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<WelfareQualify> listWelfareQualify(String hsResNo, Integer welfareType,
			Page page) throws HsException;

	/**
	 * 是否有资格
	 * 
	 * @param hsResNO
	 *            互生号 必传
	 * @param welfareType
	 *            福利类型 0 意外伤害(包含身故)、1 医疗保障 必传
	 * @return
	 * @throws HsException
	 */
	public boolean isHaveWelfareQualify(String hsResNO, Integer welfareType) throws HsException;

	/**
	 * 查询福利资格信息
	 * 
	 * @param hsResNO
	 *            互生号 必传
	 * @return
	 * @throws HsException
	 */
	public WelfareQualify findWelfareQualify(String hsResNO) throws HsException;

	/**
	 * 查询消费者历史福利资格
	 * 
	 * @param hsResNO
	 *            互生号 必传
	 * @return
	 * @throws HsException
	 */
	public List<WelfareQualify> queryHisWelfareQualify(String hsResNO) throws HsException;

	/**
	 * 分页查询消费者历史福利资格
	 * 
	 * @param hsResNO
	 *            互生号 必传
	 * @param page
	 *            分页参数 必传
	 * @return
	 * @throws HsException
	 */
	public PageData<WelfareQualify> queryHisWelfareQualify(String hsResNO, Page page)
			throws HsException;

}
