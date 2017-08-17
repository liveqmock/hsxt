package com.gy.hsxt.gpf.bm.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import com.gy.hsxt.gpf.bm.bean.IncNode;
import com.gy.hsxt.gpf.bm.bean.IncVo;
import com.gy.hsxt.gpf.bm.bean.Increment;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.mapper.IncNodeMapper;
import com.gy.hsxt.gpf.bm.service.IncrementService;
import com.gy.hsxt.gpf.bm.utils.IncrementUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * 增值节点关系Service实现
 *
 * @Package :com.gy.hsxt.gpf.bm.service.impl
 * @ClassName : IncrementServiceImpl
 * @Description : 增值节点关系Service实现
 * @Author : chenm
 * @Date : 2015/10/16 11:43
 * @Version V3.0.0.0
 */
@Service("incrementService")
public class IncrementServiceImpl implements IncrementService {

    /**
     * 增值节点Mapper接口
     */
    @Resource
    private IncNodeMapper incNodeMapper;

    /**
     * 增值节点初始化工具
     */
    @Resource
    private IncrementUtils incrementUtils;

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void save(String key, IncNode value) throws HsException {
        incNodeMapper.save(key, value);
    }

    /**
     * 根据key 获取value
     *
     * @param key 键
     * @return 值
     */
    @Override
    public IncNode getValueByKey(String key) throws HsException {
        return incNodeMapper.getValueByKey(key);
    }

    /**
     * 根据资源号查询增值节点图数据
     * 默认显示5级
     *
     * @param resNo 资源号
     * @return map
     */
    @Override
    public Map<String, IncNode> queryFiveLevelNodesByResNo(String resNo) throws HsException {
        Map<String, IncNode> resultMap = new HashMap<>();

        IncNode incNode = this.getIncNodeByResNoOrCustId(resNo);

        if (null != incNode) {
//            resultMap.put(resNo, incNode);
            resultMap.put(incNode.getCustId(), incNode);
            //获取当前资源号对应的层级
            Integer level = incNode.getLevel();
            //获取后四层
            for (int i = 1; i < 5; i++) {
                //根据层级查询对应的节点集合
                Map<String, IncNode> map = incNodeMapper.getRowByLevelMap((level + i) + "");
                if (MapUtils.isNotEmpty(map)) {
                    resultMap.putAll(map);
                }
            }
        }
        return resultMap;
    }

    /**
     * 根据资源号查询所有节点数据
     *
     * @param resNo 互生号
     * @return {@code List}
     * @throws HsException
     */
    @Override
    public List<IncNode> queryIncNodesByResNo(String resNo) throws HsException {
        return incNodeMapper.getRowByResNo(resNo);
    }

    /**
     * 根据key 获取最长的节点,测试用
     *
     * @param resNo 互生号
     * @return list
     */
    public List<String> queryLongNodesByResNo(String resNo, String area) throws HsException {

        List<String> list = new ArrayList<>();
        IncNode incNode = this.getIncNodeByResNoOrCustId(resNo);
        if (incNode != null) {
            list.add(incNode.getCustId());
            //左边最长节点
            if (Constants.LEFT.equals(area)) {
                if (StringUtils.isNotBlank(incNode.getLeft())) {
                    findLongEl(incNode.getLeft(), list);
                }
            }
            //右边最长节点
            else if (Constants.RIGHT.equals(area)) {
                if (StringUtils.isNotBlank(incNode.getRight())) {
                    findLongEl(incNode.getRight(), list);
                }
            }
        }
        return list;
    }

    /**
     * 根据key 获取最长的节点
     *
     * @param custId 客户号
     * @return String[]
     */
    public String[] getLongNodeByKey(String custId, String area) throws HsException {
        IncNode incNode = this.getValueByKey(custId);
        //左边最长节点
        if (Constants.LEFT.equals(area)) {
            if (StringUtils.isNotBlank(incNode.getLeft())) {
                return findLongEl(incNode.getLeft(), null);
            }
        }
        //右边最长节点
        else if (Constants.RIGHT.equals(area)) {
            //右边有，找右边的最长节点
            if (StringUtils.isNotBlank(incNode.getRight())) {
                return findLongEl(incNode.getRight(), null);
            } else {
                //左边没有，挂左边；左边有，才挂右边
                if (StringUtils.isNotBlank(incNode.getLeft())) {
                    return new String[]{custId, area};
                }
            }
        }
        //如果找不到返回当前节点的左节点
        return new String[]{custId, Constants.LEFT};
    }

