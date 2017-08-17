/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.as.bean.common;

import com.alibaba.fastjson.JSONObject;

/**
 * POS比对版本的信息
 * 
 * @Package: com.gy.hsxt.uc.as.bean.common  
 * @ClassName: AsPosInfo 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-12-29 下午3:29:20 
 * @version V1.0
 */
public class AsPosInfo {
	/**
	 * 企业信息
	 */
	private AsPosEnt posEnt;
	/**
	 * 基础信息
	 */
	private AsPosBaseInfo baseInfo;
	/**
	 * 积分比例
	 */
	private AsPosPointRate pointRate;
	
	
	/**
	 * @return the 积分比例
	 */
	public AsPosPointRate getPointRate() {
		return pointRate;
	}

	/**
	 * @param 积分比例 the pointRate to set
	 */
	public void setPointRate(AsPosPointRate pointRate) {
		this.pointRate = pointRate;
	}

	/**
	 * @return the 基础信息
	 */
	public AsPosBaseInfo getBaseInfo() {
		return baseInfo;
	}
	
	/**
	 * @param 基础信息 the baseInfo to set
	 */
	public void setBaseInfo(AsPosBaseInfo baseInfo) {
		this.baseInfo = baseInfo;
	}
	
	/**
	 * @return POS企业
	 */
	public AsPosEnt getPosEnt() {
		return posEnt;
	}
	
	/**
	 * @param POS企业
	 */
	public void setPosEnt(AsPosEnt posEnt) {
		this.posEnt = posEnt;
	}
	
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
