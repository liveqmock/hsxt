/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.common;

import java.util.HashSet;
import java.util.Set;

import com.gy.hsxt.common.constant.IRespCode;
import com.gy.hsxt.common.constant.RespCode;

/**
 * @Package: com.gy.hsxt.ps.common
 * @ClassName: PsRespCode
 * @Description: TODO
 * 
 * @author: chenhz
 * @date: 2016-1-25 下午4:57:49
 * @version V3.0
 */
public enum EsRespCode implements IRespCode
{
	/** 参数错误 **/
	PS_PARAM_ERROR(11001),
	/** 找不到原订单 **/
	PS_ORDER_NOT_FOUND(11002),
	/** 批结算账不平 **/
	PS_BATSETTLE_ERROR(11003),
	/** 交易类型错误 **/
	PS_TRANSTYPE_ERROR(11004),
	/** 积分小于最小值 **/
	PS_POINT_NO_MIN(11005),
	/** 找不到文件 **/
	PS_FILE_NOT_FOUND(11006),
	/** 编码格式 **/
	PS_CODE_FORMAT_ERROR(11007),
	/** 文件读写错误 **/
	PS_FILE_READ_WRITE_ERROR(11008),
	/** 数据库异常 **/
	PS_DB_SQL_ERROR(11009),
	/** 调用账务错误 **/
	PS_AC_ERROR(11010),
	/** 数据不存在 **/
	PS_NO_DATA_EXIST(11011),
	/** 数据己存在 **/
	PS_DATA_EXIST(11012),
	/*** 原订单已撤单，无法撤单 */
	PS_HAS_THE_CANCELLATION(11013),
	/** 已经被冲正，无法重复冲正 */
	PS_HAS_BEEN_REVERSED(11014),
	/** 线程异常 **/
	PS_THREAD_ERROR(11015),
	/** 写文件异常 **/
	PS_WRITE_FILE_ERROR(11016),
	/** 日终积分分配批处理任务异常 **/
	PS_POINT_ALLOC_BATCH_JOB_ERROR(11017),
	/** 日终互生币结算批处理任务异常 **/
	PS_HSB_BALANCE_BATCH_JOB_ERROR(11018),
	/** 日终积分结算批处理任务异常 **/
	PS_POINT_BALANCE_BATCH_JOB_ERROR(11019),
	/** 日终终批处理任务-日终批量退税 任务异常 **/
	PS_DAILY_BACK_TAX_JOB_ERROR(11020),
	/**PS跨平台交易异常：秘钥长度错误**/
	CHECK_CARDORPASS_FAIL(11021),
	/**请求报文格式有误**/
	REQUEST_PACK_FORMAT(11022),
	/** 调用账务非互生异常错误 **/
	PS_TO_AC_NOT_HSEXCEPTION_SRROR(11023),
	/** 调用用户中心系统读取客户号失败 **/        
	PS_UC_ERROR(11024),
	/*** 原订单已退货，无法退货 */
	PS_HAS_THE_BACKATION(11025),
	/*** 退货金额不能大于原订单金额,无法退货 */
	PS_BACK_AMOUNT_ERROR(11026),
	
	
	// 结束符
	END(00000);

	// 响应代码
	private int code;

	// 相应描述信息
	private String desc;

	EsRespCode(int code)
	{
		this.code = code;
	}

	EsRespCode(int code, String desc)
	{
		this.code = code;
		this.desc = desc;
	}

	public int getCode()
	{
		return code;
	}

	public String getDesc()
	{
		return this.desc;
	}

	/**
	 * 根据响应码反过来获取枚举对象
	 * 
	 * @param code
	 * @return
	 */
	public static RespCode valueOf(int code)
	{
		for (RespCode item : RespCode.values())
		{
			if (item.getCode() == code)
			{
				return item;
			}
		}
		return null;
	}

	public static void main(String[] args)

	{
		/**
		 * 检查代码是否有重复
		 */
		Set<Integer> codeSet = new HashSet<Integer>();
		for (RespCode item : RespCode.values())
		{
			if (codeSet.contains(item.getCode()))
			{
				System.err.println(item.name() + ":" + item.getCode() + " 代码有重复冲突，请修改！");
			} else
			{
				codeSet.add(item.getCode());
			}
		}
	}

}
