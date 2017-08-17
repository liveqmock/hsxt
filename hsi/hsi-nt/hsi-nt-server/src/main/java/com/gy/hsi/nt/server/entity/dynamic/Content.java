package com.gy.hsi.nt.server.entity.dynamic;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @className:Content
 * @author:likui
 * @date:2015年7月29日
 * @desc:互动消息内容实体
 * @company:gysit
 */
public class Content implements Serializable {

	private static final long serialVersionUID = -5010593378153846731L;

	private static String MSG_TYPE = "1";

	/**
	 * 消息类型 1.系统推送消息 2.即时聊天消息 默认为1
	 */
	private String msg_type = MSG_TYPE;
	/**
	 * 消息大类 101个人消息 102商品消息 103商铺消息 201咨询消息 202 订单消息 203内部消息 500 指令消息
	 */
	private String msg_code;
	/**
	 * 子消息分类 10101 消费者个人消息--互生消息 20201订单消息--发货提醒 20202订单消息-- 退款通知 20203订单消息--
	 * 售后申拆 20204订单消息-- 违规举报（含商品违规举报） 20205订单消息-- 订单评价 20206订单消息-- 交易成功
	 * 20207订单消息-- 待商家确认订单 20208订单消息-- 订单关闭 20301内部消息-- 互生消息 20302内部消息-- 企业公告消息
	 * 50001 强制用户登出指令-- 50011 好友添加请求-- 50012 好友确认-- 50013 好友拒绝--
	 */
	private String sub_msg_code;
	/**
	 * 消息标题
	 */
	private String msg_subject;
	/**
	 * 消息文本内容
	 */
	private String msg_content;
	/**
	 * 消息标识图标URL
	 */
	private String msg_icon;
	/**
	 * 消息标识 商品消息则为 商品编号;商铺消息则为 商铺编号;订单消息则为 订单编号
	 */
	private String msg_id;
	/**
	 * 企业管理号,除消费者个人消息外，其它类型消息都需携带
	 */
	private String res_no;
	/**
	 * 消息备注
	 */
	private String msg_note;

	public Content()
	{
		super();
	}

	public String getMsg_type()
	{
		return msg_type;
	}

	public void setMsg_type(String msg_type)
	{
		this.msg_type = msg_type;
	}

	public String getMsg_code()
	{
		return msg_code;
	}

	public void setMsg_code(String msg_code)
	{
		this.msg_code = msg_code;
	}

	public String getSub_msg_code()
	{
		return sub_msg_code;
	}

	public void setSub_msg_code(String sub_msg_code)
	{
		this.sub_msg_code = sub_msg_code;
	}

	public String getMsg_subject()
	{
		return msg_subject;
	}

	public void setMsg_subject(String msg_subject)
	{
		this.msg_subject = msg_subject;
	}

	public String getMsg_content()
	{
		return msg_content;
	}

	public void setMsg_content(String msg_content)
	{
		this.msg_content = msg_content;
	}

	public String getMsg_icon()
	{
		return msg_icon;
	}

	public void setMsg_icon(String msg_icon)
	{
		this.msg_icon = msg_icon;
	}

	public String getMsg_id()
	{
		return msg_id;
	}

	public void setMsg_id(String msg_id)
	{
		this.msg_id = msg_id;
	}

	public String getRes_no()
	{
		return res_no;
	}

	public void setRes_no(String res_no)
	{
		this.res_no = res_no;
	}

	public String getMsg_note()
	{
		return msg_note;
	}

	public void setMsg_note(String msg_note)
	{
		this.msg_note = msg_note;
	}

	@Override
	public String toString()
	{
		return JSON.toJSONString(this);
	}
}
