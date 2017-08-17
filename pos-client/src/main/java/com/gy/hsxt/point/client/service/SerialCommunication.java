package com.gy.hsxt.point.client.service;

import gnu.io.CommPortIdentifier;
import gnu.io.CommPortOwnershipListener;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.TooManyListenersException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gy.hsxt.point.client.util.ComListen;
import com.gy.hsxt.point.client.util.ConstData;

/**
 * Simple to Introduction
 * 
 * @category 串口通讯底层类
 * @projectName WritePOS
 * @package com.gy.pos.SerialCommunication.java
 * @className SerialCommunication
 * @description 串口通讯
 * @author fandi
 * @createDate 2014-8-8 上午11:35:20
 * @updateUser fandi
 * @updateDate 2014-8-8 上午11:35:20
 * @updateRemark 说明本次修改内容
 * @version V3.0
 */
public class SerialCommunication implements SerialPortEventListener,
		CommPortOwnershipListener {
	// 日志
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SerialCommunication.class);
	// 串口通讯接口
	private ComListen Owner;
	// com通讯对象
	private CommPortIdentifier portId;
	// 序列化端口
	private SerialPort sPort;
	// 输出流
	private OutputStream os;
	// 输入流
	private InputStream is;
	// 是否打开
	private boolean IsOpen;
	// 缓冲区大小
	private final int IN_BUFFER_LEN = 4096;
	// 缓冲数组
	byte[] inBuffer = new byte[IN_BUFFER_LEN];

	// 构造方法
	public SerialCommunication(ComListen Owner) {
		this.Owner = Owner;
		IsOpen = false;
	}

	/**
	 * 打开端口
	 * 
	 * @param port
	 *            com端口
	 * @param baudRate
	 *            pos端口
	 * @param dataBits
	 *            数据位数
	 * @param stopBits
	 *            停止位数
	 * @param parity
	 * @param flowControl
	 *            通讯流
	 * @throws SerialCommunicationException
	 */
	public void openConnection(String port, int baudRate, int dataBits,
			int stopBits, int parity, int flowControl)
			throws SerialCommunicationException {
		try {
			// 根据端口实例通讯对象
			LOGGER.debug("com 端口!!!!!" + port);
			portId = CommPortIdentifier.getPortIdentifier(port);
		} catch (NoSuchPortException e) {
			throw new SerialCommunicationException(e.getMessage());
		}
		try {
			// 打开端口
			sPort = (SerialPort) portId.open("SerialDemo",
					ConstData.OPEN_TIME_OUT);
		} catch (PortInUseException e) {
			throw new SerialCommunicationException(e.getMessage());
		}
		// 设置参数
		try {
			setConnectionParameters(baudRate, dataBits, stopBits, parity,
					flowControl);
		} catch (SerialCommunicationException e) {
			sPort.close();
			throw e;
		}
		// 获取输入输出流
		try {
			os = sPort.getOutputStream();
			is = sPort.getInputStream();
		} catch (IOException e) {
			sPort.close();
			throw new SerialCommunicationException("Error opening i/o streams");
		}
		// 添加监听
		try {
			LOGGER.debug("addEventListener!!!!!");
			sPort.addEventListener(this);
		} catch (TooManyListenersException e) {
			sPort.close();
			throw new SerialCommunicationException("too many listeners added");
		}
		// Set notifyOnDataAvailable to true to allow event driven input.
		sPort.notifyOnDataAvailable(true);
		// Set notifyOnBreakInterrup to allow event driven break handling.
		sPort.notifyOnBreakInterrupt(true);
		// Set receive timeout to allow breaking out of polling loop during
		// input handling.
		try {
			sPort.enableReceiveTimeout(30);
		} catch (UnsupportedCommOperationException e) {
		}
		// Add ownership listener to allow ownership event handling.
		portId.addPortOwnershipListener(this);
		IsOpen = true;
	}

	/**
	 * 通讯参数配置
	 */
	private void setConnectionParameters(int baudRate, int dataBits,
			int stopBits, int parity, int flowControl)
			throws SerialCommunicationException {
		try {
			// 设置端口参数
			sPort.setSerialPortParams(baudRate, dataBits, stopBits, parity);
		} catch (UnsupportedCommOperationException e) {
			throw new SerialCommunicationException("Unsupported parameter");
		}
		try {
			// 设置流模型
			sPort.setFlowControlMode(flowControl);
		} catch (UnsupportedCommOperationException e) {
			throw new SerialCommunicationException("Unsupported flow control");
		}
	}

	/**
	 * 事件监听
	 * 
	 * @param e
	 * @see gnu.io.SerialPortEventListener#serialEvent(gnu.io.SerialPortEvent)
	 */
	public void serialEvent(SerialPortEvent e) {
		LOGGER.debug("serialEvent--被调用!!!!!");
		// 创建接收串口的缓冲区
		int Position = 0;
		int newData = 0;
		// 事件分析
		switch (e.getEventType()) {
		// 监听是否有数据
		case SerialPortEvent.DATA_AVAILABLE:
			while (newData != -1) {
				try {
					newData = is.read();
					if (newData == -1) {
						break;
					}
					if ('\r' == (char) newData) {
						inBuffer[Position++] = '\n';
					} else {
						inBuffer[Position++] = (byte) (newData & 0xff);
					}
				} catch (IOException ex) {
					return;
				}
			}
			Owner.GetFromCom(inBuffer, Position);
			break;
		case SerialPortEvent.BI:
		}

	}

	/**
	 * 
	 * @param type
	 * @see gnu.io.CommPortOwnershipListener#ownershipChange(int)
	 */
	public void ownershipChange(int type) {
		LOGGER.debug("ownershipChange!!!!!");
		LOGGER.debug("type = " + type);
	}

	public boolean isOpen() {
		return IsOpen;
	}

	/**
	 * 关闭端口，释放资源.
	 */
	public void closeConnection() {
		// 判断端口是否关闭
		if (!IsOpen) {
			return;
		}
		// 检查端口是否为空.
		if (sPort != null) {
			try {
				// 关闭流
				os.close();
				is.close();
			} catch (IOException e) {
				System.err.println(e);
			}
			// 关闭端口
			sPort.close();
			// 移除监听
			portId.removePortOwnershipListener(this);
		}

		IsOpen = false;
	}

	/**
	 * 发送数据到pos机
	 * 
	 * @param buf
	 * @throws IOException
	 */
	public void Send(byte[] buf) throws IOException {
		LOGGER.debug("IsOpen:" + IsOpen);
		LOGGER.debug("输出流os != null:" + (os != null));
		if (IsOpen && os != null) {
			int i = 0;
			int packlen = 64;
			while (i < buf.length) {
				int writeLen;
				if (buf.length - i > packlen)
					writeLen = packlen;
				else
					writeLen = buf.length - i;
				os.write(buf, i, writeLen);
				i += writeLen;
			}
		}
	}

	public static void main(String[] args) {
		SerialCommunication com = new SerialCommunication(null);
		try {
			com.openConnection("COM5", 38400, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE,
					SerialPort.FLOWCONTROL_NONE);
			// POS通讯协议
			byte[] getMachineNoCommand = { (byte) 0x90, 0x01 };

			byte[] rc = new byte[getMachineNoCommand.length + 5];
			rc[0] = ConstData.STX;
			rc[getMachineNoCommand.length + 3] = ConstData.ETX;
			rc[1] = (byte) (getMachineNoCommand.length >> 8);
			rc[2] = (byte) (getMachineNoCommand.length & 0xff);
			int i;
			for (i = 0; i < getMachineNoCommand.length; i++) {
				rc[i + 3] = getMachineNoCommand[i];
			}
			byte lrc = 0;
			for (i = 0; i < getMachineNoCommand.length + 3; i++) {
				lrc ^= rc[i + 1];
			}
			rc[getMachineNoCommand.length + 4] = lrc;
			com.Send(rc);
		} catch (SerialCommunicationException e) {
			LOGGER.error("Open Comm error in Read POS Number");
		} catch (IOException e) {
			LOGGER.error("Open Comm error in Write POS");

		}
	}
}
