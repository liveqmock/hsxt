package com.gy.hsi.nt.api.common;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 
 * @className:ValidateParamUtil
 * @author:likui
 * @date:2015年7月28日
 * @desc:验证参数工具类 (hibernate validator)
 * @company:gyist
 */
public class ValidateParamUtil {
	
	private static Validator validator;
	
	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory(); 
        validator = factory.getValidator(); 
	}
	
	private ValidateParamUtil(){
		super();
	}	
			
	/**
	 * 
	* @author:likui
	* @created:2015年7月28日上午9:36:09
	* @desc:验证参数
	* @param:@param obj
	* @param:@return
	* @param:String
	* @throws
	 */
	public static String validateParam(Object obj){
		StringBuffer sb = new StringBuffer();
		Set<ConstraintViolation<Object>> violations = validator.validate(obj);
		if(null == violations || violations.size() == 0){
			return "";
		}
		for(ConstraintViolation<Object> violation : violations) {
			//sb.append(violation.getPropertyPath()+":"+violation.getMessage()).append(";");  
			sb.append(violation.getMessage()).append(";");  
		}
		return sb.toString();
	}
}
