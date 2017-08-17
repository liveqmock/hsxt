/***************************************************************************
 *
 * This document contains confidential and proprietary information 
 * subject to non-disclosure agreements with GUIYI Technology, Ltd. 
 * This information shall not be distributed or copied without written 
 * permission from GUIYI technology, Ltd.
 *
 ***************************************************************************/

package com.gy.hsi.fs.client.tc;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.gy.hsi.fs.client.FS;
import com.gy.hsi.fs.common.beans.SecurityToken;
import com.gy.hsi.fs.common.exception.FsException;

/***************************************************************************
 * <PRE>
 *  Project Name    : fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.tc
 * 
 *  File Name       : DownloadTestCase.java
 * 
 *  Creation Date   : 2015年6月9日
 * 
 *  Author          : 用户id
 * 
 *  Purpose         : 申请安全令牌
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class DownloadTestCase {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void downloadAnonyFile() throws FsException {
		try {
			String fileId = "00KldVBv1CR1TD5BTxNM1T$";

			boolean success = FS.getClient().downloadPublicFile(fileId,
					"C:\\Users\\Administrator\\Desktop\\downloadhaha.jpg");

			System.out.println("下载文件成功：" + success);

			Assert.assertNotEquals(null, success);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void downloadFile() throws FsException {
		try {
			String fileId = "00014Ef0IB34C7839264e161Il"; // 实名上传文件
			// String fileId = "00KldVBv1CR1TCyBTWt01T"; // 匿名上传文件

			SecurityToken securityToken = new SecurityToken("用户id", "安全令牌", "WEB");

			boolean success = FS.getClient().downloadFile(fileId,
					"C:/downloadhaha2.jpg", securityToken);

			System.out.println("下载文件成功：" + success);

			Assert.assertNotEquals(null, success);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
