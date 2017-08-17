package com.gy.hsxt.tc.batch.test;
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/



import java.util.HashMap;
import java.util.HashSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.tc.batch.service.PSACBatchService;

/** 
 * 
 * @Package: com.gy.hsxt.uc.batch.test  
 * @ClassName: PSACBatchServiceTest 
 * @Description: TODO
 *
 * @author: lvyan 
 * @date: 2015-10-26 下午2:56:47 
 * @version V1.0 
 

 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-global.xml" })
public class PSACBatchServiceTest {
    private Log log=LogFactory.getLog(this.getClass()); 
    
    @Autowired
    PSACBatchService service;
    
    @Test
    public void excuteBatch() {
        //需求提前准备好对账文件
        
        HashMap<String, String> args=new HashMap<String, String>();
        //GP_CH_YYMMDD_CHK 
        //C:\\gyxt\\tc
        args.put("checkFileAddress_PSAC_AC", "D:\\svnroot\\branches\\hsxt\\hsxt-tc\\hsxt-tc-batchService\\src\\test\\testFile\\psac\\AC_CHK.txt");
        args.put("checkFileAddress_PSAC_PS", "D:\\svnroot\\branches\\hsxt\\hsxt-tc\\hsxt-tc-batchService\\src\\test\\testFile\\psac\\PS_CHK.txt");
//D:\\svnroot\\branches\\hsxt\\hsxt-tc\\hsxt-tc-batchService\\src\\test\\testFile
        
        boolean ret=service.excuteBatch("test1",args);
        log.info(ret);
        Assert.assertTrue(ret);
    }
    
    public static void main(String[] args){
        System.out.print(Integer.valueOf("1"));
        HashSet<String> s= new HashSet<String>();
        s.add("a");
        s.remove("a");
        s.contains("a");
        HashMap<String,String> m=new HashMap<String,String>();
        m.containsValue("a");
        m.containsKey("a");
        m.get("");
        
    }

}
