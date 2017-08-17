package com.gy.hsxt.gpf.bm.service.impl;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.gpf.bm.bean.Bmlm;
import com.gy.hsxt.gpf.bm.bean.IncNode;
import com.gy.hsxt.gpf.bm.bean.Query;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.mapper.BmlmMapper;
import com.gy.hsxt.gpf.bm.mapper.IncNodeMapper;
import com.gy.hsxt.gpf.bm.service.BmlmService;
import com.gy.hsxt.gpf.bm.utils.PointIncUtil;
import com.gy.hsxt.gpf.fss.utils.FssDateUtil;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.GridPage;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 再增值积分业务接口实现
 *
 * @Package : com.gy.hsxt.gpf.bm.service.impl
 * @ClassName : BmlmServiceImpl
 * @Description : 再增值积分业务接口实现
 * @Author : chenm
 * @Date : 2016/1/8 9:55
 * @Version V3.0.0.0
 */
@Service("bmlmService")
public class BmlmServiceImpl implements BmlmService {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(BmlmServiceImpl.class);

    /**
     * 再增值积分Mapper接口
     */
    @Resource(name = "bmlmMapper")
    private BmlmMapper bmlmMapper;

    /**
     * 增值节点Mapper接口
     */
    @Resource(name = "incNodeMapper")
    private IncNodeMapper incNodeMapper;

    /**
     * 保存数据
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void save(String key, Bmlm value) throws HsException {
        bmlmMapper.save(key, value);
    }

    /**
     * 根据key 获取value
     *
     * @param key 键
     * @return 值
     */
    @Override
    public Bmlm getValueByKey(String key) throws HsException {
        return bmlmMapper.getValueByKey(key);
    }

    /**
     * 根据时间段查询再增值积分列表:按月统计
     *
     * @param startRowKey 起始key
     * @param endRowKey   结束key
     * @return list
     * @throws HsException
     */
    @Override
    public List<Bmlm> findByRowKeyRange(String startRowKey, String endRowKey) throws HsException {
        return bmlmMapper.findByRowKeyRange(startRowKey, endRowKey);
    }

    /**
     * 计算积分值、基数
     * 2015 7  7修改，返回插入数据库时的时间,方便后面的查询。否则重复操作后搜索出重复的数据
     *
     * @param param list
     * @throws HsException
     */
    @Override
    public Date calcBmlmPv(List<Bmlm> param) throws HsException {
        try {
            if (CollectionUtils.isNotEmpty(param)) {
                Map<String, Bmlm> bmlmMap = new HashMap<>();
                //迭代从文件中得到的对象，向上汇总积分总数
                for (Bmlm bmlm : param) {
                    //获取企业互生号
                    String resNo = StringUtils.left(bmlm.getCustId(), 11);
                    bmlm.setResNo(resNo);
                    bmlm.setType(HsResNoUtils.getCustTypeByHsResNo(resNo));//设置客户类型
                    if (HsResNoUtils.isServiceResNo(resNo)) {
                        //为服务公司设置积分参考值  申报企业数 * 10
                        bmlm.setPointREF(BigDecimalUtils.floorMul(bmlm.getPoint(), Constants.BMLM_NUMBER).toPlainString());
                    } else {
                        //托管企业/成员企业 给出积分总数的1%
                        String ref = BigDecimalUtils.floorMul(bmlm.getPoint(), Constants.BMLM_PCT).toPlainString();
                        bmlm.setPointREF(BigDecimalUtils.floor(ref, 2).toPlainString());
                    }
                    logger.debug("==========处理{}的父节点,累计分数{}===========", bmlm.getCustId(), bmlm.getPointREF());
                    recCalcParentBaseValue(bmlm, bmlm.getPointREF(), bmlmMap);
                }
                return batchSaveBmlm(bmlmMap);
            }
        } catch (Exception e) {
            logger.error("===========再增值积分统计异常==========", e);
        }
        return null;
    }

    /**
     * 保存积分后的积分参考值和基数
     *
     * @param bmlmMap map
     */
    private Date batchSaveBmlm(Map<String, Bmlm> bmlmMap) throws HsException {
        Date date = new Date();
        String dateStr = DateFormatUtils.format(date, Constants.DATE_TIME_FORMAT_SSS);
        for (Bmlm bmlm : bmlmMap.values()) {
            if (BigDecimalUtils.floor(bmlm.getPointREF()).longValue() > 0 && BigDecimalUtils.floor(bmlm.getBaseVal()).longValue() > 0) {
                //分配基数是节点下所有再增值积分和*0.01
                double percent = PointIncUtil.getPercent(bmlm.getPointREF());
                //计算再增值积分
                String mlmPoint = PointIncUtil.getBmlmOutValue(bmlm.getPointREF(), bmlm.getBaseVal());
                bmlm.setPercent(Double.toString(percent));//百分比
                bmlm.setMlmPoint(BigDecimalUtils.floor(mlmPoint, 2).toPlainString());
                if (BigDecimalUtils.floor(bmlm.getMlmPoint()).longValue() > 0 && PointIncUtil.allow(bmlm.getResNo())) {
                    IncNode incNode = incNodeMapper.getValueByKey(bmlm.getCustId());  //改成客户号
                    if (Constants.INCREMENT_ISACTIVE_Y.equals(incNode.getIsActive())) {
                        logger.info("===========再增值积分保存键值：{} =========", dateStr + bmlm.getResNo());
                        bmlm.setLastCalcTime(FssDateUtil.obtainToday(FssDateUtil.DATE_TIME_FORMAT));//设置计算时间
                        bmlmMapper.save(dateStr + bmlm.getResNo(), bmlm);
                    } else {
                        logger.info("===========再增值积分不保存无效节点：{} =========", incNode.getCustId());
                    }
                    incNode.setBaseValue(bmlm.getBaseVal());
                    incNode.setRefValue(bmlm.getPointREF());
                    incNodeMapper.save(bmlm.getCustId(), incNode);
                    logger.debug("========save value : {}=========", bmlm.toString());
                }
            }
        }
        return date;
    }

