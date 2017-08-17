package com.gy.hsxt.gpf.bm.utils;

import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**   
 * 序列化对象类
 * @category          序列化对象类
 * @projectName   apply-incurement
 * @package           ObjectByteUtils.java
 * @className       ObjectByteUtils
 * @description     新增序列化对象类
 * @author              zhucy
 * @createDate       2014-7-3 下午1:55:15  
 * @updateUser      zhucy
 * @updateDate      2014-7-3 下午1:55:15
 * @updateRemark    新增
 * @version              v0.0.1
 */
public class ObjectByteUtils {
	
	/**  
     * 对象转数组  
     * @param obj  
     * @return  
     */  
    public static byte[] toByteArray (Object obj) throws Exception{      
        byte[] bytes = null;      
        ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
        ObjectOutputStream oos = null;
        try {        
            oos = new ObjectOutputStream(bos);         
            oos.writeObject(obj);        
            oos.flush();         
            bytes = bos.toByteArray();      
        }finally{
			IOUtils.closeQuietly(oos);
			IOUtils.closeQuietly(bos);
		}
        return bytes;    
    }   
       
    /**  
     * 数组转对象  
     * @param bytes  
     * @return  
     */  
    public static Object toObject (byte[] bytes) throws Exception{      
        Object obj = null;   
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {        
            bis = new ByteArrayInputStream (bytes);        
            ois = new ObjectInputStream (bis);        
            obj = ois.readObject();      
        }finally{
			IOUtils.closeQuietly(ois);
			IOUtils.closeQuietly(bis);
        }
        return obj;    
    }   
}

	