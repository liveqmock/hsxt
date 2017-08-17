/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.aps.controllers.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.aps.services.common.ApsConfigService;
import com.gy.hsxt.access.web.aps.services.common.ICommonService;
import com.gy.hsxt.access.web.aps.services.common.IPubParamService;
import com.gy.hsxt.access.web.bean.ApsDoubleOperator;
import com.gy.hsxt.access.web.bean.cache.ProvCity;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.RandomCodeUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.Bank;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.bean.Currency;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.lcs.client.ProvinceTree;
import com.gy.hsxt.uc.as.api.common.IUCAsPwdService;
import com.gy.hsxt.uc.as.api.common.IUCLoginService;
import com.gy.hsxt.uc.as.api.enumerate.PlatFormEnum;
import com.gy.hsxt.uc.as.api.enumerate.SubSysEnum;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;
import com.gy.hsxt.uc.as.bean.common.AsOperatorLoginInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.as.bean.result.AsOperatorLoginResult;

/**
 * 
 * @projectName : hsxt-access-web-scs
 * @package : com.gy.hsxt.access.web.aps.controllers.common
 * @className : CommController.java
 * @description : 系统公用CommController
 * @author : maocy
 * @createDate : 2015-11-3
 * @updateUser :
 * @updateDate :
 * @updateRemark :
 * @version : v0.0.1
 */
@Controller
@RequestMapping("commController")
public class CommController extends BaseController {

	@Resource
	private RedisUtil<String> changeRedisUtil;

	@Autowired
	private LcsClient baseDataService; // 账户余额查询服务类

	@Resource
	private IPubParamService pubParamService;// 查询本平台信息

	@Resource
	private IUCLoginService ucParamService;// 登陆服务

	@Resource
	private IUCAsPwdService asPwdService;

	/** 地区平台配送Service **/
	@Autowired
	private LcsClient lcsClient;

	/** 公共Service **/
	@Autowired
	private ICommonService commonService;

	@Resource
	private ApsConfigService apsConfigService;

	@RequestMapping("/generateSecuritCode")
	public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		int width = 80;// 定义图片的width
		int height = 28;// 定义图片的height
		int fontHeight = 20; // 字体
		int xx = 10; // x坐标
		int codeY = 23;// y坐标

		// 验证码
		String numberCode = RandomCodeUtils.getNumberCode();
		boolean isFixed = apsConfigService.isImgCodeFixed();
		if (isFixed)
		{
			numberCode = "1111";
		}
		// 定义图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics gd = buffImg.getGraphics();
		// 将图像填充为白色
		gd.setColor(Color.WHITE);
		gd.fillRect(0, 0, width, height);
		// 创建字体，字体的大小应该根据图片的高度来定。
		Font font = new Font("Consolas", Font.PLAIN, fontHeight);
		// 设置字体。
		gd.setFont(font);
		// 画边框。
		gd.setColor(Color.WHITE);
		gd.drawRect(0, 0, width - 1, height - 1);
		// 用随机产生的颜色将验证码绘制到图像中。
		gd.setColor(Color.BLACK);
		gd.drawString(numberCode, xx, codeY);
		// 设置redis缓存
		this.setCodeCache(req, numberCode);
		// 禁止图像缓存。
		resp.setHeader("Pragma", "no-cache");
		resp.setHeader("Cache-Control", "no-cache");
		resp.setDateHeader("Expires", 0);
		resp.setContentType("image/jpeg");

