/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.bean.common;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;
/**
 * 密保
 * 
 * @Package: com.gy.hsxt.uc.as.bean.common  
 * @ClassName: AsPwdQuestion 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-10-19 下午4:01:30 
 * @version V1.0
 */
public class AsPwdQuestion implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7347767073264726588L;
	/**
	 * 密保ID
	 */
	Integer questionId;
	/**
	 * 密保问题
	 */
	String question;
	/**
	 * 密保答案
	 */
	String answer;

	/**
	 * ID
	 * 
	 * @return
	 */
	public Integer getQuestionId() {
		return questionId;
	}

	/**
	 * ID
	 * 
	 * @param questionId
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	/**
	 * 问题
	 * 
	 * @return
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * 问题
	 * 
	 * @param question
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * 答案
	 * 
	 * @return
	 */
	public String getAnswer() {
		return answer;
	}

	/**
	 * 答案
	 * 
	 * @param answer
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String toString(){
		return JSONObject.toJSONString(this);
	}
}
