package com.gy.hsxt.access.web.scs.controllers.common;

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
import com.gy.hsxt.access.web.bean.SCSBase;
import com.gy.hsxt.access.web.bean.cache.ProvCity;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.scs.services.common.ICommService;
import com.gy.hsxt.access.web.scs.services.common.IPubParamService;
import com.gy.hsxt.access.web.scs.services.common.SCSConfigService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.common.utils.HsResNoUtils;
import com.gy.hsxt.common.utils.RandomCodeUtils;
import com.gy.hsxt.common.utils.StringUtils;
import com.gy.hsxt.lcs.bean.Bank;
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
import com.gy.hsxt.uc.as.bean.common.AsConsumerLoginInfo;
import com.gy.hsxt.uc.as.bean.common.AsOperatorLoginInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;
import com.gy.hsxt.uc.as.bean.enumerate.ChannelTypeEnum;
import com.gy.hsxt.uc.as.bean.operator.AsOperator;
import com.gy.hsxt.uc.as.bean.result.AsCardHolderLoginResult;
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
@SuppressWarnings("rawtypes")
@Controller
@RequestMapping("commController")
public class CommController extends BaseController {

    @Autowired
    private LcsClient lcsClient; // 账户余额查询服务类

    @Resource
    private ICommService commService;

    @Resource
    private IUCLoginService ucLoginService;

    @Resource
    private IPubParamService pubParamService;// 查询本平台信息

    @Resource
    private RedisUtil<String> changeRedisUtil;
    @Resource
    private SCSConfigService scsConfigService;
    /**
     * 生成图片效验码
     * 
     * @param req
     * @param resp
     * @throws IOException
     */
    @RequestMapping("/generateSecuritCode")
    public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int width = 80;// 定义图片的width
        int height = 28;// 定义图片的height
        int fontHeight = 20; // 字体
        int xx = 10; // x坐标
        int codeY = 23;// y坐标

