/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.uc.common;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.common.utils.Base64Utils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.lcs.api.ILCSBaseDataService;
import com.gy.hsxt.uc.as.api.consumer.IUCAsCardHolderService;
import com.gy.hsxt.uc.as.api.enumerate.ErrorCodeEnum;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsSysOperLoginInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.common.service.UCAsEmailService;
import com.gy.hsxt.uc.common.service.UCAsLoginService;
import com.gy.hsxt.uc.common.service.UCAsPwdService;
import com.gy.hsxt.uc.common.service.UCAsTokenService;
import com.gy.hsxt.uc.consumer.service.UCAsNoCardHolderService;
import com.gy.hsxt.uc.ent.mapper.EntTaxRateMapper;
import com.gy.hsxt.uc.ent.service.AsEntService;
import com.gy.hsxt.uc.operator.mapper.OperatorMapper;
import com.gy.hsxt.uc.util.RedisCache;
import com.gy.hsxt.uc.util.StringEncrypt;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class ResetPwdTools {
	@Autowired
	private UCAsPwdService pwdService;

	@Autowired
	UCAsTokenService tokenService;
	@Autowired
	UCAsNoCardHolderService noCardHolderService;
	

	@Autowired
	CommonCacheService commonCacheService;
	
	
	@Autowired
	ILCSBaseDataService baseDataService;
	@Autowired
	OperatorMapper operatorMapper;
	
	@Autowired
	UCAsEmailService emailService;
	
	@Autowired
	AsEntService entService;
	
	@Autowired
	EntTaxRateMapper entTaxRateMapper;
	
	@Autowired
	private RedisCache<Object> cacheUtil;
	
	@Autowired
	UCAsLoginService loginService;
	
	@Autowired
	SysConfig config;
	
	@Autowired
	IUCAsCardHolderService cardHolderService;
	
	@Test
	/** 重置持卡人密码，登陆密码666666，交易密码88888888*/
	public void resetCardHolder() {
		String perResNo = "06002118115";
		cardHolderResetLogPwd(perResNo);
	//	cardHolderSetTradePwd(perResNo);
	}
	
	@Test
	public void resetOperLogPwd(){
		String entResNo = "00000000156";
		String operNo = "0010";
		String newLoginPwd = "n+LCw3dBNAPVk534hSda7g==";
		String secretKey = "5030dd8eeb715004";
		String userType = "3";
		String operCustId = commonCacheService.findOperCustId(entResNo, operNo);
		pwdService.setLogPwd(operCustId, newLoginPwd, userType, secretKey);
	}
	
	
	

	private MyComparator comparator = new MyComparator();
	@Test
	public void removeCahce(){
		String custId = "06002110000164063559726080";
	//	String loginKey = CacheKeyGen.genOperatorLoginAuthKey("06002110000", "0000");
	//	cache.getChangeRedisUtil().remove(loginKey);
	}
	/**
	 * 重置非持卡人登录密码
	 */
	@Test
	public void resetNoCardHolderLoginPwd() {
		String mobile = "15814759813";
		noCardHolderResetLogPwd(mobile);
	}
	/**
	 * 重置持卡人登录密码
	 */
	@Test
	public void resetCardHolderLoginPwd() {
		String perResNo = "05001081234X";
		cardHolderResetLogPwd(perResNo);
		
	}
	
	/**
	 * 重置持卡人交易密码
	 */
	@Test
	public  void resetCardHolderTradePwd(){
		String perResNo = "06002118115";
		cardHolderSetTradePwd(perResNo);
	}
	
	
	public void cardHolderSetTradePwd(String perResNo) {
		String custId = commonCacheService.findCustIdByResNo(perResNo);
		String tradePwd = "m7YOFbFC/UiTVezbZ14chZYr3oXjzAbxZpf8ph2FKmZO+luRwDd211wvmiFdz1RC";
		String secretKey = "1281d175dd555b43";
		String userType = UserTypeEnum.CARDER.getType();
		String operCustId = "777777";
		pwdService.setTradePwd(custId, tradePwd, userType, secretKey, operCustId);
	}
	
	/**
	 * 重置非持卡人的登录密码
	 */
	private void noCardHolderResetLogPwd(String mobile){
		String custId = commonCacheService.findCustIdByMobile(mobile);
		String newLoginPwd = "camxMJa6KDmExyDOPJnp7A==";
		String secretKey = "f6f69e4450521bac";
		String userType = "1";
		pwdService.setLogPwd(custId, newLoginPwd, userType, secretKey);
	}
	
	/**
	 * 重置持卡人的登录密码
	 */
	private void cardHolderResetLogPwd(String perResNo){
		String custId = commonCacheService.findCustIdByResNo(perResNo);
		String newLoginPwd = "ibEiJA7Sm5gcvy9k0Om8ug==";
		String secretKey = "87e06b173d5fd5df";
		String userType = "2";
		pwdService.setLogPwd(custId, newLoginPwd, userType, secretKey);
	}
	

	public static void main(String[] args) {
		String[] pwdArray = new String[]{"000000","111111","222222","333333","444444","555555","666666","777777","888888","999999","123456","112233","123123","456456","789789","987123","987654","654321","111222","3334444","123321","kkkk","112233","223344","334455","321654"};
		
		String key = "7b8aea4fbdf8db98"; //登陆后token
		String loginSalt = "37193453"; //登陆密码盐值 06002110000=86034861

		for(int i=0;i<pwdArray.length;i++){
			String pwd = pwdArray[i];
			String md5Login = StringEncrypt.string2MD52(pwd);//登录密码
			String aes = StringEncrypt.encrypt(pwd, key);
//			String pwdLogin = md5Login + loginSalt;
			String pwdLogin = md5Login + loginSalt;
			System.out.println();
			System.out.println();
			pwdLogin = StringEncrypt.sha256(pwdLogin);
			System.out.println("明文登录密码[" + pwd + "]");
			System.out.println("MD5登录密码[" + md5Login + "]");
			System.out.println("AES秘钥（登录密码）[" + key + "]");
			System.out.println("AES登录密码[" + aes + "]");
			System.out.println("登录密码盐值[" + loginSalt + "]");
			System.out.println("SHA256加密后的登录密码[" + pwdLogin + "]");
			System.out.println();
		}
	}
	

	@SuppressWarnings("unused")
	@Test
	public void flushRedis(){
		cacheUtil.getChangeRedisUtil().flushDB();
		cacheUtil.getFixRedisUtil().flushDB();
	}
	
	
	
	public void removeRepateCodeMsg(){
		ErrorCodeEnum[] ee =  ErrorCodeEnum.values();
		Set<Integer> set1 = new HashSet<Integer>();
		Set<String> set2 = new HashSet<String>();
		Set<String> set3 = new HashSet<String>();
		Map<Integer,String> treeMap = new TreeMap<Integer,String>();
		int count = 0;
		int outKey = 0;
		String outValue = "";
		int innerkey = 0;
		String innerValue = "";
		int keyCount = 0;
		List<Integer> list1 = new LinkedList<Integer>();
		List<String> list2 = new LinkedList<String>();
		List<String> list3 = new LinkedList<String>();
		int cc = 0;
		for(ErrorCodeEnum e:ee){
			set3.add(e.name());
			list3.add(e.name());
			cc++;
			outValue = e.getDesc();
			outKey = e.getValue();
			list1.add(outKey);
			list2.add(outValue);
			treeMap.put( e.getValue(),e.getDesc());
			set1.add(e.getValue());
			set2.add(e.getDesc());
		//	System.out.println("name["+e.name()+"]");
		}
		
		for(int i : set1){
			for (int j = 0; j < list1.size(); j++) {
				if(i == list1.get(j)){
					list1.remove(j);
					break;
				}
			}
		}
		
		for(String i : set2){
			for (int j = 0; j < list2.size(); j++) {
				if(i == list2.get(j)){
					list2.remove(j);
					break;
				}
			}
		}
		
		for (int i = 0; i < list1.size(); i++) {
			System.out.println("list   code["+list1.get(i)+"]");
		}
		
		for (int i = 0; i < list2.size(); i++) {
			System.out.println("list   desc["+list2.get(i)+"]");
		}
		System.out.println("length["+ee.length+"]");
		System.out.println("treeMap size["+treeMap.size()+"]");
		System.out.println("code size["+set1.size()+"]");
		System.out.println("msg size["+set2.size()+"]");
		System.out.println("list1 size ["+list1.size()+"]");
		System.out.println("list2 size ["+list2.size()+"]");
		System.out.println("cc["+cc+"]");

	
	}
	
	class MyComparator implements Comparator<Integer>{

		@Override
		public int compare(Integer o1, Integer o2) {
			return o1.compareTo(o2);
		}
		
	}
	
	@Test
	public void sysOperCommonLogin(){
		AsSysOperLoginInfo operLoginInfo = new AsSysOperLoginInfo();
		String userName = "admin";
		String loginIp = "192.168.229.55";
		String randomToken = "b2a69aefe1b450e6";
		String loginPwd = "nGq2537EAQCRB08isnG89w==";
		String secondLoginPwd = "bHPkRu55g4zQBdNRbeNnVA==";
		operLoginInfo.setChannelType(ChannelTypeEnum.WEB);
		operLoginInfo.setLoginPwd(loginPwd);
		operLoginInfo.setLoginIp(loginIp);
		operLoginInfo.setRandomToken(randomToken);
		operLoginInfo.setSecondLoginPwd(secondLoginPwd);
		operLoginInfo.setUserName(userName);
		loginService.sysOperCommonLogin(operLoginInfo);
	}

	@Resource
	RedisUtil<String> changeRedisUtil;

	
	@Test
	public void testzz(){
		String loginKey = "UC.m_0600211171220100007";
		String value = "00000001";
		changeRedisUtil.add(loginKey, value);
//		changeRedisUtil.getToString(loginKey); check
		changeRedisUtil.get(loginKey, String.class);
//		redisUtil.get(loginKey, id, String.class);
		changeRedisUtil.getAll(loginKey, String.class);
//		changeRedisUtil.addToList(loginKey, value, String.class);
//		redisUtil.addList(loginKey, null, String.class);
		changeRedisUtil.getList(loginKey, String.class);
//		redisUtil.rangeFromList(loginKey, start, end, String.class);
//		redisUtil.getFromList(loginKey, index, String.class);

	}
	@Test
	public void sendLogs(){
		for (int i = 0; i < 100; i++) {
			SystemLog.debug("test"+i, "test-method"+i, ""+i);
			SystemLog.info("test"+i, "test-method"+i, ""+i);
			SystemLog.warn("test"+i, "test-method"+i, ""+i);
		}
	}
	@Test
	public void check(){
		String randomToken1 = tokenService.getRandomToken("");
		boolean b1 = tokenService.checkRandomToken("", "1555233344441212aaaaa");
		boolean b11 = tokenService.checkRandomToken("", "12211212aaaaa");
		String randomToken2 = tokenService.getRandomToken("");
		boolean b2 = tokenService.checkRandomToken("", randomToken1);
		
		System.out.println("b1["+b1+"],b11["+b11+"],b2["+b2+"]");
		
		try {
			Thread.currentThread().sleep(100000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void teseSend() {
		for (int i = 0; i < 1000; i++) {
			SystemLog.info("modole["+i+"]", "method["+i+"]", "this is test ["+i+"]");
			BizLog.biz("modole["+i+"]", "method["+i+"]", "|列值1|列值2", "|恩，你哈|this is test ["+i+"]");
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void Testasdljkasdjklsa(){
		String endDate = DateUtil.getCurrentDateTime().substring(0, 10);
		long s = DateUtil.TimeDiff(
				DateUtil.StringToDateTime(endDate + " " + SysConfig
						.getPwdQuestionFailUnlockTime()),DateUtil.getCurrentDate());
	}
	
	@Test
	public void flush(){
		cacheUtil.getChangeRedisUtil().flushDB();
		cacheUtil.getFixRedisUtil().flushDB();
	}
	
	
}
