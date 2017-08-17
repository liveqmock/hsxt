/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.uc.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderAuthInfoService;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameAuth;
import com.gy.hsxt.uc.as.bean.consumer.AsRealNameReg;
import com.gy.hsxt.uc.cache.CacheKeyGen;
import com.gy.hsxt.uc.common.SysConfig;
import com.gy.hsxt.uc.common.mapper.CustAccountMapper;
import com.gy.hsxt.uc.common.mapper.PwdQuestionMapper;
import com.gy.hsxt.uc.common.service.UCFsAuthService;
import com.gy.hsxt.uc.consumer.mapper.CardHolderMapper;
import com.gy.hsxt.uc.consumer.mapper.HsCardMapper;
import com.gy.hsxt.uc.consumer.mapper.RealNameAuthMapper;
import com.gy.hsxt.uc.consumer.service.UCAsCardHolderService;
import com.gy.hsxt.uc.ent.mapper.EntExtendInfoMapper;
import com.gy.hsxt.uc.fs.api.common.IUCFsAuthService;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer
 * @ClassName: UCAsCardHolderAuthInfoServiceTest
 * @Description: TODO
 * 
 * @author: tianxh
 * @date: 2015-10-29 下午8:02:49
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml"})
public class UCAsCardHolderAuthInfoServiceTest {

    @Autowired
    private IUCAsCardHolderAuthInfoService iUCAsCardHolderAuthInfoService;
    @Autowired
    UCAsCardHolderService cardHolderService;

    @Autowired
    private RealNameAuthMapper realNameAuthMapper;

    
    @Autowired
    private PwdQuestionMapper pwdQuestionMapper;
    
    @Autowired
    private HsCardMapper hsCardMapper;
    @Autowired
    private CardHolderMapper cardHolderMapper;
    
    @Autowired
    CustAccountMapper custAccountMapper;
    
    @Autowired
    UCFsAuthService xxauthService;
    
    @Autowired
    SysConfig config;

    @Autowired
    IUCFsAuthService authService;

    @Autowired
	private EntExtendInfoMapper entExtendInfoMapper;
   @Test
    public void regByRealName() {

    	
      AsRealNameReg asRealNameReg = new AsRealNameReg();
      asRealNameReg.setCerNo("432127128202093391");
      asRealNameReg.setCertype("3");
      asRealNameReg.setEntRegAddr("萨达四大卡");
      asRealNameReg.setEntName("羔羊企业");
//      asRealNameReg.setCountryCode("86");
      asRealNameReg.setCustId("0603311012320160416");
      asRealNameReg.setRealName("李智_KK1");
      iUCAsCardHolderAuthInfoService.regByRealName(asRealNameReg);
    }
    @Test
    public void findAuthStatusByCustId() {
    	String custId = "";
        String realAuthStatus = iUCAsCardHolderAuthInfoService.findAuthStatusByCustId(custId);
        System.out.println("数据库：realAuthStatus[" + realAuthStatus + "]");
    }

     @Test
    public void searchRealNameRegInfo() {
        String custId = "0600211855120151207";
        AsRealNameReg asRealNameReg = iUCAsCardHolderAuthInfoService.searchRealNameRegInfo(custId);
    }

     @Test
    public void searchRealNameAuthInfo() {
        String custId = "0600211820220151207";
        AsRealNameAuth asRealNameAuth = iUCAsCardHolderAuthInfoService.searchRealNameAuthInfo(custId);

    }

    
    
    
    @Test
    public void ListAuthStatus(){
    	List<String>  list = new ArrayList<String>();
    	list.add("0600211814020151207");
    	list.add("0600211814120151207");
    	list.add("0600211814220151207");
    	 Map<String, String>  map = iUCAsCardHolderAuthInfoService.listAuthStatus(list);
    }
    
    @SuppressWarnings("unused")
	@Test
    public void isLogin(){
    	boolean b = authService.isLogin("09186630000162706727874560", "2", "1a5041e9a8252772cb7befbdc08123a77f6edcdc2941d70ef730814988f0fefd");
    }
    
    @SuppressWarnings({ "unused", "static-access" })
	@Test
    public void sysConfiTest(){
    	int count = config.getPwdQuestionFailTimes();
    }
    @Test
    public void searchRealNameAuthInfoTest(){
    	String custId = "0500108123020151217";
 //   	RealNameAuth Auth = cardHolderService.searchRealNameAuthInfo(custId);
    }
  
}
