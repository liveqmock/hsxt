/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.ps.distribution.handle;

import java.util.ArrayList;
import java.util.List;

import com.gy.hsxt.common.utils.BeanCopierUtils;
import com.gy.hsxt.ps.distribution.bean.Alloc;
import com.gy.hsxt.ps.distribution.bean.PointAllot;
import com.gy.hsxt.ps.points.bean.PointDetail;
import org.apache.commons.lang3.StringUtils;

/**
 * @author chenhongzhi
 * @version v3.0
 * @description 组装积分
 * @updateUser chenhongzhi
 * @createDate 2015-8-13 下午5:01:39
 * @updateDate 2015-8-13 下午5:01:39
 */
public class BatAllocHandle extends AllocItem {

    // 创建存储对象
    private List<Alloc> list = new ArrayList<>();
    private List<PointAllot> pointList = new ArrayList<>();

    /**
     * 积分明细分配处理
     *
     * @param pd 积分对象明细信息
     */
    public void insert(PointDetail pd) {

        // 把积分明细信息绑定到PointItem对象属性中
        BeanCopierUtils.copy(pd, this);

        // 调用AllocItem对象的初始化方法计算积分,传入金额、积分比例、货币转换率
        this.init(pd.getEntPoint(), pd.getCurrencyRate());

        // 解析管理公司、服务公司、托管企业互生号、成员企业互生号
        this.initHsRes(this.perResNo);
    }

    /**
     * 装入消费积分
     *
     * @param pd 积分明细信息
     */
    public void insertPoint(PointDetail pd) {
        // 积分分配基本信息处理、积分计算处理
        this.insert(pd);

        // 获取装载积分
        this.addAllocPoint();
    }

    /**
     * 装入消费积分
     *
     * @param pd 积分明细信息
     */
    public void insertUpPoint(PointDetail pd) {
        // 积分分配基本信息处理、积分计算处理
        this.insert(pd);

        // 获取装载积分
        this.addAllocUpPoint();
    }

    /**
     * 装入消费积分
     *
     * @param pd 积分明细信息
     */
    public void insertSubPoint(PointDetail pd) {
        // 积分分配基本信息处理、积分计算处理
        this.insert(pd);

        // 获取装载积分
        this.subAllocPoint();
    }

    /**
     * 获取积分分配的明细信息
     *
     * @return
     */
    public List<Alloc> getAllocList() {
        return this.list;
    }

    /**
     * 获取积分分配的明细信息
     *
     * @return
     */
    public List<PointAllot> getPointList() {
        return this.pointList;
    }

    /**
     * 获取积分分配的明细信息
     *
     * @return
     */
    public void getPointAllotData() {
        pointList.add(PointHandle.pointAllotData(list, businessType));
    }

    /**
     * 获取积分分配撤单、退货的明细信息
     * @return
     */
//	public void getPointAllotBackData()
//	{
//		pointList.add(PointHandle.pointAllotData(list, businessType));
//	}

    /**
     * 增向装载积分(托管公司、服务公司、管理公司、平台、结余)
     */
    public void addAllocPoint() {
        if (StringUtils.isNotEmpty(this.trusteeResNo) && StringUtils.isNotEmpty(this.serviceResNo) && StringUtils.isNotEmpty(this.manageResNo)) {
            // 托管公司
            list.add(this.getTrusteeAddPoint());
            // 服务公司
            list.add(this.getServiceAddPoint());

            // 管理公司
            list.add(this.getManageAddPoint());

            // 平台
            list.add(this.getAllPaasAddPoint());
            // 结余
            list.add(this.getResidueAddPoint());
        } else {
            list.add(getAllNoCardPaasAddPoint());
        }
    }



    /**
     * 增向装载积分(托管公司、服务公司、管理公司、平台、结余)
     */
    public void addAllocUpPoint() {
        if (StringUtils.isNotEmpty(this.trusteeResNo) && StringUtils.isNotEmpty(this.serviceResNo) && StringUtils.isNotEmpty(this.manageResNo)) {
            // 托管公司
            list.add(this.getTrusteeUpAddPoint());
            // 服务公司
            list.add(this.getServiceUpAddPoint());
            // 管理公司
            list.add(this.getManageUpAddPoint());
            // 平台
            list.add(this.getAllPaasUpAddPoint());
            // 结余
           // list.add(this.getResidueAddPoint());
        } else {
            list.add(getAllNoCardPaasAddPoint());
        }
    }

    /**
     * 退货减向装载积分(托管公司、服务公司、管理公司、平台、结余)
     */
    public void subAllocPoint() {
        // 托管公司
        list.add(this.getTrusteeSubPoint());

        // 服务公司
        list.add(this.getServiceSubPoint());

        // 管理公司
        list.add(this.getManageSubPoint());

        // 平台
        list.add(this.getAllPaasSubPoint());

        // 结余
        list.add(this.getResidueSubPoint());
    }

}
