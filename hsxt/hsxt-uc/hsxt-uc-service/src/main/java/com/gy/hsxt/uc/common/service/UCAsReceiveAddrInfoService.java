/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.RandomGuidAgent;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.Constants;
import com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.common.AsReceiveAddr;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.bean.ReceiveAddr;
import com.gy.hsxt.uc.common.mapper.ReceiveAddrMapper;
import com.gy.hsxt.uc.util.CustIdGenerator;

/**
 * 用户收货地址操作类
 * 
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: UCAsReceiveAddrInfoService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-11-24 下午3:37:04
 * @version V1.0
 */
@Service
public class UCAsReceiveAddrInfoService implements IUCAsReceiveAddrInfoService {

	@Autowired
	ReceiveAddrMapper receiveAddrMapper;

	/**
	 * 添加收货地址
	 * 
	 * @param custId
	 *            客户号
	 * @param addr
	 *            地址
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService#addReceiveAddr(java.lang.String,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum,
	 *      com.gy.hsxt.uc.as.bean.common.AsReceiveAddr)
	 */
	@Override
	public void addReceiveAddr(String custId, AsReceiveAddr addr)
			throws HsException {
		// 验证基本数据
		if (StringUtils.isEmpty(custId)) {
			throw new HsException(ErrorCodeEnum.USER_NAME_IS_EMPTY.getValue(),
					ErrorCodeEnum.USER_NAME_IS_EMPTY.getDesc());
		}
		if (StringUtils.isEmpty(addr.getAddress())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"收货地址为空");
		}

