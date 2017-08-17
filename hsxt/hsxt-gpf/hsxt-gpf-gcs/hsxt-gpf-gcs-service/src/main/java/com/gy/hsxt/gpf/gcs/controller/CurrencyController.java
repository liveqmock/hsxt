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
import com.gy.hsxt.gpf.gcs.bean.Currency;
import com.gy.hsxt.gpf.gcs.common.PageContext;
import com.gy.hsxt.gpf.gcs.interfaces.ICurrencyService;

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
 *  Purpose         : 币种管理控制器类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
@Controller
public class CurrencyController {
    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @Resource(name = "currencyService")
    private ICurrencyService currencyService;

    /**
     * 管理页面初始化
     */
    @RequestMapping(value = "/currency/manage")
    public void manage() {

    }

    /**
     * 获取数据分页列表
     * 
     * @param currency实体类
     *            必须，非null
     * @param pageSize每页显示多少条
     *            必须，非null
     * @param pageNo当前页码
     *            必须，非null
     * 
     * @return 返回JSON,数据为空返回空List<Country>，异常返回null
     */
    @RequestMapping(value = "/currency/managePost")
    @ResponseBody
    public String managePost(Currency currency, int pageSize, int pageNo) {
        try
        {
            PageContext page = PageContext.getContext();
            page.setPageSize(pageSize);
            page.setPageNo(pageNo);
            List<Currency> list = currencyService.getCurrencyListPage(currency);
            int totalCount = page.getTotalResults();
            Map<String, Object> mp = new HashMap<>();
            mp.put("totalCount", totalCount);
            mp.put("TotalPages", page.getTotalPages());
            mp.put("list", list);
            return JSON.toJSONString(mp);
        }
        catch (Exception e)
        {
            LOG.error("获取记录列表异常：", e);
        }
        return null;
    }

    /**
     * 获取下拉菜单列表
     * 
     * @return 返回List<Country>,数据为空返回空List<Country>，异常返回null
     */
    @RequestMapping(value = "/currency/getCurrencyDropdownmenu")
    @ResponseBody
    public List<Currency> getCurrencyDropdownmenu() {
        try
        {
            List<Currency> list = currencyService.getCurrencyAll();
            return list;
        }
        catch (Exception e)
        {
            LOG.error("获取记录列表异常：", e);
        }
        return null;
    }

    /**
     * 更新某一条记录
     * 
     * @param currency实体类
     *            必须，非null
     * @return 返回String类型 1成功，其他失败
     */
    @RequestMapping(value = "/currency/Upadte")
    @ResponseBody
    public String upadte(Currency currency) {
        try
        {
            int y = currencyService.update(currency);
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
     * @param currency
     *            实体类 必须，非null
     * @return 返String类型 1成功，其他失败
     */
    @RequestMapping(value = "/currency/add")
    @ResponseBody
    public String add(Currency currency) {
        try
        {
            int y = currencyService.insert(currency);
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
     * @param currencyNo
     *            币种代码 必须，非null
     * @return 返回String类型 1成功，其他失败
     */
    @RequestMapping(value = "/currency/del")
    @ResponseBody
    public String del(String currencyNo) {
        try
        {
            int y = currencyService.delete(currencyNo);
            return y + "";
        }
        catch (Exception e)
        {
            LOG.error("删除某个记录异常：", e);
        }
        return 0 + "";
    }
}
