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
import com.gy.hsi.fs.common.constant.FsApiConstant.FilePermission;
import com.gy.hsi.fs.common.exception.FsException;

/***************************************************************************
 * <PRE>
 *  Project Name    : fs-client
 * 
 *  Package Name    : com.gy.hsi.fs.client.tc
 * 
 *  File Name       : UploadTestCase.java
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
public class UploadTestCase {

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
	public void uploadFileWithAnony() throws FsException {
		try {
			SecurityToken securityToken = new SecurityToken("zhangysh", "test", "WEB");

			String fileId = FS.getClient().uploadPublicFile(
					"C:/testupload5.jpg", "", securityToken, true);

			System.out.println("上传文件成功,返回的文件id：" + fileId);

			Assert.assertNotEquals(null, fileId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void uploadPublicFile() throws FsException {
		try {
			SecurityToken securityToken = new SecurityToken("zhangysh",
					"dsfadfsdfsdfsfdsdfsdfs", "WEB");

			String fileId = FS.getClient().uploadPublicFile(
					"C:/testupload2.png", "", securityToken, false);

			System.out.println("上传文件成功,返回的文件id：" + fileId);

			Assert.assertNotEquals(null, fileId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void coverUploadPublicFile() throws FsException {
		try {
			String fileId = "00215446I9B5f2913e7dAFa25aBU$";

			SecurityToken securityToken = new SecurityToken("zhangys", "安全令牌", "WEB");

			boolean success = FS.getClient().overrideUploadFile(fileId,
					"C:/testupload4.png", "png", securityToken);

			System.out.println("覆盖上传文件成功：" + success);

			Assert.assertNotEquals(null, fileId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void uploadFileByRealName() throws FsException {
		try {
			SecurityToken securityToken = new SecurityToken("yangjg",
					"dsdsfdfssfdsdfsdf", "WEB");

			String fileId = FS.getClient().uploadFile(
					"C:/testupload7.jpg", ".jpg", FilePermission.PROTECTED_READ,
					securityToken);

			System.out.println("实名方式上传文件成功,返回的文件id：" + fileId);

			Assert.assertNotEquals(null, fileId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
