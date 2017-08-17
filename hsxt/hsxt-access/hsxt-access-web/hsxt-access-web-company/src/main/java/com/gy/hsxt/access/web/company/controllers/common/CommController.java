package com.gy.hsxt.access.web.company.controllers.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
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
import com.gy.hsxt.access.web.bean.CompanyBase;
import com.gy.hsxt.access.web.bean.cache.ProvCity;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.company.services.common.CompanyConfigService;
import com.gy.hsxt.access.web.company.services.common.ICommService;
import com.gy.hsxt.access.web.company.services.common.IPubParamService;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.RandomCodeUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.PayBank;
import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.lcs.client.ProvinceTree;
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
 * @package : com.gy.hsxt.access.web.scs.controllers.common
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
	private CompanyConfigService companyConfigService;

	@Autowired
	private LcsClient lcsClient; // 全局配置服务类

	@Resource
	private RedisUtil<String> changeRedisUtil;

	@Resource
	private IUCLoginService ucParamService;// 登陆服务

	@Resource
	private ICommService commService;

	@Resource
	private IPubParamService pubParamService;// 查询本平台信息

	/**
	 * 生成图片验证码
	 * 
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
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
		boolean isFixed = companyConfigService.isImgCodeFixed();
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
	 * 获取随即token
	 * 
	 * @param req
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("get_token")
	public HttpRespEnvelope getToken(HttpServletRequest request, CompanyBase companyBase) throws IOException
	{
		// 变量声明
		HttpRespEnvelope hre = null; // 返回界面封装对象

		try
		{
			// 获取token
			String token = commService.getRandomToken(companyBase.getCustId());
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
	 * 
	 * 依据互生号获取地区平台信息
	 * @param req
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("findMainInfoByResNo")
	public HttpRespEnvelope findMainInfoByResNo(HttpServletRequest request, String platEntResNo) throws IOException{
		try {
			AsEntMainInfo obj = this.commService.findMainInfoByResNo(platEntResNo);
			return new HttpRespEnvelope(obj);
		} catch (Exception e) {
			return new HttpRespEnvelope();
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
	public HttpRespEnvelope operatorLogin(HttpServletRequest request, @ModelAttribute AsOperatorLoginInfo loginInfo)
			throws IOException
	{
		HttpRespEnvelope hre = null;
		try
		{
			loginInfo.setCustType(HsResNoUtils.getCustTypeByHsResNo(loginInfo.getResNo()).toString());
			loginInfo.setChannelType(ChannelTypeEnum.WEB);
			AsOperatorLoginResult res = this.ucParamService.operatorLogin(loginInfo);
			hre = new HttpRespEnvelope(res);
		} catch (HsException e)
		{
			hre = handleModifyPasswordException(e);
		}
		return hre;
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
			changeRedisUtil.setExpireInSecond(key, companyConfigService.getImgCodeOverdueTime());
		}
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
			return new HttpRespEnvelope(commService.getLocalInfo());
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 获取国家列表
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 上午10:20:50
	 * @param request
	 * @return
	 * @throws IOException
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping("get_country_all")
	public HttpRespEnvelope getCountryAll(HttpServletRequest request) throws IOException
	{
		this.verifySecureToken(request);
		try
		{
			return new HttpRespEnvelope(commService.getCountryAll());
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 获取国家下的省列表
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 上午10:22:00
	 * @param request
	 * @return
	 * @throws IOException
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping("get_province_list")
	public HttpRespEnvelope getProvinceList(HttpServletRequest request) throws IOException
	{
		this.verifySecureToken(request);
		String countryNo = request.getParameter("countryNo");
		try
		{
			return new HttpRespEnvelope(commService.getProvinceByCountry(countryNo));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 获取省下的城市
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 上午10:31:49
	 * @param request
	 * @return
	 * @throws IOException
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping("get_city_list")
	public HttpRespEnvelope getCityList(HttpServletRequest request) throws IOException
	{
		this.verifySecureToken(request);
		String countryNo = request.getParameter("countryNo");
		String provinceNo = request.getParameter("provinceNo");
		try
		{
			return new HttpRespEnvelope(commService.getCityByProvince(countryNo, provinceNo));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 根据城市编号查询城市信息
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 上午11:25:45
	 * @param request
	 * @return
	 * @throws IOException
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping("get_city_by_id")
	public HttpRespEnvelope getCityById(HttpServletRequest request) throws IOException
	{
		this.verifySecureToken(request);
		String countryNo = request.getParameter("countryNo");
		String provinceNo = request.getParameter("provinceNo");
		String cityNo = request.getParameter("cityNo");
		try
		{
			return new HttpRespEnvelope(commService.getCityById(countryNo, provinceNo, cityNo));
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 获取银行列表
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年1月25日 上午10:32:44
	 * @param request
	 * @return
	 * @throws IOException
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping("get_bank_list")
	public HttpRespEnvelope getBankList(HttpServletRequest request) throws IOException
	{
		this.verifySecureToken(request);
		try
		{
			return new HttpRespEnvelope(commService.getBankAll());
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 根据国家省份查询下级城市
	 * 
	 * @param provinceNo国家代码
	 *            （可选参数，默认为本平台所在国家）
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findProvCity" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
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
					throw new HsException(ASRespCode.AS_GET_PALT_INFO_FAILED);
				}
				countryCode = localInfo.getCountryNo();
				countryName = localInfo.getCountryName();
			} else
			{
				Country country = this.lcsClient.getContryById(countryNo);
				if (country == null)
				{
					throw new HsException(ASRespCode.AS_GET_PALT_INFO_FAILED);
				}
				countryCode = country.getCountryNo();
				countryName = country.getCountryName();
			}
			// 获取省级城市列表
			List<ProvinceTree> list = this.lcsClient.queryProvinceTreeOfCountry(countryCode);
			if (list.isEmpty())
			{
				throw new HsException(ASRespCode.AS_GET_PALT_INFO_FAILED);
			}
			return new HttpRespEnvelope(new ProvCity(countryName, countryCode, list));
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
	@RequestMapping(value = { "/getRegionByCode" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
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
				throw new HsException(ASRespCode.AS_GET_REGION_BY_CODE_FAILED);
			}
			// 获取国家名称
			if (!StringUtils.isBlank(countryCode))
			{
				Country country = this.lcsClient.getContryById(countryCode);
				if (country == null)
				{
					throw new HsException(ASRespCode.AS_GET_REGION_BY_CODE_FAILED);
				}
				region.append(country.getCountryName()).append(linkStr);
			}
			// 获取省份名称
			if (!StringUtils.isBlank(provinceCode))
			{
				Province prov = this.lcsClient.getProvinceById(localInfo.getCountryNo(), provinceCode);
				if (prov == null)
				{
					throw new HsException(ASRespCode.AS_GET_REGION_BY_CODE_FAILED);
				}
				region.append(prov.getProvinceName()).append(linkStr);
			}
			// 获取城市名称
			if (!StringUtils.isBlank(cityCode))
			{
				City city = this.lcsClient.getCityById(localInfo.getCountryNo(), provinceCode, cityCode);
				if (city == null)
				{
					throw new HsException(ASRespCode.AS_GET_REGION_BY_CODE_FAILED);
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
	 * 地区信息连接
	 * 
	 * @Description:
	 * @author: likui
	 * @created: 2016年3月8日 下午8:05:51
	 * @param request
	 * @param countryCode
	 * @param provinceCode
	 * @param cityCode
	 * @param linkStr
	 * @return
	 * @return : HttpRespEnvelope
	 * @version V3.0.0
	 */
	@ResponseBody
	@RequestMapping(value = { "/getAreaSplitJoint" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope getAreaSplitJoint(HttpServletRequest request, String countryCode, String provinceCode,
			String cityCode)
	{
		try
		{
			// Token验证
			super.checkSecureToken(request);
			StringBuffer region = new StringBuffer();
			// 获取平台信息
			LocalInfo localInfo = this.pubParamService.findSystemInfo();
			if (localInfo == null)
			{
				throw new HsException(ASRespCode.AS_GET_REGION_BY_CODE_FAILED);
			}
			// 获取国家名称
			if (!StringUtils.isBlank(countryCode))
			{
				Country country = this.lcsClient.getContryById(countryCode);
				if (country == null)
				{
					throw new HsException(ASRespCode.AS_GET_REGION_BY_CODE_FAILED);
				}
				region.append(country.getCountryName());
			}
			// 获取省份名称
			if (!StringUtils.isBlank(provinceCode))
			{
				Province prov = this.lcsClient.getProvinceById(localInfo.getCountryNo(), provinceCode);
				if (prov == null)
				{
					throw new HsException(ASRespCode.AS_GET_REGION_BY_CODE_FAILED);
				}
				region.append(prov.getProvinceNameCn());
			}
			// 获取城市名称
			if (!StringUtils.isBlank(cityCode))
			{
				City city = this.lcsClient.getCityById(localInfo.getCountryNo(), provinceCode, cityCode);
				if (city == null)
				{
					throw new HsException(ASRespCode.AS_GET_REGION_BY_CODE_FAILED);
				}
				region.append(city.getCityName());
			}
			return new HttpRespEnvelope(region.toString());
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}

	/**
	 * 
	 * 方法名称：查询快捷支付银行列表 方法描述：查询快捷支付银行列表
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/findPayBankAll" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope findPayBankAll(HttpServletRequest request)
	{
		try
		{
			// Token验证
			super.checkSecureToken(request);
			// 获取快捷支付银行列表
			List<PayBank> list = this.lcsClient.queryPayBankAll();
			List<PayBank> debitCardList = new ArrayList<PayBank>();// 存入储蓄卡
			List<PayBank> creditCardList = new ArrayList<PayBank>();// 存入信用卡
			if (list != null)
			{
				for (PayBank bank : list)
				{
					// 支持储蓄卡的银行列表
					if (bank.isSupportDebit())
					{
						debitCardList.add(bank);
					}
					// 支持的信用卡银行列表
					if (bank.isSupportCredit())
					{
						creditCardList.add(bank);
					}
				}
			}
			Map<String, List<PayBank>> map = new HashMap<String, List<PayBank>>();
			map.put("CreditCard", creditCardList);
			map.put("DebitCard", debitCardList);
			return new HttpRespEnvelope(map);
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
	public HttpRespEnvelope findPermissionByCustidList(String custId,String entResType , HttpServletRequest request)
	{
		// 返回的结果
		HttpRespEnvelope hre = null;
		// 权限操蛋集合
		List<AsPermission> permissionList = null;
		try
		{
			permissionList = commService.findPermByCustId(PlatFormEnum.HSXT.name(), SubSysEnum.COMPANY.name(),null,(short) 0, custId,entResType);

			// 返回结果
			hre = new HttpRespEnvelope(permissionList);

		} catch (HsException he)
		{
			hre = new HttpRespEnvelope(he);
		}
		return hre;
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
			map.put("busList", this.commService.findBizDocList());
			map.put("picList", this.commService.findImageDocList());
			map.put("comList", this.commService.findNormalDocList());
			return new HttpRespEnvelope(map);
		} catch (HsException e)
		{
			return new HttpRespEnvelope(e);
		}
	}
	
	/**
  	 * 依据客户号获取操作员信息
  	 * @param operCustId 操作员客户号
  	 * @param request
  	 * @return
  	 */
    @ResponseBody
    @RequestMapping(value = { "/searchOperByCustId" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
  	public HttpRespEnvelope searchOperByCustId(String operCustId, HttpServletRequest request) {
  		try {
  			//执行查询
  			AsOperator asOperator = this.commService.searchOperByCustId(operCustId);
  			return new HttpRespEnvelope(asOperator);
  		} catch (HsException e) {
  			return new HttpRespEnvelope(e);
  		}
  	}

    /**
     * 验证验证码
     * 
     * @param custId
     * @param verificationCode
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = { "/verificationCode" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope verificationCode(String custId, String vaildCode, String codeType) throws HsException
      {
          // 获取验证码
          String vCode = changeRedisUtil.get(custId.trim() + "_" + codeType, String.class);
          // 判断为空
          if (StringUtils.isEmpty(vCode))
          {
              return new HttpRespEnvelope(new HsException(ASRespCode.VERIFICATION_CODE_INVALID));
          }
          // 判断相等
          if (!vaildCode.toUpperCase().equals(vCode.toUpperCase()))
          {
              return new HttpRespEnvelope(new HsException(ASRespCode.VERIFICATION_CODE_ERROR));
          }
          return new HttpRespEnvelope();
      }
	@Override
	protected IBaseService getEntityService()
	{
		return null;
	}

}
