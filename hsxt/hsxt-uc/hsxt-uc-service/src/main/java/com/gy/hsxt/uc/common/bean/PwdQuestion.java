package com.gy.hsxt.uc.common.bean;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.JSONObject;

/**
 * 密保问题
 * 
 * @Package: com.gy.hsxt.uc.common.bean  
 * @ClassName: PwdQuestion 
 * @Description: TODO
 *
 * @author: lixuan 
 * @date: 2015-11-23 下午6:49:17 
 * @version V1.0
 */
public class PwdQuestion implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5828505724396474510L;

	/**
	 * ID
	 */
    private Long questionId;

    /**
     * 客户号
     */
    private String custId;

    /**
     * 密保问题
     */
    private String pwdRestQuestion;

    /**
     * 密保答案
     */
    private String pwdRestPwdAnswer;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 修改日期
     */
    private Date updateDate;

    /**
     * 创建人
     */
    private String createBy;
    
    
    /**
     * 修改人
     */
    private String updateBy;
    
    
    /**
	 * @return the 创建人
	 */
	public String getCreateBy() {
		return createBy;
	}

	/**
	 * @param 创建人 the createBy to set
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	/**
	 * @return the 修改人
	 */
	public String getUpdateBy() {
		return updateBy;
	}

	/**
	 * @param 修改人 the updateBy to set
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	/**
     * 密保ID
     * @return
     */
    public Long getQuestionId() {
        return questionId;
    }

    /**
     * 密保ID
     * @param questionId
     */
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    /**
     * 客户号
     * @return
     */
    public String getCustId() {
        return custId;
    }

    /**
     * 客户号
     * @param custId
     */
    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    /**
     * 密保问题
     * @return
     */
    public String getPwdRestQuestion() {
        return pwdRestQuestion;
    }

    /**
     * 密保问题
     * @param pwdRestQuestion
     */
    public void setPwdRestQuestion(String pwdRestQuestion) {
        this.pwdRestQuestion = pwdRestQuestion == null ? null : pwdRestQuestion.trim();
    }

    public String getPwdRestPwdAnswer() {
        return pwdRestPwdAnswer;
    }

    /**
     * 密保答案 
     * @param pwdRestPwdAnswer
     */
    public void setPwdRestPwdAnswer(String pwdRestPwdAnswer) {
        this.pwdRestPwdAnswer = pwdRestPwdAnswer == null ? null : pwdRestPwdAnswer.trim();
    }

    /**
     * 创建日期
     * @return
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 创建日期
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 更新日期
     * @return
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 更新日期
     * @param updateDate
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    public String toString(){
		return JSONObject.toJSONString(this);
	}
}