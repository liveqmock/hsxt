package com.gy.hsxt.service.testcase;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;

import org.junit.Test;

import com.gy.hsxt.common.constant.TransType;

public class BaseTestCase {
	
	@Test
	public void testBatchChargeAccount() {
//		Date date1 = new Date();//获取当前时间
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String str = sdf.format(date1);//时间存储为字符串
//		System.out.println(str);
//		Timestamp.valueOf(str);//转换时间字符串为Timestamp
//		System.out.println(Timestamp.valueOf("2015-09-17 19:42:39"));//输出结果
//	    File directory = new File("");//参数为空 
//	    try
//        {
//            String courseFile = directory.getCanonicalPath() ;
//            System.out.println(courseFile);
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        } 
	    
	    String transType = TransType.REMOTE_CARD_LOCAL_EARNESTT_SETTLE.getCode();
	    
	    System.out.println(transType.charAt(3));
	}
	
	
	
}
