package com.gy.hsxt.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.beans.BeanCopier;

/**
 * 使用BeanCopier 进行字段值拷贝
 *
 * @ClassName: BeanCopierUtils 
 * @Description: TODO
 */
public class BeanCopierUtils {  
	  
	/**
	 * 缓存要转换的对象
	 */
    static final Map<String, BeanCopier> BEAN_COPIERS = new HashMap<String, BeanCopier>();  
  
    /**
     * 拷贝源对象的同一属性的值到目标对象中
     * 注意：源对象和目标对象属性类型及属性名称需相同才视为相同字段，
     *       且源对象的字段的和目标对象对应字段的setter，getter方法都需存在
     * @param srcObj  源实体对象
     * @param destObj  目标实体对象
     */
    public static void copy(Object srcObj, Object destObj) {  
        String key = genKey(srcObj.getClass(), destObj.getClass());  
        BeanCopier copier = null;  
        if (!BEAN_COPIERS.containsKey(key)) {  
            copier = BeanCopier.create(srcObj.getClass(), destObj.getClass(), false);  
            BEAN_COPIERS.put(key, copier);  
        } else {  
            copier = BEAN_COPIERS.get(key);  
        }  
        copier.copy(srcObj, destObj, null);  
    }  
  
    private static String genKey(Class<?> srcClazz, Class<?> destClazz) {  
        return srcClazz.getName() + destClazz.getName();  
    }  
     
}  

