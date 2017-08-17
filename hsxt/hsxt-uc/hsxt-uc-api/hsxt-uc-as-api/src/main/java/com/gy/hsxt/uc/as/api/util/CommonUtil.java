/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.util;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.device.IUCAsDeviceService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.bean.common.AsPosBaseInfo;
import com.gy.hsxt.uc.as.bean.common.AsPosEnt;
import com.gy.hsxt.uc.as.bean.common.AsPosInfo;
import com.gy.hsxt.uc.as.bean.common.AsPosPointRate;
import com.gy.hsxt.uc.as.bean.device.AsPos;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;

/**
 * 用于提供一些不需要调用用户中心的业务判断，如是否已登录
 * 
 * @Package: com.gy.hsxt.uc.as.api.util
 * @ClassName: CommonUtil
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-12-22 下午2:23:46
 * @version V1.0
 */
@Component
@Lazy(true)
public class CommonUtil {
	@Resource
	RedisUtil<Object> changeRedisUtil;
	@Resource
	RedisUtil<Object> fixRedisUtil;
    @Autowired(required=false) //当根据类型去找，找不到时，注入一个空
//	@Autowired(required=true) //当根据类型去找，找不到时，抛出异常信息。
	IUCAsDeviceService deviceService;

	/**
	 * 是否登录，包括持卡人、非持卡人和操作员的验证
	 * 
	 * @param custId
	 *            客户号
	 * @param loginToken
	 *            登录token
	 * @return 0 表示未登录、-1表示登录token为空，-2表示客户号为空，-3表示渠道类型不正确
	 */
	public int isLogin(String custId, String loginToken, String channelType) {
		if (loginToken == null || loginToken.equals("")) {
			return -1;
		}
		if (custId == null || custId.equals("")) {
			return -2;
		}
		ChannelTypeEnum type = null;
		try {
			type = ChannelTypeEnum.get(Integer.valueOf(channelType));
		} catch (Exception e) {
			return -3;
		}

		String loginKey = UcCacheKey.genLoginKey(type, custId);
		String token =null;
		try {
			token=changeRedisUtil.getString(loginKey);
		} catch (Exception e) {
			//如何 redisUtil 未注入属性stringRedisTemplate，会抛NullPoitException
			System.err.println("get from redis error,loginKey="+loginKey);
			e.printStackTrace();
		}
		if (loginToken.equals(token)) {
			return 1;
		}
		return 0;

	}

	/**
	 * 获取POS的版本及数据信息
	 * 
	 * @param entResNo
	 *            企业资源号
	 * @param deviceNo
	 *            POSId
	 * @return
	 */
	public AsPosInfo getPosInfoForVersion(String entResNo, String deviceNo) {
		AsPosBaseInfo baseInfo = getPosBaseInfo();
		AsPosEnt ent = getPosEnt(entResNo, deviceNo);
		AsPosPointRate rate = getPosRate(entResNo, deviceNo);
		AsPosInfo result = new AsPosInfo();
		result.setBaseInfo(baseInfo);
		result.setPointRate(rate);
		result.setPosEnt(ent);
		return result;
	}

	/**
	 * 获取POS积分版本信息
	 * 
	 * @param entResNo
	 * @param deviceNo
	 * @return
	 */
	private AsPosPointRate getPosRate(String entResNo, String deviceNo) {
		String key = UcCacheKey.genPosPointRateVersion(entResNo, deviceNo);
		AsPosPointRate posRate = (AsPosPointRate) fixRedisUtil.get(key,
				AsPosPointRate.class);
		if (posRate == null) {
			// 获取
			AsPos pos = deviceService.findPosByDeviceNo(entResNo, deviceNo);
			posRate = new AsPosPointRate();
			posRate.setRate(pos.getPointRate());
			posRate.setVersion(pos.getPointRateVer());
			System.out.println("从数据库中获取获取POS积分比例,企业互生号：" + entResNo + ", 机器编号：" + deviceNo  + ", 积分比例：" + JSON.toJSONString(posRate) );
			// 存入缓存
			fixRedisUtil.add(key, posRate);
		}
		else{
			System.out.println("从缓存中获取POS积分比例,企业互生号：" + entResNo + ", 机器编号：" + deviceNo + ", 积分比例：" + JSON.toJSONString(posRate) );
		}
		return posRate;
	}

