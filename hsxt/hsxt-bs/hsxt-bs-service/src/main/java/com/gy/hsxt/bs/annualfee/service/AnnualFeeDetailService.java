/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.annualfee.service;

import com.gy.hsi.lc.client.log4j.BizLog;
import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.bs.annualfee.interfaces.IAnnualFeeDetailService;
import com.gy.hsxt.bs.annualfee.mapper.AnnualFeeDetailMapper;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeDetail;
import com.gy.hsxt.bs.bean.annualfee.AnnualFeeDetailQuery;
import com.gy.hsxt.bs.bean.base.Query;
import com.gy.hsxt.bs.common.enumtype.BSRespCode;
import com.gy.hsxt.bs.common.enumtype.order.PayStatus;
import com.gy.hsxt.bs.disconf.BsConfig;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.constant.BizGroup;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.common.utils.HsResNoUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 年费计费单业务接口实现
 *
 * @Package :com.gy.hsxt.bs.annualfee.service
 * @ClassName : AnnualFeeDetailService
 * @Description : 年费计费单业务接口实现
 * @Author : chenm
 * @Date : 2015/12/10 20:19
 * @Version V3.0.0.0
 */
@Service
public class AnnualFeeDetailService implements IAnnualFeeDetailService {

    /**
     * 业务系统基础数据配置
     */
    @Resource
    private BsConfig bsConfig;

    /**
     * 年费计费单Mapper接口
     */
    @Resource
    private AnnualFeeDetailMapper annualFeeDetailMapper;

    /**
     * 年费价格方案Mapper接口
     */
    @Resource
    private AnnualFeePriceService annualFeePriceService;

    /**
     * 保存实体
     *
     * @param annualFeeDetail 实体
     * @return String
     * @throws HsException
     */
    @Override
    @Transactional
    public String saveBean(AnnualFeeDetail annualFeeDetail) throws HsException {
        try {
            // 写入系统日志
            SystemLog.debug(bsConfig.getSysName(), "function:saveBean", "保存年费计费单参数：" + annualFeeDetail);

            HsAssert.notNull(annualFeeDetail, RespCode.PARAM_ERROR, "年费计费单实体[annualFeeDetail]为null");
            HsAssert.hasText(annualFeeDetail.getOrderNo(), RespCode.PARAM_ERROR, "业务订单编号[orderNo]为空");
            HsAssert.hasText(annualFeeDetail.getEntCustId(), RespCode.PARAM_ERROR, "企业客户号[entCustId]为空");
            HsAssert.hasText(annualFeeDetail.getEntCustName(), RespCode.PARAM_ERROR, "企业名称[entCustName]为空");
            HsAssert.hasText(annualFeeDetail.getEntResNo(), RespCode.PARAM_ERROR, "企业互生号[entResNo]为空");
            HsAssert.hasText(annualFeeDetail.getStartDate(), RespCode.PARAM_ERROR, "年费区间-开始日期[startDate]为空");
            HsAssert.hasText(annualFeeDetail.getEndDate(), RespCode.PARAM_ERROR, "年费区间-结束日期[endDate]为空");
            HsAssert.hasText(annualFeeDetail.getCurrencyCode(), RespCode.PARAM_ERROR, "货币代码[currencyCode]为空");

            AnnualFeeDetail detailInDB = annualFeeDetailMapper.selectOneByAnnualArea(annualFeeDetail);
            //校验该区间的年费计费单是否存在
            HsAssert.isNull(detailInDB, BSRespCode.BS_ANNUAL_FEE_DETAIL_EXIST,
                    "[" + annualFeeDetail.getEntCustId() + "]企业在年费区间[" + annualFeeDetail.getStartDate() + "~" + annualFeeDetail.getEndDate() + "]的年费计费单已存在");
            //设置客户类型
            annualFeeDetail.setCustType(HsResNoUtils.getCustTypeByHsResNo(annualFeeDetail.getEntResNo()));
            //  获取对应企业类型已启用的年费价格方案
            String price = annualFeePriceService.queryPriceForEnabled(annualFeeDetail.getCustType());
            //校验年费方案
            HsAssert.hasText(price, BSRespCode.BS_ANNUAL_FEE_PRICE_NOT_EXIST, "[" + annualFeeDetail.getCustType() + "]类型企业没有对应的年费方案");
            annualFeeDetail.setAmount(price);// 年费方案金额
            annualFeeDetail.setPayStatus(PayStatus.WAIT_PAY.getCode());//0-未付款 1-已付款
            annualFeeDetail.setAnnualFeeNo(GuidAgent.getStringGuid(BizGroup.BS + bsConfig.getAppNode()));// 生成计费单GUID

            annualFeeDetailMapper.insertAnnualFeeDetail(annualFeeDetail);
            // 创建操作日志
            BizLog.biz(bsConfig.getSysName(), "function:saveBean", "params==>" + annualFeeDetail, "创建年费计费单成功");
            return annualFeeDetail.getAnnualFeeNo();
        } catch (HsException hse) {
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:saveBean", "保存年费计费单失败，param:" + annualFeeDetail, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_DETAIL_DB_ERROR, "保存年费计费单[" + annualFeeDetail + "]失败" + "，原因" + e.getMessage());
        }
    }