        // 得到随机产生的验证码数字。
        String code = RandomCodeUtils.getNumberCode();
		boolean isFixed = scsConfigService.isImgCodeFixed();
		if (isFixed) {
			code = "1111";
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
        // 用颜色将验证码绘制到图像中。
        gd.setColor(Color.BLACK);
        gd.drawString(code, xx, codeY);
        // 设置redis缓存
        this.setCodeCache(req, code);
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
    private void setCodeCache(HttpServletRequest request, String code) {
        // 操作员编号
        String custId = request.getParameter("custId");
        // 验证码类型
        String type = request.getParameter("type");

        if (!StringUtils.isEmpty(custId) && !StringUtils.isEmpty(type))
        {
            String key = custId + "_" + type;
            // 设置缓存
            changeRedisUtil.add(key, code);
            changeRedisUtil.setExpireInSecond(key, scsConfigService.imgCodeOverdueTime());
        }
    }

    /**
     * 获取随机token
     * 
     * @param req
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("get_token")
    public HttpRespEnvelope getToken(HttpServletRequest request, SCSBase scsBase) throws IOException {
        // 变量声明
        HttpRespEnvelope hre = null; // 返回界面封装对象

        try
        {
            // 获取token
            String token = commService.getRandomToken(scsBase.getCustId());
            // 封装显示结果
            hre = new HttpRespEnvelope(token);
        }
        catch (HsException e)
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
            throws IOException {
    	HttpRespEnvelope hre = null;
        try
        {
            loginInfo.setChannelType(ChannelTypeEnum.WEB);
            loginInfo.setCustType(HsResNoUtils.getCustTypeByHsResNo(loginInfo.getResNo()).toString());
            AsOperatorLoginResult res = this.ucLoginService.operatorLogin(loginInfo);
            hre = new HttpRespEnvelope(res);
        }
        catch (HsException e)
        {
        	hre = handleModifyPasswordException(e);
        }
        return hre;
    }
    
    private HttpRespEnvelope handleModifyPasswordException(HsException e){
    	HttpRespEnvelope hre = new HttpRespEnvelope();
    	int errorCode = e.getErrorCode();
//    	String errorMsg = e.getMessage();
//    	String funName = "handleModifyPasswordException";
//    	StringBuffer msg = new StringBuffer();
//    	msg.append("处理修改登录密码Exception时报错,传入参数信息  e["+JSON.toJSONString(e)+"] "+NEWLINE);
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
     * 获取所有国家信息
     * 
     * @param custId
     * @param token
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/find_contry_all" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findContryAll(String custId, String token, HttpServletRequest request) {

        // 变量声明
        List<Country> list = null;
        HttpRespEnvelope hre = null;

        // 执行查询方法
        try
        {
            // Token验证
            super.checkSecureToken(request);

            list = this.lcsClient.findContryAll();

            hre = new HttpRespEnvelope(list);

        }
        catch (HsException e)
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
    @RequestMapping(value = { "/find_province_by_parent" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findProvinceByParent(String custId, String countryNo, String token,
            HttpServletRequest request) {

        // 变量声明
        List<Province> list = null;
        HttpRespEnvelope hre = null;

        // 执行查询方法
        try
        {
            // Token验证
            super.checkSecureToken(request);

            list = this.lcsClient.queryProvinceByParent(countryNo);

            hre = new HttpRespEnvelope(list);

        }
        catch (HsException e)
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
    @RequestMapping(value = { "/find_city_by_parent" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findCityByParent(String countryNo, String provinceNo, HttpServletRequest request) {

        // 变量声明
        List<City> list = null;
        HttpRespEnvelope hre = null;

        // 执行查询方法
        try
        {
            // Token验证
            super.checkSecureToken(request);

            // 根据国家省份查询下面的城市
            list = this.lcsClient.queryCityByParent(countryNo, provinceNo);

            hre = new HttpRespEnvelope(list);

        }
        catch (HsException e)
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
    @RequestMapping(value = { "/findProvCity" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findProvCity(String countryNo) {
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
            }
            else
            {
                Country country = this.lcsClient.getContryById(countryNo);
                if (country == null)
                {
                    throw new HsException(RespCode.AS_GET_PALT_INFO_FAILED);
                }
                countryCode = country.getCountryNo();
                countryName = country.getCountryName();
            }
            // 获取省级城市列表
            List<ProvinceTree> list = this.lcsClient.queryProvinceTreeOfCountry(countryCode);
            if (list.isEmpty())
            {
                throw new HsException(RespCode.AS_GET_PALT_INFO_FAILED);
            }
            return new HttpRespEnvelope(new ProvCity(countryName, countryCode, list));
        }
        catch (HsException e)
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
    @RequestMapping(value = { "/findBankAll" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findBankAll(String custId, String countryNo, String token, HttpServletRequest request) {

        // 变量声明
        List<Bank> list = null;
        HttpRespEnvelope hre = null;

        // 执行查询方法
        try
        {
            // Token验证
            super.checkSecureToken(request);

            list = this.lcsClient.queryBankAll();

            hre = new HttpRespEnvelope(list);

        }
        catch (HsException e)
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
    @RequestMapping(value = { "/findSystemInfo" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findSystemInfo(HttpServletRequest request) {

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

        }
        catch (HsException e)
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
    public HttpRespEnvelope verifyDoublePwd(HttpServletRequest request) throws IOException {
        try
        {
            String userName = request.getParameter("userName");// 用户名称
            String passWord = request.getParameter("passWord");// 登陆密码
            String checkType = request.getParameter("checkType");// 校验类型
            String randomToken = request.getParameter("randomToken");// 校验类型
            // 非空验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { userName, RespCode.AS_VERIFY_USERNAME_INVALID },
                    new Object[] { passWord, RespCode.AS_VERIFY_PASSWORD_INVALID }, new Object[] { randomToken,
                            RespCode.AS_VERIFY_TOKEN_INVALID }, new Object[] { checkType,
                            RespCode.AS_VERIFY_CHECKTYPE_INVALID });
            // 临时挡板--需要调用用户中心接口进行校验
            if ("admin".equals(userName))
            {
                return new HttpRespEnvelope("46186000000164123662798848");
            }
            else
            {
                throw new HsException(RespCode.AS_VERIFY_LOGIN_INVALID);
            }

        }
        catch (HsException e)
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
            String cityCode, String linkStr) {
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
                Country country = this.lcsClient.getContryById(countryCode);
                if (country == null)
                {
                    throw new HsException(RespCode.AS_GET_REGION_BY_CODE_FAILED);
                }
                region.append(country.getCountryName()).append(linkStr);
            }
            // 获取省份名称
            if (!StringUtils.isBlank(provinceCode))
            {
                Province prov = this.lcsClient.getProvinceById(localInfo.getCountryNo(), provinceCode);
                if (prov == null)
                {
                    throw new HsException(RespCode.AS_GET_REGION_BY_CODE_FAILED);
                }
                region.append(prov.getProvinceName()).append(linkStr);
            }
            // 获取城市名称
            if (!StringUtils.isBlank(cityCode))
            {
                City city = this.lcsClient.getCityById(localInfo.getCountryNo(), provinceCode, cityCode);
                if (city == null)
                {
                    throw new HsException(RespCode.AS_GET_REGION_BY_CODE_FAILED);
                }
                region.append(city.getCityName()).append(linkStr);
            }
            return new HttpRespEnvelope(region.toString());
        }
        catch (HsException e)
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
    public HttpRespEnvelope findPayBankAll(HttpServletRequest request) {
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
        }
        catch (HsException e)
        {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 获取示例图片、常用业务文档、办理业务文档列表
     * @param request
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("findDocList")
    public HttpRespEnvelope findDocList(HttpServletRequest request) throws IOException {
        try{
        	Map<String, Object> map = new HashMap<String, Object>();
        	map.put("busList", this.commService.findBizDocList());
        	map.put("picList", this.commService.findImageDocList());
        	map.put("comList", this.commService.findNormalDocList());
        	return new HttpRespEnvelope(map);
        }catch (HsException e){
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
  	public HttpRespEnvelope findPermissionByCustidList(String custId,HttpServletRequest request) {
  		//返回的结果
  		HttpRespEnvelope hre = null;
  		//权限集合
  		List<AsPermission> permissionList = null;
  		try {
  			
  			permissionList = commService.findPermByCustId(PlatFormEnum.HSXT.name(), SubSysEnum.SCS.name(),(short) 0, custId);
  			
  			//返回结果
  			hre = new HttpRespEnvelope(permissionList);
  			
  		} catch (HsException he) {
  			hre = new HttpRespEnvelope(he);
  		}
  		return hre;
  	}
  	
  	/**
  	 * 获取所有的菜单信息
  	 * 
  	 * @param userName
  	 *            用户名
  	 * @param userPwd
  	 *            密码
  	 * @param loginCode
  	 *            验证码
  	 * @return
  	 */
  	@RequestMapping(value = "/findPerontIdByPermission", method = RequestMethod.POST)
  	@ResponseBody
  	public HttpRespEnvelope findPerontIdByPermission(String parentId,String custId,HttpServletRequest request) {
  		//变量声明
  		HttpRespEnvelope hre = null;				//返回结果
  		List<AsPermission> permissionList = null;	//权限集合
  		try {
  			
  			//执行查询
  			permissionList = commService.findPerontIdByPermission(PlatFormEnum.HSXT.name(), SubSysEnum.SCS.name(), (short) 0, parentId,custId);
  			
  			hre = new HttpRespEnvelope(permissionList);
  		} catch (HsException e) {
  			hre = new HttpRespEnvelope(e);
  		}
  		
  		return hre;
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
            AsOperator operator = new AsOperator();
            operator.setUserName(operCustId);
            return new HttpRespEnvelope(operator);
  		}
  	}
    
    /**
	 * 模拟消费者登陆--临时
	 * 
	 * @param custId
	 *            客户号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = { "/cardHolderLogin" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
	public HttpRespEnvelope cardHolderLogin(AsConsumerLoginInfo loginInfo, HttpServletRequest request) {
		try {
			loginInfo.setChannelType(ChannelTypeEnum.WEB);
			loginInfo.setLoginIp("127.0.1.1");
			// 获取用户手机号
			AsCardHolderLoginResult cardHolderLoginResult = ucLoginService.cardHolderLogin(loginInfo);
			return new HttpRespEnvelope(cardHolderLoginResult);
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
    protected IBaseService getEntityService() {
        return null;
    }

}