    /**
     * 根据资源号递归计算上级的积分基数  baseVal = 所有子节点的 pointREF 积分参考值 之和
     *
     * @param bmlm     当前节点
     * @param pointREF 当前资源号对应的积分参考值
     * @param bmlmMap  统计Map key:custId value:bmlm
     */
    private void recCalcParentBaseValue(Bmlm bmlm, String pointREF, Map<String, Bmlm> bmlmMap) throws HsException {
        String custId = bmlm.getCustId();
        try {
            //看map中是否存在 因为是无序，所以本节点有可能在别的递归中已经先放入MAP中
            if (bmlmMap.containsKey(custId)) {
                Bmlm bmlmIn = bmlmMap.get(custId);
                logger.debug("====1====[{}]已经在MAP,累加父节点[{}]基本分======", custId, bmlmIn.getParent());
                //如果已经存在，说明所有的上级节点在其他节点的递归中都已经创建，直接让上级节点累加积分
                bmlmIn.setPoint(bmlm.getPoint());
                bmlmIn.setPointREF(bmlm.getPointREF());
                Bmlm pa = bmlmMap.get(bmlmIn.getParent());
                if (pa != null) {
                    String base = BigDecimalUtils.floorAdd(pa.getBaseVal(), pointREF).toPlainString();
                    pa.setBaseVal(BigDecimalUtils.floor(base, 2).toPlainString());
                    pa.setCount(pa.getCount() + 1);
                    logger.debug("====1======[{}]的父节点[{}]累计后基本分数为[{}]=======", bmlm.getCustId(), bmlmIn.getParent(), pa.getBaseVal());
                    recCalcParentBaseValue(pa, pointREF, bmlmMap);
                }
            } else {
                logger.debug("====2======[{}]未加载入MAP=============", custId);
                IncNode curNode = incNodeMapper.getValueByKey(custId);
                if (null != curNode) {
                    String paKey = curNode.getParent();
                    logger.debug("====2===[{}]在MAP中不存在,但数据库查找本节点成功,父节点为[{}]========", custId, paKey);
                    if (StringUtils.isNotEmpty(paKey)) {
                        paKey = StringUtils.endsWithAny(paKey, "L","R") ? StringUtils.left(paKey, paKey.length() - 1) : paKey;
                        Bmlm paNodeVal = null;
                        if (bmlmMap.containsKey(paKey)) {
                            logger.debug("====2====父节点资源号[{}]在MAP中已经存在========", paKey);
                            paNodeVal = bmlmMap.get(paKey);
                        } else {
                            IncNode paNode = incNodeMapper.getValueByKey(paKey);
                            if (null != paNode) {
                                logger.debug("====2===父节点资源号[{}]在MAP中不存在,数据库查找父节点并设置=====", paKey);
                                paNodeVal = new Bmlm();
                                paNodeVal.setType(paNode.getType());
                                paNodeVal.setResNo(paNode.getResNo());
                                paNodeVal.setCustId(paNode.getCustId());
                                paNodeVal.setParent(paNode.getParent());
                            }
                        }
                        bmlm.setParent(paKey);
                        bmlmMap.put(custId, bmlm);
                        logger.debug("====2====MAP装入节点[{}],父节点为[{}]=========", custId, bmlm.getParent());
                        if (paNodeVal != null) {
                            String base = BigDecimalUtils.floorAdd(paNodeVal.getBaseVal(), pointREF).toPlainString();
                            paNodeVal.setBaseVal(BigDecimalUtils.floor(base, 2).toPlainString());
                            paNodeVal.setCount(paNodeVal.getCount() + 1);
                            logger.debug("====2=====[{}]的父节点[{}]累计后基本分数为[{}]===========", bmlm.getCustId(), bmlm.getParent(), paNodeVal.getBaseVal());
                            recCalcParentBaseValue(paNodeVal, pointREF, bmlmMap);
                        }
                    } else {
                        logger.debug("====2====节点[{}]已不存在父节点========", custId);
                    }
                } else {
                    logger.info("====2=====客户号[{}]在数据库中对应的增值节点不存在========", custId);
                }
            }
        } catch (Exception e) {
            logger.error("==========递归计算再增值积分的积分基数和积分参考值错误========", e);
            throw new HsException(BMRespCode.BM_SYSTEM_ERROR, "递归计算再增值积分的积分基数和积分参考值异常");
        }
    }

    /**
     * 条件查询再增值分配详情列表
     *
     * @param query 查询实体
     * @return {@code List<Bmlm>}
     * @throws HsException
     */
    @Override
    public List<Bmlm> queryBmlmList(Query query) throws HsException {
        return bmlmMapper.findBmlmListByQuery(query);
    }

    /**
     * 分页条件查询再增值分配详情列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return data
     */
    @Override
    public GridData<Bmlm> queryBmlmListPage(GridPage page, Query query) {
        return bmlmMapper.findBmlmListPage(page,query);
    }
}

	