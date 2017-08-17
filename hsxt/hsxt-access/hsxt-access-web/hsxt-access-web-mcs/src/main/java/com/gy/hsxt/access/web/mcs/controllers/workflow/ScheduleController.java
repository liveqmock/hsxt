/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.mcs.controllers.workflow;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gy.hsi.lc.client.log4j.SystemLog;
import com.gy.hsxt.access.web.bean.MCSBase;
import com.gy.hsxt.access.web.bean.workflow.McsBizTypeAuthBean;
import com.gy.hsxt.access.web.bean.workflow.McsCustomScheduleOpt;
import com.gy.hsxt.access.web.bean.workflow.McsGroupBean;
import com.gy.hsxt.access.web.bean.workflow.McsGroupUpdateBean;
import com.gy.hsxt.access.web.bean.workflow.McsMembersScheduleBean;
import com.gy.hsxt.access.web.bean.workflow.McsScheduleBean;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.constant.ASRespCode;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.access.web.common.utils.RequestUtil;
import com.gy.hsxt.access.web.mcs.services.common.ExcelExport;
import com.gy.hsxt.access.web.mcs.services.workflow.IScheduleService;
import com.gy.hsxt.access.web.mcs.services.workflow.McsScheduleExprotDataInit;
import com.gy.hsxt.common.constant.RespCode;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.tm.bean.BizType;
import com.gy.hsxt.tm.bean.Group;

/***
 * 日程安排类
 * 
 * @Package: com.gy.hsxt.access.web.aps.controllers.workflow
 * @ClassName: ScheduleController
 * @Description: TODO
 * 
 * @author: wangjg
 * @date: 2015-12-18 下午3:56:48
 * @version V1.0
 */
@Controller
@RequestMapping("schedule")
public class ScheduleController extends BaseController {

    @Resource
    private IScheduleService iScheduleService;

