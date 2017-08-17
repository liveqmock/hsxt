package com.gy.hsi.ds.client;

import org.apache.log4j.Logger;

/**
 * @author liaoqiqi
 * @version 2014-6-17
 */
public class Test {

	protected static final Logger LOGGER = Logger.getLogger(Test.class);

	/**
	 * @param args
	 *
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
//			ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
//					new String[] { "applicationContext-test.xml" });
		
//		Map<String, String> a = new HashMap<String,String>(2);
//		
//		
//		Map<String, String> b = new HashMap<String,String>(2);
//		
//		a.putAll(b);
		
		//List<String> ignoreKeyList = Arrays.asList("");
		
		String server = "http://zhang.www.com/zhang";
		System.out.println(server.replaceAll("\\/+$", ""));


		// System.exit(ret);
	}
}
