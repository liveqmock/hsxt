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
 * 销售资格证书Service接口
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.certificateManage
 * @ClassName: ISellCertificateServuce
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月22日 上午11:06:05
 * @company: gyist
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface ISellCertificateServuce extends IBaseService {

	/**
	 * 分页查询消费资格证书盖章
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午11:16:56
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<CertificateBaseInfo>
	 * @version V3.0.0
	 */
	public PageData<CertificateBaseInfo> querySellCertificateBySeal(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page);

	/**
	 * 销售资格证书盖章
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午11:18:10
	 * @param certificateNo
	 * @param custId
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void sellCertificateSeal(String certificateNo, String custId) throws HsException;

	/**
	 * 根据ID查询销售资格证书
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午11:19:42
	 * @param certificateNo
	 * @return : void
	 * @version V3.0.0
	 */
	public Certificate querySellCertificateById(String certificateNo);

	/**
	 * 查询销售资格证书内容
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午11:21:31
	 * @param certificateNo
	 * @return
	 * @return : CertificateContent
	 * @version V3.0.0
	 */
	public CertificateContent querySellCertificateContent(String certificateNo);

	/**
	 * 分页查询证书发放
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午11:25:28
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<CertificateBaseInfo>
	 * @version V3.0.0
	 */
	public PageData<CertificateBaseInfo> querySellCertificateGiveOutByPage(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page);

	/**
	 * 打印销售资格证书
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午11:26:53
	 * @param certificateNo
	 * @param custId
	 * @return
	 * @throws HsException
	 * @return : CertificateContent
	 * @version V3.0.0
	 */
	public CertificateContent printSellCertificate(String certificateNo, String custId) throws HsException;

	/**
	 * 销售资格证书发放
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午11:28:36
	 * @param giveOut
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void giveOutSellCertificate(String giveOut) throws HsException;

	/**
	 * 查询销售资格证书发放历史
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午11:29:36
	 * @param certificateNo
	 * @return
	 * @return : Map<String,Object>
	 * @version V3.0.0
	 */
	public PageData<CertificateSendHis> querySellCertificateGiveOutRecode(String certificateNo,Page page)throws HsException;
	/**
	 * 分页查询销售资格证书发放历史
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 上午11:32:29
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<CertificateBaseInfo>
	 * @version V3.0.0
	 */
	public PageData<CertificateBaseInfo> querySellCertificateRecodeByPage(Map<String, Object> filterMap,
			Map<String, Object> sortMap, Page page);
}
