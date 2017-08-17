/**
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
* <p>
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
*/
package com.gy.hsxt.bs.tool.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

/**
 * 配置单资源段Bean
 * 
 * @Package: com.gy.hsxt.bs.tool.bean
 * @ClassName: ConfigSegment
 * @Description:
 * @author: likui
 * @date: 2016/6/24 16:00
 * @company: gyist
 * @version V3.0.0
 */
public class ConfigSegment implements Serializable {

	private static final long serialVersionUID = -6573582444741196740L;

    /** 配置单编号 **/
    private String confNo;

    /** 数量 **/
    private int count;

    /** 金额 **/
    private String amount;

    public String getConfNo() {
        return confNo;
    }

    public void setConfNo(String confNo) {
        this.confNo = confNo;
    }

    public int getCount() {
        return count;
    }

	public void setCount(int count)
	{
		this.count = count;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
