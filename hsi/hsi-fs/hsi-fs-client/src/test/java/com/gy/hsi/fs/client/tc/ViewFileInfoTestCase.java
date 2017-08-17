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

import com.alibaba.fastjson.JSONObject;
import com.gy.hsi.fs.client.FS;
import com.gy.hsi.fs.common.beans.FileMetaInfo;
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
 *  Purpose         : 查看文件信息
 * 
 * 
 *  History         : none
 * 
 * </PRE>
 ***************************************************************************/
public class ViewFileInfoTestCase {

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
	public void viewPublicFileInfo() throws FsException {
		try {
			String fileId = "00KldVBv1CR1TD5BTxNM1T$";

			SecurityToken securityToken = new SecurityToken("haha", "安全令牌", "WEB");

			FileMetaInfo fileMetaInfo = FS.getClient().viewFileInfo(
					fileId, securityToken);

			System.out.println("查看文件信息："
					+ JSONObject.toJSONString(fileMetaInfo));

			Assert.assertNotEquals(null, fileMetaInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void viewFileInfo() throws FsException {
		try {
			String fileId = "00112f1FI6d3d3D83Bb4E8cf0eql$";

			SecurityToken securityToken = new SecurityToken("haha", "安全令牌", "WEB");

			FileMetaInfo fileMetaInfo = FS.getClient().viewFileInfo(
					fileId, securityToken);

			System.out.println("查看文件信息："
					+ JSONObject.toJSONString(fileMetaInfo));

			Assert.assertNotEquals(null, fileMetaInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