    /**
     * 更新实体
     *
     * @param annualFeeDetail 实体
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBean(AnnualFeeDetail annualFeeDetail) throws HsException {
        return 0;
    }

    /**
     * 支付完成后修改计费单
     *
     * @param orderNo 相关的业务订单号
     * @return int
     * @throws HsException
     */
    @Override
    @Transactional
    public int modifyBeanForPaid(String orderNo) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:modifyBeanForPaid", "支付完成后修改计费单的参数[orderNo]：" + orderNo);

        HsAssert.hasText(orderNo, RespCode.PARAM_ERROR, "年费业务单编号[orderNo]为空");
        try {
            int count = annualFeeDetailMapper.updateAllBeanForPaid(orderNo);

            BizLog.biz(bsConfig.getSysName(), "function:modifyBeanForPaid", "params==>" + orderNo, "支付完成后修改计费单成功，影响[" + count + "]条记录");

            return count;
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:modifyBeanForPaid", "支付完成后修改订单号[" + orderNo + "]的计费单失败", e);

            throw new HsException(BSRespCode.BS_ANNUAL_FEE_DETAIL_DB_ERROR, "支付完成后修改订单号[" + orderNo + "]的计费单失败,原因:" + e.getMessage());
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
    public AnnualFeeDetail queryBeanById(String id) throws HsException {
        return null;
    }

    /**
     * 查询实体列表
     *
     * @param query 查询实体
     * @return list
     * @throws HsException
     */
    @Override
    public List<AnnualFeeDetail> queryListByQuery(Query query) throws HsException {
        try {
            // 写入系统日志
            SystemLog.debug(bsConfig.getSysName(), "function:queryListByQuery", "查询年费计费单列表的参数[query]：" + query);

            AnnualFeeDetailQuery annualFeeDetailQuery = null;
            if (query != null) {
                HsAssert.isInstanceOf(AnnualFeeDetailQuery.class, query, RespCode.PARAM_ERROR, "所传查询参数不是[AnnualFeeDetailQuery]类型");
                annualFeeDetailQuery = ((AnnualFeeDetailQuery) query);
            }
            // 执行查询
            return annualFeeDetailMapper.selectAnnualFeeDetailList(annualFeeDetailQuery);
        } catch (HsException hse) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListByQuery", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            // 写入错误日志
            SystemLog.error(bsConfig.getSysName(), "function:queryListByQuery", "查询年费计费单列表失败，参数[query]:" + query, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_DETAIL_DB_ERROR, "查询年费计费单列表失败,原因" + e.getMessage());
        }
    }

    /**
     * 根据年费区间查询计费单
     *
     * @param annualFeeDetail 计费单实体
     * @return 实体
     * @throws HsException
     */
    @Override
    public AnnualFeeDetail queryBeanByAnnualArea(AnnualFeeDetail annualFeeDetail) throws HsException {
        try {
            // 写入系统日志
            SystemLog.debug(bsConfig.getSysName(), "function:queryBeanByAnnualArea", "根据年费区间查询计费单参数：" + annualFeeDetail);
            //参数校验
            HsAssert.notNull(annualFeeDetail, RespCode.PARAM_ERROR, "年费计费单实体[annualFeeDetail]为null");
            HsAssert.hasText(annualFeeDetail.getEntCustId(), RespCode.PARAM_ERROR, "企业客户号[entCustId]为空");
            HsAssert.hasText(annualFeeDetail.getStartDate(), RespCode.PARAM_ERROR, "年费区间-开始日期[startDate]为空");
            HsAssert.hasText(annualFeeDetail.getEndDate(), RespCode.PARAM_ERROR, "年费区间-结束日期[endDate]为空");

            return annualFeeDetailMapper.selectOneByAnnualArea(annualFeeDetail);
        } catch (HsException hse) {
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanByAnnualArea", hse.getMessage(), hse);
            throw hse;
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanByAnnualArea", "根据年费区间查询计费单失败，查询参数[annualFeeDetail]：" + annualFeeDetail, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_DETAIL_DB_ERROR, "根据年费区间查询计费单失败,原因:" + e.getMessage());
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
    public List<AnnualFeeDetail> queryListForPage(Page page, Query query) throws HsException {
        return null;
    }

    /**
     * 查询未支付年费区间
     *
     * @param annualFeeDetailQuery 查询实体
     * @return bean
     */
    @Override
    public AnnualFeeDetail queryAnnualAreaForArrear(AnnualFeeDetailQuery annualFeeDetailQuery) throws HsException {
        // 写入系统日志
        SystemLog.debug(bsConfig.getSysName(), "function:queryAnnualAreaForArrear", "查询未支付年费区间：" + annualFeeDetailQuery);
        HsAssert.notNull(annualFeeDetailQuery, BSRespCode.BS_PARAMS_NULL, "查询实体不能为null");
        try {
            return annualFeeDetailMapper.selectAnnualAreaForArrear(annualFeeDetailQuery);
        } catch (Exception e) {
            SystemLog.error(bsConfig.getSysName(), "function:queryBeanByAnnualArea", "查询未支付年费区间失败，查询参数[annualFeeDetailQuery]：" + annualFeeDetailQuery, e);
            throw new HsException(BSRespCode.BS_ANNUAL_FEE_DETAIL_DB_ERROR, "查询未支付年费区间失败,原因:" + e.getMessage());
        }
    }
}
