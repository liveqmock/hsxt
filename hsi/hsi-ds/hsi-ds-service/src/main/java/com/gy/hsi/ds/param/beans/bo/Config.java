package com.gy.hsi.ds.param.beans.bo;

import java.util.Date;

import com.gy.hsi.ds.common.constant.Columns;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Column;
import com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.annotation.Table;
import com.github.knightliao.apollo.db.bo.BaseObject;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Table(db = "", name = "T_DS_CONFIG", keyColumn = Columns.CONFIG_ID)
public class Config extends BaseObject<Long> {

    /**
     *
     */
    private static final long serialVersionUID = -2217832889126331664L;

    /**
     *
     */
    @Column(value = Columns.CONFIG_TYPE)
    private Integer configType;

    /**
     *
     */
    @Column(value = Columns.CONFIG_NAME)
    private String configName;

    /**
     *
     */
    @Column(value = Columns.CONFIG_VALUE)
    private String configValue;

    /**
     *
     */
    @Column(value = Columns.APP_ID)
    private Long appId;

    /**
     *
     */
    @Column(value = Columns.VERSION)
    private String version;

    /**
     *
     */
    @Column(value = Columns.ENV_ID)
    private Long envId;

    /**
     * 创建时间
     */
    @Column(value = Columns.CREATE_DATE)
    private Date createDate;

    /**
     * 更新时间
     */
    @Column(value = Columns.UPDATE_DATE)
    private Date updateDate;
    
    /**
     * 更新时间
     */
    @Column(value = Columns.EFFECTIVE_DATE)
    private Date effectiveDate;

    public Integer getConfigType() {
        return configType;
    }

    public void setConfigType(Integer configType) {
        this.configType = configType;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "Config [configType=" + configType + ", configName=" + configName + ", configValue=" + configValue
                + ", appId=" + appId + ", version=" + version + ", envId=" + envId + ", createDate=" + createDate
                + ", updateDate=" + updateDate + ", effectiveDate=" + effectiveDate + "]";
    }


}
