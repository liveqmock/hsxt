package com.gy.hsxt.gpf.bm.utils;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import com.gy.hsxt.gpf.bm.bean.IncNode;
import com.gy.hsxt.gpf.bm.bean.PointValue;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.service.ApplyRecordService;
import com.gy.hsxt.gpf.bm.service.IncrementService;
import com.gy.hsxt.gpf.bm.service.PointValueService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 增值系统操作、初始化工具类
 *
 * @Package : com.gy.hsxt.gpf.bm.utils
 * @ClassName : IncrementUtils
 * @Description :  增值系统操作、初始化工具类
 * @Author : chenm
 * @Date : 2016/1/9 16:54
 * @Version V3.0.0.0
 */
@Service
public class IncrementUtils {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(IncrementUtils.class);

    /**
     * 增值节点数据库对应service
     */
    @Resource
    private IncrementService incrementService;

    /**
     * 赠送积分数据库对应service
     */
    @Resource
    private PointValueService pointValueService;

    /**
     * 申报记录临时service
     */
    @Resource
    private ApplyRecordService applyRecordService;

    /**
     * 初始化增值节点8层数据
     *
     * @throws Exception
     */
    @PostConstruct
    public void initIncrementData() throws Exception {

        //查询是否初始化
        IncNode incNode = incrementService.getValueByKey(Constants.FIRST_RESOURCE_NO);

        if (null == incNode) {
            logger.info("===========增值系统初始化数据--开始=================");

            //初始化前面七层数据
            initBeforeSeventhData();
            //初始化第八层数据
            initEighthData(incrementService.queryAllRows());

            logger.info("============增值系统初始化数据--结束===================");
        } else {
            logger.info("============增值系统数据已初始化，无需初始化===============");
        }
    }

    /**
     * 添加增值节点
     *
     * @param useCustId    拟启用客户号
     * @param useResNo     拟启用资源号
     * @param parentCustId 父节点
     * @param region       区域
     * @param type         启用类型
     * @return boolean 成功或失败
     * @throws HsException
     */
    public boolean addNode(String useCustId, String useResNo, String parentCustId, String region, Integer type) throws HsException {

        //查询父节点
        IncNode incNode = incrementService.getValueByKey(parentCustId);

        if (null != incNode) {
            //修改父节点对应的子节点
            if (Constants.LEFT.equals(region)) {
                incNode.setLeft(useCustId);
            } else if (Constants.RIGHT.equals(region)) {
                incNode.setRight(useCustId);
            }
            //更新父节点的左、右子节点
            incrementService.save(parentCustId, incNode);

            //成员企业
            if (HsResNoUtils.isMemberResNo(type)) {
                //添加成员企业节点
                this.addNode(useCustId, new IncNode(useCustId, useResNo, parentCustId, 0, 0, 0, 0, incNode.getLevel() + 1, type));
            }
            //托管企业、服务公司
            else {
                //主节点
                this.addNode(useCustId, new IncNode(useCustId, useResNo, parentCustId, useCustId + "L", useCustId + "R", 0, 0, 0, 0, incNode.getLevel() + 1, type));
                //主节点对应左节点
                this.addNode(useCustId + "L", new IncNode(useCustId + "L", useResNo, useCustId, 0, 0, 0, 0, incNode.getLevel() + 2, type));
                //主节点对应右节点
                this.addNode(useCustId + "R", new IncNode(useCustId + "R", useResNo, useCustId, 0, 0, 0, 0, incNode.getLevel() + 2, type));
            }

            //修改申报临时记录表 状态为“已处理”
            ApplyRecord applyRecord = applyRecordService.getValueByKey(useCustId);
            if (null != applyRecord) {
                applyRecord.setStatus(Constants.APPLY_TEMP_STATUS_1);
                applyRecordService.save(useCustId, applyRecord);
            }
            return true;
        }
        return false;
    }

    /**
     * 添加节点
     *
     * @param custId 客户号
     * @param value  Values 对象
     * @throws HsException
     */
    private void addNode(String custId, IncNode value) throws HsException {
        //保存增值节点数据
        value.setIsActive(Constants.INCREMENT_ISACTIVE_Y);
        incrementService.save(custId, value);

        //更新增值点、数量等信息
        updateParentIncrement(custId);
    }

