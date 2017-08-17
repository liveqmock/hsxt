package com.gy.hsxt.es.distribution.handle;

import java.math.BigDecimal;

import com.gy.hsxt.common.constant.AccountType;
import com.gy.hsxt.common.constant.CustType;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.guid.GuidAgent;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.es.common.Compute;
import com.gy.hsxt.es.common.Constants;
import com.gy.hsxt.es.common.EsTools;
import com.gy.hsxt.es.common.TransTypeUtil;
import com.gy.hsxt.es.distribution.bean.Alloc;
import com.gy.hsxt.es.distribution.bean.PointItem;
import com.gy.hsxt.es.distribution.bean.PointRate;

/**
 * Simple to Introduction
 *
 * @author liuchao
 * @version v0.0.1
 * @category Simple to Introduction
 * @projectName hsxt-ps-service
 * @package com.gy.hsxt.ps.distribution.service.PointAlloc.java
 * @className PointAlloc
 * @description 计算积分明细
 * @createDate 2015-8-5 下午4:41:12
 * @updateUser liuchao
 * @updateDate 2015-8-5 下午4:41:12
 * @updateRemark 说明本次修改内容
 */
public class AllocItem extends PointItem {

    /**
     * 初始化 做运算
     *
     * @param entPointSum 企业应付积分款
     * @param currencyRate 货币转换率
     * @throws HsException
     */
    public void init(BigDecimal entPointSum, BigDecimal currencyRate) {
        //企业积分预付款（保留小数点2位数 ，有余数截断）
        this.entPoint = Compute.roundFloor(entPointSum, Constants.SURPLUS_TWO);
        //积分总额（企业积分预付款/货币转换率，保留两位小数，后面的截断）
        this.pointSum = Compute.divFloor(entPoint, currencyRate, Constants.SURPLUS_TWO);
        //消费者积分（积分总额*消费者积分分配比例，保留两位小数，后面的截断）
        this.perPoint = Compute.mulFloor(pointSum, PointRate.perRate, Constants.SURPLUS_TWO);
        //待分配积分（积分总额-消费者积分）
        this.waitPoint = getSubPoint(pointSum, perPoint);
        //结余积分=待分配积分
        this.residuePoint = this.waitPoint;
        //通过交易类型判断交易方式（1、互生币 2、网银 3、现金）
        this.transWay = TransTypeUtil.transWay(transType);
        //通过交易类型判断修正标识（0、普通 1、自动冲正 2、手工冲正 3、手工补单）
        this.writeBack = TransTypeUtil.writeBack(transType);

    }

    /**
     * 初始化 做运算处理 计算企业应付积分、消费者应得积分款、积分总额、待分配积分
     *
     * @param transAmout
     * @throws HsException
     */
    public void init(BigDecimal transAmout, BigDecimal pointRate, BigDecimal currencyRate) {
        // 计算企业应付积分
        System.out.println("transAmout>>>" + transAmout);
        this.entPoint = Compute.mulCeiling(transAmout, pointRate, Constants.SURPLUS_TWO);

        // 积分总额
        this.pointSum = Compute.divFloor(entPoint, currencyRate, Constants.SURPLUS_TWO);

        // 消费者应得积分款
        this.perPoint = Compute.mulFloor(pointSum, PointRate.perRate, Constants.SURPLUS_TWO);

        // 待分配积分
        this.waitPoint = getSubPoint(pointSum, perPoint);

        // 结余积分
        this.residuePoint = this.waitPoint;

        // 解析交易类型
        this.transWay = TransTypeUtil.transWay(transType);

        // 解析冲正标识
        this.writeBack = TransTypeUtil.writeBack(transType);

    }

    /**
     * 基础调用
     *
     * @param custIdPOINT_RATEPOINT_RATEPOINT_RATEPOINT_RATEPOINT_RATEPOINT_RATE
     * @param hsResNo
     * @param accType
     * @return
     */
    private Alloc get(String custId, int custType, String hsResNo, String accType) {
        Alloc ac = new Alloc();
        ac.setAllocNo(GuidAgent.getStringGuid(Constants.NO_POINT_ALLOC20));
        ac.setAccType(accType);
        ac.setCustType(custType);
        ac.setCustId(custId);
        ac.setHsResNo(hsResNo);
        ac.setChannelType(this.channelType);
        ac.setEquipmentType(this.equipmentType);
        ac.setRelTransNo(this.transNo);
        ac.setTransType(this.transType);
        ac.setWriteBack(this.writeBack);
        ac.setRelTransType(this.relTransType);
        ac.setPerResNo(this.perResNo);
        ac.setBatchNo(this.batchNo);
        ac.setSourceBatchNo(this.sourceBatchNo);
        ac.setSourceTransNo(this.sourceTransNo);
        ac.setSourceTransDate(this.sourceTransDate);
        ac.setIsDeduction(this.isDeduction);
        return ac;
    }

