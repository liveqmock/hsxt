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

package com.gy.hsxt.uc.consumer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.bs.api.consumer.IUCBsCardHolderService;
import com.gy.hsxt.uc.bs.bean.consumer.BsCardHolder;
import com.gy.hsxt.uc.bs.bean.consumer.BsHsCard;
import com.gy.hsxt.uc.cache.service.CommonCacheService;
import com.gy.hsxt.uc.consumer.mapper.HsCardMapper;

/**
 * 
 * @Package: com.gy.hsxt.uc.consumer
 * @ClassName: UCBsCardHolderServiceTest
 * @Description: 持卡人基本信息管理（BS业务系统调用）
 * 
 * @author: tianxh
 * @date: 2015-10-20 下午7:50:12
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class UCBsCardHolderServiceTest {
	@Autowired
	private IUCBsCardHolderService iUCBsCardHolderService;
	@Autowired
	private HsCardMapper hsCardMapper;
	@Autowired
	CommonCacheService commonCacheService;

	private String buildPerResNo(String str, String pad) {
		str = str.substring(0, 7);
		if (pad.length() == 1) {
			str = str + "000" + pad;
		}
		if (pad.length() == 2) {
			str = str + "00" + pad;
		}
		if (pad.length() == 3) {
			str = str + "0" + pad;
		}
		if (pad.length() == 4) {
			str = str + pad;
		}
		return str;
	}

	@Test
	public void batchAddCards() throws HsException {
		String password = "666666";
		String operatorId = "06002110000000120160114";
		String entResNo = "05001080000";// 隶属企业的资源号
		List<BsHsCard> list = new ArrayList<BsHsCard>();
		for (int i = 0; i < 1; i++) {
			String cryptogram = "TestVer[2]" + i;
//			String resNo = buildPerResNo(entResNo, "" + i);
			String resNo = "06186010004";
			BsHsCard card = new BsHsCard();
			card.setCryptogram(cryptogram);
			card.setPerResNo(resNo);
			card.setLoginPwd(password);
			list.add(card);
		}
		try {
			long start = System.currentTimeMillis();
			iUCBsCardHolderService.batchAddCards(operatorId, list, entResNo);
			long end = System.currentTimeMillis();
			long cost = end - start;
			System.out.println("---------批量开卡耗费时间:[" + cost + "]----------");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				System.in.read();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

		// }

	}

	// @Test
	public void closeAccout() {
		String perCustId = "0500108181720151111";

		String operCustId = "09186630000162706727874560";
		iUCBsCardHolderService.closeAccout(perCustId, operCustId);

	}

	// @Test
	public void searchCardHolderInfoByResNo() {
		String custId = "0500108181620151111";
		BsCardHolder cardHolder = iUCBsCardHolderService
				.searchCardHolderInfoByResNo("05001081815");
	}

	// @Test
	public void searchCardHolderInfoByCustId() {
		BsCardHolder cardHolder = iUCBsCardHolderService
				.searchCardHolderInfoByCustId("4999999999920151104");
	}

	// @Test
	public void remakeCard() {
		BsHsCard bsHsCard = new BsHsCard();
		bsHsCard.setCryptogram("%%%%%%");
		bsHsCard.setLoginPwd("666666");
		bsHsCard.setPerResNo("05001081815");
		String operCustId = "05001080000100220155555";
		iUCBsCardHolderService.remakeCard(operCustId, bsHsCard);
	}

	// @Test
	public void searchHsCardInfoByResNo() {
		BsHsCard hsCard = iUCBsCardHolderService
				.searchHsCardInfoByResNo("05001081815");
	}

	// @Test
	public void searchHsCardInfoByCustId() {
		String custId = "0500108181620151105";
		BsHsCard hsCard = iUCBsCardHolderService
				.searchHsCardInfoByCustId(custId);
	}

	// @Test
	public void findCustIdByResNo() {
		String resNo = "05001081815";
		String custId = commonCacheService.findCustIdByResNo(resNo);
	}

	// @Test
	public void batchResendCards() {
		String password = "666666";
		List<BsHsCard> list = new ArrayList<BsHsCard>();
		BsHsCard card = new BsHsCard();
		card.setCryptogram("!!k!!!");
		card.setLoginPwd(password);
		card.setPerResNo("06002118144");
		BsHsCard card1 = new BsHsCard();
		card1.setCryptogram("@@k@@@");
		card1.setLoginPwd(password);
		card1.setPerResNo("06002118145");
		BsHsCard card2 = new BsHsCard();
		card2.setCryptogram("**k***");
		card2.setLoginPwd(password);
		card2.setPerResNo("06002118146");
		BsHsCard card3 = new BsHsCard();
		card3.setCryptogram("$$$k$$$");
		card3.setLoginPwd(password);
		card3.setPerResNo("06002118147");
		list.add(card1);
		list.add(card);
		list.add(card2);
		list.add(card3);
		iUCBsCardHolderService.batchResendCards("7777", list, "09186630000");
	}

	// @Test
	public void updateLoginInfo() {
		String custId = "0500108181520151105";
		String loginIp = "192.168.1.188";
		String dateStr = "2015-11-05 12:12:12";
		iUCBsCardHolderService.updateLoginInfo(custId, loginIp, dateStr);
	}

	// @Test
	public void batchCloseAccout() {
		String updatedby = "9999";
		List<BsHsCard> list = new ArrayList<BsHsCard>();
		String loginPwd = "666666";
		String cryptogram = "1111111";
		BsHsCard card1 = new BsHsCard();
		BsHsCard card2 = new BsHsCard();
		BsHsCard card3 = new BsHsCard();
		card1.setPerResNo("06002118140");
		card1.setLoginPwd(loginPwd);
		card1.setCryptogram(cryptogram + 1);
		card2.setPerResNo("06002118141");
		card2.setLoginPwd(loginPwd);
		card2.setCryptogram(cryptogram + 2);
		card3.setPerResNo("06002118142");
		card3.setLoginPwd(loginPwd);
		card3.setCryptogram(cryptogram + 3);
		list.add(card1);
		list.add(card2);
		list.add(card3);
		String entResNo = "06002110000";
		System.out.println("用户的当前工作目录:\n" + System.getProperty("user.dir"));
		iUCBsCardHolderService.batchResendCards(updatedby, list, entResNo);

		System.out.println("用户的当前工作目录:\n" + System.getProperty("user.dir"));
	}
	
}
