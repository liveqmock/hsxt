/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
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
import com.gy.hsxt.access.web.gks.bean.UpdateDeviceInfoParam;
import com.gy.hsxt.bs.api.tool.IBSToolAfterService;
import com.gy.hsxt.bs.bean.base.BaseParam;
import com.gy.hsxt.bs.bean.tool.AfterServiceConfig;
import com.gy.hsxt.bs.bean.tool.resultbean.AfterDeviceDetail;
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
 * POS 维护配置接口实现
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.posInterface
 * @ClassName: MaintConfigService
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-11-24 上午9:28:21
 * @version V1.0
 */
@Service
public class MaintConfigServiceImpl extends BaseServiceImpl implements
		IMaintConfigService {

	@Autowired
	IUCBsDeviceService deviceService;
	@Autowired
	IUCBsEntService bsEntService;
	@Autowired
	LcsClient lcsClient;
	@Autowired
	IBSToolAfterService toolAfterService;
	@Autowired
	IUCAsRoleService roleService;

	/**
	 * 分页查询POS机维护配置单
	 * 
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.posInterface.IMaintConfigService#queryAfterSecretKeyConfigByPage(java.util.Map,
	 *      java.util.Map, com.gy.hsxt.common.bean.Page)
	 */
	@Override
	public PageData<SecretKeyOrderInfoPage> queryAfterSecretKeyConfigByPage(
			String resNo, String custName, String custId, Page page)
			throws HsException {
		// 调用接口com.gy.hsxt.bs.api.tool.IBSToolAfterService.queryAfterSecretKeyConfigByPage(BaseParam
		// bean, Page page)
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
					i++;
					if(!s.equals("1000")){
						System.out.println("操作员ID没有权限查询配置单" + custId);
					}
				}
				param.setRoleIds(roles);
			}
			param.setHsResNo(resNo);
			param.setHsCustName(custName);
			System.out.println("开始调用BS的查询配置单接口....");
			PageData<SecretKeyOrderInfoPage> skoifgList = toolAfterService
					.queryAfterSecretKeyConfigByPage(param, page);
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
	 * 分页查询POS机维护配置单详细清单
	 * 
	 * @param confNo
	 *            配置单号
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.posInterface.IMaintConfigService#queryAfterSecretKeyConfigDetail(java.lang.String)
	 */
	@Override
	public List<AfterDeviceDetail> queryAfterSecretKeyConfigDetail(String confNo)
			throws HsException {
		try {
			return toolAfterService.queryAfterSecretKeyConfigDetail(confNo);
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
	 * 验证设备
	 * 
	 * @param deviceSeqNo
	 *            设备序号
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.posInterface.IMaintConfigService#vaildSecretKeyDeviceAfter(java.lang.String)
	 */
	@Override
	public void vaildSecretKeyDeviceAfter(String deviceSeqNo)
			throws HsException {
		try {
			toolAfterService.vaildSecretKeyDeviceAfter(deviceSeqNo);
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
	 * @param entResNo
	 * @param deviceNo
	 * @param sNewVersion
	 * @param operCustId
	 * @return
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.posInterface.IMaintConfigService#reCreatePosPMK(java.lang.String,
	 *      java.lang.String, boolean, java.lang.String)
	 */
	@Override
	public GenPmkResult reCreatePosPMK(PmkSecretKeyParam param)
			throws HsException {
		try {
			System.out.println("开始验证设备");
			// 验证设备
			vaildSecretKeyDeviceAfter(param.getMachineNo());
			System.out.println("设备验证成功，开始查询企业信息");

			// 1、通过企业编号查询企业信息
			BsEntAllInfo entInfo = bsEntService.searchEntAllInfo(param
					.getEntCustId());
			System.out.println("查询企业信息成功。准备调用货币代码接口...");
			// 2、 根据货币代码查询货币信息接口
			List<CountryCurrency> currencies = lcsClient.queryCountryCurrency();
			System.out.println("货币代码接口调用成功, 货币代码数量为：" + currencies.size()
					+ "准备调用获取国家接口");
			// 3、获取本平台信息(包含平台所在地区信息)，
			// LocalInfo platformInfo = lcsClient.getLocalInfo();

			// 4、根据国家代码查询国家信息接口
			List<Country> countries = lcsClient.findContryAll();
			System.out.println("调用国家接口成功，国家数量：" + countries.size());
			// 5、POS创建秘钥
			SecretkeyResult secretkeyResult = deviceService.reCreatePosPMK(
					entInfo.getBaseInfo().getEntResNo(), param.getDeviceNo(),
					false, param.getOperCustId(), param.getNewDeviceSeqNo());
			System.out.println("POS创建秘钥成功，开始组装数据...");
			GenPmkResult genPmkResult = new GenPmkResult();
			genPmkResult.setPmk(secretkeyResult.getPmk());

			PosInfoResult posInfoResult = new PosInfoResult();

			posInfoResult.setEntNo(secretkeyResult.getEntCustId());
			posInfoResult.setPosNo(param.getDeviceNo());
			posInfoResult.setBaseInfoVersion("0001");
			posInfoResult.setCurrencyVersion("0001");
			posInfoResult.setCountryVersion("0001");
			posInfoResult.setPointInfoVersion("0001");
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
			posInfoResult.setCountryCount(currencies.size());
			posInfoResult.setEntResNo(entInfo.getBaseInfo().getEntResNo());
			posInfoResult.setCountryList(countries);
			// 积分转货币比率放大10000倍，假数据
			posInfoResult.setExchangeRate(100000);
			// 互生币转现比率放大10000倍，假数据
			posInfoResult.setHsbExchangeRate(100000);
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
	 * 配置POS机设备与企业关联关系（BS）
	 * 
	 * @param param
	 * @throws HsException
	 * @see com.gy.hsxt.access.web.aps.services.posInterface.IMaintConfigService#configSecretKeyDeviceAfter(com.gy.hsxt.bs.bean.tool.AfterServiceConfig)
	 */
	@Override
	public void configSecretKeyDeviceAfter(UpdateDeviceInfoParam param)
			throws HsException {
		try {
			AfterServiceConfig config = new AfterServiceConfig();
			config.setAfterOrderNo(param.getOrderNo());
			config.setCategoryCode(param.getCategoryCode());
			config.setConfNo(param.getConfigNo());
			config.setDeviceSeqNo(param.getDeviceSeqNo());
			config.setEntCustId(param.getEntCustId());
			config.setNewDeviceSeqNo(param.getNewDeviceSeqNo());
			config.setOperNo(param.getOperatorNo());
			config.setTerminalNo(param.getTerminalNo());
			System.out.println("开始更新UC的POS机状态");
			deviceService.updatePosStatus(param.getPmk(),
					param.getDeviceStatus(), param.getOperatorNo());

			System.out.println("UC的POS机状态，开始更新配置单的状态");
			toolAfterService.configSecretKeyDeviceAfter(config);
			System.out.println("配置单状态更新成功，");
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
