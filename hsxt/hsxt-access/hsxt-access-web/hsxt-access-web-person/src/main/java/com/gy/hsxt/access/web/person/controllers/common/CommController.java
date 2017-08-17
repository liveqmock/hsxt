package com.gy.hsxt.access.web.person.controllers.common;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.redis.client.api.RedisUtil;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.person.bean.ProvCity;
import com.gy.hsxt.access.web.person.services.common.IPubParamService;
import com.gy.hsxt.access.web.person.services.common.PersonConfigService;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
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
    private RedisUtil<String> changeRedisUtil;
    
    @Resource
    private PersonConfigService personConfigSevice;

    @Autowired
    private LcsClient lcsClient; // 账户余额查询服务类

    @Resource
    private IPubParamService pubParamService;// 查询本平台信息

    @RequestMapping("/generateSecuritCode")
    public void getCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        int width = 80;// 定义图片的width
        int height = 28;// 定义图片的height
        int fontHeight = 20; //字体
        int xx = 10;  //x坐标
        int codeY = 23;//y坐标
        
        // 验证码
        String numberCode = RandomCodeUtils.getNumberCode();
        boolean isFixed = personConfigSevice.isImgCodeFixed();
		if (isFixed) {
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
    void setCodeCache(HttpServletRequest request, String code)
    {
        // 操作员编号
        String custId = request.getParameter("custId");
        // 验证码类型
        String type = request.getParameter("type");

        if (!StringUtils.isEmpty(custId) && !StringUtils.isEmpty(type))
        {
            String key = custId.trim() + "_" + type.trim();
            // 设置缓存
            changeRedisUtil.add(key, code);
            changeRedisUtil.setExpireInSecond(key, personConfigSevice.imgCodeOverdueTime());
        }
    }

    /**
     * 获取随即token
     * 
     * @param req
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = { "/find_card_holder_token" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findCardHolderToken(String custId, HttpServletRequest request) throws IOException {
        // 变量声明
        HttpRespEnvelope hre = null; // 返回界面封装对象

        try
        {
//            // Token验证
//            super.checkSecureToken(request);
            
            // 获取token
            String token = pubParamService.getRandomToken(custId);
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
     * 获取所有国家信息
     * @param custId
     * @param token
     * @param request
     * @return
     *//*
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

    *//**
     * 根据国家代码获取省份列表
     * @param custId
     * @param token
     * @param request
     * @return
     *//*
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

    *//**
     * 根据国家省份查询下级城市
     * @param countryNo
     * @param provinceNo
     * @param request
     * @return
     *//*
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
    *//**
     * 查询所有的银行列表
     * @param custId
     * @param token
     * @param request
     * @return
     *//*
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
    }*/
 /*   
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
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 根据国家代码获取国家信息
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
            hre = new HttpRespEnvelope(e);
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
            if(StringUtils.isEmpty(provinceNo))
            {
            	provinceNo = "0";
            }
            // 根据国家省份查询下面的城市
            list = this.lcsClient.queryCityByParent(countryNo, provinceNo);

            hre = new HttpRespEnvelope(list);

        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
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
            hre = new HttpRespEnvelope(e);
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
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    /**
     * 根据国家省份查询下级城市
     * @param provinceNo国家代码（可选参数，默认为本平台所在国家）
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/findProvCity" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findProvCity(String countryNo) {
        try {
            String countryCode;//国家代码
            String countryName;//国家名称
            if(StringUtils.isBlank(countryNo)){
                LocalInfo localInfo = this.pubParamService.findSystemInfo();
                if(localInfo == null){
                    throw new HsException(RespCode.AS_GET_PALT_INFO_FAILED);
                }
                countryCode = localInfo.getCountryNo();
                countryName = localInfo.getCountryName();
            }else{
                Country country = this.lcsClient.getContryById(countryNo);
                if(country == null){
                    throw new HsException(RespCode.AS_GET_PALT_INFO_FAILED);
                }
                countryCode = country.getCountryNo();
                countryName = country.getCountryName();
            }
            //获取省级城市列表
			List<ProvinceTree> list = this.lcsClient.queryProvinceTreeOfCountry(countryCode);
			if(list.isEmpty()){
				throw new HsException(RespCode.AS_GET_PALT_INFO_FAILED);
			}
			return new HttpRespEnvelope(new ProvCity(countryName, countryCode, list));
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
        
    }
    
    /**
     * 
     * 方法名称：拼接地区名称
     * 方法描述：拼接地区名称
     * @param request HttpServletRequest对象
     * @param countryCode 国家代码
     * @param provinceCode 省份代码
     * @param cityCode 城市代码
     * @param linkStr 连接字符
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/getRegionByCode" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getRegionByCode(HttpServletRequest request, String countryCode, String provinceCode, String cityCode, String linkStr) {
        try {
            //Token验证
            super.checkSecureToken(request);
            StringBuffer region = new StringBuffer();
            linkStr = !StringUtils.isBlank(linkStr)?linkStr:"";
            //获取平台信息
            LocalInfo localInfo = this.pubParamService.findSystemInfo();
            if(localInfo == null){
                throw new HsException(RespCode.AS_GET_REGION_BY_CODE_FAILED);
            }
            //获取国家名称
            if(!StringUtils.isBlank(countryCode)){
                Country country = this.lcsClient.getContryById(countryCode);
                if(country == null){
                    throw new HsException(RespCode.AS_GET_REGION_BY_CODE_FAILED);
                }
                region.append(country.getCountryName()).append(linkStr);
            }
            //获取省份名称
            if(!StringUtils.isBlank(provinceCode)){
                Province prov = this.lcsClient.getProvinceById(localInfo.getCountryNo(), provinceCode);
                if(prov == null){
                    throw new HsException(RespCode.AS_GET_REGION_BY_CODE_FAILED);
                }
                region.append(prov.getProvinceName()).append(linkStr);
            }
            //获取城市名称
            if(!StringUtils.isBlank(cityCode)){
                City city = this.lcsClient.getCityById(localInfo.getCountryNo(), provinceCode, cityCode);
                if(city == null){
                    throw new HsException(RespCode.AS_GET_REGION_BY_CODE_FAILED);
                }
                region.append(city.getCityName()).append(linkStr);
            }
            return new HttpRespEnvelope(region.toString());
        } catch (HsException e) {
            return new HttpRespEnvelope(e);
        }
    }
    
    /**
     * 
     * 方法名称：查询快捷支付银行列表
     * 方法描述：查询快捷支付银行列表
     * @param request HttpServletRequest对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "/findPayBankAll" }, method = { RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope findPayBankAll(HttpServletRequest request) {
        try {
            //Token验证
            super.checkSecureToken(request);
            //获取快捷支付银行列表
            List<PayBank> list = this.lcsClient.queryPayBankAll();
            List<PayBank> debitCardList = new ArrayList<PayBank>();//存入储蓄卡
            List<PayBank> creditCardList = new ArrayList<PayBank>();//存入信用卡
            if(list != null){
            	for (PayBank bank : list){
                    // 支持储蓄卡的银行列表
                    if (bank.isSupportDebit()){
                    	debitCardList.add(bank);
                    }
                    // 支持的信用卡银行列表
                    if (bank.isSupportCredit()){
                    	creditCardList.add(bank);
                    }
                }
            }
            Map<String, List<PayBank>> map = new HashMap<String, List<PayBank>>();
            map.put("CreditCard", creditCardList);
            map.put("DebitCard", debitCardList);
            return new HttpRespEnvelope(map);
        } catch (HsException e) {
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
        	map.put("busList", this.pubParamService.findBizDocList());
        	map.put("picList", this.pubParamService.findImageDocList());
        	map.put("comList", this.pubParamService.findNormalDocList());
        	return new HttpRespEnvelope(map);
        }catch (HsException e){
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
