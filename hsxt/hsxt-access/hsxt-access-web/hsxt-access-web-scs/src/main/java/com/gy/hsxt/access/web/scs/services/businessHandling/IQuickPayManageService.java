/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.businessHandling;

import java.util.List;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.common.bean.OpenQuickPayBean;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.lcs.bean.PayBank;
import com.gy.hsxt.uc.as.bean.common.AsQkBank;

/**
 * 快捷支付Service接口
 * 
 * @Package: com.gy.hsxt.access.web.company.services.systemBusiness
 * @ClassName: IQuickPayManageService
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月12日 下午2:08:07
 * @company: gyist
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface IQuickPayManageService extends IBaseService {

	/**
	 * 查询绑定的快捷支付列表
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月12日 下午2:26:50
	 * @param entCustId
	 * @return
	 * @return : List<AsQkBank>
	 * @version V3.0.0
	 */
	public List<AsQkBank> queryBandQuickBank(String entCustId) throws HsException;

	/**
	 * 查询支持快捷支付的银行
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月12日 下午2:51:33
	 * @param cardType
	 * @return
	 * @return : List<PayBank>
	 * @version V3.0.0
	 */
	public List<PayBank> queryQuickPayBank(String cardType) throws HsException;

	/**
	 * 开通快捷支付
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月12日 下午6:11:23
	 * @param bean
	 * @param callType
	 * @return
	 * @throws HsException
	 * @return : String
	 * @version V3.0.0
	 */
	public String openQuickPay(OpenQuickPayBean bean, String callType) throws HsException;

	/**
	 * 发送快捷支付短信验证码
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月12日 下午6:27:24
	 * @param orderNo
	 * @param bindingNo
	 * @param privObligate
	 * @return : void
	 * @version V3.0.0
	 */
	public void sendQuickPaySmsCode(String orderNo, String bindingNo, String privObligate, String callType);
}
