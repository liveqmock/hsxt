package com.gy.hsxt.ws.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import com.gy.hsi.ds.param.HsPropertiesConfigurer;
import com.gy.hsi.nt.api.beans.DynamicBizMsgBean;
import com.gy.hsi.nt.api.beans.NoteBean;
import com.gy.hsxt.bs.common.enumtype.msgtpl.MsgBizType;
import com.gy.hsxt.common.constant.CustType;

public class NoticeMsg implements Serializable {
	/** 消息主键ID */
	private String msgId;

	/** 福利资格编号 */
	private String welfareId;

	/** 消费者客户号 */
	private String custId;

	/** 消费者互生号 */
	private String resNo;

	/** 消费者姓名 */
	private String custName;

	/** 消费者手机号码 */
	private String mobile;

	/** 发送状态： 0、未发送 1、发送成功 2、发送失败 */
	private Integer sendStatus;

	/** 发送消息类型：0、短信通知 1、互动推送消息 2、邮件通知 */
	private Integer msgType;

	/** 消息业务 1、意外保障福利生效 2、消费积分连续不足导致意外保障福利失效 3、消费者正常到期失效 4、医疗补贴福利生效 */
	private Integer bizType;

	/** 发送消息失败原因 */
	private String failMsg;

	/** 创建日期 */
	private Timestamp createDate;

	/** 更新日期 */
	private Timestamp updatedDate;

	private static final long serialVersionUID = 1L;

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId == null ? null : msgId.trim();
	}

	public String getWelfareId() {
		return welfareId;
	}

	public void setWelfareId(String welfareId) {
		this.welfareId = welfareId == null ? null : welfareId.trim();
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId == null ? null : custId.trim();
	}

	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo == null ? null : resNo.trim();
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName == null ? null : custName.trim();
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile == null ? null : mobile.trim();
	}

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Integer getMsgType() {
		return msgType;
	}

	public void setMsgType(Integer msgType) {
		this.msgType = msgType;
	}

	public Integer getBizType() {
		return bizType;
	}

	public void setBizType(Integer bizType) {
		this.bizType = bizType;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg == null ? null : failMsg.trim();
	}

	public Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public NoteBean bulidNoteBean() {
		NoteBean bean = new NoteBean();
		bean.setMsgId(this.msgId);
		bean.setHsResNo(this.resNo);
		bean.setCustName(this.custName);
		bean.setCustType(CustType.PERSON.getCode());
		/** 消息业务 1、意外保障福利生效 2、消费积分连续不足导致意外保障福利失效 3、消费者正常到期失效 4、医疗补贴福利生效 */
		// 如果是意外保障生效
		if (this.bizType == 1) {
			bean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE104.getCode()));
		}
		// 如果是消费积分连续不足导致意外保障福利失效
		if (this.bizType == 2) {
			bean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE105.getCode()));
		}
		// 如果是消费者正常到期导致意外保障福利失效
		if (this.bizType == 3) {
			bean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE119.getCode()));
		}
		// 如果是医疗补贴福利生效
		if (this.bizType == 4) {
			bean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE115.getCode()));
		}
		bean.setSender(HsPropertiesConfigurer.getProperty("system.id"));
		return bean;
	}

	public DynamicBizMsgBean bulidDynamicBizMsgBean() {
		DynamicBizMsgBean bean = new DynamicBizMsgBean();
		bean.setMsgId(this.msgId);
		bean.setHsResNo(this.resNo);
		bean.setCustName(this.custName);
		bean.setCustType(CustType.PERSON.getCode());
		bean.setMsgCode("101");//设置消费类别
		/** 消息业务 1、意外保障福利生效 2、消费积分连续不足导致意外保障福利失效 3、消费者正常到期失效 4、医疗补贴福利生效 */
		// 如果是意外保障生效
		if (this.bizType == 1) {
			bean.setMsgTitle("意外伤害保障单生效");
			bean.setSubMsgCode("1010209");
			bean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE104.getCode()));
		}
		// 如果是消费积分连续不足导致意外保障福利失效
		if (this.bizType == 2) {
			bean.setMsgTitle("撤单导致意外伤害保障单失效");
			bean.setSubMsgCode("1010210");
			bean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE105.getCode()));
		}
		// 如果是消费者正常到期导致意外保障福利失效
		if (this.bizType == 3) {
			bean.setMsgTitle("意外伤害保障单正常失效");
			bean.setSubMsgCode("101223");
			bean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE119.getCode()));
		}
		// 如果是医疗补贴福利生效
		if (this.bizType == 4) {
			bean.setMsgTitle("符合互生免费医疗补贴计划");
			bean.setSubMsgCode("1010211");
			bean.setBizType(String.valueOf(MsgBizType.MSG_BIZ_TYPE115.getCode()));
		}
		// 设置接收者
		bean.setMsgReceiver(new String[] { this.custId });
		bean.setSender(HsPropertiesConfigurer.getProperty("system.id"));
		return bean;
	}

}