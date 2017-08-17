package com.gy.hsxt.access.web.common.utils;

import com.gy.hsxt.common.utils.StringUtils;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-access-web-person
 * 
 *  Package Name    : com.gy.hsxt.access.web.common.utils.ValidateUtil
 * 
 *  File Name       : BaseController <T>.java
 * 
 *  Creation Date   : 2015-8-14 上午11:16:45  
 *  
 *  updateUse
 * 
 *  Author          : liuxy
 * 
 *  Purpose         : 公用的验证类
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ValidateUtil {
	
    public static String FX = "欧阳,太史,端木,上官,司马,东方,独孤,南宫,万俟,闻人,夏侯,诸葛,尉迟,公羊,赫连,澹台,皇甫,宗政,濮阳,公冶,太叔,申屠,公孙,慕容,仲孙,钟离,长孙,宇文,城池,司徒,鲜于,司空,汝嫣,闾丘,子车,亓官,司寇,巫马,公西,颛孙,壤驷,公良,漆雕,乐正,宰父,谷梁,拓跋,夹谷,轩辕,令狐,段干,百里,呼延,东郭,南门,羊舌,微生,公户,公玉,公仪,梁丘,公仲,公上,公门,公山,公坚,左丘,公伯,西门,公祖,第五,公乘,贯丘,公皙,南荣,东里,东宫,仲长,子书,子桑,即墨,达奚,褚师";
	/***
	 * 验证银行卡号
	 * @return
	 */
	public static boolean validateBankCardId(String str){
		String exp = "^(\\d{16}|\\d{19})$";
		if(!StringUtils.isBlank(str)&&str.matches(exp)){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证办公室电话
	 * @param str
	 * @return
	 */
	public static boolean validateOfficePhone(String str){
		String exp = "^([\\d-+]*)$";
		if(!StringUtils.isBlank(str)&&str.matches(exp)){
			return true;
		}
		return false;
	}
	/**
	 * 验证传真号码
	 * @param str
	 * @return
	 */
	public static boolean validateFax(String str){
		String exp = "^([\\d-+]*)$";
		if(!StringUtils.isBlank(str)&&str.matches(exp)){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证手机号码
	 * @param str
	 * @return
	 */
	public static boolean validateMobile(String str){
		String exp = "^(((1[0-9]{1}[0-9]{1}))+\\d{8})$";
		if(!StringUtils.isBlank(str)&&str.matches(exp)){
			return true;
		}
		return false;
	}
	
	
	/**
	 * 验证邮箱地址
	 * @param str
	 * @return
	 */
	public static boolean validateMail(String str){
		String exp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
		if(!StringUtils.isBlank(str)&&str.matches(exp)){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证邮政编码
	 * @param str
	 * @return
	 */
	public static boolean validatePostcode(String str){
		String exp = "[1-9]\\d{5}(?!\\d)";
		if(!StringUtils.isBlank(str)&&str.matches(exp)){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证QQ
	 * @param str
	 * @return
	 */
	public static boolean validateQQ(String str){
		String exp = "^\\d{5,13}$";
		if(!StringUtils.isBlank(str)&&str.matches(exp)){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证网站址
	 * @param str
	 * @return
	 */
	public static boolean validateWebsite(String str) {
		String exp="^((https?|ftp|news):\\/\\/)?([a-z]([a-z0-9\\-]*[\\.。])+([a-z]{2}|aero|arpa|biz|com|coop|edu|gov|info|int|jobs|mil|museum|name|nato|net|org|pro|travel)|(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))(\\/[a-z0-9_\\-\\.~]+)*(\\/([a-z0-9_\\-\\.]*)(\\?[a-z0-9+_\\-\\.%=&]*)?)?(#[a-z][a-z0-9_]*)?$";
		if(!StringUtils.isBlank(str)&&str.matches(exp)){
			return true;
		}
		return false;
	}
	
	/**
	 * 验证是否为复姓
	 * @param name 姓名
	 * @return
	 */
	public static boolean validateFX(String name){
	    if(StringUtils.isNotBlank(name) && name.length() > 2){
	        String xin = name.substring(0, 2);
	        if(FX.indexOf(xin) >= 0){
	            return true;
	        }
	    }
	    return false;
	}
	
}
