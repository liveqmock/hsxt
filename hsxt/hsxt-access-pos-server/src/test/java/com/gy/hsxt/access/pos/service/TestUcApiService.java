/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.pos.service;

import static org.junit.Assert.assertEquals;

import org.apache.commons.codec.binary.Hex;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.common.json.JSON;
import com.gy.hsxt.access.pos.constant.PosConstant;
import com.gy.hsxt.access.pos.constant.PosRespCode;
import com.gy.hsxt.access.pos.exception.PosException;
import com.gy.hsxt.access.pos.util.CommonUtil;
import com.gy.hsxt.access.pos.util.PosUtil;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsSignInInfo;
import com.gy.hsxt.uc.as.bean.device.AsDevice.AsDeviceStatusEnum;
import com.gy.hsxt.uc.as.bean.device.AsPos;
import com.gy.hsxt.uc.as.bean.ent.AsEntStatusInfo;
import com.gy.hsxt.uc.as.bean.enumerate.AsDeviceTypeEumn;

/**
 * 
 * 
 * @Package: com.gy.hsxt.access.pos.service
 * @ClassName: TestUcApiService
 * @Description: TODO
 * 
 * @author: guiyi149
 * @date: 2015-11-10 上午9:33:23
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/applicationContext.xml" })
public class TestUcApiService {

    Logger log = LoggerFactory.getLogger(TestUcApiService.class);

    @Autowired
    @Qualifier("ucApiService")
    private UcApiService ucApiService;

    public String entNo;

    public String posNo;

    public String operNo;

    public String cardNo;
    
    public String resNo;//个人
    
    public String credCode;
    
    public String pwd;
    
    public String tradePwd;

    @Before
    public void setUp() throws Exception {

        entNo = "06186630000";
        posNo = "0001";
        operNo = "0001";
        cardNo = "06186010006";
        
        resNo = "06186010006";
        credCode = "11560964";
        
        pwd = "666666";
        tradePwd = "88888888";
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testFindEntCustIdByEntResNo() throws Exception {

        try
        {

            String custId = ucApiService.findEntCustIdByEntResNo("11111111111");

            System.out.println(custId);
            System.out.println("-----------------");

            Assert.assertEquals("121231", custId);
            assertEquals("", custId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 签到
     * 
     * @author wucl 2015-11-5 下午2:31:39
     * @throws Exception
     */
    @Test
    public void testPosSignIn() throws Exception {
        try
        {

            AsSignInInfo siginInfo = new AsSignInInfo();
            siginInfo.setEntResNO(entNo);
            siginInfo.setDeviceNo(posNo);
            siginInfo.setUserName(operNo);

            byte[] result = ucApiService.posSignIn(siginInfo);

            Assert.assertNotEquals(null, result);

            System.out.println(Hex.encodeHexString(result));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 签退
     */
    @Test
    public void testDeviceLogout(){
        try
        {

            ucApiService.deviceLogout(entNo, posNo, operNo, AsDeviceTypeEumn.POS.getType());

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 签到鉴权
     * 
     * @author wucl 2015-11-6 下午2:56:04
     * @throws Exception
     */
    @Test
    public void testCheckAuth() throws Exception {
        try
        {

            ucApiService.checkAuth(entNo, posNo, operNo, AsDeviceTypeEumn.POS.getType());

        }
        catch (Exception e)
        {
            if (HsException.class == e.getClass())
            {

                final HsException hsEx = (HsException) e;
                int errorCode = hsEx.getErrorCode();

                log.info(hsEx.getErrorCode() + "");
                log.info(hsEx.getMessage());
                log.info("HsException  ----------" + PosRespCode.codeConverts(errorCode).getCodeStr());
            }

            e.printStackTrace();
        }
    }

    @Test
    public void testFindPosByDeviceNo() throws Exception {
        try
        {

            AsPos result = ucApiService.findPosByDeviceNo("06186630000", "0001");

            Assert.assertNotEquals(null, result);

            System.out.println("结果：" + JSON.json(result));
            Assert.assertEquals(AsDeviceStatusEnum.ENABLED, result.getStatus());
        }
        catch (Exception e)
        {
            System.out.println("--------------------1");
            
            if (e instanceof HsException)
            {

                System.out.println("--------------------2");

                final HsException hsEx = (HsException) e;
                int errorCode = hsEx.getErrorCode();

                log.info(hsEx.getErrorCode() + "");
                log.info(hsEx.getMessage());
                log.info("HsException  ----------" + PosRespCode.codeConverts(errorCode).getCodeStr());
            }

            e.printStackTrace();
        }
    }

    @Test
    public void testSearchEntStatusInfo() throws Exception {
        try
        {
            // 查询企业状态 + pos机状态
            AsEntStatusInfo entInfo = ucApiService.searchEntStatusInfoByResNo(entNo);
            
            CommonUtil.checkState(null == entInfo, "无此企业信息：" + entNo, PosRespCode.NO_FOUND_ENT);
            
            log.debug("=========" + JSON.json(entInfo));
            CommonUtil.checkState(entInfo.getIsClosedEnt().equals(PosConstant.POS_TRUE), "企业已被冻结：" + entNo,
                    PosRespCode.CHECK_ACCOUNT_FREEZE);
            CommonUtil.checkState(entInfo.getBaseStatus().equals("NORMAL"), "企业基础状态异常：" + entNo,
                    PosRespCode.NO_ENT_CARDPOINT);
            
            Assert.assertNotEquals(null, entInfo);
        }
        catch (Exception e)
        {

            System.out.println("--------------------1");
            
            if (e instanceof HsException)
            {

                System.out.println("--------------------2");

                final HsException hsEx = (HsException) e;
                int errorCode = hsEx.getErrorCode();

                log.info(hsEx.getErrorCode() + "");
                log.info(hsEx.getMessage());
                log.info("HsException  ----------" + PosRespCode.codeConverts(errorCode).getCodeStr());
            }

            e.printStackTrace();
        }
    }

    @Test
    public void testGetEncrypt() throws Exception {
        try
        {

            final byte[] pwdByte = PosUtil.encryptWithANSIFormat("123456", cardNo);
            final byte[] encryData = ucApiService.getEncrypt(posNo, entNo, pwdByte);

            @SuppressWarnings("deprecation")
            String packPin = CommonUtil.byte2HexStr(encryData);

            log.info(packPin);
        }
        catch (Exception e)
        {

            System.out.println("--------------------1");
            System.out.println(e.getClass().getSimpleName());
            if (e instanceof HsException)
            {

                System.out.println("--------------------2");

                final HsException hsEx = (HsException) e;
                int errorCode = hsEx.getErrorCode();

                log.info(hsEx.getErrorCode() + "");
                log.info(hsEx.getMessage());
                log.info("HsException  ----------" + PosRespCode.codeConverts(errorCode).getCodeStr());
            }

            e.printStackTrace();
        }
    }
    
    @Test
    public void testCheckPwdCode(){
        try
        {
            log.debug("===========" + resNo);
            log.debug("===========" + tradePwd);
            log.debug(CommonUtil.string2MD5(tradePwd));
            ucApiService.checkResNoAndCode(resNo, credCode);
            ucApiService.checkResNoAndTradePwd(resNo, CommonUtil.string2MD5(tradePwd));
            ucApiService.checkResNoPwdAndCode(resNo, CommonUtil.string2MD5(pwd), credCode);

        }
        catch (Exception e)
        {

            System.out.println("--------------------1");
            System.out.println(e.getClass().getSimpleName());
            if (HsException.class == e.getClass()) {
                
                final HsException hsEx = (HsException) e;
                int errorCode = hsEx.getErrorCode();
                 log.debug("错误代码--------------" + errorCode);
            } 

            e.printStackTrace();
        }
    }

}