	/**
	 * 获取POS基础信息
	 * 
	 * @return
	 */
	private AsPosBaseInfo getPosBaseInfo() {
		AsPosBaseInfo info = (AsPosBaseInfo) fixRedisUtil.get(
				UcCacheKey.genPosBaseInfoKey(), AsPosBaseInfo.class);
		if (info == null) {
			info = deviceService.getPosBaseInfo();
			
		}
		return info;
	}

	/**
	 * 获取POS企业信息
	 * 
	 * @param entResNo
	 * @return
	 * @throws HsException
	 */
	private AsPosEnt getPosEnt(String entResNo, String deviceNo)
			throws HsException {
		AsPosEnt cache = (AsPosEnt) fixRedisUtil.get(UcCacheKey.genPosEntKey(entResNo),
				AsPosEnt.class);
		if (cache == null) {
			cache = deviceService.getPosEnt(entResNo, deviceNo);
		}
		// 如果不为空，返回
		return cache;
	}

	/**
	 * 更新POS公用信息
	 * 
	 * @param entResNo
	 * @param baseInfo
	 */
	public void updatePosBaseInfo(AsPosBaseInfo baseInfo) {
		System.out.println("开始更新POS机公用信息，接收到的数据：" + baseInfo);

		// 获取POS企业信息
		AsPosBaseInfo c = getPosBaseInfo();
		// 成员企业
		if (baseInfo.getEntBChargeMaxValue() != null) {
			c.setEntBChargeMaxValue(baseInfo.getEntBChargeMaxValue());
		}
		if (baseInfo.getEntBChargeMinValue() != null) {
			c.setEntBChargeMinValue(baseInfo.getEntBChargeMinValue());
		}
		// 托管企业
		if (baseInfo.getEntTChargeMaxValue() != null) {
			c.setEntTChargeMaxValue(baseInfo.getEntTChargeMaxValue());
		}
		if (baseInfo.getEntTChargeMinValue() != null) {
			c.setEntTChargeMinValue(baseInfo.getEntTChargeMinValue());
		}
		// 个人
		if (baseInfo.getCarderChargeMaxValue() != null) {
			c.setCarderChargeMaxValue(baseInfo.getCarderChargeMaxValue());
		}
		if (baseInfo.getCarderChargeMinValue() != null) {
			c.setCarderChargeMinValue(baseInfo.getCarderChargeMinValue());
		}
		//
		if (baseInfo.getTelephone() != null) {
			c.setTelephone(baseInfo.getTelephone());
		}

		//
		if (baseInfo.getNetwork() != null) {
			c.setNetwork(baseInfo.getNetwork());
		}

		if (baseInfo.getHsbExchangeCurrencyRate() != null) {
			c.setHsbExchangeCurrencyRate(baseInfo.getHsbExchangeCurrencyRate());
		}

		if (baseInfo.getPointRateMaxValue() != null) {
			c.setPointRateMaxValue(baseInfo.getPointRateMaxValue());
		}

		if (baseInfo.getPointRateMinValue() != null) {
			c.setPointRateMinValue(baseInfo.getPointRateMaxValue());
		}
		c.setVersion(c.getVersion() + 1);
		fixRedisUtil.add(UcCacheKey.genPosBaseInfoKey(), c);
		System.out.println("POS 企业信息缓存更新成功");
	}

	/**
	 * 生成基础信息版本号
	 * 
	 * @param vs
	 * @return
	 */
	public String genVersion(String vs) {
		int version = Integer.valueOf(vs);
		version = version >= 99 ? 1 : version + 1;

		return String.format("%02d", version);
	}

	public static void main(String[] args) {
		CommonUtil u = new CommonUtil();
		String s = u.genVersion("02");
		//System.out.println(s);
	}

}