    /**
     * 达到赠送积分条件 1000:500保存数据
     * 存储结构{"2015020214285234500000000001":{rNo:00000000001,pv:1000,dt:2014-05-05}}
     *
     * @param eltVal 满足条件的积分增值点
     * @param value  赠送积分值
     * @throws HsException
     */
    private boolean putPointValue(IncNode eltVal, Integer value) throws HsException {
        try {
            //如果节点有效
            if (Constants.INCREMENT_ISACTIVE_Y.equals(eltVal.getIsActive())) {
                PointValue detail = new PointValue();
                detail.setPv(value);
                if (eltVal.getCustId().endsWith("R")) {
                    detail.setRight(eltVal, value);
                    IncNode paEltVal = incrementService.getValueByKey(eltVal.getParent());
                    detail.setMain(paEltVal, 0);//设置主节点的值
                    IncNode leftEltVal = incrementService.getValueByKey(paEltVal.getLeft());
                    detail.setLeft(leftEltVal, 0);//设置左节点的值
                } else if (eltVal.getCustId().endsWith("L")) {
                    detail.setLeft(eltVal, value);
                    IncNode paEltVal = incrementService.getValueByKey(eltVal.getParent());
                    detail.setMain(paEltVal, 0);//设置主节点的值
                    IncNode rightEltVal = incrementService.getValueByKey(paEltVal.getRight());
                    detail.setRight(rightEltVal, 0);//设置右节点的值
                } else {
                    detail.setMain(eltVal, value);
                    if (CustType.SERVICE_CORP.getCode() == eltVal.getType() || CustType.TRUSTEESHIP_ENT.getCode() == eltVal.getType()) {
                        IncNode leftEltVal = incrementService.getValueByKey(eltVal.getLeft());
                        detail.setLeft(leftEltVal, 0);//设置左节点的值
                        IncNode rightEltVal = incrementService.getValueByKey(eltVal.getRight());
                        detail.setRight(rightEltVal, 0);//设置右节点的值
                    }
                }
                String dateStr = DateFormatUtils.format(new Date(), Constants.DATE_TIME_FORMAT_SSS);
                //设置分配时间
                detail.setDate(dateStr);
                logger.debug("==========point value save data:{} ============", detail);
                pointValueService.save(dateStr + detail.getResNo(), detail);
            } else {
                logger.info("===========客户号[{}]为无效节点==========", eltVal.getCustId());
            }
            return true;
        } catch (Exception e) {
            logger.error("==========point value save error=========", e);
            return false;
        }
    }

    /**
     * 插入前面七层数据
     *
     * @throws HsException
     */
    private void initBeforeSeventhData() throws HsException {
        int k = 2, a = 2, q = 1;

        //添加第一层
        addNode("00000000001", new IncNode("00000000001", "00000000001", null, "00000000002", "00000000003", 0, 0, 1, CustType.MANAGE_CORP.getCode()));

        for (int i = 1; i <= 64; i = i * 2) {
            if (i == 1) {
                continue;
            }
            logger.debug("========第[{}]层=======", k);
            for (int j = 1; j <= i; j++) {
                String value = IncrementUtils.getStr(a + "", 11);

                String left = k < 7 ? IncrementUtils.getStr(a * 2 + "", 11) : IncrementUtils.get7Str(q++);
                String right = k < 7 ? IncrementUtils.getStr((a * 2 + 1) + "", 11) : IncrementUtils.get7Str(q++);
                String parent = IncrementUtils.getStr(a / 2 + "", 11);
                IncNode v = new IncNode(value, value, parent, left, right, 0, 0, k, null);
                v.setIsActive(Constants.INCREMENT_ISACTIVE_Y);
                addNode(value, v);
                a++;
            }
            k++;
        }
    }

    /**
     * 查询上级节点包含 当前节点
     *
     * @param incNodes 增值节点 values
     * @param resNo    资源号
     * @return String
     */
    private String getParentByResNo(List<IncNode> incNodes, String resNo) {
        if (CollectionUtils.isNotEmpty(incNodes)) {
            for (IncNode inc : incNodes) {
                if (StringUtils.isNotBlank(inc.getLeft()) && inc.getLeft().equals(resNo)) {
                    return inc.getResNo();
                } else if (StringUtils.isNotBlank(inc.getRight()) && inc.getRight().equals(resNo)) {
                    return inc.getResNo();
                }
            }
        }
        return null;
    }