		// 将图像输出到Servlet输出流中。
		ServletOutputStream sos = resp.getOutputStream();
		ImageIO.write(buffImg, "jpeg", sos);
		sos.close();
	}

	/**
	 * 设置验证码缓存
	 * 
	 * @param request
	 */
	private void setCodeCache(HttpServletRequest request, String code)
	{
		// 操作员编号
		String custId = request.getParameter("custId");
		// 验证码类型
		String type = request.getParameter("type");

		if (!StringUtils.isEmpty(custId) && !StringUtils.isEmpty(type))
		{
			String key = custId + "_" + type;
			// 设置缓存
			changeRedisUtil.add(key, code);
			changeRedisUtil.setExpireInSecond(key, apsConfigService.imgCodeOverdueTime());
		}
	}

	/**
	 * 获取随即token
	 * 
	 * @param req
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_card_holder_token" }, method = {
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findCardHolderToken(String custId, HttpServletRequest request) throws IOException
	{
		// 变量声明
		HttpRespEnvelope hre = null; // 返回界面封装对象

		try
		{
			// 获取token
			String token = pubParamService.getRandomToken(custId);
			// 封装显示结果
			hre = new HttpRespEnvelope(token);
		} catch (HsException e)
		{
			// 异常信息封装
			hre = new HttpRespEnvelope(e);
		}

		// 返回界面
		return hre;
	}

	/**
	 * 获取所有国家信息
	 * 
	 * @param custId
	 * @param token
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_contry_all" }, method = {
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findContryAll(String custId, String token, HttpServletRequest request)
	{

		// 变量声明
		List<Country> list = null;
		HttpRespEnvelope hre = null;

		// 执行查询方法
		try
		{
			// Token验证
			super.checkSecureToken(request);

			list = this.baseDataService.findContryAll();

			hre = new HttpRespEnvelope(list);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(RespCode.AS_GET_COUNTRY_LIST_FAILED);
		}

		return hre;
	}

	/**
	 * 根据国家代码获取省份列表
	 * 
	 * @param custId
	 * @param token
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_province_by_parent" }, method = {
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findProvinceByParent(String custId, String countryNo, String token,
			HttpServletRequest request)
	{

		// 变量声明
		List<Province> list = null;
		HttpRespEnvelope hre = null;

		// 执行查询方法
		try
		{
			// Token验证
			super.checkSecureToken(request);

			list = this.baseDataService.queryProvinceByParent(countryNo);

			hre = new HttpRespEnvelope(list);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(RespCode.AS_GET_PROV_LIST_FAILED);
		}

		return hre;
	}

	/**
	 * 根据国家省份查询下级城市
	 * 
	 * @param countryNo
	 * @param provinceNo
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/find_city_by_parent" }, method = {
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findCityByParent(String countryNo, String provinceNo, HttpServletRequest request)
	{

		// 变量声明
		List<City> list = null;
		HttpRespEnvelope hre = null;

		// 执行查询方法
		try
		{
			// Token验证
			super.checkSecureToken(request);

			// 根据国家省份查询下面的城市
			list = this.baseDataService.queryCityByParent(countryNo, provinceNo);

			hre = new HttpRespEnvelope(list);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(RespCode.AS_GET_CITY_LIST_FAILED);
		}

		return hre;
	}

	/**
	 * 根据国家省份查询下级城市
	 * 
	 * @param provinceNo国家代码
	 *            （可选参数，默认为本平台所在国家）
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findProvCity" }, method = {
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findProvCity(String countryNo)
	{
		try
		{
			String countryCode;// 国家代码
			String countryName;// 国家名称
			if (StringUtils.isBlank(countryNo))
			{
				LocalInfo localInfo = this.pubParamService.findSystemInfo();
				if (localInfo == null)
				{
					throw new HsException(RespCode.AS_GET_PALT_INFO_FAILED);
				}
				countryCode = localInfo.getCountryNo();
				countryName = localInfo.getCountryName();
			} else
			{
				Country country = this.baseDataService.getContryById(countryNo);
				if (country == null)
				{
					throw new HsException(RespCode.AS_GET_PALT_INFO_FAILED);
				}
				countryCode = country.getCountryNo();
				countryName = country.getCountryName();
			}
			// 获取省级城市列表
			List<ProvinceTree> list = this.baseDataService.queryProvinceTreeOfCountry(countryCode);
			if (list.isEmpty())
			{
				throw new HsException(RespCode.AS_GET_PALT_INFO_FAILED);
			}
			return new HttpRespEnvelope(new ProvCity(countryName, countryCode, list));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}

	}

	/**
	 * 查询所有的银行列表
	 * 
	 * @param custId
	 * @param token
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findBankAll" }, method = {
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findBankAll(String custId, String countryNo, String token, HttpServletRequest request)
	{

		// 变量声明
		List<Bank> list = null;
		HttpRespEnvelope hre = null;

		// 执行查询方法
		try
		{
			// Token验证
			super.checkSecureToken(request);

			list = this.baseDataService.queryBankAll();

			hre = new HttpRespEnvelope(list);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(RespCode.AS_GET_BANK_LIST_FAILED);
		}

		return hre;
	}

	/**
	 * 获取本平台详细信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findSystemInfo" }, method = {
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findSystemInfo(HttpServletRequest request)
	{

		// 变量声明
		LocalInfo localInfo = null;
		HttpRespEnvelope hre = null;

		// 执行查询方法
		try
		{
			// Token验证
			super.checkSecureToken(request);

			localInfo = this.pubParamService.findSystemInfo();

			hre = new HttpRespEnvelope(localInfo);

		} catch (HsException e)
		{
			hre = new HttpRespEnvelope(RespCode.AS_GET_PALT_INFO_FAILED);
		}

		return hre;
	}

	/**
	 * 验证双签用户
	 * 
	 * @param request
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("verifyDoublePwd")
	public HttpRespEnvelope verifyDoublePwd(HttpServletRequest request, ApsDoubleOperator ado) throws IOException
	{
		try
		{
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(
					new Object[] { ado.getDoubleUserName(), RespCode.AS_VERIFY_USERNAME_INVALID },
					new Object[] { ado.getDoublePwd(), RespCode.AS_VERIFY_PASSWORD_INVALID },
					new Object[] { ado.getRandomToken(), RespCode.AS_VERIFY_TOKEN_INVALID },
					new Object[] { ado.getCheckType(), RespCode.AS_VERIFY_CHECKTYPE_INVALID });

			// 用户双签验证
			String confirmerCustId = pubParamService.verifyDoublePwd(ado);
			return new HttpRespEnvelope(confirmerCustId);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 登陆
	 * 
	 * @param request
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("operatorLogin")
	public HttpRespEnvelope operatorLogin(HttpServletRequest request, @ModelAttribute AsOperatorLoginInfo loginInfo)throws IOException{
			HttpRespEnvelope hre = null;
	{
		try
		{
			loginInfo.setChannelType(ChannelTypeEnum.WEB);
			loginInfo.setCustType("6");
			AsOperatorLoginResult res = this.ucParamService.operatorLogin(loginInfo);
			hre = new HttpRespEnvelope(res);
		} catch (HsException e)
		{
			hre = handleModifyPasswordException(e);
		}
		return hre;
	}
}

	 private HttpRespEnvelope handleModifyPasswordException(HsException e){
	    	HttpRespEnvelope hre = new HttpRespEnvelope();
	    	int errorCode = e.getErrorCode();
//	    	String errorMsg = e.getMessage();
//	    	String funName = "handleModifyPasswordException";
//	    	StringBuffer msg = new StringBuffer();
//	    	msg.append("处理修改登录密码Exception时报错,传入参数信息  e["+JSON.toJSONString(e)+"] "+NEWLINE);
	    	if(160359 == errorCode || 160108 == errorCode){
	    		/**保留
	     		try {
	        			if(!StringUtils.isEmptyTrim(errorMsg) && errorMsg.contains(",")){
	            			String[] msgInfo = errorMsg.split(",");
	            			if(msgInfo.length > 1){
	            				int loginFailTimes = Integer.parseInt(msgInfo[0]);
	            				int maxFailTimes = Integer.parseInt(msgInfo[1]);
	            			}
	            		}
	            		
	    			} catch (Exception e2) {
	    				SystemLog.error(MOUDLENAME, funName, msg.toString(), e2);
	    			}
	        		*/
	    		hre = new HttpRespEnvelope(e.getErrorCode(),"登录密码不正确");
	    	}else if(160467 == errorCode){
	    		hre = new HttpRespEnvelope(e.getErrorCode(),e.getMessage());
	    	}else{
	    		hre = new HttpRespEnvelope(e);
	    	}
	    	return hre;
	    }
	/**
	 * 获取地区平台信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 上午10:18:43
	 * @param request
	 * @return
	 * @throws IOException
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping("get_localInfo")
	public HttpRespEnvelope getLocalInfo(HttpServletRequest request) throws IOException
	{
		this.verifySecureToken(request);
		try
		{
			return new HttpRespEnvelope(lcsClient.getLocalInfo());
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：拼接地区名称 方法描述：拼接地区名称
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param countryCode
	 *            国家代码
	 * @param provinceCode
	 *            省份代码
	 * @param cityCode
	 *            城市代码
	 * @param linkStr
	 *            连接字符
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/getRegionByCode" }, method = {
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getRegionByCode(HttpServletRequest request, String countryCode, String provinceCode,
			String cityCode, String linkStr)
	{
		try
		{
			// Token验证
			super.checkSecureToken(request);
			StringBuffer region = new StringBuffer();
			linkStr = !StringUtils.isBlank(linkStr) ? linkStr : "";
			// 获取平台信息
			LocalInfo localInfo = this.pubParamService.findSystemInfo();
			if (localInfo == null)
			{
				throw new HsException(RespCode.AS_GET_REGION_BY_CODE_FAILED);
			}
			// 获取国家名称
			if (!StringUtils.isBlank(countryCode))
			{
				Country country = this.baseDataService.getContryById(countryCode);
				if (country == null)
				{
					throw new HsException(RespCode.AS_GET_REGION_BY_CODE_FAILED);
				}
				region.append(country.getCountryName()).append(linkStr);
			}
			// 获取省份名称
			if (!StringUtils.isBlank(provinceCode))
			{
				Province prov = this.baseDataService.getProvinceById(localInfo.getCountryNo(), provinceCode);
				if (prov == null)
				{
					throw new HsException(RespCode.AS_GET_REGION_BY_CODE_FAILED);
				}
				region.append(prov.getProvinceName()).append(linkStr);
			}
			// 获取城市名称
			if (!StringUtils.isBlank(cityCode))
			{
				City city = this.baseDataService.getCityById(localInfo.getCountryNo(), provinceCode, cityCode);
				if (city == null)
				{
					throw new HsException(RespCode.AS_GET_REGION_BY_CODE_FAILED);
				}
				region.append(city.getCityName()).append(linkStr);
			}
			return new HttpRespEnvelope(region.toString());
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 获取示例图片、常用业务文档、办理业务文档列表
	 * 
	 * @param request
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("findDocList")
	public HttpRespEnvelope findDocList(HttpServletRequest request) throws IOException
	{
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("busList", this.pubParamService.findBizDocList());
			map.put("picList", this.pubParamService.findImageDocList());
			map.put("comList", this.pubParamService.findNormalDocList());
			return new HttpRespEnvelope(map);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 根据货币代码查询货币信息
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findCurrencyByCode" }, method = {
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findCurrencyByCode(HttpServletRequest request)
	{
		try
		{
			String currencyCode = request.getParameter("currencyCode");
			if (StringUtils.isBlank(currencyCode))
			{
				return new HttpRespEnvelope(new Currency());
			}
			Currency currency = this.baseDataService.queryCurrencyByCode(currencyCode);
			if (currency == null)
			{
				return new HttpRespEnvelope(new Currency());
			}
			return new HttpRespEnvelope(currency);
		} catch (Exception e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 依据客户号获取操作员信息
	 * 
	 * @param operCustId
	 *            操作员客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/searchOperByCustId" }, method = {
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope searchOperByCustId(String operCustId, HttpServletRequest request)
	{
		try
		{
			// 执行查询
			AsOperator asOperator = this.pubParamService.searchOperByCustId(operCustId);
			return new HttpRespEnvelope(asOperator);
		} catch (HsException e)
		{
			AsOperator operator = new AsOperator();
			operator.setUserName(operCustId);
			return new HttpRespEnvelope(operator);
		}
	}

	/**
	 * 依据企业互生号查询企业信息
	 * 
	 * @param companyResNo
	 *            企业互生号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findMainInfoByResNo" }, method = {
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findMainInfoByResNo(String companyResNo, HttpServletRequest request)
	{
		try
		{
			AsEntMainInfo obj = this.pubParamService.findMainInfoByResNo(companyResNo);
			return new HttpRespEnvelope(obj);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 根据客户号获取权限信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findPermissionByCustidList")
	@ResponseBody
	public HttpRespEnvelope findPermissionByCustidList(String custId, HttpServletRequest request)
	{
		// 返回的结果
		HttpRespEnvelope hre = null;
		// 权限操蛋集合
		List<AsPermission> permissionList = null;
		try
		{

			permissionList = pubParamService.findPermByCustId(PlatFormEnum.HSXT.name(), SubSysEnum.APS.name(), null,
					(short) 0, custId, SubSysEnum.APS.name());

			// 返回结果
			hre = new HttpRespEnvelope(permissionList);

		} catch (HsException he)
		{
			hre = new HttpRespEnvelope(he);
		}
		return hre;
	}

	/**
	 * 工单拒绝受理
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月11日 上午10:23:28
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/work_task_refuse_accept" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope workTaskRefuseAccept(HttpServletRequest request)
	{
		try
		{
			String bizNo = request.getParameter("bizNo");
			String exeCustId = request.getParameter("custId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { bizNo, ASRespCode.AS_BIZ_NO_INVALID },
					new Object[] { exeCustId, ASRespCode.AS_EXECUSTID_INVALID });

			commonService.workTaskRefuseAccept(bizNo, exeCustId);
			return new HttpRespEnvelope();
		} catch (HsException ex)
		{
			return new HttpRespEnvelope(ex);
		}
	}

	/**
	 * 工单挂起
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月11日 上午10:23:33
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/work_task_hang_up" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope workTaskHangUp(HttpServletRequest request)
	{
		try
		{
			String bizNo = request.getParameter("bizNo");        //业务单号
			String exeCustId = request.getParameter("custId");   //经办人
			String remark = request.getParameter("remark");      //备注(原因)
			
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { bizNo, ASRespCode.AS_BIZ_NO_INVALID },
					new Object[] { exeCustId, ASRespCode.AS_EXECUSTID_INVALID });

			commonService.workTaskHangUp(bizNo, exeCustId, remark);
			return new HttpRespEnvelope();
		} catch (HsException ex)
		{
			return new HttpRespEnvelope(ex);
		}
	}

	/**
	 * 查询企业扩展信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:47:02
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_company_ext_info" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findCompanyExtInfo(HttpServletRequest request)
	{
		try
		{
			String companyCustId = request.getParameter("companyCustId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { companyCustId, ASRespCode.AS_CUSTID_INVALID });
			return new HttpRespEnvelope(commonService.findCompanyExtInfo(companyCustId));
		} catch (HsException ex)
		{
			return new HttpRespEnvelope(ex);
		}
	}

	/**
	 * 查询企业一般信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:48:09
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_company_base_info" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findCompanyBaseInfo(HttpServletRequest request)
	{
		try
		{
			String companyCustId = request.getParameter("companyCustId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { companyCustId, ASRespCode.AS_CUSTID_INVALID });
			return new HttpRespEnvelope(commonService.findCompanyBaseInfo(companyCustId));
		} catch (HsException ex)
		{
			return new HttpRespEnvelope(ex);
		}
	}

	/**
	 * 查询企业重要信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:48:09
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_company_main_info" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findCompanyMainInfo(HttpServletRequest request)
	{
		try
		{
			String companyCustId = request.getParameter("companyCustId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { companyCustId, ASRespCode.AS_CUSTID_INVALID });
			return new HttpRespEnvelope(commonService.findCompanyMainInfo(companyCustId));
		} catch (HsException ex)
		{
			return new HttpRespEnvelope(ex);
		}
	}

	/**
	 * 查询企业所有信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:48:09
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_company_all_info" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findCompanyAllInfo(HttpServletRequest request)
	{
		try
		{
			String companyCustId = request.getParameter("companyCustId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { companyCustId, ASRespCode.AS_CUSTID_INVALID });
			return new HttpRespEnvelope(commonService.findCompanyAllInfo(companyCustId));
		} catch (HsException ex)
		{
			return new HttpRespEnvelope(ex);
		}
	}

	/**
	 * 查询企业状态信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:48:09
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_company_status_info" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findCompanyStatusInfo(HttpServletRequest request)
	{
		try
		{
			String companyCustId = request.getParameter("companyCustId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { companyCustId, ASRespCode.AS_CUSTID_INVALID });
			return new HttpRespEnvelope(commonService.findCompanyStatusInfo(companyCustId));
		} catch (HsException ex)
		{
			return new HttpRespEnvelope(ex);
		}
	}

	/**
	 * 根据企业资源号获取企业重要信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:48:09
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_company_main_info_by_res_no" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findCompanyMainInfoByResNo(HttpServletRequest request)
	{
		try
		{
			String companyResNo = request.getParameter("companyResNo");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { companyResNo, ASRespCode.AS_POINTNO_INVALID });
			return new HttpRespEnvelope(commonService.findCompanyMainInfoByResNo(companyResNo));
		} catch (HsException ex)
		{
			return new HttpRespEnvelope(ex);
		}
	}

	/**
	 * 查询企业的联系信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:48:09
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_company_contact_info" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findCompanyContactInfo(HttpServletRequest request)
	{
		try
		{
			String companyCustId = request.getParameter("companyCustId");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { companyCustId, ASRespCode.AS_CUSTID_INVALID });
			return new HttpRespEnvelope(commonService.findCompanyContactInfo(companyCustId));
		} catch (HsException ex)
		{
			return new HttpRespEnvelope(ex);
		}
	}

	/**
	 * 查询企业客户号 ，通过企业互生号
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年4月18日 下午2:48:09
	 * @param request
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@RequestMapping(value = { "/find_company_cust_id_by_res_no" }, method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	@ResponseBody
	public HttpRespEnvelope findCompanyCustIdByResNo(HttpServletRequest request)
	{
		try
		{
			String companyResNo = request.getParameter("companyResNo");
			// 非空验证
			RequestUtil.verifyParamsIsNotEmpty(new Object[] { companyResNo, ASRespCode.AS_POINTNO_INVALID });
			return new HttpRespEnvelope(commonService.findCompanyCustIdByEntResNo(companyResNo));
		} catch (HsException ex)
		{
			return new HttpRespEnvelope(ex);
		}
	}

	@Override
	protected IBaseService getEntityService()
	{
		return null;
	}

}
