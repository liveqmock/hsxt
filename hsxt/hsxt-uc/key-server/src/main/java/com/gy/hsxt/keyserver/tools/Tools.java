package com.gy.hsxt.keyserver.tools;


/***************************************************************************
 * <PRE>
 *  Project Name    : KeyServer
 * 
 *  Package Name    : com.gy.kms.keyserver.Socket
 * 
 *  File Name       : Tools.java
 * 
 *  Creation Date   : 2014-7-4
 * 
 *  Author          : fandi
 * 
 *  Purpose         : 类的描述
 *  
 *  History         : 常用工具
 * 
 * </PRE>
 ***************************************************************************/
public class Tools {
/**
 * 从byte数组中取出SHORT
 * @param msg 数组
 * @param index 位置 
 * @return 数字
 */
	public static int getShortFromString(byte []msg,int index){
		return ((int)(msg[index]) & 0xff<<8) |((int)(msg[index + 1]) & 0xff); 
	}
/**
 * 从byte数组中取出
 * @param data
 * @param outLen 数组长度，不足位补零
 * @return 数组
 */
	public static byte[] getByteFromByte(byte [] data, int start,int len) {
		byte rc[] = new byte[len];
		System.arraycopy(data, start, rc, 0,
				min(len, data.length - start));
		return rc;
	}
/**
 * 将byte数据转化为字符串
 * @param data 数组
 * @param start 起始位置
 * @param len   转化长度
 * @return   字符串
 */
	public static String GetFromByte(byte[] data, int start, int len) {
		int i;
		for (i = start; i < start + len; i++) {
			if (data[i] == 0)
				break;
		}
		byte rc[] = new byte[i - start];
		System.arraycopy(data, start, rc, 0, i - start);
		return new String(rc);
	}
/**
 * 将字符串转化为byte数组
 * @param inString
 * @param outLen 数组长度，不足位补零，超长截断
 * @return 数组
 */
	public static byte[] getByteFromString(String inString, int outLen) {
		byte rc[] = new byte[outLen];
		System.arraycopy(inString.getBytes(), 0, rc, 0,
				min(outLen, inString.length()));
		return rc;
	}

	private static int min(int a, int b) {
		return (a < b) ? a : b;
	}

	/**
	 * 将整型数放入数组（只取2位）
	 * 放入按照网络字节序排列
	 * @param taget 数组
	 * @param position 放入位置
	 * @param data 数
	 */
	public static void SetShort2Byte(byte[] taget, int position, int data) {
		taget[position] = (byte) (data >> 8);
		taget[position + 1] = (byte) (data & 0xff);
	}
	/**
	 * 将整型数放入数组
	 * 放入按照网络字节序排列
	 * @param taget 数组
	 * @param position 放入位置
	 * @param data 数
	 */
	public static void SetInt2Byte(byte[] taget, int position, int data) {
		taget[position] = (byte) (data >> 24);
		taget[position + 1] = (byte) ((data >> 16) & 0xff);
		taget[position + 2] = (byte) ((data >> 8) & 0xff);
		taget[position + 3] = (byte) (data & 0xff);
	}
	/**
	 * 将长整型数放入数组（只取4位）
	 * 放入按照网络字节序排列
	 * @param taget 数组
	 * @param position 放入位置
	 * @param data 数
	 */
	public static void SetInt2Byte(byte[] taget, int position, long data) {
		taget[position] = (byte) (data >> 24);
		taget[position + 1] = (byte) ((data >> 16) & 0xff);
		taget[position + 2] = (byte) ((data >> 8) & 0xff);
		taget[position + 3] = (byte) (data & 0xff);
	}
/**
 * 组包：对于空输入，组失败包；对于成功包设置包命令字和包长度
 * 对空输入，增加失败计数，对于成功包，增加成功计数
 * @param Command 命令字
 * @param input
 * @return

	public static byte[]Packup(short Command,byte[]input){
		byte[] rc = null;
		if (input != null) {
			input[0] = (byte) (Command >> 8);
			input[1] = (byte) (Command & 0xff);
			input[2] = (byte) ((input.length - 4) >> 8);
			input[3] = (byte) ((input.length - 4) & 0xff);
			if (Command < 0x30) {
				Monitor.INCDoneWork();
			}
			return input;
		} else {
			rc = new byte[4];
			rc[0] = (byte) (Command >> 8);
			rc[1] = (byte) (Command & 0xff);
			rc[2] = 0;
			rc[3] = 0;
			Monitor.INCErrorWork();
		}
		return rc;
	}
 */
	public static byte []StringToByte(String data){
		if (data.length() % 2 != 0)
			return null;
		byte []rc = new byte [data.length() / 2];
		for (int i = 0;i < rc.length;i++){
			byte hi = GetChar(data.charAt(2*i));
			byte lo = GetChar(data.charAt(2*i + 1));
			if (hi == -1 || lo == -1)
				return null;
			rc[i] = (byte)((hi << 4) | lo);
		}
		return rc;
	}
	 static byte GetChar(char data){
		if (data >= '0' && data <= '9')
			return (byte)(data - '0');
		else if (data >='a' && data <= 'f')
			return (byte)(data - 'a' + 10);
		else if (data >='A' && data <= 'F')
			return (byte)(data - 'A' + 10);
		return (byte)0xff;
	}
}
