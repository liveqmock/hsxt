package com.gy.hsxt.gpf.bm.service;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import com.gy.hsxt.gpf.bm.bean.Increment;
import com.gy.hsxt.gpf.bm.bean.IncNode;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 增值节点关系Service接口
 *
 * @author zhucy
 * @version v0.0.1
 * @category 增值节点关系Service接口
 * @projectName apply-incurement
 * @package com.gy.apply.admin.increment.service.IncrementService.java
 * @className IncrementService
 * @description 增值节点关系Service接口
 * @createDate 2014-6-19 上午8:47:41
 * @updateUser zhucy
 * @updateDate 2014-6-19 上午8:47:41
 * @updateRemark 新增
 */
public interface IncrementService extends BaseService<IncNode> {

    /**
     * 根据资源号查询增值节点图数据
     * 默认显示5级
     *
     * @param resNo 资源号
     * @return map
     */
    Map<String, IncNode> queryFiveLevelNodesByResNo(String resNo) throws HsException;

    /**
     * 根据资源号查询所有节点数据
     *
     * @param resNo 资源号
     * @return list
     * @throws HsException
     */
    List<IncNode> queryIncNodesByResNo(String resNo) throws HsException;

    /**
     * 根据key 获取最长的节点,测试用
     *
     * @param resNo 互生号
     * @param area 区域(left || right)
     * @return list
     */
    List<String> queryLongNodesByResNo(String resNo, String area) throws HsException;

    /**
     * 查询当前节点的父节点
     *
     * @param resNo 资源号
     * @return
     */
    List<String> queryParentListByResNo(String resNo) throws HsException;

    /**
     * 获取所有的节点集合
     *
     * @return list
     */
    List<IncNode> queryAllRows() throws HsException;

    /**
     * 添加增值节点（用于企业申报）
     *
     * @param applyRecord 申报临时记录对象
     */
    boolean addNode(ApplyRecord applyRecord) throws HsException;

    /**
     * 根据服务公司查询子节点为 托管企业、成员企业数据
     *
     * @param serverNo 企业资源号
     * @return Increment
     * @throws HsException
     */
    Increment getChildrenTB(String serverNo) throws HsException;

    /**
     * 通过行键删除记录
     *
     * @param rowKey
     * @throws HsException
     */
    void removeByRowKey(String rowKey) throws HsException;

    /**
     * 校验上下级关系(地区平台用)
     *
     * @param spreadResNo 推广互生号
     * @param subResNo 下级互生号
     * @return
     * @throws HsException
     */
    boolean checkSubRelation(String spreadResNo, String subResNo)throws HsException;
}

	