    /**
     * 插入管理公司号(第八层数据)
     *
     * @throws HsException
     */
    private void initEighthData(List<IncNode> incNodes) throws HsException {

        for (int i = 1; i <= 99; i++) {
            String resNo = IncrementUtils.get7Str(i);
            IncNode v = new IncNode(resNo, resNo, getParentByResNo(incNodes, resNo), 0, 0, 0, 0, 8, null);
            v.setIsActive(Constants.INCREMENT_ISACTIVE_Y);
            addNode(resNo, v);
        }
    }

    /**
     * 递归更新 公式=左边积分值+右边积分值+100 积分值、数量
     *
     * @param childCustId 当前节点客户号
     * @throws HsException
     */
    private void updateParentIncrement(String childCustId) throws HsException {

        if (PointIncUtil.allow(StringUtils.left(childCustId, 11))) {
            //查询当前节点数据
            IncNode incNode = incrementService.getValueByKey(childCustId);

            if (null != incNode) {

                //获取父节点
                String parent = incNode.getParent();

                if (StringUtils.isNotBlank(parent)) {
                    //查询父节点,更新父节点对应增值数
                    IncNode parEltVal = incrementService.getValueByKey(parent);

                    if (null != parEltVal) {
                        //左节点
                        String leftP = parEltVal.getLeft();
                        //右节点
                        String rightP = parEltVal.getRight();

                        ////////////////////////////////////计算统计数量////////////////////////////////////

                        if (null != leftP && leftP.equals(childCustId)) {
                            parEltVal.setLCount(parEltVal.getLCount() + 1);
                        } else {
                            parEltVal.setRCount(parEltVal.getRCount() + 1);
                        }

                        //计算积分值
                        if (null != leftP && leftP.equals(childCustId)) {
                            parEltVal.setLP(getValue(parEltVal.getLP()) + 100);
                        } else if (null != rightP && rightP.equals(childCustId)) {
                            parEltVal.setRP(getValue(parEltVal.getRP()) + 100);
                        }

                        /////////判断是否满足规则1000:500或者500:1000
                        Integer lVal = parEltVal.getLP();//左边积分值
                        Integer rVal = parEltVal.getRP();//右边积分值

                        if (null != lVal && null != rVal && PointIncUtil.allow(parEltVal.getResNo())) {
                            if (lVal >= 1000 && rVal >= 500) {
                                logger.info("(当前资源号：{} )满足规则----》当前节点：{} ====>左边积分值:{} -------右边积分值：{} ", childCustId, parent, lVal, rVal);
                                //达到条件赠送积分值
                                if (putPointValue(parEltVal, 1000)) {
                                    lVal -= 1000;
                                    rVal -= 500;
                                    parEltVal.setLP(lVal);
                                    parEltVal.setRP(rVal);
                                }
                            } else if (rVal >= 1000 && lVal >= 500) {
                                logger.info("(当前资源号：{} )满足规则----》当前节点：{} ====>左边积分值:{} -------右边积分值：{} ", childCustId, parent, lVal, rVal);
                                //达到条件赠送积分值
                                if (putPointValue(parEltVal, 1000)) {
                                    lVal -= 500;
                                    rVal -= 1000;
                                    parEltVal.setLP(lVal);
                                    parEltVal.setRP(rVal);
                                }
                            }
                        }

                        ////////////////////////////////////计算统计数量////////////////////////////////////

                        //修改数据
                        incrementService.save(parent, parEltVal);
                        //递归计算
                        updateParentIncrement(parent);
                    }
                }
            }
        }
    }

    private Integer getValue(Integer value) {
        return null == value ? 0 : value;
    }

    /**
     * 小于100 根据a 后面补零 000000000
     *
     * @param a 序号
     * @return String
     */
    public static String get7Str(int a) {
        return (a > 99) ? "" : getStr((a) + "", 2) + "000000000";
    }

    /**
     * 根据str 不足11位前面补0
     *
     * @param str preStr
     * @return String
     */
    public static String getStr(String str, int l) {
        int len = str.length();
        if (len < l) {
            StringBuffer sb = new StringBuffer();
            while (len++ < l) {
                sb.append("0");
            }
            return sb.append(str).toString();
        }
        return str;
    }
}

	