    /**
     * 获取值班组列表
     * 
     * @param request
     * @param apsBase
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_group_list", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getGroupList(HttpServletRequest request, MCSBase mcsBase) {
        HttpRespEnvelope hre = null;
        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            // 2、获取列表
            List<Map<String, Object>> gList = iScheduleService.getGroupList(mcsBase);
            hre = new HttpRespEnvelope(gList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 获取值班组组员、值班计划
     * 
     * @param request
     * @param amsb
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_members_schedule", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getMembersSchedule(HttpServletRequest request, McsMembersScheduleBean amsb) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            // 2、验证数据
            amsb.checkData();
            // 3、获取组员、值班计划
            Map<String, Object> ret = iScheduleService.getMembersSchedule(amsb);
            hre = new HttpRespEnvelope(ret);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 查询值班组信息
     * 
     * @param request
     * @param agub
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_groupInfo", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getGroupInfo(HttpServletRequest request, McsMembersScheduleBean amsb) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证数据
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { amsb.getGroupId(), RespCode.APS_GROUP_ID_NOT_NULL });

            // 3、获取列表
            List<Group> gList = iScheduleService.getGroupInfo(amsb);
            hre = new HttpRespEnvelope(gList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 保存值班组
     * 
     * @param request
     * @param ag
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "group_add", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope groupAdd(HttpServletRequest request, McsGroupBean ag, MCSBase mcsBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证数据
            ag.checkAddData();

            // 3、保存值班组
            iScheduleService.groupAdd(ag, mcsBase);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 修改值班组
     * 
     * @param request
     * @param ag
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "group_update", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope groupUpdate(HttpServletRequest request, McsGroupBean ag, MCSBase mcsBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证数据
            ag.checkUpdateData();

            // 3、保存值班组
            iScheduleService.groupUpdate(ag, mcsBase);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 新增值班员业务节点
     * 
     * @param request
     * @param abtab
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "add_biztype", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope addBizType(HttpServletRequest request, McsBizTypeAuthBean abtab, MCSBase mcsBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证数据
            abtab.checkData();

            // 3、值班员新增业务节点
            iScheduleService.addBizType(abtab, mcsBase);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 删除值班员业务节点
     * 
     * @param request
     * @param abtab
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "delete_biztype", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope deleteBizType(HttpServletRequest request, McsBizTypeAuthBean abtab, MCSBase mcsBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证数据
            abtab.checkData();

            // 3、删除
            iScheduleService.deleteBizType(abtab, mcsBase);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 值班组开启或关闭
     * 
     * @param request
     * @param agub
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "udpate_group_status", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope udpateGroupOpenedStatus(HttpServletRequest request, McsGroupUpdateBean agub, MCSBase mcsBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证数据
            agub.checkOpenStatusData();

            // 3、更新值班组状态
            iScheduleService.udpateGroupOpenedStatus(agub, mcsBase);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 移除值班组组员
     * 
     * @param request
     * @param groupId
     * @param optCustId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "remove_operator", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope removeOperator(HttpServletRequest request, String groupId, String optCustId, MCSBase mcsBase) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证数据
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { groupId, RespCode.APS_GROUP_ID_NOT_NULL }, new Object[] {
                    optCustId, RespCode.APS_OPTCUSTID_NOT_NULL });

            // 3、更新值班组状态
            iScheduleService.removeOperator(groupId, optCustId, mcsBase);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 获取值班员明细
     * 
     * @param request
     * @param optCustId
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "get_attendant_info", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getAttendantInfo(HttpServletRequest request, String optCustId, String groupId,
            String scheduleId) {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证数据
            RequestUtil.verifyParamsIsNotEmpty(
                    new Object[] { optCustId, RespCode.APS_OPTCUSTID_NOT_NULL },
                    new Object[] { groupId, RespCode.APS_GROUP_ID_NOT_NULL }
                    );

            // 3、获取列表
            Map<String, Object> coList = iScheduleService.getAttendantInfo(optCustId,groupId, scheduleId);
            hre = new HttpRespEnvelope(coList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 保存值班计划
     * 
     * @param schedule
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#saveSchedule(com.gy.hsxt.tm.bean.Schedule)
     */
    @ResponseBody
    @RequestMapping(value = "save_schedule", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope saveSchedule(HttpServletRequest request, McsScheduleBean asb, MCSBase mcsBase) throws HsException {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证数据
            asb.checkData();

            // 3、保存值班计划
            Map<String, String> retMap = iScheduleService.saveSchedule(asb, mcsBase);
            hre = new HttpRespEnvelope(retMap);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 执行值班计划
     * 
     * @param agub
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#executeSchedule(com.gy.hsxt.access.web.bean.workflow.McsGroupUpdateBean)
     */
    @ResponseBody
    @RequestMapping(value = "execute_schedule", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope executeSchedule(HttpServletRequest request, McsCustomScheduleOpt acso, MCSBase mcsBase) throws HsException {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证数据
            acso.checkData();

            // 3、执行值班计划
            Map<String, String> retMap = iScheduleService.executeSchedule(acso, mcsBase);
            hre = new HttpRespEnvelope(retMap);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 暂停值班组计划
     * 
     * @param agub
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.workflow.IScheduleService#pauseSchedule(com.gy.hsxt.access.web.bean.workflow.McsGroupUpdateBean)
     */
    @ResponseBody
    @RequestMapping(value = "pause_schedule", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope pauseSchedule(HttpServletRequest request, McsGroupUpdateBean agub, MCSBase mcsBase) throws HsException {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、验证数据
            agub.checkScheduleData();

            // 3、暂停值班组计划
            iScheduleService.pauseSchedule(agub, mcsBase);
            hre = new HttpRespEnvelope();
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 获取企业员工
     * 
     * @param request
     * @param apsBase
     * @return
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = "get_ent_oper_list", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getEntOperList(HttpServletRequest request, MCSBase mcsBase) throws HsException {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);

            // 2、获取企业员工
            List<Map<String, String>> aoList = iScheduleService.getListOperByEntCustId(mcsBase);
            hre = new HttpRespEnvelope(aoList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    /**
     * 导出值班组计划
     * 
     * @param request
     * @param response
     * @param amsb
     * @throws IOException
     */
    @RequestMapping(value = "export")
    public void export(HttpServletRequest request, HttpServletResponse response, McsMembersScheduleBean amsb)
            throws IOException {
        response.setContentType("application/vnd.ms-excel"); // 导出格式设置
        OutputStream fOut = null;

        try
        {
            // 导出数据验证
            amsb.checkExportData();

            // 进行转码，使其支持中文文件名
            String fileName = java.net.URLEncoder.encode(amsb.getYear() + "年" + amsb.getMonth() + "月"
                    + amsb.getGroupName() + "排班表", "UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xls");

            // 获取xls导出数据
            McsScheduleExprotDataInit asedi = iScheduleService.export(amsb);
            HSSFWorkbook hssfwb = ExcelExport.createExcel(amsb.getGroupName(), asedi.columnNum, asedi.cellTitle,
                    asedi.content);

            // xls个性化
            new ScheduleExportPersonalized(asedi, hssfwb);

            fOut = response.getOutputStream();
            hssfwb.write(fOut);
        }
        catch (HsException e)
        {
            SystemLog.error("workflow", "export", "导出值班组计划异常", e);

            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(RespCode.APS_SCHEDULE_DOWNLOAD_FAIL.getDesc());
        }
        finally
        {
            try
            {
                if (fOut != null)
                {
                    fOut.flush();
                    fOut.close();
                }
            }
            catch (IOException e)
            {
                SystemLog.error("workflow", "export", "导出值班组计划关闭流异常", e);

                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(RespCode.APS_SCHEDULE_DOWNLOAD_FAIL.getDesc());
            }
        }
    }
    
    /**
     * 获取操作员对应的业务类型
     * @param request
     * @param apsBase
     * @return
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = "get_biz_type_list", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope getBizTypeList(HttpServletRequest request, String optCustId) throws HsException {
        HttpRespEnvelope hre = null;

        try
        {
            // 1、验证token
            super.verifySecureToken(request);
            // 2、有效数据验证
            RequestUtil.verifyParamsIsNotEmpty(new Object[] { optCustId, ASRespCode.AS_PARAM_INVALID });
            // 3、获取类型
            List<BizType> btList = iScheduleService.getBizTypeList(optCustId);
            hre = new HttpRespEnvelope(btList);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }
    
    
    /**
     * 判断操作员是否为值班主任
     * @param request
     * @param apsBase
     * @return
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping(value = "is_chief", method = { RequestMethod.GET, RequestMethod.POST }, produces = "application/json;charset=UTF-8")
    public HttpRespEnvelope isChief(HttpServletRequest request, MCSBase mcsBase) throws HsException {
        HttpRespEnvelope hre = null;

        try
        {
            // 验证token
            super.verifySecureToken(request);
          
            // 获取操作员是否为值班主任
            boolean isChief = iScheduleService.isChief(mcsBase);
            hre = new HttpRespEnvelope(isChief);
        }
        catch (HsException e)
        {
            hre = new HttpRespEnvelope(e);
        }

        return hre;
    }

    @Override
    protected IBaseService getEntityService() {
        // TODO Auto-generated method stub
        return null;
    }
}