    /**
     * 获取减向金额
     *
     * @param addAmout
     * @param custId
     * @param hsResNo
     * @param accType
     * @return
     */
    private Alloc get(BigDecimal subAmount, String custId, int custType, String hsResNo, String accType) {
        Alloc ac = this.get(custId, custType, hsResNo, accType);
        ac.setSubAmount(subAmount);
        return ac;
    }

    /**
     * 获取增向金额
     *
     * @param custId
     * @param hsResNo
     * @param accType
     * @param addAmout
     * @return
     */
    private Alloc get(String custId, int custType, String hsResNo, String accType, BigDecimal addAmout) {
        Alloc ac = this.get(custId, custType, hsResNo, accType);
        ac.setAddAmount(addAmout);
        // ac.setDeductionAmount(addAmout);
        return ac;
    }

    /**
     * 获取个人增向
     *
     * @param accType
     * @param amount
     * @return
     */
    private Alloc getAddPer(String accType, BigDecimal amount) {
        return this.get(this.perCustId, CustType.PERSON.getCode(), this.perResNo, accType, amount);
    }
    
    /**
     * 获取非持卡人增向
     *
     * @param accType
     * @param amount
     * @return
     */
    private Alloc getNotCardAddPer(String accType, BigDecimal amount) {
        return this.get(this.perCustId, CustType.NOT_HS_PERSON.getCode(), this.perResNo, accType, amount);
    }

    /**
     * 获取个人减向
     *
     * @param accType
     * @param amount
     * @return
     */
    private Alloc getSubPer(String accType, BigDecimal amount) {
        return this.get(amount, this.perCustId, CustType.PERSON.getCode(), this.perResNo, accType);
    }
    
    /**
     * 获取非持卡人减向
     * @param accType
     * @param amount
     * @return
     */
    private Alloc getNotCardSubPer(String accType, BigDecimal amount) {
        return this.get(amount, this.perCustId, CustType.NOT_HS_PERSON.getCode(), this.perResNo, accType);
    }

    /**
     * 获取消费者得到的积分
     *
     * @return
     */
    public Alloc getPerAddPoint() {
        return this.getAddPer(AccountType.ACC_TYPE_POINT10110.getCode(), this.perPoint);
    }
    
    /**
     * 获取消费者得到的积分
     *
     * @return
     */
    public Alloc getNotCardPerAddPoint() {
        return this.getAddPer(AccountType.ACC_TYPE_POINT10110.getCode(), BigDecimal.ZERO);
    }

    /**
     * 获取消费者减向积分
     *
     * @return
     */
    public Alloc getPerSubPoint() {
        return this.getSubPer(AccountType.ACC_TYPE_POINT10110.getCode(), this.perPoint);
    }

    /**
     * 获取扣除消费者的流通币互生币
     *
     * @return
     */
    public Alloc getPerSubHsb1() {
        return this.getSubPer(AccountType.ACC_TYPE_POINT20110.getCode(), this.transAmount);
    }
    
    /**
     * 获取扣除非持卡人的流通币互生币
     *
     * @return
     */
    public Alloc getNotCardSubHsb1(){
        return this.getNotCardSubPer(AccountType.ACC_TYPE_POINT20110.getCode(), this.transAmount);
    }

    /**
     * 获取扣除消费者的互生定向币
     *
     * @return
     */
//	public Alloc getPerSubHsb2()
//	{
//		return this.getSubPer(AccountType.ACC_TYPE_POINT20120.getCode(), this.transAmount);
//	}

    /**
     * 获取增向消费者的流通币互生币
     *
     * @return
     */
    public Alloc getPerAddHsb1() {
        return this.getAddPer(AccountType.ACC_TYPE_POINT20110.getCode(), this.transAmount);
    }
    
    /**
     * 获取增向消费者的流通币互生币
     *
     * @return
     */
    public Alloc getNotCardAddHsb1() {
        return this.getNotCardAddPer(AccountType.ACC_TYPE_POINT20110.getCode(), this.transAmount);
    }

    /**
     * 获取增向消费者的互生定向币
     *
     * @return
     */
//	public Alloc getPerAddHsb2()
//	{
//		return this.getAddPer(AccountType.ACC_TYPE_POINT20122.getCode(), this.transAmount);
//	}

    /**
     * 获取扣除预留消费者的流通生币
     *
     * @param custId
     * @param hsResNo
     * @return
     */
//	public Alloc getPerSubHsbByRes1()
//	{
//		return this.getSubPer(AccountType.ACC_TYPE_POINT20112.getCode(), this.transAmount);
//	}

    /**
     * 获取扣除预留消费者的定向币
     *
     * @param custId
     * @param hsResNo
     * @return
     */
//	public Alloc getPerSubHsbByRes2()
//	{
//		return this.getSubPer(AccountType.ACC_TYPE_POINT20122.getCode(), this.transAmount);
//	}

    /**
     * 获取增向预留消费者的流通币
     *
     * @param custId
     * @param hsResNo
     * @return
     */
//	public Alloc getPerAddHsbByRes1()
//	{
//		return this.getAddPer(AccountType.ACC_TYPE_POINT20112.getCode(), this.transAmount);
//	}

