package com.gy.hsxt.gpf.bm.controller;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.bm.bean.Bmlm;
import com.gy.hsxt.gpf.bm.bean.PointValue;
import com.gy.hsxt.gpf.bm.bean.Query;
import com.gy.hsxt.gpf.bm.service.BmlmService;
import com.gy.hsxt.gpf.bm.service.PointValueService;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.GridPage;
import com.gy.hsxt.gpf.um.bean.RespInfo;
import com.gy.hsxt.gpf.um.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 查询赠送积分数据库对象控制层
 *
 * @Package :com.gy.hsxt.gpf.bm.controller
 * @ClassName : IncrementController
 * @Description : 查询赠送积分数据库对象控制层
 * @Author : chenm
 * @Date : 2015/9/30 13:17
 * @Version V3.0.0.0
 */
@Controller
@RequestMapping("/pointValue")
public class PointValueController extends BaseController {

    /**
     * 积分互生分配业务接口
     */
    @Resource
    private PointValueService pointValueService;


    /**
     * 再增值分配业务接口
     */
    @Resource
    private BmlmService bmlmService;


    /**
     * 条件查询互生积分分配详情列表
     *
     * @param query 查询实体
     * @return {@code List<PointValue>}
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryPointValueList")
    public RespInfo<List<PointValue>> queryPointValueList(Query query) throws HsException {
        //返回操作结果
        List<PointValue> pointValues = pointValueService.queryPointValueList(query);
        return RespInfo.bulid(pointValues);
    }

    /**
     * 条件查询再增值分配详情列表
     *
     * @param query 查询实体
     * @return {@code List<Bmlm>}
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryBmlmList")
    public RespInfo<List<Bmlm>> queryBmlmList(Query query) throws HsException {
        //返回操作结果
        List<Bmlm> bmlms = bmlmService.queryBmlmList(query);
        return RespInfo.bulid(bmlms);
    }

    /**
     * 分页条件查询积分互生分配详情列表
     *
     * @param page  分页对象
     * @param query 查询实体
     * @return data
     */
    @ResponseBody
    @RequestMapping("/queryPointValueListPage")
    public GridData<PointValue> queryPointValueListPage(GridPage page,Query query){
        //返回操作结果
        return pointValueService.queryPointValueListPage(page,query);
    }


    /**
     * 分页条件查询再增值分配详情列表
     * @param page 分页对象
     * @param query 查询实体
     * @return data
     */
    @ResponseBody
    @RequestMapping("/queryBmlmListPage")
    public GridData<Bmlm> queryBmlmListPage(GridPage page, Query query){
        //返回操作结果
        return bmlmService.queryBmlmListPage(page,query);
    }
}

	