package com.gy.hsi.ds.login.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.github.knightliao.apollo.db.bo.BaseObject;
import com.github.knightliao.apollo.utils.common.StringUtil;
import com.gy.hsi.ds.common.constant.UserConstant;

/**
 * @author liaoqiqi
 * @version 2013-11-26
 */
public class Visitor extends BaseObject<Long> implements Serializable {
	
    private static final long serialVersionUID = 5621993194031128338L;

    protected static final Logger LOG = LoggerFactory.getLogger(Visitor.class);

    // uc's name
    private String loginUserName;

    // role
    private int roleId;

    // app list
    private Set<Long> appIds;
    
    //env list
    private Set<Long> envIds;

    /**
     * @return the loginUserId
     */
    public Long getLoginUserId() {
        return getId();
    }

    /**
     * @param loginUserId the loginUserId to set
     */
    public void setLoginUserId(Long loginUserId) {
        setId(loginUserId);
    }

    /**
     * @return the loginUserName
     */
    public String getLoginUserName() {
        return loginUserName;
    }

    /**
     * @param loginUserName the loginUserName to set
     */
    public void setLoginUserName(String loginUserName) {
        this.loginUserName = loginUserName;
    }

    public int getRoleId() {
        return roleId;
    }

    public Set<Long> getAppIds() {
        return appIds;
    }
    
    public Set<Long> getEnvIds() {
        return envIds;
    }

    public void setAppIds(Set<Long> appIds) {
        this.appIds = appIds;
    }
    
    public void setEnvIds(Set<Long> envIds) {
        this.envIds = envIds;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    @Override
    public String toString() {
        return "Visitor [loginUserName=" + loginUserName + ", roleId=" + roleId + ", appIds=" + appIds+ ", envIds=" + envIds + "]";
    }

    public void setAppIds(String appIds) {

        if (!StringUtils.isEmpty(appIds)) {

            try {

                List<Long> ids = StringUtil.parseStringToLongList(appIds, UserConstant.USER_APP_SEP);
                setAppIds(new HashSet<Long>(ids));

            } catch (Exception e) {

                LOG.error(e.toString());
            }
        }
    }
    
    public void setEnvIds(String envIds) {

        if (!StringUtils.isEmpty(envIds)) {

            try {

                List<Long> ids = StringUtil.parseStringToLongList(envIds, UserConstant.USER_APP_SEP);
                setEnvIds(new HashSet<Long>(ids));

            } catch (Exception e) {

                LOG.error(e.toString());
            }
        }
    }
}