    /**
     * 获取增向预留消费者的定向币
     *
     * @param custId
     * @param hsResNo
     * @return
     */
//	public Alloc getPerAddHsbByRes2()
//	{
//		return this.getAddPer(AccountType.ACC_TYPE_POINT20122.getCode(), this.transAmount);
//	}

    /**
     * 获取企业增向
     *
     * @param accType
     * @param amount
     * @return
     */
    private Alloc getAddEnt(String accType, BigDecimal amount) {
        if (CustType.MEMBER_ENT.getCode() == HsResNoUtils.getCustTypeByHsResNo(this.entResNo)) {
            return this.get(this.entCustId, CustType.MEMBER_ENT.getCode(), this.entResNo, accType, amount);
        } else if (CustType.TRUSTEESHIP_ENT.getCode() == HsResNoUtils.getCustTypeByHsResNo(this.entResNo)) {
            return this.get(this.entCustId, CustType.TRUSTEESHIP_ENT.getCode(), this.entResNo, accType, amount);
        }
        return null;
    }

    /**
     * 获取企业减向
     *
     * @param accType 流通币账户
     * @param amount 企业积分预付款
     * @return
     */
    private Alloc getSubEnt(String accType, BigDecimal amount) {
        //判断客户类型
        //当客户类型为成员企业
        if (CustType.MEMBER_ENT.getCode() == HsResNoUtils.getCustTypeByHsResNo(this.entResNo)) {
            return this.get(amount, this.entCustId, CustType.MEMBER_ENT.getCode(), this.entResNo, accType);
        }
        //当客户类型为托管企业
        else if (CustType.TRUSTEESHIP_ENT.getCode() == HsResNoUtils.getCustTypeByHsResNo(this.entResNo)) {
            return this.get(amount, this.entCustId, CustType.TRUSTEESHIP_ENT.getCode(), this.entResNo, accType);
        }
        return null;
    }

    /**
     * 获取增加企业积分款
     *
     * @return
     */
    public Alloc getEntAddPoints() {
        return this.getAddEnt(AccountType.ACC_TYPE_POINT20110.getCode(), this.getEntPoint());
    }

    /**
     * 获取扣除企业积分款
     *
     * @return
     */
    public Alloc getEntSubPoints() {
        return this.getSubEnt(AccountType.ACC_TYPE_POINT20110.getCode(), this.getEntPoint());
    }

    /**
     * 获取增加预留企业积分款
     *
     * @return
     */
//	public Alloc getEntAddPointsByRes()
//	{
//		return this.getAddEnt(AccountType.ACC_TYPE_POINT30212.getCode(), this.getEntPoint());
//	}

    /**
     * 获取扣除预留企业积分款
     *
     * @return
     */
//	public Alloc getEntSubPointsByRes()
//	{
//		return this.getSubEnt(AccountType.ACC_TYPE_POINT30212.getCode(), this.getEntPoint());
//	}


    /**
     * 获取企业增向退税
     *
     * @return
     */
    public Alloc getEntAddPointEntTax(int custType) {
        return this.get(this.entCustId, custType, this.entResNo, AccountType.ACC_TYPE_POINT10110.getCode(), this.getEntPoint());
    }

    /**
     * 获取企业减向退税
     *
     * @return
     */
    public Alloc getEntSubPointEntTax(int custType) {
        return this.get(this.getEntPoint(), this.entCustId, custType, this.entResNo, AccountType.ACC_TYPE_POINT10510.getCode());
    }


    /**
     * 获取企业待清算增向互生币
     *
     * @return
     */
    public Alloc getEntAddHsb() {
        return this.getAddEnt(AccountType.ACC_TYPE_POINT20111.getCode(), this.getTransAmount());
    }

    /**
     * 获取企业待清算减向互生币
     *
     * @return
     */
    public Alloc getEntSubHsb() {
        return this.getSubEnt(AccountType.ACC_TYPE_POINT20111.getCode(), this.getTransAmount());
    }

    /**
     * 获取企业增向互生币
     *
     * @return
     */
    public Alloc getEntAddHsb20110() {
        return this.getAddEnt(AccountType.ACC_TYPE_POINT20110.getCode(), this.getTransAmount());
    }

    /**
     * 获取企业减向互生币
     *
     * @return
     */
    public Alloc getEntSubHsb20110() {
        return this.getSubEnt(AccountType.ACC_TYPE_POINT20110.getCode(), this.getTransAmount());
    }

    /**
     * 获取剩余減向积分
     *
     * @return
     */
    public BigDecimal getSubPoint(BigDecimal residue, BigDecimal point) {
        return Compute.sub(residue, point);
    }

    /**
     * 获取企业积分
     *
     * @return
     */
    public BigDecimal getOrgPoint(BigDecimal rate) {
        BigDecimal point = Compute.mulFloor(this.pointSum, rate, Constants.SURPLUS_TWO);
        this.residuePoint = getSubPoint(this.residuePoint, point);
        return point;
    }
    
