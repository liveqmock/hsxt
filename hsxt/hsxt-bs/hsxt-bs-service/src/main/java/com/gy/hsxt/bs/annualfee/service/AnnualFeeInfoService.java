/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.bs.annualfee.service;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeInfoService;
import com.gy.hsxt.bs.annualfee.mapper.AnnualFeeDetailMapper;
import com.gy.hsxt.bs.annualfee.mapper.AnnualFeeInfoMapper;
import com.gy.hsxt.bs.annualfee.mapper.AnnualFeePriceMapper;
import com.gy.hsxt.bs.annualfee.utils.AnnualAreaUtils;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeDetail;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeDetailQuery;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfo;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeInfoQuery;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.common.PageContext;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 企业年费信息业务层
 *
 * @Package :com.gy.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeeInfoService
 * @Description : 企业年费信息业务层
 * @Author : chenm
 * @Date : 2015/12/10 11:45
 * @Version V3.0.0.0
 */
@Service
public class AnnualFeeInfoService implements IAnnualFeeInfoService {

    /**
     * 业务系统基础数据配置
     */
    @Resource
    private BsConfig bsConfig;

    /**
     * 年费信息Mapper接口
     */
    @Resource
    private AnnualFeeInfoMapper annualFeeInfoMapper;

    /**
     * 年费价格方案Mapper接口
     */
    @Resource
    private AnnualFeePriceMapper annualFeePriceMapper;

    /**
     * 年费计费单Mapper接口
     */
    @Resource
    private AnnualFeeDetailMapper annualFeeDetailMapper;

