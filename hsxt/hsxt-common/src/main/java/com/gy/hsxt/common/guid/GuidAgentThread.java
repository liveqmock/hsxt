package com.gy.hsxt.common.guid;


/**
 * 多线程并发GUID生成测试类
 *
 * @ClassName: GuidAgentThread 
 * @Description: TODO
 * @date 2015-8-3 下午12:31:01 
 * @version V1.0
 */
public class GuidAgentThread extends Thread  {

	public String prefix;
	
	public GuidAgentThread(String prefix){
		super();
		this.prefix = prefix;
	}
		
	@Override
	public void run() {
		// TODO Auto-generated method stub
		int i=100;
		while(i > 0){
			System.out.println(GuidAgent.getStringGuid(prefix));
			--i;
		}
		try {
			sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
		}
	}
	
	
//	public static void main(String args[])
//	{
//		 GuidAgentThread gat1 = new GuidAgentThread("00");
//		 GuidAgentThread gat2 = new GuidAgentThread("11");
//	
//		 gat1.start();
//		 gat2.start();
//		
//	}

	
}