    /**
     * 获取企业减向积分
     *
     * @return
     */
    public BigDecimal getOrgSubPoint(BigDecimal rate)
    {
        BigDecimal point = Compute.mulCeiling(this.pointSum, rate, Constants.SURPLUS_TWO);
        this.residuePoint = getSubPoint(this.residuePoint, point);
        return point;
    }

    /**
     * 获取企业多次算法
     *
     * @return
     */
    private BigDecimal getOrgTax(BigDecimal rate, BigDecimal taxRate) {
        BigDecimal point = Compute.mulFloor(rate, taxRate, Constants.SURPLUS_TWO);
        return point;
    }

    /**
     * 获取托管公司积分
     *
     * @return
     */
    public Alloc getTrusteeAddPoint() {
        BigDecimal amout = this.getOrgPoint(PointRate.trusteeRate);
        return this.get(this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(), this.trusteeResNo, AccountType.ACC_TYPE_POINT10110.getCode(), amout);
    }
    
    /**
     * 获取托管公司积分
     *
     * @return
     */
    public Alloc getTrusteeUpAddPoint()
    {
        BigDecimal amout = this.getOrgSubPoint(PointRate.trusteeRate);
        return this.getAllocAddAmount(this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(),
                this.trusteeResNo, AccountType.ACC_TYPE_POINT10110.getCode(), amout);
    }

    /**
     * 获取托管公司减向积分
     *
     * @return
     */
    public Alloc getTrusteeSubPoint() {
        BigDecimal amout = this.getOrgPoint(PointRate.trusteeRate);
        amout = amout.subtract(this.getOrgTax(amout, PointRate.trusteeTaxRate));
        return this.get(amout, this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(), this.trusteeResNo, AccountType.ACC_TYPE_POINT10110.getCode());
    }

    /**
     * 获取托管公司减向积分 无扣税
     *
     * @return
     */
    public Alloc getTrusteeSubPoint1() {
        BigDecimal amout = this.getOrgPoint(PointRate.trusteeRate);
        return this.get(amout, this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(), this.trusteeResNo, AccountType.ACC_TYPE_POINT10110.getCode());
    }

    /**
     * 获取增向托管公司待分配互生币
     *
     * @return
     */
    public Alloc getTrusteeAddHsb() {
        return this.getAddTrustee(AccountType.ACC_TYPE_POINT20111.getCode(), this.transAmount);
    }

    /**
     * 获取减向托管公司待分配互生币
     *
     * @return
     */
    public Alloc getTrusteeSubHsb() {
        return this.getSubTrustee(AccountType.ACC_TYPE_POINT20111.getCode(), this.transAmount);
    }

    /**
     * 获取托管公司互生币增向
     *
     * @param accType
     * @param amout
     * @return
     */
    private Alloc getAddTrustee(String accType, BigDecimal amout) {
        return this.get(this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(), this.trusteeResNo, accType, amout);
    }

    /**
     * 获取托管公司互生币减向
     *
     * @param accType
     * @param amout
     * @return
     */
    private Alloc getSubTrustee(String accType, BigDecimal amout) {
        return this.get(amout, this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(), this.trusteeResNo, accType);
    }

    /**
     * 获取托管公司城市税收
     *
     * @return
     */
    public Alloc getTrusteeAddTax(BigDecimal trusteeRate) {
        BigDecimal tax = this.getOrgTax(trusteeRate, PointRate.trusteeTaxRate);
        return this.get(this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(), this.trusteeResNo, AccountType.ACC_TYPE_POINT10510.getCode(), tax);
    }

    /**
     * 获取托管公司城市税收减向
     *
     * @return
     */
    public Alloc getTrusteeSubTax() {
        BigDecimal trusteeRate = Compute.mulFloor(this.pointSum, PointRate.trusteeRate, Constants.SURPLUS_TWO);
        BigDecimal tax = this.getOrgTax(trusteeRate, PointRate.trusteeTaxRate);
        return this.get(tax, this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(), this.trusteeResNo, AccountType.ACC_TYPE_POINT10510.getCode());
    }

    /**
     * 获取托管公司商业服务费减向
     *
     * @return
     */
    public Alloc getTrusteeServiceSubTax() {
        BigDecimal tax = this.getOrgTax(this.transAmount, PointRate.trusteeServiceRate);
        return this.get(tax, this.trusteeCustId, CustType.TRUSTEESHIP_ENT.getCode(), this.trusteeResNo, AccountType.ACC_TYPE_POINT20421.getCode());
    }

    /**
     * 获取服务公司获得积分
     *
     * @return
     */
    public Alloc getServiceAddPoint() {
        BigDecimal amout = this.getOrgPoint(PointRate.serviceRate);
        return this.get(this.serviceCustId, CustType.SERVICE_CORP.getCode(), this.serviceResNo, AccountType.ACC_TYPE_POINT10110.getCode(), amout);
    }
    