    /**
     * 查询最长的节点
     *
     * @param custId 父节点的资源号
     * @throws HsException
     */
    private String[] findLongEl(String custId, List<String> list) throws HsException {

        IncNode incNode = getValueByKey(custId);
        String shortReNo = null;
        boolean left = false;
        if (incNode != null) {
            if (null != list) {
                list.add(custId);
            }

            //获取左边节点数量
            Integer lCount = incNode.getLCount();
            //获取右边节点数量
            Integer rCount = incNode.getRCount();

            if (lCount >= rCount) {//左边
                shortReNo = incNode.getLeft();
                left = true;
            } else {//右边
                shortReNo = incNode.getRight();
            }
        }
        return StringUtils.isBlank(shortReNo) ? new String[]{custId, left ? Constants.LEFT : Constants.RIGHT} : findLongEl(shortReNo, list);
    }

    /**
     * 查询最短的节点
     *
     * @param custId 父节点的资源号
     * @throws HsException
     */
    public String[] getShortNodeByKey(String custId) throws HsException {
        IncNode incNode = getValueByKey(custId);
        String shortCustId = null;
        boolean left = false;
        if (incNode != null) {
            //获取左边节点数量
            Integer lCount = incNode.getLCount();
            //获取右边节点数量
            Integer rCount = incNode.getRCount();

            if (lCount.equals(rCount) || lCount < rCount) {//左边
                shortCustId = incNode.getLeft();
                left = true;
            } else if (lCount > rCount) {//右边
                shortCustId = incNode.getRight();
            }
        }
        return StringUtils.isBlank(shortCustId) ? new String[]{custId, left ? Constants.LEFT : Constants.RIGHT} : getShortNodeByKey(shortCustId);
    }

    /**
     * 查询当前节点的父节点
     *
     * @param resNo 互生号
     * @return list
     */
    public List<String> queryParentListByResNo(String resNo) throws HsException {
        List<String> list = new ArrayList<>();

        String parent = resNo;
        while (true) {
            IncNode incNode = this.getIncNodeByResNoOrCustId(parent);

            //如果当前节点为空就返回
            if (StringUtils.isBlank(parent)||incNode==null) {
                break;
            } else {
                list.add(parent);
                //不存在父节点就跳出
                parent = incNode.getParent();
            }
        }
        return list;
    }

    /**
     * 获取增值点对象
     *
     * @param param 资源号或客户号
     * @return value
     * @throws HsException
     */
    private IncNode getIncNodeByResNoOrCustId(String param) throws HsException {
        IncNode value = null;
        if (StringUtils.isNotBlank(param)) {
            if (param.length() == 11) {
                List<IncNode> vList = incNodeMapper.getRowByResNo(param);
                if (CollectionUtils.isNotEmpty(vList)) {
                    for (IncNode v : vList) {
                        if (!StringUtils.endsWithAny(v.getCustId(), "L", "R") && Constants.INCREMENT_ISACTIVE_Y.equals(v.getIsActive())) {
                            value = v;
                            break;
                        }
                    }
                }
            } else {
                value = getValueByKey(param);
            }
        }
        return value;
    }

    /**
     * 获取增值所有value
     * return collection
     */
    public List<IncNode> queryAllRows() throws HsException {
        return incNodeMapper.getAllRows();
    }

    /**
     * 添加增值节点（用于企业申报）
     *
     * @param applyRecord 申报临时记录对象
     */
    @Override
    public boolean addNode(ApplyRecord applyRecord) throws HsException {

        //判断是否已经存在 根据客户号查询
        IncNode incNode = getValueByKey(applyRecord.getPopCustId());
        if (null != incNode) {
            if (Constants.INCREMENT_ISACTIVE_Y.equals(incNode.getIsActive())) {
                return false;
            }
        }

        String[] t;

        //跨库申报
        if (Constants.FLAG_0.equals(applyRecord.getFlag())) {
            //查找管理公司下的最短路径
            t = this.getShortNodeByKey(StringUtils.left(applyRecord.getManageCustId(), 11));
        } else {
            //查询最长路径
            t = this.getLongNodeByKey(applyRecord.getAppCustId(), applyRecord.getArea());
        }
        //添加节点
        return incrementUtils.addNode(applyRecord.getPopCustId(), applyRecord.getPopNo(), t[0], t[1], applyRecord.getType());
    }

