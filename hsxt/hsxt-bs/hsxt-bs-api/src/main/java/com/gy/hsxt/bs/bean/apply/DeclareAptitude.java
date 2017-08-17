/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.bs.bean.apply;

import com.gy.hsxt.bs.bean.base.OptInfo;

/**
 * 申报企业资质附件信息
 *
 * @Package : com.gy.hsxt.bs.bean.apply
 * @ClassName : DeclareAptitude
 * @Description : 申报企业资质附件信息
 * @Author : xiaofl
 * @Date : 2015-9-1 上午11:29:56
 * @Version V1.0
 */
public class DeclareAptitude extends OptInfo {

    private static final long serialVersionUID = -1509614621001948896L;

    /**
     * 资质附件ID
     */
    private String aptitudeId;

    /**
     * 申请编号
     */
    private String applyId;

    /**
     * 资质类型
     */
    private Integer aptitudeType;

    /**
     * 资质名称
     */
    private String aptitudeName;

    /**
     * 资质文件编号
     */
    private String fileId;

    public String getAptitudeId() {
        return aptitudeId;
    }

    public void setAptitudeId(String aptitudeId) {
        this.aptitudeId = aptitudeId;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId;
    }

    public Integer getAptitudeType() {
        return aptitudeType;
    }

    public void setAptitudeType(Integer aptitudeType) {
        this.aptitudeType = aptitudeType;
    }

    public String getAptitudeName() {
        return aptitudeName;
    }

    public void setAptitudeName(String aptitudeName) {
        this.aptitudeName = aptitudeName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

}
