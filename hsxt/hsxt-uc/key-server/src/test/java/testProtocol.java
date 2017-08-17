import java.io.IOException;

import junit.framework.TestCase;
import junit.framework.*; 

import org.apache.commons.codec.binary.Hex;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gy.hsxt.keyserver.beans.ErrorCode;
import com.gy.hsxt.keyserver.beans.MacDecode;
import com.gy.hsxt.keyserver.beans.OutData;
import com.gy.hsxt.keyserver.service.IMonitor;
import com.gy.hsxt.keyserver.service.IService;
import com.gy.hsxt.keyserver.tools.FindFactoryBean;
import com.gy.kms.keyserver.CoDecode;


public class testProtocol extends TestCase{
	static IService demoService;
	static IMonitor Monitor;
	protected void setUp() {
		final ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		FindFactoryBean findFactoryBean = new FindFactoryBean();
		findFactoryBean.setApplicationContext(ctx);
		
		@SuppressWarnings("resource")
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "spring-keyserver.xml" });
		context.start();
		demoService = (IService) context
				.getBean("keyservice");

		Monitor = (IMonitor)context.getBean("keyservicemonitor");
	}
	public void testAdd(){
		GetPMK();
		CheckPMK();
		GetPIKMAK();
		GetMAC();
		Encode();
		Decode();
		GetMacDecode();		
	}
	
/*	public void testAdd(){
		CheckPMKError();
		GetPIKMAKError();
		GetMACError();
		EncodeError();
		DecodeError();
		GetMacDecodeError();		
		
	}
*/	private void GetPMK(){
    	OutData rc = demoService.GetPMK("12345678901", "1234", "0001");
    	Assert.assertEquals(rc.getStatus(),ErrorCode.OK);
    	Assert.assertNotNull(rc.getData());
	}
	
	private void CheckPMK(){
    	OutData rc = demoService.GetPMK("12345678901", "1234", "0001");
    	Assert.assertEquals(rc.getStatus(),ErrorCode.OK);
   		byte []operatorbyte = getByteFromString("0001",16);
   		byte check[] = new byte[16];
   		CoDecode.DES3encrypt((byte [])rc.getData(), operatorbyte, 16, check);
//   		System.out.println("check:" + Hex.encodeHexString(check));

   		OutData rc1 = demoService.CheckPMK("12345678901", "1234","0001" ,Hex.encodeHexString(check));
   		Assert.assertEquals (rc1.getStatus() , ErrorCode.OK);
	}
	private void GetPIKMAK(){
    	OutData rc = demoService.GetPIKMAK("12345678901", "1234", "0001");
    	Assert.assertEquals(rc.getStatus(),ErrorCode.OK);
    	Assert.assertNotNull(rc.getData());
	}
	private void GetMAC(){
		byte data[] = {1,2,3,4,5,6};
		OutData rc = demoService.GetMac("12345678901", "1234", data);
	   	Assert.assertEquals(rc.getStatus(),ErrorCode.OK);
		Assert.assertNotNull(rc.getData());
	}
	private void Encode(){
		byte data[] = {1,2,3,4,5,6};
		OutData rc = demoService.EnCode("12345678901", "1234", data);
	   	Assert.assertEquals(rc.getStatus(),ErrorCode.OK);
		Assert.assertNotNull(rc.getData());
	}
	private void Decode(){
		byte data[] = {0x75,(byte)0xd8,0x5d,(byte)0xe5,(byte)0x5d,(byte)0xa6};
		OutData rc = demoService.Decode("12345678901", "1234", data);
	   	Assert.assertEquals(rc.getStatus(),ErrorCode.OK);
		Assert.assertNotNull(rc.getData());
	}
	private void GetMacDecode(){
		byte data[] = {1,2,3,4,5,6};
		byte encodedata[] = {0x75,(byte)0xd8,0x5d,(byte)0xe5,(byte)0x5d,(byte)0xa6};
		OutData rc = demoService.GetMacDecode("12345678901", "1234", data,encodedata);
	   	Assert.assertEquals(rc.getStatus(),ErrorCode.OK);
		Assert.assertNotNull(rc.getData());
		MacDecode mac = (MacDecode) rc.getData();
		Assert.assertNotNull(mac.getMac());
		Assert.assertNotNull(mac.getData());	
	}
	private void CheckPMKError(){
    	OutData rc = demoService.GetPMK("12345678901", "1234", "0001");
	   	Assert.assertEquals(rc.getStatus(),ErrorCode.OK);
//    		System.out.println("return pmk:" + Hex.encodeHexString((byte[])rc.getData()));
    		byte []operatorbyte = getByteFromString("0001",16);
    		byte check[] = new byte[16];
    		CoDecode.DES3encrypt((byte [])rc.getData(), operatorbyte, 16, check);
//    		System.out.println("check:" + Hex.encodeHexString(check));
    		check[15] = 0;
    		OutData rc1 = demoService.CheckPMK("12345678901", "1234","0001" ,Hex.encodeHexString(check));
    	   	Assert.assertEquals(rc1.getStatus(),ErrorCode.OTHERERROR);  		
	}
	private void GetPIKMAKError(){
    	OutData rc = demoService.GetPIKMAK("12345678902", "1234", "0001");
	   	Assert.assertEquals(rc.getStatus(),ErrorCode.NODATAFOUND);  		
	}
	private void GetMACError(){
		byte data[] = {1,2,3,4,5,6};
		OutData rc = demoService.GetMac("12345678902", "1234", data);
	   	Assert.assertEquals(rc.getStatus(),ErrorCode.NODATAFOUND);  		
	}
	private void EncodeError(){
		byte data[] = {1,2,3,4,5,6};
		OutData rc = demoService.EnCode("12345678902", "1234", data);
	   	Assert.assertEquals(rc.getStatus(),ErrorCode.NODATAFOUND);  		
	}
	private void DecodeError(){
		byte data[] = {0x75,(byte)0xd8,0x5d,(byte)0xe5,(byte)0x5d,(byte)0xa6};
		OutData rc = demoService.Decode("12345678902", "1234", data);
	   	Assert.assertEquals(rc.getStatus(),ErrorCode.NODATAFOUND);  		
	}
	private void GetMacDecodeError(){
		byte data[] = {1,2,3,4,5,6};
		byte encodedata[] = {0x75,(byte)0xd8,0x5d,(byte)0xe5,(byte)0x5d,(byte)0xa6};
		OutData rc = demoService.GetMacDecode("12345678902", "1234", data,encodedata);
	   	Assert.assertEquals(rc.getStatus(),ErrorCode.NODATAFOUND);  		
	}
	private byte[] getByteFromString(String inString, int outLen) {
		byte rc[] = new byte[outLen];
		System.arraycopy(inString.getBytes(), 0, rc, 0,
				min(outLen, inString.length()));
		return rc;
	}
	private int min(int a, int b) {
		return (a < b) ? a : b;
	}

}
