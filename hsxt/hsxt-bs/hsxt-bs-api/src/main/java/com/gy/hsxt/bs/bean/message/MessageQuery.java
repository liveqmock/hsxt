/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.bean.message;

import com.gy.hsxt.bs.bean.base.Query;

/**
 * 消息查询实体
 *
 * @Package :com.gy.hsxt.bs.bean.message
 * @ClassName : MessageQuery
 * @Description : 消息查询实体
 * @Author : chenm
 * @Date : 2015/12/1 14:27
 * @Version V3.0.0.0
 */
public class MessageQuery extends Query {

    private static final long serialVersionUID = -6641406252760311386L;
    /**
     * 标题
     **/
    private String title;
    /**
     * 内容
     **/
    private String msg;
    /**
     * 消息类型
     * <p/>
     * 0公告 1通知 2 私信
     *
     * @see com.gy.hsxt.bs.common.enumtype.message.MessageType
     **/
    private Integer type;
    /**
     * 消息级别
     * <p/>
     * 1为一般，2为重要
     *
     * @see com.gy.hsxt.bs.common.enumtype.message.MessageLevel
     **/
    private Integer level;
    /**
     * 发送人
     */
    private String sender;
    /**
     * 接收者（ 可查看公告消息的人群）
     * <p/>
     * 0：全体公告（任何人可见） 1：平台全部企业 2：平台全体管理公司 3：平台全体服务公司 4：平台全体托管企业 5：平台全体成员企业
     * 6：平台全体持卡消费者 7：下级全部企业 8：下级全体服务公司 9：下级全体托管企业 10：下级全体成员企业
     * （私信可以输入：接收者输入互生号，可输入多个）
     **/
    private String receiptor;
    /**
     * 发送互生号
     **/
    private String entResNo;
    /**
     * 发送企业客户号
     **/
    private String entCustId;
    /**
     * 发送企业名称
     **/
    private String entCustName;
    /**
     * 发送时间
     **/
    private String sendTime;
    /**
     * 消息状态
     * <p/>
     * 0未发送，1已发送
     *
     * @see com.gy.hsxt.bs.common.enumtype.message.MessageStatus
     **/
    private Integer status;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiptor() {
        return receiptor;
    }

    public void setReceiptor(String receiptor) {
        this.receiptor = receiptor;
    }

    public String getEntResNo() {
        return entResNo;
    }

    public void setEntResNo(String entResNo) {
        this.entResNo = entResNo;
    }

    public String getEntCustId() {
        return entCustId;
    }

    public void setEntCustId(String entCustId) {
        this.entCustId = entCustId;
    }

    public String getEntCustName() {
        return entCustName;
    }

    public void setEntCustName(String entCustName) {
        this.entCustName = entCustName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