    /**
     * 获取服务公司获得积分
     *
     * @return
     */
    public Alloc getServiceUpAddPoint()
    {
        BigDecimal amout = this.getOrgSubPoint(PointRate.serviceRate);
        return this.getAllocAddAmount(this.serviceCustId, CustType.SERVICE_CORP.getCode(),
                this.serviceResNo, AccountType.ACC_TYPE_POINT10110.getCode(), amout);
    }

    /**
     * 获取服务公司减向积分
     *
     * @return
     */
    public Alloc getServiceSubPoint() {
        BigDecimal amout = this.getOrgPoint(PointRate.serviceRate);
        amout = amout.subtract(this.getOrgTax(amout, PointRate.serviceTaxRate));
        return this.get(amout, this.serviceCustId, CustType.SERVICE_CORP.getCode(), this.serviceResNo, AccountType.ACC_TYPE_POINT10110.getCode());
    }

    /**
     * 获取服务公司减向积分 无扣税
     *
     * @return
     */
    public Alloc getServiceSubPoint1() {
        BigDecimal amout = this.getOrgPoint(PointRate.serviceRate);
        return this.get(amout, this.serviceCustId, CustType.SERVICE_CORP.getCode(), this.serviceResNo, AccountType.ACC_TYPE_POINT10110.getCode());
    }

    /**
     * 获取服务公司城市税收
     *
     * @return
     */
    public Alloc getServiceAddTax(BigDecimal serviceRate) {
        BigDecimal tax = this.getOrgTax(serviceRate, PointRate.serviceTaxRate);
        return this.get(this.serviceCustId, CustType.SERVICE_CORP.getCode(), this.serviceResNo, AccountType.ACC_TYPE_POINT10510.getCode(), tax);
    }

    /**
     * 获取服务公司城市税收减向
     *
     * @return
     */
    public Alloc getServiceSubTax() {
        BigDecimal serviceRate = Compute.mulFloor(this.pointSum, PointRate.serviceRate, Constants.SURPLUS_TWO);
        BigDecimal tax = this.getOrgTax(serviceRate, PointRate.serviceTaxRate);
        return this.get(tax, this.serviceCustId, CustType.SERVICE_CORP.getCode(), this.serviceResNo, AccountType.ACC_TYPE_POINT10510.getCode());
    }

    /**
     * 获取服务公司城市税收
     *
     * @return
     */
    public Alloc getServiceAddHsbTax(BigDecimal serviceRate) {
        BigDecimal tax = this.getOrgTax(serviceRate, PointRate.serviceTaxRate);
        return this.get(this.serviceCustId, CustType.SERVICE_CORP.getCode(), this.serviceResNo, AccountType.ACC_TYPE_POINT20610.getCode(), tax);
    }

    /**
     * 获取服务公司城市税收减向
     *
     * @return
     */
    public Alloc getServiceSubHsbTax() {
        BigDecimal serviceRate = Compute.mulFloor(this.pointSum, PointRate.serviceRate, Constants.SURPLUS_TWO);
        BigDecimal tax = this.getOrgTax(serviceRate, PointRate.serviceTaxRate);
        return this.get(tax, this.serviceCustId, CustType.SERVICE_CORP.getCode(), this.serviceResNo, AccountType.ACC_TYPE_POINT20610.getCode());
    }

    /**
     * 获取管理公司获得积分
     *
     * @return
     */
    public Alloc getManageAddPoint() {
        BigDecimal amout = this.getOrgPoint(PointRate.manageRate);
        return this.get(this.manageCustId, CustType.MANAGE_CORP.getCode(), this.manageResNo, AccountType.ACC_TYPE_POINT10110.getCode(), amout);
    }
    
    /**
     * 获取管理公司获得积分
     *
     * @return
     */
    public Alloc getManageUpAddPoint()
    {
        BigDecimal amout = this.getOrgSubPoint(PointRate.manageRate);
        return this.getAllocAddAmount(this.manageCustId, CustType.MANAGE_CORP.getCode(),
                this.manageResNo, AccountType.ACC_TYPE_POINT10110.getCode(), amout);
    }

    /**
     * 获取管理公司减向积分
     *
     * @return
     */
    public Alloc getManageSubPoint() {
        BigDecimal amout = this.getOrgPoint(PointRate.manageRate);
        amout = amout.subtract(this.getOrgTax(amout, PointRate.manageTaxRate));
        return this.get(amout, this.manageCustId, CustType.MANAGE_CORP.getCode(), this.manageResNo, AccountType.ACC_TYPE_POINT10110.getCode());
    }

    /**
     * 获取管理公司减向积分 无扣税
     *
     * @return
     */
    public Alloc getManageSubPoint1() {
        BigDecimal amout = this.getOrgPoint(PointRate.manageRate);
        return this.get(amout, this.manageCustId, CustType.MANAGE_CORP.getCode(), this.manageResNo, AccountType.ACC_TYPE_POINT10110.getCode());
    }

    /**
     * 获取管理公司城市税收
     *
     * @return
     */
    public Alloc getManageAddTax(BigDecimal manageRate) {
        BigDecimal tax = this.getOrgTax(manageRate, PointRate.manageTaxRate);
        return this.get(this.manageCustId, CustType.MANAGE_CORP.getCode(), this.manageResNo, AccountType.ACC_TYPE_POINT10510.getCode(), tax);
    }

