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

package com.gy.hsxt.service.testcase;

import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.bp.api.IBusinessSysParamService;
import com.gy.hsxt.bp.bean.BusinessSysParamGroup;
import com.gy.hsxt.bp.bean.BusinessSysParamItems;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:/spring/spring-global.xml"})
public class BusinessSysParamServiceTest {

    @Autowired
    public IBusinessSysParamService iSysParamService;
    
    @Test
    public void test() throws java.text.ParseException{
        try{
            BusinessSysParamGroup sysParamGroup = new BusinessSysParamGroup();
            sysParamGroup.setSysGroupCode("PUB_PLAT_PARA");
            sysParamGroup.setSysGroupName("地区平台参数");
            sysParamGroup.setIsActive("Y");
            sysParamGroup.setIsPublic("Y");
            sysParamGroup.setCreatedby("BOOTUP");
            sysParamGroup.setUpdatedby("BOOTUP");
            Date date = new Date();
            //sysParamGroup.setCreatedDate("2015-11-20");
//            sysParamGroup.setUpdatedby("3");
            //sysParamGroup.setUpdatedDate("2015-11-20");
            String sysGroupCode = "4"; 
            Page page = new Page(1,3);
//            iSysParamService.addSysParamGroup(sysParamGroup);
//            iSysParamService.updateSysParamGroup(sysParamGroup);
//            iSysParamService.deleteSysParamGroup(sysGroupCode);
//            iSysParamService.getSysParamGroupByCode(sysGroupCode);
//            iSysParamService.searchSysParamGroupPage(sysParamGroup, page);
            
            
            BusinessSysParamItems sysParamItems = new BusinessSysParamItems();
            sysParamItems.setSysGroupCode("PUB_PLAT_PARA");
            sysParamItems.setSysItemsKey("CORP_CODE");
            sysParamItems.setSysItemsName("CORP_CODE");
            sysParamItems.setSysItemsValue("0156");
            sysParamItems.setAccessLevel(1);
            sysParamItems.setIsActive("Y");
            sysParamItems.setCreatedby("BOOTUP");
            sysParamItems.setUpdatedby("BOOTUP");
            iSysParamService.addSysParamItems(sysParamItems);
//            iSysParamService.updateSysParamItems(sysParamItems);
//            iSysParamService.deleteSysParamItems("2");
//            iSysParamService.getSysParamItemsByCode("1");
//            iSysParamService.searchSysParamItemsPage(sysParamItems, page);
            
        }catch(HsException ex){
            fail("ErrorCode:" + ex.getErrorCode() + " ErrorMessage:" + ex.getMessage() + "\n" + ex.getStackTrace());
        }
    
    }
    
    
}
