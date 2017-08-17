/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
*/
package com.gy.hsxt.bs.api.apply;

import com.gy.hsxt.bs.bean.apply.AcceptStatus;
import com.gy.hsxt.bs.bean.apply.IntentCust;
import com.gy.hsxt.bs.bean.apply.IntentCustBaseInfo;
import com.gy.hsxt.bs.bean.apply.IntentCustQueryParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 *
 * @Package: com.gy.hsxt.bs.api.app
 * @ClassName: IBSIntentCustService
 * @Description: 意向客户管理接口
 *
 * @author: xiaofl
 * @date: 2015-8-31 下午3:39:50
 * @version V1.0


 */
public interface IBSIntentCustService {

	/**
	 * 客户查询受理进度状态
	 * @param acceptNo 受理编号  必填
	 * @param licenseNo 意向客户企业营业执照号 必填
	 * @return 返回受理状态信息
	 */
    public AcceptStatus queryStatus(String acceptNo,String licenseNo);

	/**
	 * 服务公司查询意向客户
	 * @param intentCustQueryParam 查询条件
	 * @param page 分页信息 必填
	 * @return 返回意向客户基本信息列表
	 * @throws HsException
	 */
    public PageData<IntentCustBaseInfo> serviceEntQueryIntentCust(IntentCustQueryParam intentCustQueryParam,Page page) throws HsException;

	/**
	 * 地区平台查询意向客户
	 * @param intentCustQueryParam 查询条件
	 * @param page 分页信息 必填
	 * @return 返回意向客户基本信息列表
	 * @throws HsException
	 */
    public PageData<IntentCustBaseInfo> platQueryIntentCust(IntentCustQueryParam intentCustQueryParam,Page page) throws HsException;

	/**
	 * 服务公司/地区平台查询意向客户信息详情
	 * @param applyId 申请编号 必填
	 * @return 返回意向客户信息
	 */
    public IntentCust queryIntentCustById(String applyId) ;

    /**
     * 服务公司审批意向客户
     * @param applyId 申请编号 必填
     * @param apprOperator 审批操作员客户号 必填
     * @param status 状态 必填
     * @param apprRemark 审批意见
     * @throws HsException
     */
    public void apprIntentCust(String applyId,String apprOperator,Integer status,String apprRemark) throws HsException;
}
