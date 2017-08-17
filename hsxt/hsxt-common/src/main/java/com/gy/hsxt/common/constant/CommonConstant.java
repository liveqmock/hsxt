package com.gy.hsxt.common.constant;
		
/***************************************************************************
 * <PRE>
 * Description 		: Simple to Introduction
 *
 * Project Name   	: hsxt-common
 *
 * Package Name     : com.gy.hsxt.common.constant
 *
 * File Name        : CommonConstant
 * 
 * Author           : wucl
 *
 * Create Date      : 2015-9-24 上午10:06:38  
 *
 * Update User      : wucl
 *
 * Update Date      : 2015-9-24 上午10:06:38
 *
 * UpdateRemark 	: 说明本次修改内容
 *
 * Version          : v0.0.1
 *
 * </PRE>
 ***************************************************************************/
public enum CommonConstant{
	
	
	//--------渠道类型-----------------
//	/** pos **/
//	CHANNEL_POS(1),
//	/** 刷卡器 **/
//	CHANNEL_MCR(2),
//	/** 平板 **/
//	CHANNEL_PAD(3),
//	/** 互商 **/
//	CHANNEL_HS(4),
//	/** pos **/
//	CHANNEL_3RD(5),
	
	//--------设备类型-----------------
    /** 设备类型 WEB */
	EQUIPMENT_WEB(1),
	/** 设备类型 POS */
	EQUIPMENT_POS(2),
	/** 设备类型 MCR(连接刷卡器) */
	EQUIPMENT_MCR(3),
	/** 设备类型 手机 */
	EQUIPMENT_MOBILE(4),
	/** 设备类型 互生平板 */
	EQUIPMENT_HSPAD(5),
	/** 设备类型 平板 */
	EQUIPMENT_PAD(6),
	/** 设备类型 IVR(电话) */
	EQUIPMENT_IVR(7),
	/** 设备类型 其他设备 */
	EQUIPMENT_SHOP(8),

	//--------PS参数-----------------
	PS_CANCEL(1),
	PS_BACK(2),
	PS_POINT(3),
	//--------PS参数-----------------


	end(0);
	private int code;

	CommonConstant(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

	