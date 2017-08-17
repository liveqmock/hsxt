package com.gy.hsxt.es.distribution.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.common.utils.DateUtil;
import com.gy.hsxt.es.common.Constants;
import com.gy.hsxt.es.common.TransTypeUtil;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.bean.EntryAllot;
import com.gy.hsxt.es.distribution.bean.PointAllot;
import com.gy.hsxt.es.distribution.handle.BackAllocHandle;
import com.gy.hsxt.es.distribution.handle.CancelAllocHandle;
import com.gy.hsxt.es.distribution.handle.PointAllocHandle;
import com.gy.hsxt.es.distribution.handle.RetreatAllocHandle;
import com.gy.hsxt.es.distribution.mapper.PointAllocMapper;
import com.gy.hsxt.es.distribution.mapper.RetreatAllocMapper;
import com.gy.hsxt.es.points.bean.BackDetail;
import com.gy.hsxt.es.points.bean.CancellationDetail;
import com.gy.hsxt.es.points.bean.CorrectDetail;
import com.gy.hsxt.es.points.bean.PointDetail;

/**
 * @author chenhz
 * @version v0.0.1
 * @description 积分分配服务接口实现类
 * @createDate 2015-7-27 上午10:13:12
 * @Company 深圳市归一科技研发有限公司
 */
@Service
public class PointAllocService implements IPointAllocService {

    @Autowired
    private PointAllocMapper pointAllocMapper;
    @Autowired
    private RetreatAllocMapper retreatAllocMapper;
    /**
     * 积分
     */
    public List<Alloc> allocPoint(PointDetail pointDetail) throws HsException {
        //初始化积分对象
        PointAllocHandle pointAllocHandle = new PointAllocHandle(pointDetail);
        //通过交易类型判断（交易特征+交易状态）
        String type = TransTypeUtil.traitWay(pointDetail.getTransType());
        return pointAllocHandle.getList(type);
    }

    /**
     * 查出原单反向交易(撤单、退货、冲正)
     */
    public List<Alloc> allocBackPoint(BackDetail back, PointDetail pointDetail) throws HsException {
        String transType = back.getTransType();
        BackAllocHandle pointAllocHandle = new BackAllocHandle(back, pointDetail);

        // 获取批次号
//        String batchNo = pointDetail.getBatchNo();

        String type = TransTypeUtil.traitWay(transType);
//        if (pointDetail.getIsSettle() != Constants.IS_SETTLE0 &&batchNo.equals(DateUtil.DateToString(new Date(), DateUtil.DEFAULT_DATE_FORMAT))) {
        if (pointDetail.getIsSettle() != Constants.IS_SETTLE0)
        {
            return pointAllocHandle.getCurBackList(type);
        } else {
            return pointAllocHandle.getNotCurBackList(type);
        }
    }

    /**
     * 实时撤单
     */
    public List<Alloc> allocCancelPoint(CancellationDetail cancel, PointDetail pointDetail) throws HsException {
        String transType = cancel.getTransType();
        CancelAllocHandle pointAllocHandle = new CancelAllocHandle(cancel, pointDetail);

        String traitWay = TransTypeUtil.traitWay(transType);

        // 获取批次号
        String batchNo = pointDetail.getBatchNo();

        // 当日退货、撤单、冲正
        if (pointDetail.getIsSettle() != Constants.IS_SETTLE0 &&batchNo.equals(DateUtil.DateToString(new Date(), DateUtil.DEFAULT_DATE_FORMAT))) {
            return pointAllocHandle.getCurCancelList(traitWay);
        } else {
            return pointAllocHandle.getNotCurCancelList(traitWay);
        }

    }


