/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.uc.as.api.common.IUCAsPwdQuestionService;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.operator.service.OperatorService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-uc.xml" })
public class UCAsPwdQuestionServiceTest {
	@Autowired
	OperatorService operatorService;

	@Autowired
	IUCAsPwdQuestionService asPwdQuestionService;

	@Test
	@SuppressWarnings("unused")
	public void listDefaultPwdQuestions() {
		List<AsPwdQuestion> list = asPwdQuestionService
				.listDefaultPwdQuestions();
	}

	@Test
	public void updatePwdQuestion() {
		String perCustId = "905175622882754560";
		AsPwdQuestion question = new AsPwdQuestion();
		String answer = string2MD5("猴子");
		question.setAnswer(answer);
		question.setQuestion("您的宠物名字？");
		asPwdQuestionService.updatePwdQuestion(perCustId, question);

	}

	@Test
	public void checkPwdQuestion() {
			String custId = "905175622882754560";
			AsPwdQuestion question = new AsPwdQuestion();
			String answer = string2MD5("1234");
			System.out.println();
			question.setAnswer(answer);
			question.setQuestion("您的宠物名字？");
			String usertype = "1";
			asPwdQuestionService.checkPwdQuestion(custId, usertype, question);

	}

	public static String string2MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		String md5Code = hexValue.toString();
		return md5Code;
	}
	
	
}
