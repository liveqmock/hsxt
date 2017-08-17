package com.gy.test;


public class Rename {

//	public static void main(String[] args) {
//		File[] files = new File("C:/FS-TEMP-DATA").listFiles();
//
//		if (null == files || (0 >= files.length)) {
//			return;
//		}
//		
//		File dest;
//
//		for (File f : files) {
//			if (f.isFile()
//					&& (f.getName().startsWith("T") || f.getName().startsWith(
//							"L"))) {
//				try {
//					dest = new File("C:/PRO_PIC/" + f.getName() + ".jpg");
//					dest.createNewFile();
//
//					FileUtils.copyFile(f, dest);
//
//					FileUtils.forceDelete(f);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
		
//	}
	
	private static String toHexUtil(int n){
        String rt="";
        switch(n){
        case 10:rt+="A";break;
        case 11:rt+="B";break;
        case 12:rt+="C";break;
        case 13:rt+="D";break;
        case 14:rt+="E";break;
        case 15:rt+="F";break;
        default:
            rt+=n;
        }
        return rt;
    }
    
    public static String toHex(int n){
        StringBuilder sb=new StringBuilder();
        if(n/16==0){
            return toHexUtil(n);
        }else{
            String t=toHex(n/16);
            int nn=n%16;
            sb.append(t).append(toHexUtil(nn));
        }
        return sb.toString();
    }
    
    public static String parseAscii(String str){
        StringBuilder sb=new StringBuilder();
        byte[] bs=str.getBytes();
        for(int i=0;i<bs.length;i++)
            sb.append(toHex(bs[i]));
        return sb.toString();
    }

    public static void main(String args[]){
//        String s="00AHKdVBvCR1TZyBTxzo1T";
//        System.out.println("转换后的字符串是："+parseAscii(s));
    	
    	System.out.println((511)%256);
    	
//    	long dd = 99999999999999999L;
    }

}
