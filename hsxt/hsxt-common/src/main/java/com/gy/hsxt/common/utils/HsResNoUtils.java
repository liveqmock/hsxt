package com.gy.hsxt.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.gy.hsxt.common.constant.CustType;

/**
 * 互生号客户类型
 * 
 * @Package: com.gy.hsxt.common.utils
 * @ClassName: HsResNoUtils
 * @Description: TODO
 * @author: likui
 * @date: 2015年12月25日 上午10:34:20
 * @company: gyist
 * @version V3.0.0
 */
public class HsResNoUtils {

	// 地区平台
	private static final String AREA_PLAT = "^([0]{8})(([1-9]\\d\\d)|(\\d[1-9]\\d)|(\\d\\d[1-9]))$";

	// 管理
	private static final String MANAGE_CORP = "^(([1-9]\\d){1}|(\\d[1-9]){1})([0]{9})$";

	// 服务
	private static final String SERVICE_CORP = "^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|\\d[1-9]\\d)([0]{6})$";

	// 托管
	private static final String TRUSTEESHIP_ENT = "^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|\\d[1-9]\\d)(([1-9]\\d){1}|(\\d[1-9]){1})([0]{4})$";

	// 创业人员
	private static final String TRUSTEESHIP_ENT_E = "^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|\\d[1-9]\\d)([0][1-9]|[1][0])([0]{4})$";

	// 成员
	private static final String MEMBER_ENT = "^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|\\d[1-9]\\d)([0]{2})([1-9]\\d{3}|\\d{3}[1-9]|\\d{2}[1-9]\\d|\\d[1-9]\\d{2})$";

	// 消费者
	private static final String PERSON = "^(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{2}|\\d{2}[1-9]|\\d[1-9]\\d)(([1-9]\\d){1}|(\\d[1-9]){1})([1-9]\\d{3}|\\d{3}[1-9]|\\d{2}[1-9]\\d|\\d[1-9]\\d{2})$";

	// 互生号
	private static final String HS_RES_NO = "^[0-9]{11}$";

	/**
	 * 企业互生号转成服务互生号
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:39:04
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String cResNoToSresNo(String entResNo)
	{
		if (isHsResNo(entResNo))
		{
			return entResNo.substring(0, 5) + "000000";
		}
		return null;
	}

	/**
	 * 服务互生号转成管理互生号
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:39:24
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @return : String
	 * @version V3.0.0
	 */
	public static String sResNoToMresNo(String entResNo)
	{
		if (isHsResNo(entResNo))
		{
			return entResNo.substring(0, 2) + "000000000";
		}
		return null;
	}

	/**
	 * 判断互生号类型
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:39:35
	 * @param hsResNo
	 *            互生号
	 * @return
	 * @return : Integer
	 * @version V3.0.0
	 */
	public static Integer getCustTypeByHsResNo(String hsResNo)
	{
	    if(StringUtils.isBlank(hsResNo))
	    {
	        return CustType.NOT_HS_PERSON.getCode();
	    }
		if (isHsResNo(hsResNo))
		{
			if ("00000000000".equals(hsResNo))
			{// 总平台
				return CustType.CENTER_PLAT.getCode();
			}
			if (isAreaPlatResNo(hsResNo))
			{// 地区平台
				return CustType.AREA_PLAT.getCode();
			} else if (isManageResNo(hsResNo))
			{// 管理公司
				return CustType.MANAGE_CORP.getCode();
			} else if (isServiceResNo(hsResNo))
			{// 服务公司
				return CustType.SERVICE_CORP.getCode();
			} else if (isTrustResNo(hsResNo))
			{// 托管企业
				return CustType.TRUSTEESHIP_ENT.getCode();
			} else if (isMemberResNo(hsResNo))
			{// 成员企业
				return CustType.MEMBER_ENT.getCode();
			} else if (isPersonResNo(hsResNo))
			{// 消费者
				return CustType.PERSON.getCode();
			}
		}
		return null;
	}

	/**
	 * 是否是地区平台互生号
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:35:52
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isAreaPlatResNo(String entResNo)
	{
		return regex(entResNo, AREA_PLAT);
	}

	/**
	 * 检查互生号是否管理公司
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:36:28
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isManageResNo(String entResNo)
	{
		return regex(entResNo, MANAGE_CORP);
	}

	/**
	 * 检查互生号是否服务公司
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:36:52
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isServiceResNo(String entResNo)
	{
		return regex(entResNo, SERVICE_CORP);
	}

	/**
	 * 检查互生号是否服务公司
	 * 
	 * @param typeCode
	 * @return
	 */
	public static boolean isServiceResNo(int typeCode)
	{
		return CustType.SERVICE_CORP.getCode() == typeCode;
	}

	/**
	 * 检查互生号是否托管企业
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:37:42
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isTrustResNo(String entResNo)
	{
		return regex(entResNo, TRUSTEESHIP_ENT);
	}

	/**
	 * 检查互生号是否托管企业
	 * 
	 * @param typeCode
	 * @return
	 */
	public static boolean isTrustResNo(int typeCode)
	{
		return CustType.TRUSTEESHIP_ENT.getCode() == typeCode;
	}

	/**
	 * 检查企业互生号是否为创业者
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:38:09
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isPioneer(String entResNo)
	{
		return regex(entResNo, TRUSTEESHIP_ENT_E);
	}

	/**
	 * 检查互生号是否成员企业
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:38:26
	 * @param entResNo
	 *            企业互生号
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isMemberResNo(String entResNo)
	{
		return regex(entResNo, MEMBER_ENT);
	}

	/**
	 * 检查互生号是否成员企业
	 * 
	 * @param typeCode
	 * @return
	 */
	public static boolean isMemberResNo(int typeCode)
	{
		return CustType.MEMBER_ENT.getCode() == typeCode;
	}

	/**
	 * 检查消费者是否正确
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:38:46
	 * @param preResNo
	 *            消费者互生号
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isPersonResNo(String preResNo)
	{
		return regex(preResNo, PERSON);
	}

	/**
	 * 是否是互生号
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:40:27
	 * @param hsResNo
	 *            互生号
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	public static boolean isHsResNo(String hsResNo)
	{
		return regex(hsResNo, HS_RES_NO);
	}
	
	/**
     * 是否为创业资源互生号
     * 
     * @Description:
     * @author: likui
     * @created: 2015年12月25日 上午10:40:27
     * @param hsResNo
     *            互生号
     * @return
     * @return : boolean
     * @version V3.0.0
     */
    public static boolean isBusinessNo(String hsResNo)
    {
        return regex(hsResNo, TRUSTEESHIP_ENT_E);
    }

	/**
	 * java正则
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2015年12月25日 上午10:40:44
	 * @param str
	 *            值
	 * @param regex
	 *            正则表达式
	 * @return
	 * @return : boolean
	 * @version V3.0.0
	 */
	private static boolean regex(String str, String regex)
	{
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	public static void main(String[] args)
	{
		// 1 持卡人;2 成员企业;3 托管企业;4 服务公司;5 管理公司;6 地区平台;7 总平台;8 非持卡人; 9 非互生格式化企业
		String hsResNo = "";
		System.out.println(isServiceResNo(hsResNo));
	}
	
	
}
