//package com.gy.hsxt.common.utils;
//
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import com.gy.hsec.common.constant.Constants;
//import com.gy.hsec.common.log.GyLogger;
//		
///**   
// * 11 位资源号转换类
// * @category          11 位资源号转换类
// * @className       ResourceUtils
// * @description      11 位资源号转换类
// * @author              zhucy
// * @createDate       2015-2-6 下午12:17:19  
// * @updateUser      zhucy
// * @updateDate      2015-2-6 下午12:17:19
// * @updateRemark     新增
// * @version              v0.0.1
// */
//public class ResourceUtils {
//
//	//管理公司号正则表达式
//	private static final String RESOURCE_TYPE_M = "\\d{1}[1-9]{1}000000000"; 
//	//服务公司号正则表达式
//	private static final String RESOURCE_TYPE_S = "\\d{1}[1-9]{1}\\d{3}000000"; 
//	//托管企业号正则表达式
//	private static final String RESOURCE_TYPE_T = "\\d{7}0000"; 
//	//成员企业号正则表达式
//	private static final String RESOURCE_TYPE_B = "\\d{5}00\\d{4}";
//	
//	private static final String RESOURCE_TYPE = "\\d{11}";
//	
//	/***
//	 * 企业管理号转换成功服务公司号
//	 * @param companyResNo
//	 */
//	public static String cResNoToSresNo(String companyResNo){
//		if(null != companyResNo && !"".equals(companyResNo.trim())){
//			if(Constants.RESOURCE_NO_LENGTH.intValue() != companyResNo.trim().length()){
//				logger.debug("资源号({0})位数不为11位！",companyResNo);
//				return null;
//			}
//			return companyResNo.substring(0,5) + "000000";
//		}
//		return null;
//	}
//	/**
//	 * 根据互生号判断用户是否 管理公司(M)|服务公司(S)|托管企业(T)|成员企业(B)
//	 * @param resourceNo
//	 * @return
//	 */
//	public static String getResourceTypeByResNo(String resourceNo){
//		if(isResNoTrue(resourceNo)){
//			if(isManageResNo(resourceNo)){//管理公司
//				return Constants.M_RESOURCE_TYPE;
//			}else if(isServiceResNo(resourceNo)){//服务公司
//				return Constants.S_RESOURCE_TYPE;
//			}else if(isTrustResNo(resourceNo)){//托管企业
//				return Constants.T_RESOURCE_TYPE;
//			}else if(isMemberResNo(resourceNo)){//成员企业
//				return Constants.B_RESOURCE_TYPE;
//			}
//		}
//		return null;
//	}
//	
//	/**
//	 * 检查互生号是否服务公司
//	 * @param resourceNo
//	 * @return true 是 | false 否
//	 */
//	public static boolean isServiceResNo(String resourceNo){
//		return regex(resourceNo,RESOURCE_TYPE_S);
//	}
//	
//	/**
//	 * 检查互生号是否托管企业
//	 * @param resourceNo
//	 * @return true 是 | false 否
//	 */
//	public static boolean isTrustResNo(String resourceNo){
//		return regex(resourceNo,RESOURCE_TYPE_T);
//	}
//	
//	/**
//	 * 检查互生号是否成员企业
//	 * @param resourceNo
//	 * @return true 是 | false 否
//	 */
//	public static boolean isMemberResNo(String resourceNo){
//		return regex(resourceNo,RESOURCE_TYPE_B);
//	}
//	
//	/**
//	 * 检查卡号是否正确
//	 * @param resourceNo
//	 * @return true 是 | false 否
//	 */
//	public static boolean isResNoTrue(String resourceNo){
//		return regex(resourceNo,RESOURCE_TYPE);
//	}
//	
//	/**
//	 * 检查互生号是否管理公司
//	 * @param resourceNo
//	 * @return true 是 | false 否
//	 */
//	public static boolean isManageResNo(String resourceNo){
//		return regex(resourceNo,RESOURCE_TYPE_M);
//	}
//	
//	/**
//	 * 检查企业互生号是否为创业者
//	 * @param resourceNo
//	 * @return
//	 */
//	public static boolean isPioneer(String resourceNo){
//		int num = Integer.valueOf(resourceNo.substring(5,7));
//		if(num >=1 && num <= 10){
//			return true;
//		}
//		return false;
//	}
//	
//	private static boolean regex(String str,String regex){
//		Pattern pattern = Pattern.compile(regex);
//		Matcher matcher = pattern.matcher(str);
//		return matcher.matches();
//	}
//	public static void main(String[] args) {
//		String res = "0611110010000";
//		System.out.println(ResourceUtils.isPioneer(res));
////		System.out.println(res.equals(null));
//	}
//}
//
//	