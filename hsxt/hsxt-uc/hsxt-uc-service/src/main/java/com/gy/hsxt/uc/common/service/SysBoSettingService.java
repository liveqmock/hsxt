/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.uc.as.api.common.IUCAsSysBoSettingService;
import com.gy.hsxt.uc.as.bean.common.AsSysBoSetting;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.bean.SysBoSetting;
import com.gy.hsxt.uc.common.mapper.SysBoSettingMapper;

/**
 * @Package: com.gy.hsxt.uc.common.service
 * @ClassName: SysBoSettingService
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2016-1-13 下午7:47:33
 * @version V1.0
 */
@Service
public class SysBoSettingService implements IUCAsSysBoSettingService {
	@Autowired
	private SysBoSettingMapper sysBoSettingMapper;

	@Autowired
	CommonCacheService commonCacheService;

	/**
	 * 删除指定客户所有设置项
	 * 
	 * @param custId
	 *            客户号
	 * @return
	 */
	// int deleteByCustId(String custId){
	// return 0;
	// }

	/**
	 * 保存用户业务操作限制设置
	 * 删除旧数据,插入有效数据（settingValue="1"）
	 * @param custId
	 *            企业客户号or消费者客户号
	 * @param list
	 *            设置项列表
	 * @param operator
	 *            登陆用户id
	 * @return 生效设置条数
	 */
	public int setCustSettings(String custId, List<AsSysBoSetting> list,
			String operator) {
		// 删除旧数据
		sysBoSettingMapper.deleteByCustId(custId);

		if (list != null) {
			// ArrayList<SysBoSetting> settingList = new
			// ArrayList<SysBoSetting>();
			// 待保存至缓存的禁止项目设置
			HashMap<String, String> settingMap = new HashMap<String, String>();

			// 临时变量 属性名，属性值
			String settingName, settingValue;
			SysBoSetting bean; // 临时变量 数据库配置信息
			for (AsSysBoSetting vo : list) {
				settingName = vo.getSettingName();
				settingValue = vo.getSettingValue();
				if (StringUtils.isBlank(settingValue)
						|| "0".equals(settingValue)) {
					// 空数据及默认数据0不保存
				} else {// 保存有效设置数据
					bean = vo2bean(vo);
					bean.setCreatedby(operator);
					sysBoSettingMapper.insertSelective(bean);
					settingMap.put(settingName, settingValue);
				}
			}
			commonCacheService.setBoSetting(custId, settingMap);
			return settingMap.size();
		}
		return 0;
	}

	/**
	 * 查询指定客户所有设置项
	 * 
	 * @param custId
	 *            企业客户号or消费者客户号
	 * @return
	 */
	public List<AsSysBoSetting> getCustSettings(String custId) {
		// 从数据查询数据
		List<SysBoSetting> list = sysBoSettingMapper.selectByCustId(custId);

		// 把数据转化成vo并返回
		ArrayList<AsSysBoSetting> ret = new ArrayList<AsSysBoSetting>();
		if (list != null && !list.isEmpty()) {
			for (SysBoSetting bean : list) {
				ret.add(bean2vo(bean));
			}
		}
		return ret;
	}

	/**
	 * 查询指定客户所有设置项，并保存进缓存
	 * 
	 * @param custId
	 *            企业客户号or消费者客户号
	 * @return
	 */
	public List<AsSysBoSetting> loadCustSettings(String custId) {
		List<SysBoSetting> list = sysBoSettingMapper.selectByCustId(custId);
		ArrayList<AsSysBoSetting> ret = new ArrayList<AsSysBoSetting>();
		if (list != null && !list.isEmpty()) {
			HashMap<String, String> settingMap = new HashMap<String, String>();
			// 临时变量 属性名，属性值
			String settingName, settingValue;
			for (SysBoSetting bean : list) {
				settingName = bean.getSettingName();
				settingValue = bean.getSettingValue();
				if (StringUtils.isBlank(settingValue)
						|| "0".equals(settingValue)) {
					// 空数据及默认数据0不保存
				} else {// 保存有效设置数据

					settingMap.put(settingName, settingValue);
				}
				ret.add(bean2vo(bean));
			}
			commonCacheService.setBoSetting(custId, settingMap);
		}
		return ret;
	}

	/**
	 * 修改单挑设置项
	 * 
	 * @param vo
	 *            设置内容
	 * @param operator
	 *            登陆用户id
	 * @return 修改成功条数
	 */
	public int updateByPrimaryKeySelective(AsSysBoSetting vo, String operator) {
		SysBoSetting record = vo2bean(vo);
		record.setUpdatedby(operator);
		record.setUpdateDate(StringUtils.getNowTimestamp());
		int ret = sysBoSettingMapper.updateByPrimaryKeySelective(record);
		if (ret > 0) {// 加载缓存
			loadCustSettings(record.getCustId());
		}
		return ret;
	}

	/**
	 * 对象类型转换
	 * 
	 * @param vo
	 * @return SysBoSetting
	 */
	private SysBoSetting vo2bean(AsSysBoSetting vo) {
		SysBoSetting bean = new SysBoSetting();
		BeanUtils.copyProperties(vo, bean);
		return bean;
	}

	/**
	 * 对象类型转换
	 * 
	 * @param bean
	 * @return AsSysBoSetting
	 */
	private AsSysBoSetting bean2vo(SysBoSetting bean) {
		AsSysBoSetting vo = new AsSysBoSetting();
		BeanUtils.copyProperties(bean, vo);
		return vo;
	}
}
