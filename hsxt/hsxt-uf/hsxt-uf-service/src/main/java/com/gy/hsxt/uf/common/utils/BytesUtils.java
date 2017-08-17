/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsxt.uf.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.log4j.Logger;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-uf-service
 * 
 *  Package Name    : com.gy.hsxt.uf.common.utils
 * 
 *  File Name       : BytesUtils.java
 * 
 *  Creation Date   : 2015-5-27
 * 
 *  Author          : zhangysh
 * 
 *  Purpose         : 字节处理工具类
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public final class BytesUtils {
	/** 记录日志对象 **/
	private static final Logger logger = Logger.getLogger(BytesUtils.class);

	/**
	 * 私有构造函数
	 */
	private BytesUtils() {
	}

	/**
	 * 字节类型转换为对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object byte2Object(byte[] bytes) {
		Object obj = null;
		ByteArrayInputStream bi = null;
		ObjectInputStream oi = null;

		try {
			bi = new ByteArrayInputStream(bytes);
			oi = new ObjectInputStream(bi);

			obj = oi.readObject();
		} catch (IOException | ClassNotFoundException e) {
			logger.error("", e);
		} finally {
			try {
				if (null != bi) {
					bi.close();
				}

				if (null != oi) {
					oi.close();
				}
			} catch (IOException e) {
			}

			bi = null;
			oi = null;
		}

		return obj;
	}

	/**
	 * 对象转换为字节类型
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] object2Byte(Object obj) {
		byte[] bytes = null;
		ByteArrayOutputStream bo = null;
		ObjectOutputStream oo = null;

		try {
			bo = new ByteArrayOutputStream();
			oo = new ObjectOutputStream(bo);
			oo.writeObject(obj);

			bytes = bo.toByteArray();
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			try {
				if (null != bo) {
					bo.close();
				}

				if (null != oo) {
					oo.close();
				}
			} catch (IOException e) {
			}

			bo = null;
			oo = null;
		}

		return bytes;
	}

	/**
	 * 将Data类型数据对象序列化对象压缩,返回字节数组,压缩后的对象数组可写入文件保存或用于网络传输
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] object2ByteByzip(Object obj) {
		byte[] resultBytes = null;

		try {
			// 建立字节数组输出流
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

			// 建立gzip压缩输出流
			GZIPOutputStream gzOutput = new GZIPOutputStream(byteOutput);

			// 建立对象序列化输出流
			ObjectOutputStream objOutput = new ObjectOutputStream(gzOutput);

			objOutput.writeObject(obj);
			objOutput.flush();

			objOutput.close();
			gzOutput.close();

			// 返回压缩字节流
			resultBytes = byteOutput.toByteArray();
			byteOutput.close();
		} catch (IOException e) {
		}

		return resultBytes;
	}

	/**
	 * 将压缩字节数组还原为Data类型数据对象
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object byte2ObjectByzip(byte[] bytes) {
		Object resultObj = null;

		try {
			// 建立字节数组输入流
			ByteArrayInputStream byteInput = new ByteArrayInputStream(bytes);

			// 建立gzip解压输入流
			GZIPInputStream gzInput = new GZIPInputStream(byteInput);

			// 建立对象序列化输入流
			ObjectInputStream objInput = new ObjectInputStream(gzInput);

			// 按制定类型还原对象
			resultObj = objInput.readObject();

			byteInput.close();
			gzInput.close();
			objInput.close();
		} catch (IOException | ClassNotFoundException e) {
		}

		return resultObj;
	}
}
