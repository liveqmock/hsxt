/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.controllers.infomanage;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.gy.hsxt.access.web.aps.services.infomanage.InfoManageService;
import com.gy.hsxt.access.web.common.bean.HttpRespEnvelope;
import com.gy.hsxt.access.web.common.controller.BaseController;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.constant.AsConstants;
import com.gy.hsxt.common.utils.StringUtils;

public abstract class InfoManageBaseController extends BaseController<Object> {

    /**
     * 这里是为信息管理中特制的controller  其中实名认证、 积分活动、 成员企业资格注销、重要信息变更
     * 查询中查询要审批的结果集时  相应的conroller都继承该类，其效果类似与BaseController中的doList，
     * 只是这里针对的是以上提到的审批列表查询业务部分。
     * 
     * 根据默认的请求参数进行分页查询。 过滤条件，如：filterMap.put("search_id", 12)。
     * 可以为null,条件必须“search_”开头<br>
     * 排序条件，如：sortMap.put("sort_id", "desc"); asc为正序，desc为倒序。
     * 可以为null,条件必须“sort_”开头<br>
     * 回调函数：beforeDoList(...), afterDoList(...)
     * 
     * @param request
     *            当前的HttpServletRequest
     * @return ScrollResult<T>
     */
    @ResponseBody
    @RequestMapping(value = "/appr_list")
    public HttpRespEnvelope apprList(HttpServletRequest request, HttpServletResponse response) {
        // 声明变量
        Page page = null; // 分页对象
        PageData<?> pd = null;
        Integer no = null; // 当前页码
        Integer size = null; // 每页显示条数
        HttpRespEnvelope hre = null; // 返回界面数据封装对象

        // 提取客户端可能传送的过滤条件
        Map<String, Object> filterMap = WebUtils.getParametersStartingWith(request, "search_");

        // 如果支持排序则则添加相关代码
        Map<String, Object> sortMap = WebUtils.getParametersStartingWith(request, "sort_");

        // 获取客户端传递每页显示条数
        String pageSize = (String) request.getParameter("pageSize");

        // 获取客户端传递当前页码
        String curPage = (String) request.getParameter("curPage");

        // 设置当前页默认第一页
        if (StringUtils.isBlank(curPage))
        {
            no = AsConstants.PAGE_NO;
        }
        else
        {
            no = Integer.parseInt(curPage);
        }

        // 设置页显示行数
        if (StringUtils.isBlank(pageSize))
        {
            size = AsConstants.PAGE_SIZE;
        }
        else
        {
            size = Integer.parseInt(pageSize);
        }

        // 构造分页对象
        page = new Page(no, size);


        try
        {
            pd = this.getInfoManageService().queryApprResult(filterMap, sortMap, page);
        }
        catch (Exception e)
        {
            return new HttpRespEnvelope(e);
        }


        // 封装返回界面json字符串
        hre = new HttpRespEnvelope();
        if(pd != null && pd.getCount() > 0)
        {
            hre.setData(pd.getResult());
            hre.setTotalRows(pd.getCount());
        }
        hre.setCurPage(page.getCurPage());
        return hre;

    }
    
    /**
     * 获取实体服务类的实例
     * 
     * @return
     */
    protected abstract InfoManageService getInfoManageService();
}
