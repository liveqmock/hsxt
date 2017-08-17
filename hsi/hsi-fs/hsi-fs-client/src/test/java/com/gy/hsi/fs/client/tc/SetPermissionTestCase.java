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
 *  File Name       : SetPermissionTestCase.java
 * 
 *  Creation Date   : 2015年6月9日
 * 
 *  Author          : 用户id
 * 
 *  Purpose         : 设置权限
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class SetPermissionTestCase {

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
	public void setPermissionAnonyFile() throws FsException {
		try {
			String fileId = "00KldVBv1CR1TD5BTxNM1T$";

			SecurityToken securityToken = new SecurityToken("zhangysh", "secure token", "WEB");

			boolean success = FS.getClient().setFilePermission(fileId,
					FilePermission.PROTECTED_READ_WRITE, securityToken);

			System.out.println("设置文件权限：" + success);

			Assert.assertNotEquals(true, success);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void setPermission() throws FsException {
		try {
			String fileId = "00215446I9B5f2913e7dAFa25aBU$";

			SecurityToken securityToken = new SecurityToken("yangjg", "erwerereererwrewrewrew", "WEB");

			boolean success = FS.getClient().setFilePermission(fileId,
					FilePermission.PUBLIC_READ, securityToken);

			System.out.println("设置文件权限：" + success);

			Assert.assertNotEquals(true, success);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
