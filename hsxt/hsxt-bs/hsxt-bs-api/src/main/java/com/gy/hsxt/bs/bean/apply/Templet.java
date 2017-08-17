/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.apply;

import com.gy.hsxt.bs.bean.base.Base;

import java.util.List;

/**
 * 模版信息
 *
 * @Package : com.gy.hsxt.bs.bean.apply
 * @ClassName : Templet
 * @Description : 模板信息 ： 合同/证书模版
 * @Author : xiaofl
 * @Date : 2015-9-2 下午12:09:43
 * @Version V1.0
 */
public class Templet  extends Base {

    private static final long serialVersionUID = -1966345651141169099L;

    /**
     * 模板ID
     **/
    private String templetId;

    /**
     * 模板类型：1.合同 2.第三方业务证书 3.销售资格证书
     **/
    private Integer templetType;

    /**
     * 适合客户类型：2.成员企业 3.托管企业 4.服务公司
     **/
    private Integer custType;

    /**
     * 启用消费者资源类型:1.首段资源 2.创业资源 3.全部资源
     **/
    private Integer resType;

    /**
     * 模板名称
     **/
    private String templetName;

    /**
     * 模板内容
     **/
    private String templetContent;

    /**
     * 模版状态:0-已启用 1-待启用
     **/
    private Integer status;

    /**
     * 审批状态 0-已启用 1-待启用 2-待启用复核 3-待停用复核
     */
    private Integer apprStatus;

    /**
     * 模版图片ID
     */
    private String tempPicId;

    /**
     * 模版文件ID
     */
    private String tempFileId;

    /**
     * 审核记录列表
     */
    private List<TemplateAppr> templateApprs;

    public String getTempletId() {
        return templetId;
    }

    public void setTempletId(String templetId) {
        this.templetId = templetId;
    }

    public Integer getTempletType() {
        return templetType;
    }

    public void setTempletType(Integer templetType) {
        this.templetType = templetType;
    }

    public Integer getCustType() {
        return custType;
    }

    public void setCustType(Integer custType) {
        this.custType = custType;
    }

    public Integer getResType() {
        return resType;
    }

    public void setResType(Integer resType) {
        this.resType = resType;
    }

    public String getTempletName() {
        return templetName;
    }

    public void setTempletName(String templetName) {
        this.templetName = templetName;
    }

    public String getTempletContent() {
        return templetContent;
    }

    public void setTempletContent(String templetContent) {
        this.templetContent = templetContent;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getApprStatus() {
        return apprStatus;
    }

    public void setApprStatus(Integer apprStatus) {
        this.apprStatus = apprStatus;
    }

    public String getTempPicId() {
        return tempPicId;
    }

    public void setTempPicId(String tempPicId) {
        this.tempPicId = tempPicId;
    }

    public String getTempFileId() {
        return tempFileId;
    }

    public void setTempFileId(String tempFileId) {
        this.tempFileId = tempFileId;
    }

    public List<TemplateAppr> getTemplateApprs() {
        return templateApprs;
    }

    public void setTemplateApprs(List<TemplateAppr> templateApprs) {
        this.templateApprs = templateApprs;
    }
}
