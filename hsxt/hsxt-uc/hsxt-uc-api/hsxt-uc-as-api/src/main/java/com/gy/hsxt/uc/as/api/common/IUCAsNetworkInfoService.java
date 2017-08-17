/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsCustPrivacy;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfo;
import com.gy.hsxt.uc.as.bean.common.AsNetworkInfoMini;

/**
 * 网络信息
 * 
 * @Package: com.gy.hsxt.uc.as.api.common
 * @ClassName: IUCAsNetworkInfoService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午2:51:02
 * @version V1.0
 */
public interface IUCAsNetworkInfoService {
	/**
	 * 查询网络信息详情(提供自己查询)
	 * 
	 * @param custId
	 *            客户号(包含持卡人、非持卡人、企业)
	 * @return
	 * @throws HsException
	 */
	public AsNetworkInfo searchByCustId(String custId)
			throws HsException;

	/**
	 * 查询网络信息
	 * @param custIdList 客户号列表
	 * @return 
	 * @throws HsException
	 */
	public List<AsNetworkInfoMini> listNetWorkInfo(List<String> custIdList) throws HsException;

	/**
	 * 修改网络信息
	 * @param netWorkInfo 网络信息
	 * @throws HsException
	 */
	public void updateNetworkInfo(AsNetworkInfo netWorkInfo) throws HsException;
	
	/**
	 * 获取个人隐私设置
	 * @param custId 客户号
	 * @return
	 * @throws HsException
	 */
	public AsCustPrivacy searchPrivacy(String custId) throws HsException ;
	/**
	 * 更新个人隐私设置
	 * @param asInfo 隐私设置信息
	 * @throws HsException
	 */
	public void updatePrivacy(AsCustPrivacy asInfo) throws HsException;
}
