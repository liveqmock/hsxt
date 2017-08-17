package com.gy.hsxt.gpf.gcs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.gpf.gcs.bean.Country;
import com.gy.hsxt.gpf.gcs.common.PageContext;
import com.gy.hsxt.gpf.gcs.interfaces.ICountryService;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.controller
 * 
 *  File Name       : CountryController.java
 * 
 *  Creation Date   : 2015-7-3
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 国家代码管理控制器类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Controller
@RequestMapping("/")
public class CountryController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Resource(name = "countryService")
    private ICountryService countryService;

    /**
     * 管理页面初始化
     */
    @RequestMapping(value = "/country/manage")
    public void manage() {

    }

    /**
     * 获取数据分页列表
     * 
     * @param country实体类
     *            必须，非null
     * @param pageSize每页显示多少条
     *            必须，非null
     * @param pageNo当前页码
     *            必须，非null
     * 
     * @return 返回JSON,数据为空返回空List<Country>，异常返回null
     */
    @RequestMapping(value = "/country/managePost")
    @ResponseBody
    public String managePost(Country country, int pageSize, int pageNo) {
        try
        {
            // 创建分页对象
            PageContext page = PageContext.getContext();
            page.setPageSize(pageSize);
            page.setPageNo(pageNo);
            // 获取分页后的数据
            List<Country> list = countryService.getCountryListPage(country);
            int totalCount = page.getTotalResults();
            Map<String, Object> mp = new HashMap<>();
            mp.put("totalCount", totalCount);
            mp.put("TotalPages", page.getTotalPages());
            mp.put("list", list);
            // mp转换成json
            return JSON.toJSONString(mp);
        }
        catch (Exception e)
        {
            LOG.error("获取数据分页列表异常：", e);
        }
        return null;
    }

    /**
     * 获取下拉菜单列表
     * 
     * @return 返回List<Country>,数据为空返回空List<Country>，异常返回null
     */
    @RequestMapping(value = "/country/getCountryDropdownmenu")
    @ResponseBody
    public List<Country> getCountryDropdownmenu() {
        try
        {
            List<Country> list = countryService.getCountryAll();
            return list;
        }
        catch (Exception e)
        {
            LOG.error("获取记录列表异常：", e);
        }
        return null;
    }

    /**
     * 更新某条记录
     * 
     * @param country实体类
     *            必须，非null
     * @return 返回String类型 1成功，其他失败
     */
    @RequestMapping(value = "/country/Upadte")
    @ResponseBody
    public String upadte(Country country) {
        try
        {
            int y = countryService.update(country);
            return y + "";
        }
        catch (Exception e)
        {
            LOG.error("修改某个记录异常：", e);
        }
        return 0 + "";
    }

    /**
     * 插入记录
     * 
     * @param country
     *            实体类 必须，非null
     * @return 返String类型 1成功，其他失败
     */
    @RequestMapping(value = "/country/add")
    @ResponseBody
    public String add(Country country) {
        try
        {
            int y = countryService.insert(country);
            return y + "";
        }
        catch (Exception e)
        {
            LOG.error("插入某个记录异常：", e);
        }
        return 0 + "";
    }

    /**
     * 删除某条记录
     * 
     * @param countryNo
     *            国家代码 必须，非null
     * @return 返String类型 1成功，其他失败
     */
    @RequestMapping(value = "/country/del")
    @ResponseBody
    public String del(String countryNo) {
        try
        {
            int y = countryService.delete(countryNo);
            return y + "";
        }
        catch (Exception e)
        {
            LOG.error("删除某个记录异常：", e);
        }
        return 0 + "";
    }
}
