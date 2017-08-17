/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.service.testcase.bizfile;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bs.api.bizfile.IBSBizFileService;
import com.gy.hsxt.bs.bean.bizfile.BizDoc;
import com.gy.hsxt.bs.bean.bizfile.ImageDoc;
import com.gy.hsxt.bs.bean.bizfile.NormalDoc;
import com.gy.hsxt.bs.common.enumtype.bizfile.DocStatus;
import com.gy.hsxt.bs.common.enumtype.bizfile.FileType;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

@FixMethodOrder(MethodSorters.DEFAULT)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:test/spring-global_all.xml")
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
public class BizFileServiceTest {

    @Autowired
    IBSBizFileService ibsBizFileService;

    /**
     * 保存文档
     */
    @Test
    public void testSaveDoc() {
        ImageDoc imageDoc = null;
        // BizDoc imageDoc = null;
        // NormalDoc imageDoc = null;
        for (int i = 0; i < 1; i++)
        {
            imageDoc = new ImageDoc();
            // imageDoc = new BizDoc();
            // imageDoc = new NormalDoc();
            imageDoc.setDocName("法人代表证件正面" + i);
            imageDoc.setDocType(".doc");
            imageDoc.setFileId(i + "");
            imageDoc.setDocCode("1001");
            imageDoc.setRemark("这是说明这是说明");
            ibsBizFileService.saveDoc(imageDoc);
        }
    }

    /**
     * 发布文档
     */
    @Test
    public void testReleaseDoc() {
        // ibsBizFileService.releaseDoc("110120160222154738000000",
        // FileType.IMAGE.getCode());
        // ibsBizFileService.releaseDoc("110120160222190620000000",
        // FileType.BIZ.getCode());
        ibsBizFileService.releaseDoc("110120160222195136000000", FileType.NORMAL.getCode());
    }

    /**
     * 修改文档
     */
    @Test
    public void testModifyDoc() {
        ImageDoc imageDoc = null;
        // BizDoc imageDoc = null;
        // NormalDoc imageDoc = null;

        imageDoc = new ImageDoc();
        // imageDoc = new BizDoc();
        // imageDoc = new NormalDoc();
        imageDoc.setDocId("110120160227170931000000");
        imageDoc.setDocName("幽幽幽幽纟");
        imageDoc.setDocType(".doc");
        imageDoc.setFileId("1008");
        imageDoc.setDocCode("1008");
        imageDoc.setRemark("这是说明这是说明b");
        ibsBizFileService.modifyDoc(imageDoc);
    }

    /**
     * 删除文档
     */
    @Test
    public void testDeleteDoc() {
        ibsBizFileService.deleteDoc("1028", FileType.IMAGE.getCode());
        // ibsBizFileService.deleteDoc("110120160222190620000000",
        // FileType.BIZ.getCode());
        // ibsBizFileService.deleteDoc("110120160222195136000000",
        // FileType.NORMAL.getCode());
    }

    /**
     * 获取历史版本
     */
    @Test
    public void testGetImageDocHis() {
        List<ImageDoc> imageDocList = ibsBizFileService.getImageDocHis("1001");
        System.out.println(imageDocList);
    }

    /**
     * 停用文档
     */
    @Test
    public void testStopUsedDoc() {
        ibsBizFileService.stopUsedDoc("1002", FileType.IMAGE.getCode());
        // ibsBizFileService.stopUsedDoc("110120160222190620000000",
        // FileType.BIZ.getCode());
        // ibsBizFileService.stopUsedDoc("110120160222195136000000",
        // FileType.NORMAL.getCode());
    }

    /**
     * 获取示例图片管理列表
     */
    @Test
    public void testGetImageDocList() {
        List<ImageDoc> imageDocManageList = ibsBizFileService.getImageDocList(DocStatus.NORMAL.getCode());
        System.out.println(imageDocManageList);
    }

    /**
     * 恢复示例图片版本
     */
    @Test
    public void testRecoveryImageDoc() {
        ibsBizFileService.recoveryImageDoc("110120160222114044000000");
    }

    /**
     * 查询详情
     */
    @Test
    public void testGetDocInfo() {
        // System.out.println(ibsBizFileService.getDocInfo("110120160222114044032100",
        // FileType.IMAGE.getCode()));
        // System.out.println(ibsBizFileService.getDocInfo("110120160222195136000000",
        // FileType.BIZ.getCode()));
        System.out.println(ibsBizFileService.getDocInfo("110120160222195136000000", FileType.NORMAL.getCode()));
    }

    /**
     * 查询常用文档列表
     */
    @Test
    public void testGetNormalDocList() {
        Page page = new Page(1);
        page.setCurPage(1);
        page.setPageSize(10000);
        try
        {
            PageData<NormalDoc> orderList = ibsBizFileService.getNormalDocList(null, 2, page);

            List<NormalDoc> orders = orderList.getResult();
            for (NormalDoc o : orders)
            {
                System.out.println("订单号 = " + o);
            }
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 查询业务文档列表
     */
    @Test
    public void testBizDocList() {
        Page page = new Page(1);
        page.setCurPage(1);
        page.setPageSize(10000);
        try
        {
            PageData<BizDoc> orderList = ibsBizFileService.getBizDocList(null, null, page);

            List<BizDoc> orders = orderList.getResult();
            for (BizDoc o : orders)
            {
                System.out.println("订单号 = " + o);
            }
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
