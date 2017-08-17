/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.constant.TransType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.common.Compute;
import com.gy.hsxt.ps.common.Constants;
import com.gy.hsxt.ps.distribution.handle.BatAllocHandle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.PointAllot;
import com.gy.hsxt.ps.distribution.bean.PointPage;
import com.gy.hsxt.ps.distribution.mapper.PointAllocMapper;
import com.gy.hsxt.ps.points.bean.PointDetail;
import org.springframework.transaction.annotation.Transactional;

/**
 * @version V3.0
 * @Package: com.gy.hsxt.ps.distribution.service
 * @ClassName: RunnableService
 * @Description: 线程池服务类
 * @author: chenhongzhi
 * @date: 2015-11-18 下午6:01:34
 */
@Service
public class RunnableService {

    /**
     * 积分分配映射器
     */
    @Autowired
    private PointAllocMapper pointAllocMapper;

    /**
     * 查询积分总数
     *
     * @return
     */
    public int queryPointSum(String runDate) throws SQLException {
        return pointAllocMapper.queryPointSum(runDate);
    }

    /**
     * 分页查询积分
     *
     * @param pp
     * @return
     */
    public List<PointDetail> queryPoint(PointPage pp) throws SQLException {
        return this.pointAllocMapper.queryPoint(pp);
    }
    /**
     * 修改原单状态
     *
     * @param transNo
     */
    public void updatePointSettle(String transNo) throws SQLException {
        this.pointAllocMapper.updatePointSettle(transNo);
    }


    /**
     * 日终积分分配线程
     *
     * @throws HsException
     */
    @Transactional
    public void batDispose(List<PointDetail> list, String runDate) throws HsException {
        try {
            List<PointAllot> pointList = new ArrayList<>();
            // 创建积分组装对象
            BatAllocHandle bat;
            // 记数
            int count = 0;
            // 循环分批插入数据库
            for (PointDetail pd : list) {
                bat = new BatAllocHandle();
                // 传入积分明细信息做分配处理
                pd.setBatchNo(runDate);
                pd.setBusinessType(Constants.BUSINESS_TYPE0);
                pd.setTransType(TransType.HSB_BUSINESS_POINT.getCode());
                bat.insertPoint(pd);
                bat.getPointAllotData();
                pointList.addAll(bat.getPointList());
                // 每分配一条记数加1
                count++;
                // 判断是否满X笔积分记录(默认1500笔)
                if (count == Constants.BAT_MAXSUM) {
                    // 每满X笔批量插入一次数据库 (默认300笔积分记录)
                    // 调用已积分分配的信息list
                   // this.batPointAllotData(pointList);
                    pointAllocMapper.insertPointAllotDaily(pointList);
                    pointAllocMapper.batchUpdateNdetail(pointList);
                    // 保存数据库后清空list
                    pointList.clear();
                    // 重新记数
                    count = 0;
                }
            }
            if (count > 0) {
                // 如果不满X笔(默认1500笔积分记录)记录直接一次性保存到数据库
                //this.batPointAllotData(pointList);
                pointAllocMapper.insertPointAllotDaily(pointList);
                pointAllocMapper.batchUpdateNdetail(pointList);
                // 保存数据库后清空list
                pointList.clear();
            }
            pointAllocMapper.updateBPointSettle(runDate);
        } catch (SQLException e) {
            // 积分汇总信息不能为空,抛出互生异常
            throw new HsException(RespCode.PS_DB_SQL_ERROR.getCode(), e.getMessage());
        } catch (Exception e) {
            if (e instanceof HsException) {
                // 积分汇总信息不能为空,抛出互生异常
                throw e;
            } else {
                // 积分汇总信息不能为空,抛出互生异常
                throw new HsException(RespCode.FAIL.getCode(), e.getMessage());
            }
        }
    }

}
