package com.gy.kms.keyserver;

import java.io.File;

//import com.gy.common.log.GyLogger;
/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer
 * 
 *  Package Name    : com.gy.kms.keyserver
 * 
 *  File Name       : CoDecode.java
 * 
 *  Creation Date   : 2014-7-4
 * 
 *  Author          : fandi
 * 
 *  Purpose         : 加解密函数
 * 
 * </PRE>
 ***************************************************************************/
public class CoDecode {
//	private static final GyLogger LOGGER = GyLogger.getLogger(CoDecode.class);
    public native static void DESencrypt(byte key[],byte input[],int in_len, byte []output, int ed_flag);
    public native static int DES3encrypt(byte key[],byte input[],int in_len, byte []output);
    public native static int DES3decrypt(byte key[],byte input[],int in_len, byte []output);
    public native static void DES3Mac(byte key[],byte data[],int in_len, byte []mac);
    public final static int ENCRYPT = 0;
    public final static int DESCRYPT = 1;
	final static byte PMK_ENKEY[] = { 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37,
			0x38, 0x39, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66 };
    static {
        String path = null;
        File f = new File("libext"); 
        try {
            path = f.getAbsolutePath();
	        System.setProperty("java.library.path", path);
//	        LOGGER.info("set library path:"+ System.getProperty("java.library.path"));
	        java.lang.reflect.Field fieldSysPath;
			fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
	        fieldSysPath.setAccessible(true);
	        fieldSysPath.set(null, null);        
        } catch (Exception e) {
        	e.printStackTrace();
        }
        System.loadLibrary("DesForJava");
    }
    public static byte []encryptPMK(byte pmk[]){
    	byte rc[] = new byte[16];
    	DES3encrypt(PMK_ENKEY,pmk,16,rc);
    	return rc;
    }
    public static byte []decryptPMK(byte pmk[]){
    	byte rc[] = new byte[16];
    	DES3decrypt(PMK_ENKEY,pmk,16,rc);
    	return rc;
    }
}