    /**
     * 冲正
     */
    public List<Alloc> allocReturnPoint(CorrectDetail correct, PointDetail pointDetail) throws HsException {
        String transType = correct.getTransType();
        String transWay = TransTypeUtil.transWay(transType);
        String writeBack = TransTypeUtil.writeBack(transType);
        BackDetail back = new BackDetail();
        CancellationDetail cancel = new CancellationDetail();
        List<Alloc> list = null;
        switch (transWay) {
            case Constants.POINT_BUSS_TYPE0:
            case Constants.POINT_BUSS_TYPE3:
            case Constants.POINT_BUSS_TYPE4:
                list = this.allocPoint(pointDetail);
                break;
            case Constants.POINT_BUSS_TYPE1:
                BeanCopierUtils.copy(pointDetail, cancel);
                list = this.allocCancelPoint(cancel, pointDetail);
                break;

            case Constants.POINT_BUSS_TYPE6:
                BeanCopierUtils.copy(pointDetail, cancel);
                list = this.allocCancelPoint(cancel, pointDetail);
                break;
            case Constants.POINT_BUSS_TYPE2:
                BeanCopierUtils.copy(pointDetail, back);
                list = this.allocBackPoint(back, pointDetail);
                break;
            default:
                throw new HsException(RespCode.PS_TRANSTYPE_ERROR.getCode(), "交易类型错误");
        }
        for (Alloc alloc : list) {
            alloc.setWriteBack(writeBack);
            alloc.setTransNo(correct.getReturnNo());
            alloc.setTransType(correct.getTransType());
            alloc.setRelTransNo(pointDetail.getTransNo());
            alloc.setRelTransType(pointDetail.getTransType());
            BigDecimal addAmount = alloc.getAddAmount();
            BigDecimal subAmout = alloc.getSubAmount();
            alloc.setAddAmount(addAmount.negate());
            alloc.setSubAmount(subAmout.negate());
        }
        return list;
    }




    /**
     * 实时/日终插入分配数据(电商批结算)
     */
//    public void getAllocDetail(List<Alloc> list) {
//        this.pointAllocMapper.insertAlloc(list);
//    }



    /**
     * 保存分录数据
     *
     * @param entryAllot
     *            分录明细信息
     * @throws HsException
     */
//    public void getEntryDetail(EntryAllot entryAllot) throws HsException
//    {
//        // 插入数据到分录表
//         pointAllocMapper.insertEntry(entryAllot);
//    }

    /**
     * 修改分录数据
     *
     * @param entryAllot
     *            分录明细信息
     * @throws HsException
     */
//    public void updateEntryDetail(EntryAllot entryAllot) throws HsException
//    {
//        // 插入数据到分录表
//        pointAllocMapper.updateEntryDetail(entryAllot);
//    }

    /**
     * 修改分录数据
     *
     * @param entryAllot
     *            分录明细信息
     * @throws HsException
     */
//    public void updateEntryByEntryNo(EntryAllot entryAllot) throws HsException
//    {
//        // 插入数据到分录表
//        pointAllocMapper.updateEntryByEntryNo(entryAllot);
//    }

    /**
     * 实时保存积分分配数据
     * @param pointAllot
     */
//    public void addPointAllotData(List<PointAllot> pointAllot) {
//        this.pointAllocMapper.insertPointAllotDaily(pointAllot);
//    }
    
    /**
     * 获取原交易已分配的积分
     * @param relTransNo
     * @return 
     */
    public PointAllot getPointAllot(String relTransNo){
    	return this.pointAllocMapper.getPointAllot(relTransNo);
    }



    /**
     * 查出原单反向交易(撤单、退货、冲正)
     *
     * @param pointDetail 积分明细信息
     * @return 返回分配list
     * @throws HsException
     */
    public EntryAllot queryOnlineEntryNo(PointDetail pointDetail) throws HsException {

        return retreatAllocMapper.queryOnlineEntryNo(pointDetail.getTransNo());

    }



    /**
     * 查出原单反向交易(撤单、退货、冲正)
     *
     * @param pointDetail 积分明细信息
     * @return 返回分配list
     * @throws HsException
     */
    public EntryAllot queryOnlineOldTransNo(PointDetail pointDetail) throws HsException {

        return retreatAllocMapper.queryOnlineOldTransNo(pointDetail.getTransNo());

    }

    /**
     * 查出原单反向交易(撤单、退货、冲正)
     *
     * @param transNo 原交易流水号
     * @return 返回分配list
     * @throws HsException
     */
    public Pair<List<Alloc>,EntryAllot> canceQueryOnLineEntry(String transNo) throws HsException {

        // 创建退回处理对象
        RetreatAllocHandle retreatAllocHandle = new RetreatAllocHandle();

        EntryAllot entryAllot = retreatAllocMapper.queryOnlineEntry(transNo);

        //积分分路拆分
        List<Alloc> listDataList = RetreatAllocHandle.pointAllotSplit(entryAllot);

        // 分录list数据处理,金额反向操作
        retreatAllocHandle.retreatAlloc(listDataList);

        // 返回
        return Pair.of(listDataList,entryAllot);
    }
}
