/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.services.certificateManage.imp;

import java.net.URLDecoder;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.aps.services.certificateManage.ICertificateTempService;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.bs.api.apply.IBSCertificateService;
import com.gy.hsxt.bs.bean.apply.TemplateAppr;
import com.gy.hsxt.bs.bean.apply.Templet;
import com.gy.hsxt.bs.bean.apply.TempletQuery;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.StringUtils;

/**
 * 证书模板Service实现类
 * 
 * @Package: com.gy.hsxt.access.web.aps.services.contractManage.imp
 * @ClassName: CertificateTempService
 * @Description: TODO
 * @author: likui
 * @date: 2016年1月22日 上午11:10:13
 * @company: gyist
 * @version V3.0.0
 */
@Service
public class CertificateTempService extends BaseServiceImpl<CertificateTempService> implements ICertificateTempService {

	/**
	 * BS证书Service
	 */
	@Autowired
	private IBSCertificateService bSCertificateService;

	/**
	 * 分页查询证书模板
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<Templet> queryCertificateTempByPage(Map<String, Object> filterMap, Map<String, Object> sortMap,
			Page page)
	{
		try
		{
			TempletQuery query = new TempletQuery();
			String tempName = (String) filterMap.get("templetName");
			tempName = StringUtils.isNotBlank(tempName) ? URLDecoder.decode(tempName, "UTF-8") : tempName;
			query.setTempletName(tempName);
			Object templetTypeObj = filterMap.get("templetType");
			if(templetTypeObj!=null){
				query.setTempletType(Integer.parseInt((String)templetTypeObj));
			}
			PageData<Templet> pd = bSCertificateService.queryTempletList(query, page);
			return pd;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryCertificateTempByPage", "分页查询证书模板失败", ex);
		}
		return null;
	}

	/**
	 * 新增证书模板
	 * 
	 * @Description:
	 * @param templet
	 * @throws HsException
	 */
	@Override
	public void addCertificateTemp(Templet bean) throws HsException
	{
		try
		{
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { bean.getCustType(), ASRespCode.ASP_DOC_ENTCUSTTYPE_INVALID.getCode(), ASRespCode.ASP_DOC_ENTCUSTTYPE_INVALID.getDesc() }, 
					new Object[] { bean.getTempletName(), ASRespCode.ASP_DOC_TEMPNAME_INVALID.getCode(), ASRespCode.ASP_DOC_TEMPNAME_INVALID.getDesc() },
					new Object[] { bean.getTempPicId(), ASRespCode.ASP_CERTIFICATENO_TEMPPICID_INVALID.getCode(), ASRespCode.ASP_CERTIFICATENO_TEMPPICID_INVALID.getDesc()},
					new Object[] { bean.getTempFileId(), ASRespCode.ASP_CERTIFICATENO_TEMPFILEID_INVALID.getCode(), ASRespCode.ASP_CERTIFICATENO_TEMPFILEID_INVALID.getDesc()},
					new Object[] { bean.getCreatedBy(), ASRespCode.ASP_CERTIFICATENO_CREATEDBY_INVALID.getCode(), ASRespCode.ASP_CERTIFICATENO_CREATEDBY_INVALID.getDesc()}
				);
			bSCertificateService.createTemplet(bean);
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12685 保存证书模板失败
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "addTemplet", "新增证书模板失败" , ex);
			throw new HsException(RespCode.FAIL);
		}
	}

	/**
	 * 验证模板参数
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月28日 下午3:33:19
	 * @param bean
	 * @throws HsException
	 * @return : void
	 * @version V3.0.0
	 */
	private void vaildTempletParam(Templet bean) throws HsException
	{
		RequestUtil.verifyParamsIsNotEmpty(
				new Object[] { bean.getCustType(), ASRespCode.ASP_DOC_ENTCUSTTYPE_INVALID.getCode(),
						ASRespCode.ASP_DOC_ENTCUSTTYPE_INVALID.getDesc() }, new Object[] { bean.getTempletName(),
						ASRespCode.ASP_DOC_TEMPNAME_INVALID.getCode(), ASRespCode.ASP_DOC_TEMPNAME_INVALID.getDesc() },
				new Object[] { bean.getTempletContent(), ASRespCode.ASP_DOC_TEMPCONTENT_INVALID.getCode(),
						ASRespCode.ASP_DOC_TEMPCONTENT_INVALID.getDesc() });
	}

	/**
	 * 修改证书模板
	 * 
	 * @Description:
	 * @param templet
	 * @throws HsException
	 */
	@Override
	public void modifyCertificateTemp(Templet bean) throws HsException
	{
		try
		{
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { bean.getTempletId(), ASRespCode.ASP_DOC_TEMP_ID_INVALID.getCode(), ASRespCode.ASP_DOC_TEMP_ID_INVALID.getDesc()},
					new Object[] { bean.getCustType(), ASRespCode.ASP_DOC_ENTCUSTTYPE_INVALID.getCode(), ASRespCode.ASP_DOC_ENTCUSTTYPE_INVALID.getDesc()},
					new Object[] { bean.getTempletName(), ASRespCode.ASP_DOC_TEMPNAME_INVALID.getCode(), ASRespCode.ASP_DOC_TEMPNAME_INVALID.getDesc()},
					new Object[] { bean.getUpdatedBy(), ASRespCode.ASP_CERTIFICATENO_UPDATEBY_INVALID.getCode(), ASRespCode.ASP_CERTIFICATENO_UPDATEBY_INVALID.getDesc()}
					);
			bSCertificateService.modifyTemplet(bean);
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12685 保存证书模板失败
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "modifyCertificateTemp", "修改证书模板失败", ex);
			throw new HsException(RespCode.FAIL);
		}
	}

	/**
	 * 根据ID查询证书模板
	 * 
	 * @Description:
	 * @param templetId
	 * @return
	 */
	@Override
	public Templet queryCertificateTempById(String templetId)
	{
		try
		{
			return bSCertificateService.queryTempletById(templetId);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryCertificateTempById", "根据ID查询证书模板失败", ex);
		}
		return null;
	}

	/**
	 * 启用证书模板
	 * 
	 * @Description:
	 * @param templetId
	 * @param custId
	 * @throws HsException
	 */
	@Override
	public void enableCertificateTemp(String templetId, String custId) throws HsException
	{
		try
		{
			bSCertificateService.enableTemplet(templetId, custId);
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12687 启用证书模板失败
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "enableCertificateTemp," + templetId + "," + custId, "启用证书模板失败",
					ex);
			throw new HsException(RespCode.FAIL);
		}
	}

	/**
	 * 停用证书模板
	 * 
	 * @Description:
	 * @param templetId
	 * @param custId
	 * @throws HsException
	 */
	@Override
	public void stopCertificateTemp(String templetId, String custId) throws HsException
	{
		try
		{
			bSCertificateService.disableTemplet(templetId, custId);
		} catch (HsException ex)
		{
			// 12000 参数错误
			// 12688 停用证书模板失败
			throw ex;
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "stopCertificateTemp," + templetId + "," + custId, "停用证书模板失败",
					ex);
			throw new HsException(RespCode.FAIL);
		}
	}

	/**
	 * 分页查询证书模板审批
	 * 
	 * @Description:
	 * @param filterMap
	 * @param sortMap
	 * @param page
	 * @return
	 */
	@Override
	public PageData<Templet> queryCertificateTempApprByPage(Map<String, Object> filterMap, Map<String, Object> sortMap,
			Page page)
	{
		try
		{
			TempletQuery query = new TempletQuery();
			query.setOptCustId((String) filterMap.get("custId"));
			String tempName = (String) filterMap.get("templetName");
			tempName = StringUtils.isNotBlank(tempName) ? URLDecoder.decode(tempName, "UTF-8") : tempName;
			Object templetType = filterMap.get("templetType");
			Integer tempType = null;
			if(templetType!=null){
				tempType = Integer.parseInt((String)templetType);
			}
			return bSCertificateService.queryTemplet4Appr(query,page);
		} catch (Exception ex)
		{
			SystemLog.error(this.getClass().getName(), "queryCertificateTempApprByPage", "分页查询证书模板审批失败", ex);
		}
		return null;
	}

	/**
	 * 证书模板审批
	 * 
	 * @Description:
	 * @param appr
	 * @throws HsException
	 */
	@Override
	public void certificateTempAppr(TemplateAppr bean) throws HsException
	{
		bSCertificateService.apprTemplet(bean);
	}
	
	
	/**
	 * @Description:查询证书模板最新审核记录详情
	 * @param templetId
	 * @return
	 * @throws HsException
	 */
	public TemplateAppr queryLastTemplateAppr(String templetId) throws HsException {
		return bSCertificateService.queryLastTemplateAppr(templetId);
	}
	
	/*
	public static void main(String[] args){
		//String path = "D:/webspace/hsxt-portal/hsxt-portal-aps-web/src/main/webapp/modules/certificateManage/tpl/zsmb/dsfzsmb.html";
		String path = "D:/dymb/xszsmb.html";
		File file = new File(path);
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String tempStr = null;
			while((tempStr=br.readLine())!=null){
				sb.append(tempStr);
				sb.append("\r");
			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HtmlImageGenerator imageGenerator = new HtmlImageGenerator();
		String url = "http://192.168.41.171:8080/hsxt-portal-aps-web/xszsmb.html";
		imageGenerator.loadUrl(url);//"http://news.qq.com/a/20160316/040625.htm");
		//imageGenerator.loadHtml(sb.toString());
//		Dimension dimension = new Dimension();
//		dimension.setSize(1350, 766);
//		imageGenerator.setSize(dimension);
		imageGenerator.getBufferedImage();  
        imageGenerator.saveAsImage("d:/hello-world.png");  
        imageGenerator.saveAsHtmlWithMap("d:/hello-world.html", "hello-world.png");  
	}*/
}