    /**
     * 获取管理公司城市税收减向
     *
     * @return
     */
    public Alloc getManageSubTax() {
        BigDecimal manageRate = Compute.mulFloor(this.pointSum, PointRate.manageRate, Constants.SURPLUS_TWO);
        BigDecimal tax = this.getOrgTax(manageRate, PointRate.manageTaxRate);
        return this.get(tax, this.manageCustId, CustType.MANAGE_CORP.getCode(), this.manageResNo, AccountType.ACC_TYPE_POINT10510.getCode());
    }

    /**
     * 获取平台增向
     *
     * @param accType
     * @param amout
     * @return
     */
    private Alloc getAddPaas(String accType, BigDecimal amout) {
        return this.get(this.paasCustId, CustType.AREA_PLAT.getCode(), this.paasResNo, accType, amout);
    }

    /**
     * 获取平台减向
     *
     * @param accType
     * @param amout
     * @return
     */
    private Alloc getSubPaas(String accType, BigDecimal amout) {
        return this.get(amout, this.paasCustId, CustType.AREA_PLAT.getCode(), this.paasResNo, accType);
    }

    /**
     * 获取增向平台待分配互生币
     *
     * @return
     */
//	public Alloc getPaasAddHsb()
//	{
//		return this.getAddPaas(AccountType.ACC_TYPE_POINT20111.getCode(), this.transAmount);
//	}

    /**
     * 获取减向平台待分配互生币
     *
     * @return
     */
//	public Alloc getPaasSubHsb()
//	{
//		return this.getSubPaas(AccountType.ACC_TYPE_POINT20111.getCode(), this.transAmount);
//	}

    /**
     * 获取平台分配积分
     *
     * @return
     */
    public Alloc getPaasAddPoint() {
        return this.getAddPaas(AccountType.ACC_TYPE_POINT10110.getCode(), this.waitPoint);
    }

    /**
     * 获取减向平台分配积分
     *
     * @return
     */
    public Alloc getPaasSubPoint() {
        return this.getSubPaas(AccountType.ACC_TYPE_POINT10110.getCode(), this.waitPoint);
    }

    /**
     * 获取平台沉淀积分(非持卡人积分)
     *
     * @return
     */
    public Alloc getPaasSedimentAddPoint() {
        return this.getAddPaas(AccountType.ACC_TYPE_POINT10210.getCode(), this.pointSum);
    }

    /**
     * 获取平台减向沉淀积分(非持卡人积分)
     *
     * @return
     */
    public Alloc getPaasSedimentSubPoint() {
        return this.getSubPaas(AccountType.ACC_TYPE_POINT10210.getCode(), this.pointSum);
    }

    /**
     * 获取平台积分
     *
     * @return
     */
    public Alloc getPaasAddPoints() {
        return this.getAddPaas(AccountType.ACC_TYPE_POINT20110.getCode(), this.getEntPoint());
    }

    /**
     * 平台20%后额度
     *
     * @param rate
     * @return
     */
    public BigDecimal getPaasPoint(BigDecimal rate) {
        BigDecimal point = Compute.mulFloor(this.pointSum, rate, Constants.SURPLUS_TWO);
        this.residuePoint = Compute.sub(this.residuePoint, point);
        return point;
    }

    /**
     * 获取平台总共积分
     *
     * @return
     */
    public Alloc getAllPaasPoint() {
        BigDecimal rate = this.getPaasPoint(PointRate.paasRate);
        return this.getAddPaas(AccountType.ACC_TYPE_POINT10110.getCode(), rate);
    }

    /**
     * 获取平台税收
     *
     * @return
     */
//	public Alloc getPaasAddTax()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasTaxRate);
//		return this.getAddPaas(AccountType.ACC_TYPE_POINT10310.getCode(), tax);
//	}

    /**
     * 获取平台税收减向
     *
     * @return
     */
//	public Alloc getPaasSubTax()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasTaxRate);
//		return this.getSubPaas(AccountType.ACC_TYPE_POINT10310.getCode(), tax);
//	}

    /**
     * 平台运营成本
     *
     * @return
     */
//	public Alloc getPaasAddOpex()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasOpexRate);
//		return this.getAddPaas(AccountType.ACC_TYPE_POINT10350.getCode(), tax);
//	}

    /**
     * 平台运营成本减向
     *
     * @return
     */
//	public Alloc getPaasSubOpex()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasOpexRate);
//		return this.getSubPaas(AccountType.ACC_TYPE_POINT10350.getCode(), tax);
//	}

    /**
     * 互生慈善救助基金
     *
     * @return
     */
//	public Alloc getPaasAddSalvage()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasSalvageRate);
//		return this.getAddPaas(AccountType.ACC_TYPE_POINT10360.getCode(), tax);
//	}

