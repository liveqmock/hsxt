/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.bean.platformDebit;

import java.io.Serializable;

import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.bean.deduction.HsbDeduction;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.NumbericUtils;

/***
 * 地区平台扣款申请
 * 
 * @Package: com.gy.hsxt.access.web.bean.platformDebit
 * @ClassName: ApsHsbDeduction
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2016-4-19 下午2:54:02
 * @version V1.0
 */
public class ApsHsbDeduction extends HsbDeduction implements Serializable {
    private static final long serialVersionUID = -3480279027722987946L;

    /**
     * 扣款互生号
     */
    private String deductResNo; 

    /**
     * 扣款互生号对应的客户号
     */
    private String deductCustId; 

    /**
     * 扣款单位名称
     */
    private String deductCustName;
    
    /**
     * 扣款客户类型
     */
    private String deductCustType;

    /**
     * 验证参数
     * 
     * @throws HsException
     */
    public void checkData() throws HsException {
        //非空验证
        RequestUtil.checkParamIsNotEmpty(
                new Object[] { this.deductResNo, ASRespCode.APS_DEDUCT_RESNO_NULL },                    //扣款互生号
                new Object[] { this.deductCustId, ASRespCode.APS_DEDUCT_RESNO_CUSTID_NULL },            //扣款互生号对应的客户号
                new Object[] { this.deductCustName, ASRespCode.APS_DEDUCT_RESNO_NAME_NULL },            //扣款单位名称
                new Object[] { super.getAmount(), ASRespCode.APS_DEDUCT_RESNO_AMOUNT_FORMAT_ERROR },    //扣款金额
                new Object[] { this.deductCustType, ASRespCode.APS_GJPSGL_CUSTTYPE }                    //扣款互生号类型
                );
        
        //字符长度验证
        RequestUtil.verifyParamsLength(new Object[] { super.getApplyRemark(), 0, 3000, ASRespCode.APS_DEDUCT_REMARK_MAX_LENGTH_ERROR});
        
        //扣款金额验证
        if (!NumbericUtils.isPlus(super.getAmount())) {
            throw new HsException(ASRespCode.APS_DEDUCT_RESNO_AMOUNT_FORMAT_ERROR);
        }
    }
    
    /**
     * 转换成互生币扣除对象
     * @return
     */
    public HsbDeduction convertHsbDeduction() {
        
        super.setEntResNo(this.deductResNo);
        super.setEntCustId(this.deductCustId);
        super.setEntCustName(this.deductCustName);
        super.setCustType(Integer.parseInt(this.deductCustType));
        
        return (HsbDeduction) this;
    }
    

    /**
     * @return the 扣款互生号
     */
    public String getDeductResNo() {
        return deductResNo;
    }

    /**
     * @param 扣款互生号 the deductResNo to set
     */
    public void setDeductResNo(String deductResNo) {
        this.deductResNo = deductResNo;
    }

    /**
     * @return the 扣款互生号对应的客户号
     */
    public String getDeductCustId() {
        return deductCustId;
    }

    /**
     * @param 扣款互生号对应的客户号 the deductCustId to set
     */
    public void setDeductCustId(String deductCustId) {
        this.deductCustId = deductCustId;
    }

    /**
     * @return the 扣款单位名称
     */
    public String getDeductCustName() {
        return deductCustName;
    }

    /**
     * @param 扣款单位名称 the deductCustName to set
     */
    public void setDeductCustName(String deductCustName) {
        this.deductCustName = deductCustName;
    }

    /**
     * @return the 扣款客户类型
     */
    public String getDeductCustType() {
        return deductCustType;
    }

    /**
     * @param 扣款客户类型 the deductCustType to set
     */
    public void setDeductCustType(String deductCustType) {
        this.deductCustType = deductCustType;
    }

}
