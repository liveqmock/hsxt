/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.controller;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.res.bean.PlatMent;
import com.gy.hsxt.gpf.res.bean.PlatMents;
import com.gy.hsxt.gpf.res.interfaces.IManageService;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.RespInfo;
import com.gy.hsxt.gpf.um.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("manage")
public class ManageController extends BaseController {

    @Resource
    private IManageService manageService;

    /**
     * 查询管理公司列表
     * 
     * @param entResNo
     *            管理公司互生号
     * @param entCustName
     *            管理公司名称
     * @param page
     *            分页
     * @return
     * @throws HsException
     */
    @RequestMapping(value = { "/manageEntList" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public GridData<PlatMent> manageEntList(String entResNo, String entCustName, Page page) throws HsException {
        return manageService.queryManageList(entResNo, entCustName, page);
    }

    /**
     * 添加管理公司
     * 
     * @param platMents
     *            管理公司信息列表
     * @return
     */
    @RequestMapping(value = { "/addPlatMent" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<Boolean> addPlatMent(PlatMents platMents) {
        manageService.addPlatMents(platMents);
        return RespInfo.bulid(true);
    }

    /**
     * 同步管理公司信息到用户中心
     * 
     * @param platNo
     *            平台代码
     * @param entResNo
     *            管理公司互生号
     * @return
     */
    @RequestMapping(value = { "/syncManageToUc" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<Boolean> syncManageToUc(String platNo, String entResNo) {
        manageService.syncManageToUc(platNo, entResNo);
        return RespInfo.bulid(true);
    }

    /**
     * 同步管理公司信息到地区平台
     * 
     * @param platNo
     *            平台代码
     * @param entResNo
     *            管理公司互生号
     * @return
     */
    @RequestMapping(value = { "/syncManageToBs" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<Boolean> syncManageToBs(String platNo, String entResNo) {
        manageService.syncManageToBs(platNo, entResNo);
        return RespInfo.bulid(true);
    }
}
