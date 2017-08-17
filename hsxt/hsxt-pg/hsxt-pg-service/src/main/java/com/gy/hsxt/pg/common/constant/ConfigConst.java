/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.pg.common.constant;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-pg-service
 * 
 *  Package Name    : com.gy.hsxt.pg.common.constant
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

	/** 是否为生产环境[0:开发环境, 1:生产环境] **/
	public static final String PRODUCTION_ENV = "system.production.env";

	/** 本应用对外的访问地址(即：通过外网访问的地址) **/
	public static final String SYSTEM_EXTERNAL_ACCESS_ADDRESS = "system.external.access.address";

	/** 系统实例编号 **/
	public static final String SYSTEM_INSTANCE_NO = "system.instance.no";

	/** 系统id **/
	public static final String SYSTEM_ID = "system.id";
		
	/** 是否是生产环境标志, 用于bank adapter **/
	public static final String IS_PRODUCTION_ENV = "IS_PRODUCTION_ENV";

	/** dubbo 注册地址 **/
	public static final String DUBBO_REG_ADDRESS = "dubbo.registry.address";
	
	/** 用于对账文件相关常量 **/
	public static interface TcConfigConsts {

		/** 替换字串$YYYYMMDD **/
		public static final String $YYYYMMDD = "$YYYYMMDD";

		/** 替换字串$SN **/
		public static final String $SN = "$SN";

		/** 文件内容结尾 **/
		public static final String KEY_SYS_TC_BALANCE_DATA_END = "system.tc.balance.data.end";
		public static final String KEY_SYS_TC_BALANCE_COUNT = "system.tc.balance.rows.count";

		/** 中国银联订单号范围使用约定 **/
		// public static final String KEY_SYS_TC_CHINAPAY_ORDERNO_RANGE =
		// "system.tc.chinapay.orderid.range";

		public static final String KEY_SYS_TC_NFS_SHARE_DIR = "system.tc.nfs.share.dir";

		public static final String KEY_SYS_TC_CHK_CH_GP_PAY = "system.tc.chk.ch_gp_pay";
		public static final String KEY_SYS_TC_DET_CH_GP_PAY = "system.tc.det.ch_gp_pay";

		public static final String KEY_SYS_TC_TRIGGER_DS_JOBNAME = "system.tc.trigger.ds.job.name";
		public static final String KEY_SYS_TC_TRIGGER_DS_JOBGROUP = "system.tc.trigger.ds.job.group";		
	}
}
