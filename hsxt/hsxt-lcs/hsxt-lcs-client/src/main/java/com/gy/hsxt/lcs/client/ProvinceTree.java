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
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.Province;

/**
 * 省份及下级城市列表
 * 
 * @Package: com.gy.hsxt.lcs.ProvinceTree
 * @ClassName: CityTree
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-12-29 下午12:01:50
 * @version V1.0
 */
public class ProvinceTree implements Serializable {
    private static final long serialVersionUID = -6377406495076752305L;

    /**
     * 省对象
     */
    private Province province;

    /**
     * 省下级城市列表
     */
    private List<City> citys;

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public List<City> getCitys() {
        return citys;
    }

    public void setCitys(List<City> citys) {
        this.citys = citys;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
