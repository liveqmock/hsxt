/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.certificateManage;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.Certificate;
import com.gy.hsxt.bs.bean.apply.CertificateBaseInfo;
import com.gy.hsxt.bs.bean.apply.CertificateContent;
import com.gy.hsxt.bs.bean.apply.CertificateSendHis;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 第三方证书Service接口
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.certificateManage
 * @ClassName: IThirdCertificateService
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月22日 上午11:06:35
 * @company: gyist
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface IThirdCertificateService extends IBaseService {

	/**
	 * 分页查询第三方证书发放
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午2:25:52
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<CertificateBaseInfo>
	 * @version V3.0.0
	 */
	public PageData<CertificateBaseInfo> queryThirdCertificateGiveOutByPage(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page);

	/**
	 * 打印第三方证书
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午2:26:47
	 * @param certificateNo
	 * @param custId
	 * @return
	 * @throws HsException
	 * @return : CertificateContent
	 * @version V3.0.0
	 */
	public CertificateContent printThirdCertificate(String certificateNo, String custId);

	/**
	 * 发放第三方证书
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午2:27:24
	 * @param giveOut
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void giveOutThirdCertificate(CertificateSendHis bean) throws HsException;

	/**
	 * 查询第三方证书发放历史
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午2:28:32
	 * @param certificateNo
	 * @return
	 * @return : Map<String,Object>
	 * @version V3.0.0
	 */
	public PageData<CertificateSendHis> queryThirdCertificateGiveOutRecode(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page)throws HsException;

	/**
	 * 分页查询第三方证书发放历史
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午2:29:23
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<CertificateBaseInfo>
	 * @version V3.0.0
	 */
	public PageData<CertificateBaseInfo> queryThirdCertificateRecodeByPage(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page);
	
	/**
	 * 通过ID查询证书内容
	 * @param certificateNo
	 * @param realTime 是否实时
	 * @return
	 * @throws HsException
	 */
	public CertificateContent queryCreContentById(String certificateNo,boolean realTime)throws HsException;
}