    /**
     * 互生慈善救助基金减向
     *
     * @return
     */
//	public Alloc getPaasSubSalvage()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasSalvageRate);
//		return this.getSubPaas(AccountType.ACC_TYPE_POINT10360.getCode(), tax);
//	}

    /**
     * 社会安全保障基金
     *
     * @return
     */
//	public Alloc getPaasAddInsurance()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasInsuranceRate);
//		return this.getAddPaas(AccountType.ACC_TYPE_POINT10330.getCode(), tax);
//	}

    /**
     * 社会安全保障基金减向
     *
     * @return
     */
//	public Alloc getPaasSubInsurance()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasInsuranceRate);
//		return this.getSubPaas(AccountType.ACC_TYPE_POINT10330.getCode(), tax);
//	}

    /**
     * 积分企业增值基金
     *
     * @return
     */
//	public Alloc getPaasAddIncrement()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasIncrementRate);
//		return this.getAddPaas(AccountType.ACC_TYPE_POINT10340.getCode(), tax);
//	}

    /**
     * 积分企业增值基金减向
     *
     * @return
     */
//	public Alloc getPaasSubIncrement()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasIncrementRate);
//		return this.getSubPaas(AccountType.ACC_TYPE_POINT10340.getCode(), tax);
//	}

    /**
     * 社会应急储备基金
     *
     * @return
     */
//	public Alloc getPaasAddReserve()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasReserveRate);
//		return this.getAddPaas(AccountType.ACC_TYPE_POINT10370.getCode(), tax);
//	}

    /**
     * 社会应急储备基金减向
     *
     * @return
     */
//	public Alloc getPaasSubReserve()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasReserveRate);
//		return this.getSubPaas(AccountType.ACC_TYPE_POINT10370.getCode(), tax);
//	}

    /**
     * 消费者意外保障
     *
     * @return
     */
//	public Alloc getPaasAddAccident()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasAccidentRate);
//		return this.getAddPaas(AccountType.ACC_TYPE_POINT10320.getCode(), tax);
//	}

    /**
     * 消费者意外保障 减向
     *
     * @return
     */
//	public Alloc getPaasSubAccident()
//	{
//		BigDecimal tax = this.getPaasPoint(PointRate.paasAccidentRate);
//		return this.getSubPaas(AccountType.ACC_TYPE_POINT10320.getCode(), tax);
//	}

    /**
     * 获取结余
     *
     * @return
     */
    public Alloc getResidueAddPoint() {
        return this.getAddPaas(AccountType.ACC_TYPE_POINT10220.getCode(), this.residuePoint);
    }

    /**
     * 获取结余减向
     *
     * @return
     */
    public Alloc getResidueSubPoint() {
        System.out.println("[this.residuePoint]" + this.residuePoint);
        return this.getSubPaas(AccountType.ACC_TYPE_POINT10220.getCode(), this.residuePoint);
    }

    /**
     * 日终税收
     *
     * @return
     */
    public static BigDecimal getTaxPoint(BigDecimal taxAmount, int taxRate) {
        BigDecimal tax = new BigDecimal(0.00);
        BigDecimal rate = new BigDecimal(0.00);
        if (taxRate == Constants.HS_MANAGE) {
            rate = PointRate.manageTaxRate;
        } else if (taxRate == Constants.HS_SERVICE) {
            rate = PointRate.serviceTaxRate;
        } else if (taxRate == Constants.HS_TRUSTEE) {
            rate = PointRate.trusteeTaxRate;
        }
        tax = Compute.mulFloor(taxAmount, rate, Constants.SURPLUS_TWO);
        return tax;
    }

    /**
     * 日终商业服务费暂存
     *
     * @param amount
     * @return
     */
    public static BigDecimal getBusinessServiceFee(BigDecimal amount) {
        return Compute.mulFloor(amount, PointRate.trusteeServiceRate, Constants.SURPLUS_TWO);
    }

    /**
     * 汇总后减去日终税收、商业服务费
     *
     * @param addAmount
     * @param deductionAmount
     * @return
     */
    public static BigDecimal getSumSubTax(BigDecimal sum, BigDecimal tax) {
        return (tax != null) ? Compute.sub(sum, tax) : sum;
    }

    /**
     * 汇总后减去日终商业服务费
     *
     * @param addAmount
     * @param aAmount
     * @return
     */
    public static BigDecimal getSumSubServiceDayFee(BigDecimal addAmount, BigDecimal subAmount, BigDecimal serviceFee) {
        BigDecimal bd = new BigDecimal(0.00);
        if (subAmount == null) {
            bd = Compute.sub(addAmount, serviceFee);
        } else if (addAmount != null && subAmount != null && serviceFee != null) {
            bd = Compute.sub(addAmount, subAmount, serviceFee);
        }
        return bd;
    }

    /**
     * 月结商业服务费的税收(服务公司)
     *
     * @param serviceFee
     * @return
     */
    public static BigDecimal cityTaxService(BigDecimal serviceFee) {
        return Compute.mulFloor(serviceFee, PointRate.serviceTaxRate, Constants.SURPLUS_SIX);
    }

