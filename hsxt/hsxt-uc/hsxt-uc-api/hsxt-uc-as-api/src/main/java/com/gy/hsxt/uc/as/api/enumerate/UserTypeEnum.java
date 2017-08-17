package com.gy.hsxt.uc.as.api.enumerate;
		
/**   
 * 用户类型的枚举类，包括非持卡人，持卡人，企业用户等
 * @category          Simple to Introduction
 * @projectName   hsxt-uc-bs-api
 * @package           com.gy.hsxt.uc.UserTypeEnum.java
 * @className       UserTypeEnum
 * @description      一句话描述该类的功能 
 * @author              lixuan
 * @createDate       2015-10-14 上午10:19:48  
 * @updateUser      lixuan
 * @updateDate      2015-10-14 上午10:19:48
 * @updateRemark 说明本次修改内容
 * @version              v0.0.1
 */
public enum UserTypeEnum {
	NO_CARDER("1","nc"),   //非持卡人
	CARDER("2","c"),       //持卡人
	OPERATOR("3","o"),     //操作员
	ENT("4","e"),//企业
	SYSTEM("5","s"),      //系统管理员
	CHECKER("6","dc"),    //系统管理员2
	OPERATOR_USE_CARDER("7","o"), // 操作员使用消费者卡登录
	;
	String type;           
	String prefix;
	
	UserTypeEnum(String type,String prefix){
		this.type = type;
		this.prefix = prefix;
	}
	
	public String getType(){
		return type;
	}
	
	public String getPrefix(){
		return prefix;
	}
	
	/**
	 * 通过type获取prefix
	 * @param type 
	 * @return
	 */
	public String getType(String type){
		UserTypeEnum[] userTypeEnums = UserTypeEnum.values();
		for(UserTypeEnum UserTypeEnum : userTypeEnums){
			if(UserTypeEnum.getType().equals(type)){
				return UserTypeEnum.getPrefix();
			}
		}
		return null;
	}
	
	/**
	 * 通过prefix获取用户类型对象
	 * @param prefix
	 * @return
	 */
	public static UserTypeEnum get(String prefix){
		if(prefix.trim().equals("c")){
			return UserTypeEnum.CARDER;
		}
		if(prefix.trim().equals("nc")){
			return UserTypeEnum.NO_CARDER;
		}
		
		if(prefix.trim().equals("e")){
			return UserTypeEnum.OPERATOR;
		}
		if(prefix.trim().equals("s")){
			return UserTypeEnum.ENT;
		}
		
		return null;
	}
	/**
	 * 通过type获取用户类型对象
	 * @param type
	 * @return
	 */
	   public static UserTypeEnum getUserTypeEnum(String type){
	        if(UserTypeEnum.NO_CARDER.getType().equals(type)){
	            return UserTypeEnum.NO_CARDER;
	        }
	        if(UserTypeEnum.CARDER.getType().equals(type)){
	            return UserTypeEnum.CARDER;
	        }
	        
	        if(UserTypeEnum.OPERATOR.getType().equals(type)){
	            return UserTypeEnum.OPERATOR;
	        }
	        if(UserTypeEnum.ENT.getType().equals(type)){
	            return UserTypeEnum.ENT;
	        }
	        if(UserTypeEnum.SYSTEM.getType().equals(type)){
	            return UserTypeEnum.SYSTEM;
	        }
	        return null;
	    }
}

	