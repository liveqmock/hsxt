package com.gy.hsxt.gpf.bm.service;/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.gpf.bm.bean.Bmlm;
import com.gy.hsxt.gpf.bm.bean.IncNode;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.database.HBaseDB;
import com.gy.hsxt.gpf.bm.migrate.ComInfoLoader;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Package : com.gy.hsxt.gpf.bm.service
 * @ClassName : MigrateDataServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/1/19 20:48
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class MigrateDataServiceTest {

    private static Logger logger = LoggerFactory.getLogger(MigrateDataServiceTest.class);

    @Resource
    private ComInfoLoader comInfoLoader;

    @Resource
    private MigrateDataService migrateDataService;

    private HBaseDB oldDB;

    @Before
    public void setUp() throws Exception {

        String server = "192.168.229.26";
//        String server = "192.168.229.44";
//        String server = "192.168.253.22";
//        String server = "dev44";
        String port = "";
        String master = "";
        this.oldDB = new HBaseDB(server, port, master);
    }

    /**
     * 迁移增值节点数据
     *
     * @throws Exception
     */
    @Test
    public void testMigrateData() throws Exception {

        Map<String, IncNode> map = oldDB.queryListToMap(Constants.T_APP_INCREMENT, IncNode.class);

        List<IncNode> nodes = new ArrayList<>();

        for (Map.Entry<String, IncNode> entry : map.entrySet()) {
            IncNode node = entry.getValue();
            String rowKey = entry.getKey();
            if (StringUtils.isNotBlank(rowKey)) {
                String custId = getCustId(rowKey);
                if (StringUtils.isBlank(custId)) {
                    logger.warn("=======节点参数KEY[{}]的custId为[{}]=========", rowKey, custId);
                    continue;
                }
                //设置客户号
                node.setCustId(custId);

                node.setResNo(StringUtils.left(rowKey, 11));

                //检查左节点
                String left = node.getLeft();
                node.setLeft(getCustId(left));
                //检查右节点
                String right = node.getRight();
                node.setRight(getCustId(right));
                //检查父节点
                String parent = node.getParent();
                node.setParent(getCustId(parent));

                node.setIsActive(Constants.INCREMENT_ISACTIVE_Y);
                node.setType(HsResNoUtils.getCustTypeByHsResNo(node.getResNo()));

                nodes.add(node);
            }
        }

        migrateDataService.migrateData(nodes);

    }

    /**
     * 获取客户号
     *
     * @param resNo 互生号
     * @return str
     */
    private String getCustId(String resNo) {
        String custId = "";
        if (StringUtils.isNotBlank(resNo)) {
            String rN = StringUtils.left(resNo, 11);
            if (HsResNoUtils.isServiceResNo(rN) || HsResNoUtils.isMemberResNo(rN) || HsResNoUtils.isTrustResNo(rN)) {
                String result = comInfoLoader.getCustIdByResNo(StringUtils.left(resNo, 11));
                custId = StringUtils.isBlank(result) ? "" : (StringUtils.length(resNo) == 11 ? result : result + StringUtils.right(resNo, 1));
                logger.info("================从文件[info.txt]中查询互生号[{}]对应的客户号[{}]=============", resNo, custId);
            } else {
                custId = resNo;
            }
        }
        return custId;
    }

    /**
     * 迁移再增值数据
     */
    @Test
    public void syncBmlmData() throws Exception {
        Map<String, Bmlm> map = oldDB.queryListToMap(Constants.T_APP_BMLM, Bmlm.class);

        Map<String, Bmlm> after = new HashMap<>();

        for (Map.Entry<String, Bmlm> entry : map.entrySet()) {
            Bmlm bmlm = entry.getValue();
            String resNo = entry.getValue().getResNo();
            if (StringUtils.isNoneBlank(resNo)) {
                String custId = getCustId(resNo);
                if (StringUtils.isBlank(custId)) {
                    if (HsResNoUtils.isServiceResNo(resNo) || HsResNoUtils.isMemberResNo(resNo) || HsResNoUtils.isTrustResNo(resNo)) {
                        logger.warn("=======节点参数KEY[{}]的custId为[{}]=========", resNo, custId);
                    }
                    continue;
                }
                //设置客户号
                bmlm.setCustId(custId);

                bmlm.setType(HsResNoUtils.getCustTypeByHsResNo(bmlm.getResNo()));

                after.put(entry.getKey(), bmlm);

            }
        }

        migrateDataService.migrateBmlmMap(after);
    }

    /**
     * 迁移互生积分分配数据
     *
     * @throws Exception
     */
    @Test
    public void syncMlmData() throws Exception {

        Map<String, IncNode> map =  oldDB.queryFilterMap(Constants.T_APP_INCREMENT, "right", null, "0600100000020151221", IncNode.class);

        if (!map.isEmpty()) {
            for (Map.Entry<String, IncNode> entry : map.entrySet()) {
                logger.info("=========key:[{}]=======",entry.getKey());
//                oldDB.deleteByRowKey(Constants.T_APP_INCREMENT,entry.getKey());
            }
//            oldDB.deleteByRowKey(Constants.T_APP_INCREMENT,"0600212000020151221");
        }
    }
}