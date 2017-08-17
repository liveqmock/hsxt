package com.gy.hsxt.point.client.service;


import gnu.io.CommPortIdentifier;
import java.awt.Choice;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.gy.hsxt.point.client.entry.MainView;
import com.gy.hsxt.point.client.util.ComListen;
import com.gy.hsxt.point.client.util.ConstData;
import com.gy.hsxt.point.client.util.Wait;
import com.gy.hsxt.point.client.util.WaitForTime;

/**
 * Simple to Introduction
 * 
 * @category Simple to Introduction
 * @projectName WritePOS
 * @package com.gy.pos.ComToPos.java
 * @className ComToPos
 * @description 与POS通讯
 * @author fandi
 * @createDate 2014-8-7 下午4:17:45
 * @updateUser fandi
 * @updateDate 2014-8-7 下午4:17:45
 * @updateRemark 说明本次修改内容
 * @version V3.0ComToPos
 */
public class ComToPos implements WaitForTime, ComListen {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ComToPos.class);
	SerialCommunication com = new SerialCommunication(this);
	Wait wait;

	boolean IsWaitForPOS = false;

	byte[] PMK;
	MainView owner;

	/**
	 * 收到POS回传信息
	 * 
	 * @param Buffer
	 *            数据
	 * @param length
	 *            数据长度
	 */
	public void GetFromCom(byte[] Buffer, int length) {
		// synchronized (this) {
		DUMP(Buffer, length, false, "POS机");
		/*
		 * if (!IsWaitForPOS) { owner.POSCommunicationErr(); return; }
		 */

		byte[] con;
		if (owner.ConfigPara.ifNewVersion()) {
			con = checkPack(Buffer, length);
		} else {
			con = checkPack_OLD(Buffer, length);
		}
		if (con == null) {
			// 解包失败
			LOGGER.info("unpack POS Pack error");
			CloseCom();
			wait.interrupt();
			owner.POSCommunicationErr();
			return;
		}
		if (con[1] == 0x11)
		// 取机器号成功
		{
			// CloseCom();
			// wait.interrupt();
			con[0] = 0x20;
			con[1] = 0x20;
			con[2] = 0x20;
			con[3] = 0x20;

			// 获取pmk 并向pos写入同步数据
			if (owner.ConfigPara.ifNewVersion()) {
				// 新版本
				owner.GotMathineNO((new String(con)).trim());

			} else {
				// owner.GotMathineNO_OLD((new String(con)).trim());
			}
		} else if (con[1] == 0x12)
		// 写入企业信息秘钥成功
		{
			LOGGER.info("Write to POS com back!");
			wait.interrupt();
			owner.POSWriteOK();
			if (SendShutDown()) {
				wait = new Wait(this, owner.ConfigPara.getPosTimeOut());
			} else {
				CloseCom();
				LOGGER.info("error Send shut down to POS");
				owner.POSWriteCloseErr();
			}
		} else if (con[1] == 0x19)
		// 关闭成功
		{
			wait.interrupt();
			CloseCom();
			// 修改流程步驟，前面已經修改過了
			owner.ClosePOSOK();
			return;
		} else {
			LOGGER.info("error command in POS!!!!");
			wait.interrupt();
			owner.POSCommunicationErr();
			return;
		}
		// }
	}

	public ComToPos(MainView owner) {
		this.owner = owner;
	}

	/*
	 * 获得串口列表
	 */
	/**
	 * 获取本机串口列表
	 * 
	 * @param chc
	 *            可选列表
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void listPortChoices(Choice chc) {
		CommPortIdentifier portId;

		Enumeration en = CommPortIdentifier.getPortIdentifiers();
		List<String> listPort = new ArrayList<String>();

		// iterate through the ports.
		while (en.hasMoreElements()) {
			portId = (CommPortIdentifier) en.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				listPort.add(portId.getName());
			}
		}
	    chc.addItem(listPort.get(listPort.size() - 1));
}


	



	/*
	 * 进入写POS流程 调用者外部 成功返回0 打开端口错误返回1 发送数据错误返回2
	 */
	/**
	 * 获取POS机序列号
	 * 
	 * @return 0成功、1打开端口失败、2发送数据失败
	 */
	public int GetPOSNO() {
		// 打开端口
		try {
			com.openConnection(owner.ConfigPara.getComPort(), owner.ConfigPara
					.ifNewVersion() ? ConstData.BAUDRATE
					: ConstData.BAUDRATE_OLD, ConstData.DATABITS,
					ConstData.STOPBITS, ConstData.PARITY, ConstData.FLOWCONTROL);
			com.Send(Packup(ConstData.GET_MACHINE_NO_COMMAND, "POS机"));
		} catch (SerialCommunicationException e) {
			LOGGER.error("Open Comm error in Read POS Number");
			return 1;
		} catch (IOException e) {
			LOGGER.error("Open Comm error in Write POS");
			return 2;
		}
		/*
		 * wait = new Wait(this, owner.ConfigPara.getPosTimeOut()); IsWaitForPOS
		 * = true; wait.start();
		 */
		return 0;
	}

	/**
	 * 写POS数据
	 * 
	 * @return 0成功、1打开端口失败、2发送数据失败
	 */
	public int WritePos(byte[] PMK) {
		// 打开端口
		try {
			com.Send(Packup(PMK, "POS机"));
		} catch (IOException e) {
			LOGGER.error("Open Comm error in Write POS");
			return 2;
		}
		wait = new Wait(this, owner.ConfigPara.getPosTimeOut());
		IsWaitForPOS = true;
		wait.start();
		return 0;
	}

	//
	/**
	 * 发送关闭
	 * 
	 * @return成功、失败
	 */
	private boolean SendShutDown() {
		try {
			com.Send(Packup(ConstData.SHUT_DOWN_COMMAND, "POS机"));
		} catch (IOException e) {
			return false;
		}
		return true;
	}

	/**
	 * 协议打包 发送POS打包 加 STX，长度，ETX和LRC
	 */
	static public byte[] Packup(byte[] con, String where) {
		StringBuffer tempStr1 = new StringBuffer();
		for (int j = 0; j < con.length; j++) {
			tempStr1.append(con[j] + ",");
		}
		LOGGER.debug(con.length + "after:" + tempStr1);
		byte[] rc = new byte[con.length + 5];
		rc[0] = ConstData.STX;
		rc[con.length + 3] = ConstData.ETX;
		rc[1] = (byte) (con.length >> 8);
		rc[2] = (byte) (con.length & 0xff);
		int i;
		for (i = 0; i < con.length; i++) {
			rc[i + 3] = con[i];
		}
		byte lrc = 0;
		for (i = 0; i < con.length + 3; i++) {
			lrc ^= rc[i + 1];
		}
		rc[con.length + 4] = lrc;
		StringBuffer tempStr = new StringBuffer();
		for (int j = 0; j < rc.length; j++) {
			tempStr.append(rc[j] + ",");
		}
		LOGGER.debug(rc.length + "Write to POS:" + tempStr);
		DUMP(rc, rc.length, true, where);
		return rc;
	}

	public static void main(String args[]) {
		byte[] b = { -76, -27, -127, 86, 76, 11, 24, 69, 11, 40, 10, 4, 112,
				52, 105, 57 };
		byte[] bresult = Packup(b, "POS机");

		System.out.println(bresult);
	}

	/**
	 * 调试数据
	 * 
	 * @param buf
	 *            数据
	 * @param Len
	 *            数据长度
	 * 
	 *            public static void DUMP(byte[] buf, int Len, boolean iFsend) {
	 *            byte[] tmp = new byte[Len]; System.arraycopy(buf, 0, tmp, 0,
	 *            Len); DUMP(tmp, iFsend); }
	 */
	/**
	 * 调试数据
	 * 
	 * @param buf
	 *            byte[] 数据
	 * @param iFsend
	 *            boolean 是否为发送数据
	 * @param where
	 *            String 数据来源
	 */
	public static void DUMP(byte[] buf, int len, boolean iFsend, String where) {
		if (ConstData.IFDEBUG) {

			String aLine = "";
			for (int i = 0; i < len; i++) {
				if ((buf[i] & 0xFF) < 0x10)
					aLine += "0";
				aLine += Integer.toHexString(buf[i] & 0xFF) + " ";
				if (i > 0 && ((i % 16) == 15)) {
					aLine += "\r\n";
				}
			}

			if (iFsend) {
				System.out.println("发送数据到:" + where + ", data:" + aLine);
				LOGGER.debug("发送数据到:" + where + ", data:" + aLine);
			} else {
				LOGGER.debug("接收数据从:" + where + ", data:" + aLine);
			}
		}
	}

	/**
	 * 超时返回
	 */
	public void TimeOut() {
		synchronized (this) {
			if (!IsWaitForPOS)
				return;
			LOGGER.error("Communication to POS Time out");
			IsWaitForPOS = false;
			CloseCom();
			owner.POSTimeOut();
		}
	}

	/*
	 * 解包 头 CON 尾 (guiyiapp) Var （append）成功返回 CON 失败返回null
	 */
	/**
	 * 解包 头 CON 尾 (guiyiapp) Var （append）成功返回 CON 失败返回null
	 * 
	 * @param buffer
	 *            数据
	 * @param length
	 *            数据长度
	 * @return 解包数据
	 */
	private byte[] checkPack_OLD(byte[] buffer, int length) {
		int len = buffer.length;

		if (len < 20)
			return null;
		int i;
		for (i = 0; i < ConstData.IN_BUF_HEAD.length(); i++) {
			if (buffer[i] != (byte) (ConstData.IN_BUF_HEAD.charAt(i) & 0xff))
				return null;
		}
		for (i = 0; i < ConstData.IN_BUF_TAIL.length(); i++) {
			if (buffer[length - ConstData.IN_BUF_TAIL.length() + i] != (byte) (ConstData.IN_BUF_TAIL
					.charAt(i) & 0xff))
				return null;
		}
		byte[] rc = new byte[length - ConstData.IN_BUF_HEAD.length()
				- ConstData.IN_BUF_TAIL.length()];
		if (buffer[ConstData.IN_BUF_HEAD.length()] != (byte) 0x90
				|| buffer[ConstData.IN_BUF_HEAD.length() + 2] != 0x30
				|| buffer[ConstData.IN_BUF_HEAD.length() + 3] != 0x30) {
			return null;
		}
		for (i = 0; i < rc.length; i++) {
			rc[i] = buffer[ConstData.IN_BUF_HEAD.length() + i];
		}
		return rc;
	}

	private byte[] checkPack(byte[] buffer, int length) {
		// int len = buffer.length;

		if (length < 5)
			return null;
		int i;
		int len = length - 5;
		if ((buffer[0] != ConstData.STX)
				|| (buffer[length - 2] != ConstData.ETX)
				|| (buffer[1] != len >> 8)
				|| (buffer[2] != (byte) (len & 0xff))) {
			return null;
		}
		/*
		 * for (i = 0; i < ConstData.IN_BUF_HEAD.length(); i++) { if (buffer[i]
		 * != (byte) (ConstData.IN_BUF_HEAD.charAt(i) & 0xff)) return null; }
		 * for (i = 0; i < ConstData.IN_BUF_TAIL.length(); i++) { if
		 * (buffer[length - ConstData.IN_BUF_TAIL.length() + i] != (byte)
		 * (ConstData.IN_BUF_TAIL .charAt(i) & 0xff)) return null; }
		 */
		byte[] rc = new byte[len];
		if (buffer[3] != (byte) 0x90 || buffer[5] != 0x30 || buffer[6] != 0x30) {
			return null;
		}
		for (i = 0; i < rc.length; i++) {
			rc[i] = buffer[3 + i];
		}
		return rc;
	}

	/**
	 * 关闭串口 恢复主窗口
	 */
	public void CloseCom() {
		IsWaitForPOS = false;
		com.closeConnection();
	}

	/**
	 * int 到2位BCD转换
	 * 
	 * @param len
	 *            int
	 * @return 2位BCD
	 */
	public static byte[] GetBCD2(int len) {
		byte[] rc = new byte[2];
		rc[0] = (byte) ((((len / 1000) % 10) << 4) | ((len / 100) % 10));
		rc[1] = (byte) ((((len / 10) % 10) << 4) | (len % 10));
		return rc;
	}

	/**
	 * 将一个byte数组插入另一个数据
	 * 
	 * @param des
	 *            目标数组
	 * @param start
	 *            插入位置
	 * @param src
	 *            源数组
	 * @return 插入个数
	 */
	static public int InsertByte(byte[] des, int start, byte[] src) {
		System.arraycopy(src, 0, des, start, src.length);
		return src.length;
	}

	/**
	 * 将一个字符串插入另一个数据
	 * 
	 * @param des
	 *            目标数组
	 * @param start
	 *            插入位置
	 * @param src
	 *            源字符串
	 * @return插入个数
	 */
	static public int InsertByte(byte[] des, int start, String src) {
		byte[] srcByte = src.getBytes();
		System.arraycopy(srcByte, 0, des, start, srcByte.length);
		return srcByte.length;
	}
}