		if (StringUtils.isEmpty(addr.getMobile())
				&& StringUtils.isEmpty(addr.getTelphone())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"手机号和电话都为空");
		}
		if (StringUtils.isEmpty(addr.getReceiver())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"联系人为空");
		}
		// 判断是否是默认，如果是默认，将以前的收货地址设置为非默认
		if (null != addr.getIsDefault() && addr.getIsDefault().intValue() == 1) {
			try {
				receiveAddrMapper.setNoDefaultByCustId(custId);
			} catch (HsException e) {
				throw new HsException(
						ErrorCodeEnum.RECEIVE_ADDR_MODIFY_FAILED.getValue(),
						"设置默认收货地址失败");
			}
		}

		// 保存
		
		ReceiveAddr newAddr = new ReceiveAddr();
		BeanUtils.copyProperties(addr, newAddr);		
		newAddr.setCustId(custId);
		try {
			//主键id
//			String id = CustIdGenerator.genSeqId(Constants.GENERAL_GUID_PERFIX);
//			newAddr.setAddrId(Long.valueOf(id)); //页面 json不兼容15位以上长数字，暂时还是使用自增长
			receiveAddrMapper.insertSelective(newAddr);
		} catch (HsException e) {
			throw new HsException(
					ErrorCodeEnum.RECEIVEADDR_SAVE_ERROR.getValue(),
					ErrorCodeEnum.RECEIVEADDR_SAVE_ERROR.getDesc() + ","
							+ e.getMessage());
		}

	}

	/**
	 * 删除收货地址
	 * 
	 * @param custId
	 *            客户号
	 * @param addrNo
	 *            地址ID
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService#deleteReceiveAddr(java.lang.String,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum, long)
	 */
	@Override
	public void deleteReceiveAddr(String custId, long addrNo)
			throws HsException {
		checkParams(custId, addrNo);
		// 判断收货地址与客户号是否是同一个人
		ReceiveAddr addr = receiveAddrMapper.selectByPrimaryKey(addrNo);
		if (!addr.getCustId().equals(custId)) {
			throw new HsException(
					ErrorCodeEnum.RECEIVE_ADDR_NOT_MATCH.getValue(),
					ErrorCodeEnum.REALNAME_NOT_MATCH.getDesc());
		}
		// 删除
		try {
			receiveAddrMapper.deleteByPrimaryKey(addrNo);
		} catch (HsException e) {
			throw new HsException(
					ErrorCodeEnum.RECEIVEADDR_DELETE_ERROR.getValue(),
					"删除收货地址异常," + e.getMessage());
		}

	}

	/**
	 * 更新收货地址
	 * 
	 * @param custId
	 *            客户号
	 * @param addr
	 *            地址对象
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService#updateReceiveAddr(java.lang.String,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum,
	 *      com.gy.hsxt.uc.as.bean.common.AsReceiveAddr)
	 */
	@Override
	public void updateReceiveAddr(String custId, AsReceiveAddr addr)
			throws HsException {
		// 验证基本数据
		if (StringUtils.isEmpty(custId)) {
			throw new HsException(ErrorCodeEnum.USER_NAME_IS_EMPTY.getValue(),
					ErrorCodeEnum.USER_NAME_IS_EMPTY.getDesc());
		}
		if (StringUtils.isEmpty(addr.getAddress())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"收货地址为空");
		}

		if (StringUtils.isEmpty(addr.getMobile())
				&& StringUtils.isEmpty(addr.getTelphone())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"手机号和电话都为空");
		}
		if (StringUtils.isEmpty(addr.getReceiver())) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"联系人为空");
		}
		if (addr.getAddrId() == null) {
			throw new HsException(
					ErrorCodeEnum.RECEIVE_ADDR_ID_IS_NULL.getValue(),
					ErrorCodeEnum.RECEIVE_ADDR_ID_IS_NULL.getDesc());
		}

		// 判断是否是默认，如果是默认，将以前的收货地址设置为非默认
		if (addr.getIsDefault().intValue() == 1) {
			receiveAddrMapper.setNoDefaultByCustId(custId);
		}

		// 保存
		ReceiveAddr newAddr = new ReceiveAddr();
		BeanUtils.copyProperties(addr, newAddr);
		try {
			receiveAddrMapper.updateByPrimaryKeySelective(newAddr);
		} catch (HsException e) {
			throw new HsException(
					ErrorCodeEnum.RECEIVEADDR_UPDATE_ERROR.getValue(),
					e.getMessage());
		}

	}

	/**
	 * 根据客户号查询收货地址
	 * 
	 * @param custId
	 *            客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService#listReceiveAddrByCustId(java.lang.String,
	 *      com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum)
	 */
	@Override
	public List<AsReceiveAddr> listReceiveAddrByCustId(String custId)
			throws HsException {
		List<ReceiveAddr> list = receiveAddrMapper.selectByCustId(custId);
		List<AsReceiveAddr> result = new ArrayList<AsReceiveAddr>();
		for (ReceiveAddr ra : list) {
			AsReceiveAddr as = new AsReceiveAddr();
			BeanUtils.copyProperties(ra, as);
			result.add(as);
		}

		return result;
	}

	/**
	 * 根据客户号和收货地址编号设置默认收货地址信息
	 * 
	 * @param custId
	 * @param addrId
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService#setDefaultReceiveAddr(java.lang.String,
	 *      java.lang.Long)
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void setDefaultReceiveAddr(String custId, Long addrId)
			throws HsException {
		checkParams(custId, addrId);
		String cid = custId.trim();
		ReceiveAddr defaultReceiveAddr = new ReceiveAddr();
		defaultReceiveAddr.setCustId(cid);
		defaultReceiveAddr.setAddrId(addrId);
		defaultReceiveAddr.setIsDefault(1);
		try {
			receiveAddrMapper.setNoDefaultByCustId(cid);// 设置默认收货地址为普通收货地址
			receiveAddrMapper.updateByPrimaryKeySelective(defaultReceiveAddr);// 设置新的收货地址为默认的收货地址
		} catch (HsException e) {
			throw new HsException(
					ErrorCodeEnum.RECEIVEADDR_UPDATE_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 根据客户号和收货地址编号查询收货地址信息
	 * 
	 * @param custId
	 * @param addrId
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService#searchReceiveAddrInfo(java.lang.String,
	 *      java.lang.Long)
	 */
	@Override
	public AsReceiveAddr searchReceiveAddrInfo(String custId, Long addrId)
			throws HsException {
		checkParams(custId, addrId);
		ReceiveAddr receiveAddr = receiveAddrMapper.selectAddrInfo(custId,
				addrId);
		if(receiveAddr==null){
			return null;
		}
		AsReceiveAddr asReceiveAddr = new AsReceiveAddr();
		BeanCopierUtils.copy(receiveAddr, asReceiveAddr);
		return asReceiveAddr;
	}

	/**
	 * 根据客户号查询默认的收货地址
	 * 
	 * @param custId
	 *            客户号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.uc.as.api.common.IUCAsReceiveAddrInfoService#searchDefaultReceiveAddrInfo(java.lang.String)
	 */
	@Override
	public AsReceiveAddr searchDefaultReceiveAddrInfo(String custId)
			throws HsException {
		if (StringUtils.isEmpty(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号为空");
		}
		ReceiveAddr receiveAddr = receiveAddrMapper
				.selectDefaultAddrInfo(custId);
		if (null == receiveAddr) {
			return null;
		}
		AsReceiveAddr asReceiveAddr = new AsReceiveAddr();
		if (null != receiveAddr) {
			BeanCopierUtils.copy(receiveAddr, asReceiveAddr);
		}
		return asReceiveAddr;
	}

	private void checkParams(String custId, Long addrId) {
		if (StringUtils.isEmpty(custId)) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"输入参数客户号不能为空");
		}
		if (null == addrId) {
			throw new HsException(ErrorCodeEnum.PARAM_IS_REQUIRED.getValue(),
					"收货地址编号ID不能为空");
		}
	}
}