    /**
     * 月终服务公司商业服务费结算
     *
     * @param sum
     * @param serviceFee
     * @return
     */
    public static BigDecimal getSumSubServiceMonthFee(BigDecimal serviceFee) {
        BigDecimal bd = new BigDecimal(0.00);
        bd = Compute.mulFloor(serviceFee, PointRate.serviceBusinessRate, Constants.SURPLUS_SIX);
        return Compute.sub(bd, cityTaxService(serviceFee));
    }

    /**
     * 月终平台商业服务费结算
     *
     * @param addAmount
     * @param aAmount
     * @return
     */
    public static BigDecimal getSumSubPaasMonthFee(BigDecimal paasFee, BigDecimal serviceFee) {
        return Compute.sub(paasFee, serviceFee);
    }
    /**
     * 获取平台总共积分
     *
     * @return
     */
    public Alloc getAllPaasAddPoint()
    {
        BigDecimal rate = this.getPaasPoint(PointRate.paasRate);
        return this.getAddPaas(AccountType.ACC_TYPE_POINT10110.getCode(), rate);
    }
    
    /**
     * 获取平台总共积分
     *
     * @return
     */
    public Alloc getAllPaasUpAddPoint()
    {
        BigDecimal rate = this.getPaasUpPoint();
        return this.getAddPaas(AccountType.ACC_TYPE_POINT10110.getCode(), rate);
    }
    
    /**
     * 获取增向金额
     *
     * @param custId
     * @param hsResNo
     * @param accType
     * @param addAmout
     * @return
     */
    private Alloc getAllocAddAmount(String custId, int custType, String hsResNo, String accType,
            BigDecimal addAmout)
    {
        // 积分分配基本信息
        Alloc ac = this.getDetail(custId, custType, hsResNo, accType);

        // 设置增向金额
        ac.setAddAmount(addAmout);
        // ac.setDeductionAmount(addAmout);
        return ac;
    }
    
    /**
     * 积分分配基本信息
     *
     * @param custId
     *            客户号
     * @param custType
     *            客户类型
     * @param hsResNo
     *            互生号
     * @param accType
     *            交易类型
     * @return 返回分配明细信息
     */
    private Alloc getDetail(String custId, int custType, String hsResNo, String accType)
    {
        // 创建积分分配信息存储对象
        Alloc ac = new Alloc();
        // 设置积分分配表流水号
        ac.setAllocNo(GuidAgent.getStringGuid(Constants.NO_POINT_ALLOC20+EsTools.getInstanceNo()));

        // 设置交易类型
        ac.setAccType(accType);

        // 设置客户类型
        ac.setCustType(custType);

        // 设置客户ID
        ac.setCustId(custId);

        // 设置互生号
        ac.setHsResNo(hsResNo);

        // 设置渠道类型
        ac.setChannelType(this.channelType);

        // 设置设备来源
        ac.setEquipmentType(this.equipmentType);

        // 设置关联流水号
        ac.setRelTransNo(this.transNo);

        // 设置交易类型
        ac.setTransType(this.transType);
        //ac.setTransType(Constants.HSB_BUSINESS_POINT);

        // 设置冲正标识
        ac.setWriteBack(this.writeBack);

        // 设置关联的交易类型
        ac.setRelTransType(this.relTransType);

        // 设置消费者互生号
        ac.setPerResNo(this.perResNo);

        // 设置批次号
        ac.setBatchNo(this.batchNo);

        // 设置原批次号
        ac.setSourceBatchNo(this.sourceBatchNo);

        // 设置原交易流水号
        ac.setSourceTransNo(this.sourceTransNo);

        // 设置原交易时间
        ac.setSourceTransDate(this.sourceTransDate);

        ac.setIsSettle(Constants.IS_SETTLE1);

//      if (accType.equals(AccountType.ACC_TYPE_POINT20111.getCode()))
//      {
//          ac.setIsSettle(Constants.IS_SETTLE1);
//      } else
//      {
//          ac.setIsSettle(Constants.IS_SETTLE0);
//      }
        return ac;
    }
    
    /**
     * 平台20%后额度退回（隔日退货)
     * @return
     */
    public BigDecimal getPaasUpPoint()
    {
        BigDecimal point = Compute.sub(this.pointSum,this.getTrusteeUpAddPoint().getAddAmount().add(this.getManageUpAddPoint().getAddAmount()).add(this.getServiceUpAddPoint().getAddAmount()).add(Compute.mulCeiling(pointSum, PointRate.perRate, Constants.SURPLUS_TWO)));
        this.residuePoint = Compute.sub(this.residuePoint, point);
        return point;
    }
    
    /**
     * 获取平台总共积分(非持卡人积分都给平台)
     *
     * @return
     */
    public Alloc getAllNoCardPaasAddPoint()
    {
        BigDecimal rate = this.getPaasPoint(BigDecimal.ONE);
        return this.getAddPaas(AccountType.ACC_TYPE_POINT10110.getCode(),rate);
    }
}