    /**
     * 根据服务公司查询子节点为 托管企业、成员企业数据
     *
     * @param resNo 企业资源号
     * @return Increment
     * @throws HsException
     */
    public Increment getChildrenTB(String resNo) throws HsException {
        List<IncNode> vList = incNodeMapper.getRowByResNo(resNo);
//        String custId = "";
        //服务公司或企业对应的所有信息节点
        Map<String, IncVo> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(vList)) {
            for (IncNode v : vList) {
                if (Constants.INCREMENT_ISACTIVE_Y.equals(v.getIsActive())) {
//                    if (!StringUtils.endsWithAny(v.getCustId(), "L", "R")) {
//                        custId = v.getCustId();
//                    }
                    map.put(v.getCustId(), IncVo.bulid(v));//添加信息节点
                }
            }
        }
        List<String> childList = new ArrayList<>();
        //查询子节点（托管企业、成员企业）2016-04-13 停用
//        if (HsResNoUtils.isServiceResNo(resNo)) {
//            findChildNode(custId, childList,StringUtils.left(resNo,5));
//        }

        Increment increment = new Increment();
        increment.setChildList(childList);
        increment.setIncrementMap(map);

        return increment;
    }

    /**
     * 递归查询子节点（托管企业、成员企业）
     *
     * @param custId    客户号
     * @param childList 结果集
     * @param prefixNo 前缀
     */
    @SuppressWarnings("unused")
    private void findChildNode(String custId, List<String> childList,String prefixNo) throws HsException {

        if (StringUtils.isNotBlank(custId)) {
            IncNode val = getValueByKey(custId);
            if (null != val) {
                //添加节点
                if (!StringUtils.endsWithAny(val.getCustId(), "L", "R")) {
                    //只需要成员企业、托管企业数据
                    if (prefixNo.equals(StringUtils.left(val.getResNo(),5))&&(HsResNoUtils.isMemberResNo(val.getType()) || HsResNoUtils.isTrustResNo(val.getType()))) {
                        childList.add(val.getResNo());
                    }
                }
                findChildNode(val.getLeft(), childList,prefixNo);//左边递归
                findChildNode(val.getRight(), childList,prefixNo);//右边递归
            }
        }
    }

    /**
     * 通过行键删除记录
     *
     * @param rowKey 行key
     * @throws HsException
     */
    @Override
    public void removeByRowKey(String rowKey) throws HsException {
        incNodeMapper.deleteByRowKey(rowKey);
    }

    /**
     * 校验上下级关系(地区平台用)
     *
     * @param spreadResNo 推广互生号
     * @param subResNo    下级互生号
     * @return
     * @throws HsException
     */
    @Override
    public boolean checkSubRelation(String spreadResNo, String subResNo) throws HsException {
        //获取推广节点
        IncNode spread = this.getIncNodeByResNoOrCustId(spreadResNo);
        List<String> subList = new ArrayList<>();
        findSubNode(spread.getCustId(),subList,StringUtils.left(spreadResNo,2));
        return CollectionUtils.isNotEmpty(subList) && subList.contains(subResNo);
    }

    /**
     * 递归查询子节点（托管企业、成员企业）
     *
     * @param custId    客户号
     * @param subList 结果集
     * @param prefixNo 前缀
     */
    private void findSubNode(String custId, List<String> subList, String prefixNo) throws HsException {
        if (StringUtils.isNotBlank(custId)) {
            IncNode val = getValueByKey(custId);
            if (null != val) {
                //添加节点
                if (!StringUtils.endsWithAny(val.getCustId(), "L", "R")&&prefixNo.equals(StringUtils.left(val.getResNo(),2))) {
                    subList.add(val.getResNo());
                }
                findSubNode(val.getLeft(), subList, prefixNo);//左边递归
                findSubNode(val.getRight(), subList, prefixNo);//右边递归
            }
        }
    }
}

	