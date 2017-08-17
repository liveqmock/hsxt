/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.gpf.res.controller;

import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.res.bean.PlatInfo;
import com.gy.hsxt.gpf.res.interfaces.IPlatService;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.RespInfo;
import com.gy.hsxt.gpf.um.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("plat")
public class PlatController extends BaseController {

    @Resource
    private IPlatService platService;

    /**
     * 查询未初始化的所有平台
     * 
     * @return
     * @throws HsException
     */
    @RequestMapping(value = { "/unInitPlatInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<List<PlatInfo>> queryUnInitPlatInfo() throws HsException {
        List<PlatInfo> platInfos = platService.queryUnInitPlatInfo();
        return RespInfo.bulid(platInfos);
    }

    /**
     * 查询平台列表
     * 
     * @param platNo
     *            平台代码
     * @param entCustName
     *            平台名称
     * @param page
     *            分页
     * @return
     * @throws HsException
     */
    @RequestMapping(value = { "/platList" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public GridData<PlatInfo> platList(String platNo, String entCustName, Page page) throws HsException {
        return platService.queryPlatInfo(platNo, entCustName, page);
    }

    /**
     * 查询所有平台列表
     * 
     * @return
     * @throws HsException
     */
    @RequestMapping(value = { "/platListAll" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<List<PlatInfo>> platListAll() throws HsException {
        List<PlatInfo> platInfos = platService.platListAll();
        return RespInfo.bulid(platInfos);
    }

    /**
     * 添加平台公司信息
     * 
     * @param platInfo
     *            平台公司信息
     * @throws HsException
     */
    @RequestMapping(value = { "/addPlatInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<Boolean> addPlatInfo(PlatInfo platInfo) throws HsException {
        platService.addPlatInfo(platInfo);
        return RespInfo.bulid(true);
    }

    /**
     * 添加平台公司信息
     * 
     * @param platInfo
     *            平台公司信息
     * @throws HsException
     */
    @RequestMapping(value = { "/updatePlatInfo" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<Boolean> updatePlatInfo(PlatInfo platInfo) {
        platService.updatePlatInfo(platInfo);
        return RespInfo.bulid(true);
    }

    /**
     * 同步平台公司信息到用户中心
     * 
     * @param platNo
     *            平台代码
     * @throws HsException
     */
    @RequestMapping(value = { "/syncPlatToUc" }, method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public RespInfo<Boolean> syncPlatToUc(String platNo) {
        platService.syncPlatToUc(platNo);
        return RespInfo.bulid(true);
    }
}
