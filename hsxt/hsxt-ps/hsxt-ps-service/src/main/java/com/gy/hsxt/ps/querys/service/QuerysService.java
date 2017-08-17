package com.gy.hsxt.ps.querys.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gy.hsxt.ps.bean.*;
import com.gy.hsxt.ps.validator.GeneralValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BigDecimalUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.ps.api.IPsQueryService;
import com.gy.hsxt.ps.common.Compute;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.common.PageContext;
import com.gy.hsxt.ps.common.PsException;
import com.gy.hsxt.ps.common.PsTools;
import com.gy.hsxt.ps.common.ValidateModelUtil;
import com.gy.hsxt.ps.querys.mapper.QuerysMapper;

/**
 * @author chenhz
 * @version v3.0
 * @updateUser guopengfei
 * @description 查询服务接口实现类
 * @createDate 2015-7-27 上午10:14:12
 * @Company 深圳市归一科技研发有限公司
 */
@Service
public class QuerysService implements IPsQueryService {

    @Autowired
    private QuerysMapper queryMapper;

    /**
     * 单笔查询
     *
     * @param querySingle
     * @throws HsException
     */
    @Override
    public QueryResult singleQuery(QuerySingle querySingle) throws HsException {
        QueryResult queryResult = null;
        if (querySingle != null) {
            queryResult = queryMapper.singleQuery(querySingle);
        } else {
            int errorCode = RespCode.PS_PARAM_ERROR.getCode();
            String message = "参数错误";
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], errorCode, message);
        }
        return queryResult;
    }

    /**
     * 查询积分明细
     *
     * @param queryDetail 查询明细实体对象信息
     * @return 查询明细信息汇总
     * @throws HsException
     */
    @Override
    public AllocDetailSum queryPointDetailSum(QueryDetail queryDetail) throws HsException {
        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(queryDetail);
        AllocDetailSum allocDetailSum = queryMapper.queryPointAllocSum(queryDetail.getResNo(),
                queryDetail.getBatchNo(), HsResNoUtils.getCustTypeByHsResNo(queryDetail.getResNo()));

        if (allocDetailSum.getSum() == null) {
            allocDetailSum.setSum(String.valueOf(BigDecimal.ZERO));
        }
        if (allocDetailSum.getBackSum() == null) {
            allocDetailSum.setBackSum(String.valueOf(BigDecimal.ZERO));
        }
        allocDetailSum.setCollectCount(Math.abs(allocDetailSum.getCount() + allocDetailSum.getBackCount()));
        /*allocDetailSum.setCollect(String.valueOf((Compute.sub(new BigDecimal(allocDetailSum.getSum()), new BigDecimal(
                allocDetailSum.getBackSum()))).abs()));*/
        allocDetailSum.setCollect(String.valueOf((Compute.sub(new BigDecimal(allocDetailSum.getSum()), new BigDecimal(
                allocDetailSum.getBackSum())))));
        String tax = queryMapper.queryAllocTaxAmount(queryDetail);
        if (StringUtils.isEmpty(tax)) {
            allocDetailSum.setTaxes(String.valueOf(BigDecimal.ZERO));
        } else {
            allocDetailSum.setTaxes(tax);
        }
        return allocDetailSum;
    }

    /**
     * 查询积分明细
     *
     * @param queryDetail 查询明细实体对象信息
     * @return 查询明细信息列表
     * @throws HsException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public PageData<AllocDetail> queryPointDetail(QueryDetail queryDetail) throws HsException {
        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(queryDetail);
        Page page = new Page(queryDetail.getNumber(), queryDetail.getCount());
        PageContext.setPage(page);
        // 查询分配明细
        List<AllocDetail> piontAlloc = queryMapper.queryPointAllocPage(queryDetail.getResNo(),
                queryDetail.getBatchNo(), HsResNoUtils.getCustTypeByHsResNo(queryDetail.getResNo()));
        return new PageData(page.getCount(), piontAlloc);
    }

    /**
     * POS单笔查询
     *
     * @param querySingle 单笔查询实体对象信息
     * @return 查询明细信息
     * @throws HsException
     */
    @Override
    public QueryResult singlePosQuery(QuerySingle querySingle) throws HsException {
        QueryResult queryResult = null;
        if (querySingle != null) { // 单笔查询POS消费积分
            queryResult = queryMapper.singlePosQuery(querySingle, null);
        } else {
            // 抛出异常
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "参数错误");
        }
        return queryResult;
    }

    /**
     * 查询积分记录
     *
     * @param queryPointRecord 查询积分记录参数
     * @return
     * @throws HsException
     * @see com.gy.hsxt.ps.api.IPsQueryService#pointRecord(com.gy.hsxt.ps.bean.QueryPointRecord)
     */
    @Override
    public List<PointRecordResult> pointRecord(QueryPointRecord queryPointRecord) throws HsException {
        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(queryPointRecord);

        List<PointRecordResult> pointRecordResult = null;

        // 分页
        Page page = new Page(queryPointRecord.getPageNumber(), queryPointRecord.getCount());
        PageContext.setPage(page);

        switch (queryPointRecord.getBusinessType()) {
            // 可撤单流水查询
            case Constants.TRANS_NO_CANCEL11:
                pointRecordResult = queryMapper.pointToCancelRecordPage(queryPointRecord);
                break;
            // 可退货流水查询
            case Constants.TRANS_NO_BACK12:
                pointRecordResult = queryMapper.pointToBackRecordPage(queryPointRecord);
                break;
            default:
                PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "对象参数错误！");
        }

        return pointRecordResult;
    }

    /**
     * 网上积分登记查询
     *
     * @param queryPointRecord
     * @return
     * @throws HsException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public PageData<PointRecordResult> pointRegisterRecord(QueryPointRecord queryPointRecord) throws HsException {
        // 另一个查询要校验这个值，这个查询给个默认值，没有啥作用，就是为了通过校验
        queryPointRecord.setBusinessType(String.valueOf(Constants.BUSINESS_TYPE0));
        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(queryPointRecord);
        // 分页
        Page page = new Page(queryPointRecord.getPageNumber(), queryPointRecord.getCount());
        PageContext.setPage(page);
        List<PointRecordResult> prrList = queryMapper.pointRegisterRecordPage(queryPointRecord);
        return new PageData(page.getCount(), prrList);
    }

    /**
     * 查询详单
     *
     * @param transNo 流水号
     * @return
     * @throws HsException
     */
    @Override
    public QueryResult queryDetailsByTransNo(String transNo) throws HsException {
        QueryResult queryResult = new QueryResult();
        // 去除左右两边的空
        String transNoNew = StringUtils.trimToEmpty(transNo);

        switch (PsTools.getBusinessNo(transNoNew)) {
            // 消费积分(积分、互生币)
            case Constants.TRANS_NO_POINT10:
                queryResult = queryMapper.queryPointDetails(transNoNew);
                break;
            // 撤单
            case Constants.TRANS_NO_CANCEL11:
                queryResult = queryMapper.queryCancelDetails(transNoNew);
                break;
            // 退货
            case Constants.TRANS_NO_BACK12:
                queryResult = queryMapper.queryBackDetails(transNoNew);
                break;
            default:
                PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "对象参数错误！");
        }
        if (PsTools.isEmpty(queryResult)) {
            // 判断为空时抛异常。
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_NO_DATA_EXIST.getCode(), "数据不存在！");
        }
        return queryResult;
    }

    /**
     * 企业收入详情
     *
     * @param proceeds 返回查询结果
     * @param proceeds 查询入参条件
     * @return
     * @throws HsException
     */
    @Override
    public ProceedsResult proceedsDetail(Proceeds proceeds) throws HsException {
        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(proceeds);

        ProceedsResult proceedsResult = new ProceedsResult();

        if (proceeds == null) {
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(),
                    "Proceeds对象为空，请不要传空对象过来！");
        }

        if (!PsTools.isEmpty(proceeds)) {
            String transNo = proceeds.getTransNo();
            if (StringUtils.isEmpty(transNo)) {
                if (StringUtils.isEmpty(proceeds.getEntCustId()) || StringUtils.isEmpty(proceeds.getEntResNo())
                        || StringUtils.isEmpty(proceeds.getTransDate())) {
                    PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(),
                            "对象参数错误！ 企业互生号、企业客户号、会计时间不能为空！");
                } else {
                    proceedsResult = queryMapper.proceedsAllDetail(proceeds);
                }
            }

            switch (PsTools.getBusinessNo(proceeds.getTransNo())) {
                // 商城收入
                case Constants.NO_HSB_ONLINE_SUM30:
                    proceedsResult = queryMapper.proceedsDetail(transNo);
                    break;
                // 线下收入
                case Constants.NO_HSB_OFFLINE_SUM31:
                    proceedsResult = queryMapper.proceedsDetail(transNo);
                    break;
                default:
                    PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "对象参数错误！");
            }
        }
        if (PsTools.isEmpty(proceedsResult)) {
            // 判断为空时抛异常。
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_NO_DATA_EXIST.getCode(), "数据不存在！");
        }
        return proceedsResult;
    }

    /**
     * 企业税收详单
     *
     * @param proceeds 返回查询结果
     * @param proceeds 查询入参条件
     * @return
     * @throws HsException
     */
    @Override
    public TaxResult queryTaxDetail(Proceeds proceeds) throws HsException {
        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(proceeds);

        TaxResult taxResult = new TaxResult();

        if (!PsTools.isEmpty(proceeds)) {
            String transNo = proceeds.getTransNo();
            if (StringUtils.isEmpty(transNo)) {
                PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "流水号不能为空！");
            }

            if (proceeds.getTradeType() == 1) {
                if (PsTools.getBusinessNo(transNo).equals(Constants.NO_POINT_TAX22)) {
                    taxResult = queryMapper.queryDayTax(transNo);
                }
            }
            if (proceeds.getTradeType() == 2) {
                if (PsTools.getBusinessNo(transNo).equals(Constants.NO_POINT_TAX22)) {
                    taxResult = queryMapper.queryCscTax(transNo);
                }
            }
        }
        if (PsTools.isEmpty(taxResult)) {
            // 判断为空时抛异常。
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_NO_DATA_EXIST.getCode(), "数据不存在！");
        }
        return taxResult;
    }

    @Override
    public QueryResult singlePosQuery(QueryPosSingle queryPosSingle) throws HsException {
        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(queryPosSingle);
        QueryResult queryResult = null;
        if (queryPosSingle != null) { // 单笔查询POS消费积分
            queryResult = queryMapper.singlePosQuery(null, queryPosSingle);
        } else {
            // 抛出异常
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "参数错误");
        }
        return queryResult;
    }

    /**
     * 查询企业收入明细(线下)
     *
     * @param queryDetail
     * @return
     * @throws HsException
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public PageData<QueryEntry> proceedsEntryList(QueryDetail queryDetail) throws HsException {
        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(queryDetail);
        // 分页
        Page page = new Page(queryDetail.getNumber(), queryDetail.getCount());
        PageContext.setPage(page);
        List<QueryEntry> prrList = queryMapper.proceedsOffLineEntryPage(queryDetail);
        return new PageData(page.getCount(), prrList);
    }

    /**
     * 查询企业收入明细(线上)
     *
     * @param queryDetail
     * @return
     * @throws HsException
     */
    @Override
    public PageData<QueryEntry> proceedsOnLineEntryList(QueryDetail queryDetail) throws HsException {
        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(queryDetail);
        // 分页
        Page page = new Page(queryDetail.getNumber(), queryDetail.getCount());
        PageContext.setPage(page);
        List<QueryEntry> prrList = queryMapper.proceedsOnlineEntryPage(queryDetail);
        return new PageData(page.getCount(), prrList);
    }

    /**
     * 定金单查询
     *
     * @param querySingle
     * @param page
     * @return
     * @throws HsException
     * @see com.gy.hsxt.ps.api.IPsQueryService#searchPosEarnest(com.gy.hsxt.ps.bean.QuerySingle,
     * com.gy.hsxt.common.bean.Page)
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public PageData<PosEarnest> searchPosEarnest(QuerySingle querySingle, Page page) throws HsException {
        if (querySingle == null) {
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "QuerySingle对象为空，请不要传空对象过来！");
        }
        if (page == null) {
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "Page对象为空，请不要传空对象过来！");
        }
        if (StringUtils.isEmpty(querySingle.getEntCustId())) {
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "企业客户ID为空！");
        }
        if (StringUtils.isEmpty(querySingle.getPerCustId())) {
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "消费者客户ID为空！");
        }
        //验证开始、结束时间日期格
        validateDateFormat(querySingle.getStartDate(), querySingle.getEndDate());
        querySingle.setTransTypes("'" + TransType.LOCAL_CARD_LOCAL_EARNEST.getCode() + "', '" + TransType.REMOTE_CARD_LOCAL_EARNEST.getCode() + "', '" + TransType.LOCAL_CARD_REMOTE_EARNEST.getCode() + "'");
        PageContext.setPage(page);
        List<PosEarnest> earnestList = queryMapper.searchPosEarnestPage(querySingle);
        return new PageData(page.getCount(), earnestList);
    }

    /**
     * 验证开始、结束时间日期格式，开始时间时分秒为00:00:00,结束时间时分秒为23:59:59
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return Map<String,String> 封装时间格式后的开始和结束时间
     * @throws HsException 异常处理
     */
    public Map<String, Timestamp> validateDateFormat(String beginDate, String endDate) throws HsException {
        Map<String, Timestamp> dateMap = new HashMap<String, Timestamp>();

        // 格式化日期并转换日期格式String-->TimeStamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // 开始时间
        if (beginDate != null && !"".equals(beginDate)) {
            try {
                Date date = sdf.parse(beginDate);
                beginDate = sdf.format(date) + " 00:00:00";
                dateMap.put("beginDate", Timestamp.valueOf(beginDate));
            } catch (ParseException e) {
                SystemLog.debug("HSXT_AC", "方法：validateDateFormat", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + beginDate + " = " + beginDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), beginDate + " = " + beginDate
                        + " ,日期格式错误，正确格式 yyyy-MM-dd");
            }
        }

        // 结束时间
        if (endDate != null && !"".equals(endDate)) {
            try {
                Date date = sdf.parse(endDate);
                endDate = sdf.format(date) + " 23:59:59";
                dateMap.put("endDate", Timestamp.valueOf(endDate));
            } catch (ParseException e) {
                SystemLog.debug("HSXT_AC", "方法：validateDateFormat", RespCode.AC_PARAMETER_FORMAT_ERROR.getCode() + ","
                        + endDate + " = " + endDate + " ,日期格式错误，正确格式 yyyy-MM-dd");
                throw new HsException(RespCode.AC_PARAMETER_FORMAT_ERROR.getCode(), endDate + " = " + endDate
                        + " ,日期格式错误，正确格式 yyyy-MM-dd");
            }
        }
        return dateMap;
    }

    /**
     * 根据企业custId查询是否有未结单的交易
     *
     * @param entCustId
     * @return
     * @throws HsException
     */
    @Override
    public boolean queryIsSettleByEntCustId(String entCustId) throws HsException {
        //查询是否有未结单的交易
        int count = queryMapper.queryIsSettleByEntCustId(entCustId);
        if (count > 0) {
            return false;
        }
        return true;
    }

    /**
     * 根据custId查询是否有未结算的预付定金的交易
     *
     * @param custId 客户ID
     * @return
     * @throws HsException
     */
    @Override
    public boolean queryPrePayByCustId(String custId) throws HsException {
        //查询是否有未结单的交易
        int count;
        try {
            count = queryMapper.queryPrePayByCustId(custId);
            if (count > 0) {
                return false;
            }
        } catch (Exception e) {
            PsException.psThrowException(new Throwable().getStackTrace()[0], RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage(), e);
        }
        return true;
    }

    /**
     * 查询消费积分分配明细
     *
     * @param queryDetail 查询明细实体对象信息
     * @return 查询明细信息汇总
     * @throws HsException
     */
    @Override
    public PageData<AllocDetailSum> queryPointDetailSumPage(QueryDetail queryDetail, Page page) throws HsException {

        // 查询对象不能为空
        if (queryDetail == null) {
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "queryDetail对象为空，请不要传空对象过来！");
        }
        // 检查互生号是否为空
        String resNo = queryDetail.getResNo();
        if (StringUtils.isBlank(resNo)) {
            PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "HsResNo:互生号为空！");
        }
        // 开始批次号的校验
        String beginBatchNo = queryDetail.getBeginBatchNo();
        if (StringUtils.isNotBlank(beginBatchNo)) {
            beginBatchNo = DateUtil.DateToString(DateUtil.StringToDate(beginBatchNo));
            if (StringUtils.isBlank(beginBatchNo)) {
                PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "开始批次格式错误，正确格式 yyyy-MM-dd");
            }
        }
        // 结束批次号的校验
        String endBatchNo = queryDetail.getEndBatchNo();
        if (StringUtils.isNotBlank(endBatchNo)) {
            endBatchNo = DateUtil.DateToString(DateUtil.StringToDate(endBatchNo));
            if (StringUtils.isBlank(endBatchNo)) {
                PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "结束批次格式错误，正确格式 yyyy-MM-dd");
            }
        }
        // 通过互生号获取改互生号所属企业类型
        int custType = HsResNoUtils.getCustTypeByHsResNo(queryDetail.getResNo());
        PageContext.setPage(page); // 分页
        PageData<AllocDetailSum> pd = null;// 返回的积分分配集合
        List<AllocDetailSum> list = queryMapper.queryPointAllocSumPage(resNo, custType, beginBatchNo, endBatchNo);
        if (!list.isEmpty()) {
            for (AllocDetailSum aloc : list) {
                // 计算获分配积分数
                aloc.setCollect(String.valueOf(BigDecimalUtils.ceilingSub(aloc.getSum(), aloc.getBackSum())));
            }
            pd = new PageData<AllocDetailSum>(page.getCount(), list);
        }
        return pd;
    }

    /**
     * 积分记录(给平板上用，正常单，退货单，撤单单)
     *
     * @return QueryPageResult 还回对象
     * @throws HsException
     */
    @Override
    public PageData<QueryPageResult> queryPointNBCPage(QueryPage queryPage) throws HsException {

        // 校验参数是否合法,不合法则抛出异常
        ValidateModelUtil.validateModel(queryPage);

        GeneralValidator.notNull(queryPage, RespCode.PARAM_ERROR, "您没有传参数");

        GeneralValidator.notNull(queryPage.getPage(), RespCode.PARAM_ERROR, "翻页page为空");

        PageContext.setPage(queryPage.getPage()); // 分页

        if (StringUtils.isBlank(queryPage.getStartDate())) {
            queryPage.setStartDate(DateUtil.DateToString(DateUtil.today()));
        }
        if (StringUtils.isBlank(queryPage.getEndDate())) {
            queryPage.setEndDate(DateUtil.DateToString(DateUtil.today()));
        }

        // 创建数组,存储积分表名、撤单表名、退货表名
        String[] T_PS_TABLE = {Constants.T_PS_NDETAIL,Constants.T_PS_BDETAIL, Constants.T_PS_CDETAIL};

        List<QueryPageResult> pointRecordResults=null;
        //查询类型（1为消费积分，2为退货，3为撤单）
        switch (queryPage.getQueryType()) {
            // 1为消费积分
            case 1:
                pointRecordResults = queryMapper.queryPointNBCPage(queryPage,T_PS_TABLE[0]);
                break;
            // 2为退货
            case 2:
                pointRecordResults = queryMapper.queryPointNBCPage(queryPage, T_PS_TABLE[1]);
                break;
            // 3为撤单
            case 3:
                pointRecordResults = queryMapper.queryPointNBCPage(queryPage, T_PS_TABLE[2]);
                break;
            default:
                PsException.psHsThrowException(new Throwable().getStackTrace()[0], RespCode.PS_PARAM_ERROR.getCode(), "查询类型（1为消费积分，2为退货，3为撤单）填写错误！");
        }
        return PageData.bulid(queryPage.getPage().getCount(), pointRecordResults);
    }


}
