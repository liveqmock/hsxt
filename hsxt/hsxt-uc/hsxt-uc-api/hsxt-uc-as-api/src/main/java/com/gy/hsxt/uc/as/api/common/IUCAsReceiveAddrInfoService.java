/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;
import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;

/**
 * 收货地址管理接口
 * 
 * @Package: com.gy.hsxt.uc.as.api.common  
 * @ClassName: IUCAsReceiveAddrInfoService 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-25 上午11:56:03 
 * @version V1.0
 */
public interface IUCAsReceiveAddrInfoService {
	/**
	 * 添加收货地址
	 * @param custId
	 *            客户号(持卡人、非持卡人、企业)
	 * @param addr 地址
	 * @throws HsException
	 */
	public void addReceiveAddr(String custId, 
			AsReceiveAddr addr) throws HsException;

	/**
	 * 删除收货地址
	 * 
	 * @param custId
	 *            客户号(持卡人、非持卡人、企业)
	 * @param addrNo 
	 *            收货地址编号
	 * @throws HsException
	 */
	public void deleteReceiveAddr(String custId,
			long addrNo) throws HsException;

	/**
	 * 修改收货地址
	 * @param custId
	 *            客户号(持卡人、非持卡人、企业)
	 * @param addr
	 *            收货地址
	 * @throws HsException
	 */
	public void updateReceiveAddr(String custId, 
			AsReceiveAddr addr) throws HsException;

	/**
	 * 根据客户号查询收货地址
	 * @param custId 客户号(持卡人、非持卡人、企业)
	 * @return
	 * @throws HsException
	 */
	public List<AsReceiveAddr> listReceiveAddrByCustId(String custId) throws HsException;
	
	/**
	 * 根据客户号和收货地址编号ID设置默认的收货地址信息
	 * @param custId
	 * @param addrId
	 * @throws HsException
	 */
	public void setDefaultReceiveAddr(String custId,Long addrId)throws HsException;
	
	/**
	 * 根据客户号和收货地址编号ID查询收货地址信息
	 * @param custId
	 * @param addrId
	 * @throws HsException
	 */
	public AsReceiveAddr searchReceiveAddrInfo(String custId,Long addrId)throws HsException;
	
	
	/**
	 * 根据客户号和收货地址编号ID查询收货地址信息
	 * @param custId
	 * @param addrId
	 * @throws HsException
	 */
	public AsReceiveAddr searchDefaultReceiveAddrInfo(String custId)throws HsException;
}
