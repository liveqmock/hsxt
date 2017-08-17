/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.uc.as.api.common;

import java.util.List;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.common.AsPwdQuestion;

/**
 * 密保问题操作接口
 * 
 * @Package: com.gy.hsxt.uc.as.api.common
 * @ClassName: IUCAsPwdQuestionService
 * @Description: TODO
 * 
 * @author: lixuan
 * @date: 2015-10-19 下午2:39:51
 * @version V1.0
 */
public interface IUCAsPwdQuestionService {
	
	/**
	 * 返回默认的密保问题
	 * @return
	 * @throws HsException
	 */
	public  List<AsPwdQuestion>   listDefaultPwdQuestions() throws  HsException;
	

	/**
	 * 更新密保问题
	 * @param custId 客户号（包含持卡人客户号和企业客户号）
	 * @param question 问题ID和答案必填
	 * @throws HsException
	 */
	public void updatePwdQuestion(String custId, AsPwdQuestion question)
			throws HsException;

	/**
	 * 密保验证（仅操作员和持卡人有密保）
	 * @param custId 客户号（持卡人、操作员）
	 * @param usertype 用户类型     2 持卡人  4 企业
	 * @param question 问题和答案为必填
	 * @throws HsException
	 */
	public String checkPwdQuestion(String custId,String usertype,AsPwdQuestion question) throws HsException ;
	/**
	 * 判断持卡人是否已设置密保问题
	 * @param perResNo	持卡人互生号
	 * @return
	 * @throws HsException
	 */
	public boolean isSetCarderPwdQuestion(String perResNo) throws HsException;
	
	/**
	 * 判断非持卡人是否已设置密保问题
	 * @param mobile	非持卡人手机号码
	 * @return
	 * @throws HsException
	 */
	public boolean isSetNoCarderPwdQuestion(String mobile) throws HsException;
	
	/**
	 * 判断企业是否已设置密码保问题
	 * @param entResNo	企业互生号
	 * @return
	 * @throws HsException
	 */
	public boolean isSetEntPwdQuestion(String entResNo)throws HsException;

}
