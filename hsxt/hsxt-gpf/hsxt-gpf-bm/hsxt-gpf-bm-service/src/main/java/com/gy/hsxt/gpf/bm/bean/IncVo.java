/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.bm.bean;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;

/**
 * 增值节点传值对象
 * 增值点 == 子节点数
 * 积点数 == 1000:500 的剩余值
 *
 * @Package : com.gy.hsxt.gpf.bm.bean
 * @ClassName : IncVo
 * @Description : 增值节点传值对象
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
public class IncVo implements Serializable {

    private static final long serialVersionUID = -2733858226593504794L;
    /**
     * 资源号
     */
    private String resNo;

    /**
     * 企业客户号
     */
    private String custId;

    /**
     * 左增值节点的子节点数量（不包含自身）
     */
    private Integer lCount;

    /**
     * 右增值节点的子节点数量（不包含自身）
     */
    private Integer rCount;

    /**
     * 左增值点
     */
    private Integer lP;

    /**
     * 右增值点
     */
    private Integer rP;

    public IncVo() {
        super();
    }

    public IncVo(String resNo, String custId, Integer lP, Integer rP, Integer lCount, Integer rCount) {
        this.resNo = resNo;
        this.custId = custId;
        this.lP = lP;
        this.rP = rP;
        this.lCount = lCount;
        this.rCount = rCount;
    }

    public String getResNo() {
        return resNo;
    }

    public void setResNo(String resNo) {
        this.resNo = resNo;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public Integer getLCount() {
        return lCount;
    }

    public void setLCount(Integer lCount) {
        this.lCount = lCount;
    }

    public Integer getRCount() {
        return rCount;
    }

    public void setRCount(Integer rCount) {
        this.rCount = rCount;
    }

    public Integer getLP() {
        return lP;
    }

    public void setLP(Integer lP) {
        this.lP = lP;
    }

    public Integer getRP() {
        return rP;
    }

    public void setRP(Integer rP) {
        this.rP = rP;
    }

    /**
     * 构建传值对象
     *
     * @param incNode 增值节点
     * @return vo
     */
    public static IncVo bulid(IncNode incNode) {
        IncVo vo = new IncVo();
        try {
            BeanUtils.copyProperties(vo, incNode);
        } catch (Exception e) {
            throw new HsException(BMRespCode.BM_SYSTEM_ERROR.getCode(), "增值节点传值对象赋值错误");
        }
        return vo;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
