/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.certificateManage;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.apply.CertificateContent;
import com.gy.hsxt.bs.bean.apply.TemplateAppr;
import com.gy.hsxt.bs.bean.apply.Templet;
import com.gy.hsxt.bs.bean.base.ApprParam;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 证书模板Service接口
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.certificateManage
 * @ClassName: ICertificateTempService
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月22日 上午11:06:49
 * @company: gyist
 * @version V3.0.0
 */
@SuppressWarnings("rawtypes")
public interface ICertificateTempService extends IBaseService {

	/**
	 * 分页查询证书模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午2:59:13
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<Templet>
	 * @version V3.0.0
	 */
	public PageData<Templet> queryCertificateTempByPage(Map<String, Object> filterMap, Map<String, Object> sortMap,
			Page page);

	/**
	 * 新增证书模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午3:00:42
	 * @param templet
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void addCertificateTemp(Templet bean) throws HsException;

	/**
	 * 修改证书模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午3:02:03
	 * @param templet
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void modifyCertificateTemp(Templet templet) throws HsException;

	/**
	 * 根据ID查询证书模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午3:02:51
	 * @param templetId
	 * @return
	 * @return : Templet
	 * @version V3.0.0
	 */
	public Templet queryCertificateTempById(String templetId);

	/**
	 * 启用证书模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午3:04:19
	 * @param templetId
	 * @param custId
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void enableCertificateTemp(String templetId, String custId) throws HsException;

	/**
	 * 停用证书模板
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午3:04:22
	 * @param templetId
	 * @param custId
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void stopCertificateTemp(String templetId, String custId) throws HsException;

	/**
	 * 分页查询证书模板审批
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午3:05:21
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 * @return : PageData<Templet>
	 * @version V3.0.0
	 */
	public PageData<Templet> queryCertificateTempApprByPage(Map<String, Object> filterMap, Map<String, Object> sortMap,
			Page page);

	/**
	 * 证书模板审批
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月22日 下午3:06:29
	 * @param appr
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	public void certificateTempAppr(TemplateAppr bean) throws HsException;
	
	/**
	 * @Description:查询证书模板最新审核记录详情
	 * @param templetId
	 * @return
	 * @throws HsException
	 */
	public TemplateAppr queryLastTemplateAppr(String templetId) throws HsException;
	
}