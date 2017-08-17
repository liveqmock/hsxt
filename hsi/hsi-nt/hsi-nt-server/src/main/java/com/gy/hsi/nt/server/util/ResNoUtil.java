package com.gy.hsi.nt.server.util;

import com.gy.hsi.nt.server.util.enumtype.ResType;
/**
 * 
 * @className:ResNoUtil
 * @author:likui
 * @date:2015年8月5日
 * @desc:资源类型工具类
 * @company:gysit
 */
public class ResNoUtil {
	public static final String RES_TYPE_M = ResType.M.name();   //管理公司类型
	public static final String RES_TYPE_S = ResType.S.name();	//服务公司类型
	public static final String RES_TYPE_T = ResType.T.name();	//托管企业类型
	public static final String RES_TYPE_B = ResType.B.name();	//成员企业类型
	public static final String RES_TYPE_P = ResType.P.name();	//个人消费者类型
	public static final String RES_TYPE_A = ResType.A.name();	//互生平台类型
	public static final String RES_TYPE_F = ResType.F.name();	//结算公司类型
	public static final String RES_TYPE_O="O";					//其它类型
	
	
	private ResNoUtil(){
		super();
	}
	
	/**
	 * 判断资源号是否合法,11位数字
	 * @param resNo
	 * @return
	 */
	public static boolean isResNo(String resNo){
		if(resNo==null){
			return false;
		}
		if(resNo.matches("[0-9]{11}")){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据11位资源号得到资源类型
	 * @param resNo
	 * @return
	 */
	public static String type(String resNo){
		if(!isResNo(resNo)){
			throw new RuntimeException("资源号格式不对");
		}
		int subM = Integer.parseInt(resNo.substring(0,2));
		int subS = Integer.parseInt(resNo.substring(2,5));
		int subT = Integer.parseInt(resNo.substring(5,7));
		int subP = Integer.parseInt(resNo.substring(7,11));
		if("00000000000".equals(resNo)){
			return RES_TYPE_A;
		}
		if(subM>0){
			if(subS>0){
				if(subT>0){
					if(subP>0){
						return RES_TYPE_P;
					}else{
						return RES_TYPE_T;
					}
				}else{
					if(subP>0){
						return RES_TYPE_B;
					}else{
						return RES_TYPE_S;
					}
				}
			}else{
				if((subP+subT)==0){
					return RES_TYPE_M;
				}
			}
		}else{
			if(subS==0 && subT==0 && subP>0){
				return RES_TYPE_F;
			}
		}
		return RES_TYPE_O;
	}
}
