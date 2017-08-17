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
 *  File Name       : DeleteFileTestCase.java
 * 
 *  Creation Date   : 2015年6月9日
 * 
 *  Author          : 用户id
 * 
 *  Purpose         : 删除文件
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class DeleteFileTestCase {

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
	public void deleteAnonyFile() throws FsException {
		try {
			String fileId = "00214881I2b23DFe90C4Ff13c1jB$";

			SecurityToken securityToken = new SecurityToken("zhangysh", "安全令牌", "WEB");

			boolean success = FS.getClient().deleteFile(fileId,
					securityToken);

			System.out.println("删除文件：" + success);

			Assert.assertNotEquals(true, success);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteFile() throws FsException {
		try {
			String fileId = "004171dBIBc0d89344A97c98zb";

			SecurityToken securityToken = new SecurityToken("用户id", "安全令牌", "WEB");

			boolean success = FS.getClient().deleteFile(fileId,
					securityToken);

			System.out.println("删除文件：" + success);

			Assert.assertNotEquals(true, success);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
