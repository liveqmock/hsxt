/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.gks.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.gks.bean.GenPmkResult;
import com.gy.hsxt.access.web.gks.bean.PmkSecretKeyParam;
import com.gy.hsxt.access.web.gks.bean.PosInfoResult;
import com.gy.hsxt.access.web.gks.bean.UpdateDeviceStatusParam;
import com.gy.hsxt.bs.api.tool.IBSToolAfterService;
import com.gy.hsxt.bs.api.tool.IBSToolMarkService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.resultbean.DeviceTerminalNo;
import com.gy.hsxt.bs.bean.tool.resultbean.SecretKeyOrderInfoPage;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.bean.CountryCurrency;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.bs.api.device.IUCBsDeviceService;
import com.gy.hsxt.uc.bs.api.ent.IUCBsEntService;
import com.gy.hsxt.uc.bs.bean.device.SecretkeyResult;
import com.gy.hsxt.uc.bs.bean.ent.BsEntAllInfo;

/**
 * POS 申购配置接口类服务类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.posInterface
 * @ClassName: PurchaseConfigService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-24 上午9:29:50
 * @version V1.0
 */
@Service
public class PurchaseConfigServiceImpl extends BaseServiceImpl implements
		IPurchaseConfigService {

	@Autowired
	IUCBsDeviceService deviceService;
	@Autowired
	LcsClient lcsClient;
	@Autowired
	IUCBsEntService bsEntService;
	@Autowired
	IBSToolAfterService bsToolAfterService;
	@Autowired
	IBSToolMarkService toolMarkService;
	@Autowired
	IUCAsRoleService roleService;

	/**
	 * 分页查询POS机申购配置单（BS）
	 * 
	 * @param resNo
	 *            资源号
	 * @param custName
	 *            客户名称
	 * @param custId
	 *            客户号
	 * @param page
	 *            页码
	 * @return
	 * @see com.gy.hsxt.access.web.aps.services.posInterface.IPurchaseConfigService#querySecretKeyConfigByPage(java.util.Map,
	 *      java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<SecretKeyOrderInfoPage> querySecretKeyConfigByPage(
			String resNo, String custName, String custId, Page page)
			throws HsException {
		try {
			BaseParam param = new BaseParam();
			System.out.println("开始调用角色权限...");

			Set<String> roleIds = roleService.getCustRoleIdSet(custId);
			System.out.println("调用角色权限成功");

			if (roleIds == null) {
				System.out.println("用户角色为空。 操作员ID：" + custId);
				param.setRoleIds(new String[] {});
			} else {
				String[] roles = new String[roleIds.size()];
				int i = 0;
				for(Object s : roleIds){
					roles[i] = String.valueOf(s);
					if(!s.equals("1000")){
						System.out.println("操作员ID没有权限查询配置单" + custId);
					}
					i++;
				}
				param.setRoleIds(roles);
			}
			param.setHsResNo(resNo);
			param.setHsCustName(custName);
			System.out.println("开始调用BS的查询配置单接口....");
			PageData<SecretKeyOrderInfoPage> skoifgList = toolMarkService
					.querySecretKeyConfigByPage(param, page);

			System.out.println("调用BS的查询配置单成功， 返回的数据为："
					+ JSONObject.toJSONString(skoifgList));
			return skoifgList;
		} catch (HsException hse) {
			hse.printStackTrace();
			throw hse;
		} catch (Exception e) {
			e.printStackTrace();
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 查询设备的终端编号列表
	 * 
	 * @param entCustId
	 *            客户号
	 * @param confNo
	 *            配置单号
	 * @param categoryCode
	 *            工具类别
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.posInterface.IPurchaseConfigService#queryConifgDeviceTerminalNo(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public DeviceTerminalNo queryConifgDeviceTerminalNo(String entCustId,
			String confNo, String categoryCode) throws HsException {
		try {
			return toolMarkService.queryConifgDeviceTerminalNo(entCustId,
					confNo, categoryCode);
		} catch (HsException hse) {
			hse.printStackTrace();
			throw hse;
		} catch (Exception e) {
			e.printStackTrace();
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 获取PMK秘钥（UC）
	 * 
	 * @param param
	 * @return
	 * @see com.gy.hsxt.access.web.aps.services.posInterface.IPurchaseConfigService#createPosPMK(com.gy.hsxt.access.web.PmkSecretKeyParam.posInterface.PMKSecretKeyBean)
	 */
	@Override
	public GenPmkResult createPosPMK(PmkSecretKeyParam param) {
		try {
			System.out.println("开始获取终端编号...");
			// 获取终端编号
			String posNo = toolMarkService.configToolPos(param.getEntCustId(),
					param.getConfNo(), param.getMachineNo());
			System.out.println("获取终端编号成功，编号：" + posNo + "，并开始准备查询企业信息");
			// 1、通过企业编号查询企业信息
			BsEntAllInfo entInfo = bsEntService.searchEntAllInfo(param
					.getEntCustId());
			System.out.println("查询企业信息成功。准备调用货币代码接口...");
			// 2、 根据货币代码查询货币信息接口(货币代码可在注释3中获取)
			List<CountryCurrency> currencies = lcsClient.queryCountryCurrency();
			System.out.println("货币代码接口调用成功, 货币代码数量为：" + currencies.size()
					+ "准备调用获取国家接口");
			// 3、获取本平台信息(包含平台所在地区信息)
			// LocalInfo platformInfo = lcsClient.getLocalInfo();
			// 4、根据国家代码查询国家信息接口(国家代码可在注释3中获取)
			List<Country> countries = lcsClient.findContryAll();
			System.out.println("调用国家接口成功，国家数量：" + countries.size());

			// 5、POS创建秘钥
			String deviceNo = posNo.substring(11);
			SecretkeyResult secretkeyResult = deviceService.createPosPMK(
					entInfo.getBaseInfo().getEntResNo(), deviceNo,
					param.getMachineNo(), false, param.getOperCustId());
			System.out.println("POS创建秘钥成功，开始组装数据...");
			GenPmkResult genPmkResult = new GenPmkResult();
			genPmkResult.setPmk(secretkeyResult.getPmk());

			PosInfoResult posInfoResult = new PosInfoResult();

			posInfoResult.setEntNo(secretkeyResult.getEntCustId());
			posInfoResult.setPosNo(posNo);
			posInfoResult.setBaseInfoVersion("0001");
			posInfoResult.setCurrencyVersion("0001");
			posInfoResult.setCountryVersion("0001");
			posInfoResult.setPointInfoVersion("0001");
			posInfoResult.setCurrency(currencies);
			posInfoResult.setEntName(entInfo.getBaseInfo().getEntName());
			// 设置企业类型
			switch (entInfo.getBaseInfo().getEntCustType().intValue()) {
			case 2:
				posInfoResult.setEntType("B");
				break;
			case 3:
				posInfoResult.setEntType("T");
				break;
			case 4:
				posInfoResult.setEntType("S");
				break;
			case 5:
				posInfoResult.setEntType("M");
				break;
			}
			posInfoResult.setCurrency(currencies);
			posInfoResult.setCountryCount(countries.size());

			posInfoResult.setCountryList(countries);
			// 积分转货币比率放大10000倍
			posInfoResult.setExchangeRate(10000);
			// 互生币转现比率放大10000倍
			posInfoResult.setHsbExchangeRate(10000);
			posInfoResult
					.setPointRateCount(secretkeyResult.getPointRate().length);
			posInfoResult.setPointRates(secretkeyResult.getPointRate());
			genPmkResult.setPosInfo(posInfoResult);
			System.out.println("数据组装成功，返回数据");
			return genPmkResult;

		} catch (HsException hse) {
			hse.printStackTrace();
			throw hse;
		} catch (Exception e) {
			e.printStackTrace();
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					e.getMessage());
		}
	}

	/**
	 * 修改POS机启用状态
	 * 
	 * @param entResNo
	 *            企业资源号
	 * @param deviceNo
	 *            设备编号
	 * @param status
	 *            状态
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.posInterface.IPurchaseConfigService#updateDeviceStatus(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public void updateDeviceStatus(UpdateDeviceStatusParam param)
			throws HsException {
		try {
			System.out.println("开始更新配置单的状态");
			// 调用接口
			toolMarkService.configToolDeviceIsUsed(param.getEntCustId(),
					param.getConfNo(), param.getTerminalNo(),
					param.getOperator());
			System.out.println("配置单状态更新成功，开始更新UC的POS机状态");
			deviceService.updatePosStatus(param.getPmk(),
					Integer.parseInt(param.getStatus()), param.getOperator());
			System.out.println("UC的POS机状态更新成功");
		} catch (HsException hse) {
			hse.printStackTrace();
			throw hse;
		} catch (Exception e) {
			e.printStackTrace();
			throw new HsException(ErrorCodeEnum.UNKNOW_ERROR.getValue(),
					e.getMessage());
		}
	}

}