    /**
     * 保存实体
     *
     * @param annualFeeInfo 实体
     * @return String
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(AnnualFeeInfo annualFeeInfo) throws HsException {
        try {
            // 写入系统日志
            SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "保存企业年费信息参数：" + annualFeeInfo);

            HsAssert.notNull(annualFeeInfo, RespCode.PARAM_ERROR, "企业年费信息实体[entAnnualFeeInfo]为null");

            HsAssert.hasText(annualFeeInfo.getEntCustId(), RespCode.PARAM_ERROR, "企业客户号[entCustId]为空");
            // 检查该企业是否存在企业年费信息
            int exist = annualFeeInfoMapper.existEntAnnualFeeInfo(annualFeeInfo.getEntCustId());
            // 存在企业年费信息则抛出异常
            HsAssert.isTrue(exist == 0, BSRespCode.BS_ANNUAL_FEE_INFO_EXIST, "[" + annualFeeInfo.getEntCustId()
                    + "]客户年费信息已经存在");

            HsAssert.hasText(annualFeeInfo.getEntCustName(), RespCode.PARAM_ERROR, "企业名称[entCustName]为空");
            HsAssert.hasText(annualFeeInfo.getEntResNo(), RespCode.PARAM_ERROR, "企业互生号[entResNo]为空");
            HsAssert.hasText(annualFeeInfo.getCreatedby(), RespCode.PARAM_ERROR, "创建者[createdby]为空");

            // Calendar cal = Calendar.getInstance();
            // cal.setTime(DateUtil.getCurrentDate());// 获取系统时间
            //
            // cal.add(Calendar.YEAR, +1);// 日期加1年
            // cal.add(Calendar.DAY_OF_YEAR,-1);//减一天
            // Date endDate = cal.getTime();
            // annualFeeInfo.setEndDate(DateUtil.DateToString(endDate));//
            // 年费截止日期
            //
            // cal.add(Calendar.MONTH, -2);// 日期减2个月
            // Date warningDate = cal.getTime();
            // annualFeeInfo.setWarningDate(DateUtil.DateToString(warningDate));//
            // 年费缴费提示日期

            annualFeeInfo.setCustType(HsResNoUtils.getCustTypeByHsResNo(annualFeeInfo.getEntResNo()));

            annualFeeInfoMapper.insertAnnualFeeInfo(annualFeeInfo);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + annualFeeInfo, "创建["
                    + annualFeeInfo.getEntCustId() + "]企业年费信息成功");

            return annualFeeInfo.getEntCustId();
        } catch (HsException hse) {
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "保存企业年费信息失败,参数:" + annualFeeInfo, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_INFO_DB_ERROR, "保存企业[" + annualFeeInfo.getEntCustId()
                    + "]年费信息失败,原因:" + e.getMessage());
        }
    }

    /**
     * 更新实体
     *
     * @param annualFeeInfo 实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(AnnualFeeInfo annualFeeInfo) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBean", "修改年费信息参数[annualFeeInfo]:" + annualFeeInfo);
        // 参数校验
        HsAssert.hasText(annualFeeInfo.getEntCustId(), RespCode.PARAM_ERROR, "企业客户号[entCustId]为空");
        HsAssert.hasText(annualFeeInfo.getUpdatedby(), RespCode.PARAM_ERROR, "更新操作者[updatedBy]为空");

        try {
            return annualFeeInfoMapper.updateBean(annualFeeInfo);
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBean",
                    "修改年费信息失败,参数[annualFeeInfo]:" + annualFeeInfo, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_INFO_DB_ERROR, "修改年费信息失败，原因:" + e.getMessage());
        }
    }

    /**
     * 修改支付完成的年费信息
     *
     * @param custId  客户号
     * @param orderNo 订单号
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public String modifyBeanForPaid(String custId, String orderNo) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBeanForPaid", "修改支付完成的年费信息参数：" + custId);
        // 校验参数
        HsAssert.hasText(custId, RespCode.PARAM_ERROR, "企业客户号[custId]为空");
        try {
            AnnualFeeInfo infoInDb = annualFeeInfoMapper.selectBeanById(custId);
            String orgEndDate = infoInDb.getEndDate();
            // 更新有变更的企业年费信息
            AnnualFeeInfo annualFeeInfo = new AnnualFeeInfo();// 创建实体对象
            annualFeeInfo.setArrearAmount("0");// 全部支付完成
            annualFeeInfo.setEntCustId(custId);// 企业客户号
            annualFeeInfo.setStatusChangeDate(DateUtil.getCurrentDateTime());// 年费状态变更日期
            annualFeeInfo.setArrearYearNum(0);// 欠缴年数

            // // 年费 计费单全部已缴费
            AnnualFeeDetailQuery query = new AnnualFeeDetailQuery();
            query.setEntCustId(custId);
            query.setOrderNo(orderNo);
            AnnualFeeDetail annualFeeDetail = annualFeeDetailMapper.selectAnnualAreaForArrear(query);
            String endDate = annualFeeDetail.getEndDate();
            annualFeeInfo.setEndDate(endDate);// 截止日期

            Calendar cal = Calendar.getInstance();
            cal.setTime(DateUtil.StringToDate(endDate));// 当前时间
            cal.add(Calendar.MONTH, -2);// 日期减2月
            Date newWarningDate = cal.getTime();
            annualFeeInfo.setWarningDate(DateUtil.DateToString(newWarningDate));// 提醒日期

            annualFeeInfo.setIsArrear(0);// 是否欠年费--0：否1：是


            // 执行更新
            annualFeeInfoMapper.updateBeanForPaid(annualFeeInfo);

            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:modifyBeanForPaid", "params==>" + custId, "修改支付完成的年费信息成功");
            return orgEndDate;
        } catch (Exception e) {
            // 写入系统日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBeanForPaid", "修改支付完成的年费信息失败,参数[custId]:" + custId, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_INFO_DB_ERROR, "修改支付完成的年费信息失败,原因：" + e.getMessage());
        }
    }

    /**
     * 根据ID查询实体
     *
     * @param id key
     * @return t
     * @throws HsException
     */
    @Override
    public AnnualFeeInfo queryBeanById(String id) throws HsException {
        try {
            // 写入系统日志
            SystemLog.debug(bsConfig.getSysName(), "function:queryBeanById", "查询企业年费信息参数：" + id);
            // 校验参数
            HsAssert.hasText(id, RespCode.PARAM_ERROR, "企业客户号[custId]为空");

            AnnualFeeInfo annualFeeInfo = annualFeeInfoMapper.selectBeanById(id);

            HsAssert.notNull(annualFeeInfo, BSRespCode.BS_ANNUAL_FEE_INFO_NOT_EXIST, "企业客户号[" + id + "]对应的年费信息不存在");

            if (StringUtils.isNotEmpty(annualFeeInfo.getEndDate())) {//免费的截止日期为空
                // 设置缴费区间
                String startDate = AnnualAreaUtils.obtainAnnualAreaStartDate(annualFeeInfo);
                String endDate = AnnualAreaUtils.obtainAnnualAreaEndDate(annualFeeInfo, true);
                annualFeeInfo.setAreaStartDate(startDate);
                annualFeeInfo.setAreaEndDate(endDate);

                // 订单金额=原来的欠费金额+年费方案金额
                List<AnnualFeeDetail> details = AnnualAreaUtils.parseAnnualAreas(annualFeeInfo, true);
                if (CollectionUtils.isNotEmpty(details)) {
                    //应缴年费对应的互生币数额
                    String hsbAmount = BigDecimalUtils.ceilingMul(annualFeeInfo.getPrice(), String.valueOf(details.size())).toPlainString();
                    annualFeeInfo.setArrearAmount(hsbAmount);
                }
            }

            return annualFeeInfo;
        } catch (HsException hse) {
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", "查询企业年费信息失败，参数[custId]:" + id, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_INFO_DB_ERROR, "查询企业[" + id + "]年费信息失败,原因:" + e.getMessage());
        }
    }

