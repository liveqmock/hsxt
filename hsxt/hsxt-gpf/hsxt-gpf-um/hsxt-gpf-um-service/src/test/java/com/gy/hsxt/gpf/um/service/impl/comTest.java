package com.gy.hsxt.gpf.um.service.impl;/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.utils.DateUtil;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Package : com.gy.hsxt.gpf.um.service.impl
 * @ClassName : OperatorServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/1/27 11:27
 * @Version V3.0.0.0
 */
public class comTest {

    @Test
    public void testThink() throws Exception {


        Tr tr = new Tr();
        tr.setId("1212");
        tr.setpId("sssss");
        System.out.println(tr);
    }

    public static class Tr {
        private String id;

        private String pId;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getpId() {
            return pId;
        }

        public void setpId(String pId) {
            this.pId = pId;
        }


        @Override
        public String toString() {
            return JSON.toJSONString(this);
        }
    }

}