package com.gy.hsxt.gpf.bm.bean;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 对外增值对象
 *
 * @Package :com.gy.hsxt.gpf.bm.bean
 * @ClassName : IncVo
 * @Description : Increment
 * @Author : chenm
 * @Date : 2015/10/12 19:29
 * @Version V3.0.0.0
 */
public class Increment implements Serializable {

    private static final long serialVersionUID = 1313057168456399785L;

    /**
     * 子节点 托管企业、成员企业
     */
    private List<String> childList;

    /**
     * 增值节点信息
     */
    private Map<String, IncVo> incrementMap;

    /**
     * 是否为上下级关系
     */
    private Boolean subRelation;

    public Increment() {
        super();
    }

    public Increment(List<String> childList, Map<String, IncVo> incrementMap) {
        this.childList = childList;
        this.incrementMap = incrementMap;
    }

    public List<String> getChildList() {
        return childList;
    }

    public void setChildList(List<String> childList) {
        this.childList = childList;
    }

    public Map<String, IncVo> getIncrementMap() {
        return incrementMap;
    }

    public void setIncrementMap(Map<String, IncVo> incrementMap) {
        this.incrementMap = incrementMap;
    }

    public Boolean getSubRelation() {
        return subRelation;
    }

    public void setSubRelation(Boolean subRelation) {
        this.subRelation = subRelation;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

	