/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

package com.gy.hsxt.access.web.os.bp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.bp.api.IBusinessPosUpgradeInfoService;
import com.gy.hsxt.bp.bean.BusinessPosUpgrade;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * POS机升级信息控制类
 * @Package: com.gy.hsxt.access.web.os.bp.controller  
 * @ClassName: PosUpgradeController 
 * @Description: TODO
 *
 * @author: weixz 
 * @date: 2016-4-12 上午10:39:03 
 * @version V1.0
 */
@Controller
@RequestMapping("/posUpgradeController")
public class PosUpgradeController {

    @Autowired
    private IBusinessPosUpgradeInfoService posUpgradeInfoService;

    /**
     * 
     * 方法名称：新增POS机升级信息
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/addPosUpgradeVerInfo")
    public HttpRespEnvelope addPosUpgradeVerInfo(BusinessPosUpgrade businessPosUpgrade,HttpServletRequest request) {
        HttpRespEnvelope hre = new HttpRespEnvelope();
        String posFileKey = request.getParameter("posFileKey");
        String fileHttpUrl = businessPosUpgrade.getFileHttpUrl() + posFileKey + ".so";
        businessPosUpgrade.setFileHttpUrl(fileHttpUrl);
        try {
            posUpgradeInfoService.addPosUpgradeVerInfo(businessPosUpgrade);
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }

    /**
     * 
     * 方法名称：分页查询POS机升级信息列表
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/searchPosUpPage")
    public HttpRespEnvelope searchPosUpPage(BusinessPosUpgrade businessPosUpgrade,HttpServletRequest request) {
        HttpRespEnvelope hre = new HttpRespEnvelope();
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));// 每页大小
        int curPage = Integer.parseInt(request.getParameter("curPage"));// 当前页
        try {
            Page page = new Page(curPage,pageSize);// 设置分页信息
            PageData<BusinessPosUpgrade> pd =  posUpgradeInfoService.searchPosUpgradeVerInfoPage(businessPosUpgrade, page);
            if(pd != null){
               hre.setData(pd.getResult());
               hre.setTotalRows(pd.getCount());
               hre.setCurPage(curPage);
           }
        } catch (HsException e) {
            hre = new HttpRespEnvelope(e);
        }
        return hre;
    }
    
    /**
    * 方法名称：更新POS机升级信息列表
    * @param request
    * @return
    */
   @ResponseBody
   @RequestMapping(value = "/updatePosUpInfo")
   public HttpRespEnvelope updatePosUpInfo(BusinessPosUpgrade businessPosUpgrade,HttpServletRequest request) {
       HttpRespEnvelope hre = new HttpRespEnvelope();
       try {
           posUpgradeInfoService.updatePosUpInfo(businessPosUpgrade);
       } catch (HsException e) {
           hre = new HttpRespEnvelope(e);
       }
       return hre;
   }
    
    
   
}
