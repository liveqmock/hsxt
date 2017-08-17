/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.lcs.client;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 已开启地区平台的国家名称及对应官网和统一登录后台
 * 
 * @Package: com.gy.hsxt.lcs.client
 * @ClassName: OpenCountry
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-12-30 下午7:36:11
 * @version V1.0
 */
public class OpenCountry implements Serializable {
    private static final long serialVersionUID = -6261583471333060940L;

    /**
     * 国家数字代码
     */
    private String countryNo;

    /**
     * 国家当地名称
     */
    private String countryName;

    /**
     * 官网后台访问地址
     */
    private String officialWebBack;

    /**
     * 统一登录后台访问地址
     */
    private String ulsWebBack;

    public OpenCountry() {
    }

    public OpenCountry(String countryNo, String countryName, String officialWebBack, String ulsWebBack) {
        this.countryNo = countryNo;
        this.countryName = countryName;
        this.officialWebBack = officialWebBack;
        this.ulsWebBack = ulsWebBack;
    }

    public String getCountryNo() {
        return countryNo;
    }

    public void setCountryNo(String countryNo) {
        this.countryNo = countryNo;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getOfficialWebBack() {
        return officialWebBack;
    }

    public void setOfficialWebBack(String officialWebBack) {
        this.officialWebBack = officialWebBack;
    }

    public String getUlsWebBack() {
        return ulsWebBack;
    }

    public void setUlsWebBack(String ulsWebBack) {
        this.ulsWebBack = ulsWebBack;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
