/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.es.queryReport.testcase;

/**
 * @Package: com.gy.hsxt.ps.queryReport.testcase
 * @ClassName: Test
 * @Description: TODO
 * 
 * @author: chenhongzhi
 * @date: 2015-12-10 上午11:12:14
 * @version V3.0
 */
public class Test11111
{
	   public static void main(String[] args) {
	        StackTraceElement ste1 = null;
	        
	        // get current thread and its related stack trace
	        StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
	        int steArrayLength = steArray.length;

	        String s = null;
	        
	        // output all related info of the existing stack traces
	        if(steArrayLength==0) {
	            System.err.println("No Stack Trace.");
	        } else {
	            for (int i=0; i<steArrayLength; i++) {
	                System.out.println("Stack Trace-" + i);
	                ste1 = steArray[i];
	                s = ste1.getFileName() + ": Line " + ste1.getLineNumber();
	                System.out.println(s);
	            }
	        }
	        // the following code segment will output the line number of the "new " clause
	        // that's to say the line number of "StackTraceElement ste2 = new Throwable().getStackTrace()[0];"
	        StackTraceElement ste2 = new Throwable().getStackTrace()[0];
	        System.out.println(ste2.getFileName() + ": Line " + ste2.getLineNumber());
	        // the following clause will output the line number in the external method "getLineInfo()"
	        System.out.println(getLineInfo());
	        // the following clause will output its current line number
	        System.out.println(getLineInfo(new Throwable().getStackTrace()[0]));
	    }
	    
	    /**
	     * return current java file name and code line number
	     * @return String
	     */
	    public static String getLineInfo() {
	        StackTraceElement ste3 = new Throwable().getStackTrace()[0];
	        return (ste3.getFileName() + ": Line " + ste3.getLineNumber());
	    }
	    
	    /**
	     * return current java file name and code line name
	     * @return String
	     */
	    public static String getLineInfo(StackTraceElement ste4) {
	        return (ste4.getFileName() + ": Line " + (ste4.getLineNumber()));
	    }
	    
	    public static String getMethod(StackTraceElement ste5){
	    	ste5 = (new Exception()).getStackTrace()[1];  
	    	return ste5.toString();
	    }
}
