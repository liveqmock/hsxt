/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.gp.common.constant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gp-service
 * 
 *  Package Name    : com.gy.hsxt.gp.common.constant
 * 
 *  File Name       : ConfigConst.java
 * 
 *  Creation Date   : 2015-9-25
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 属性文件中配置的key值常量
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ConfigConst {

	/** 系统实例编号 **/
	public static final String SYSTEM_INSTANCE_NO = "system.instance.no";

	/** 系统id **/
	public static final String SYSTEM_ID = "system.id";
	
	/** 支付系统所属虚拟分组(用于区分互生和互商,互生：HSXT_GP, 互商：HSEC_GP, ...) **/
	public static final String SYSTEM_GROUP_ID = "system.group.id";
	
	/** 交易过期时间，单位分钟 **/
	public static final String EXPIRED_DATE = "system.payment.expiredDate";

	/** dubbo 注册地址 **/
	public static final String DUBBO_REG_ADDRESS = "dubbo.registry.address";

	/** dubbo 超时时间5000毫秒 **/
	public static final String DUBBO_TIMEOUT5000 = "dubbo.service.timeout5000";
	
	/** 用于对账文件相关常量 **/
	public static interface TcConfigConsts {
		
		/** 替换字串$SN **/
		public static final String $SN = "$SN";
		
		/** 替换字串$YYYYMMDD **/
		public static final String $YYYYMMDD = "$YYYYMMDD";
		
		/** 文件内容结尾 **/
		public static final String KEY_SYS_TC_BALANCE_DATA_END = "system.tc.balance.data.end";
		public static final String KEY_SYS_TC_BALANCE_COUNT = "system.tc.balance.rows.count";
		
		/** 中国银联订单号范围使用约定 **/
		public static final String KEY_SYS_TC_CHINAPAY_ORDERNO_RANGE = "system.tc.chinapay.orderid.range";
		
		public static final String KEY_SYS_TC_NFS_SHARE_DIR = "system.tc.nfs.share.dir";
		
		public static final String KEY_SYS_TC_CHK_GP_AO_TRANS = "system.tc.chk.gp_ao_trans";
		public static final String KEY_SYS_TC_CHK_GP_AO_PAY = "system.tc.chk.gp_ao_pay";
		public static final String KEY_SYS_TC_CHK_GP_BS_PAY = "system.tc.chk.gp_bs_pay";
		public static final String KEY_SYS_TC_CHK_GP_PS_PAY = "system.tc.chk.gp_ps_pay";
		public static final String KEY_SYS_TC_CHK_GP_CH_PAY = "system.tc.chk.gp_ch_pay";
		
		public static final String KEY_SYS_TC_DET_GP_AO_TRANS = "system.tc.det.gp_ao_trans";
		public static final String KEY_SYS_TC_DET_GP_AO_PAY = "system.tc.det.gp_ao_pay";
		public static final String KEY_SYS_TC_DET_GP_BS_PAY = "system.tc.det.gp_bs_pay";
		public static final String KEY_SYS_TC_DET_GP_PS_PAY = "system.tc.det.gp_ps_pay";
		public static final String KEY_SYS_TC_DET_GP_CH_PAY = "system.tc.det.gp_ch_pay";
	}
}