    /**
     * 查询实体列表
     *
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<AnnualFeeInfo> queryListByQuery(Query query) throws HsException {
        SystemLog.debug(bsConfig.getSysName(), "function:queryListByQuery", "查询年费信息列表参数[query]：" + query);

        AnnualFeeInfoQuery annualFeeInfoQuery = null;
        if (query != null) {
            HsAssert.isInstanceOf(AnnualFeeInfoQuery.class, query, RespCode.PARAM_ERROR,
                    "查询实体[query]不是AnnualFeeInfoQuery类型");
            annualFeeInfoQuery = (AnnualFeeInfoQuery) query;
        }
        try {
            List<AnnualFeeInfo> annualFeeInfos = annualFeeInfoMapper.selectListByQuery(annualFeeInfoQuery);

            if (CollectionUtils.isNotEmpty(annualFeeInfos)) {
                for (AnnualFeeInfo annualFeeInfo : annualFeeInfos) {
                    String price = annualFeePriceMapper.selectPriceForEnabled(annualFeeInfo.getCustType());
                    annualFeeInfo.setPrice(price);
                }
            }
            return annualFeeInfos;
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", "查询年费信息列表失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_INFO_DB_ERROR, "查询年费信息列表失败,原因:" + e.getMessage());
        }
    }

    /**
     * 分页查询列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<AnnualFeeInfo> queryListForPage(Page page, Query query) throws HsException {
        SystemLog.debug(bsConfig.getSysName(), "function:queryListByQuery", "分页查询年费信息列表参数[query]：" + query);

        HsAssert.notNull(page, RespCode.PARAM_ERROR, "分页对象[page]为空");

        AnnualFeeInfoQuery annualFeeInfoQuery = null;
        if (query != null) {
            HsAssert.isInstanceOf(AnnualFeeInfoQuery.class, query, RespCode.PARAM_ERROR, "查询实体[query]不是AnnualFeeInfoQuery类型");
            annualFeeInfoQuery = (AnnualFeeInfoQuery) query;
        }
        try {
            PageContext.setPage(page);
            return annualFeeInfoMapper.selectBeanListPage(annualFeeInfoQuery);
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanById", "分页查询年费信息列表失败,参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_INFO_DB_ERROR, "分页查询年费信息列表失败,原因:" + e.getMessage());
        }
    }

    /**
     * 判断是否欠年费 0：否 1：是
     *
     * @param custId 客户号
     * @return boolean
     * @throws HsException
     */
    @Override
    public boolean isArrear(String custId) throws HsException {
        SystemLog.debug(bsConfig.getSysName(), "function:isArrear", "判断企业是否欠年费参数：" + custId);
        try {
            // 查询年费信息
            AnnualFeeInfo annualFeeInfo = annualFeeInfoMapper.selectBeanById(custId);
            // 判断年费是否存在
            HsAssert.notNull(annualFeeInfo, BSRespCode.BS_ANNUAL_FEE_INFO_NOT_EXIST, "企业客户号[" + custId + "]对应的年费信息不存在");
            return annualFeeInfo.getIsArrear() == 1;
        } catch (HsException hse) {
            SystemLog.error(bsConfig.getSysName(), "function:isArrear", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:isArrear", "判断企业[" + custId + "]是否欠年费异常", e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_INFO_DB_ERROR, "判断企业[" + custId + "]是否欠年费异常，原因:"
                    + e.getMessage());
        }
    }
}
