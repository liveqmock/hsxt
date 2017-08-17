package com.gy.hsxt.gpf.bm.controller;

import com.gy.hsxt.common.utils.HsAssert;
import com.gy.hsxt.gpf.bm.bean.ApplyRecord;
import com.gy.hsxt.gpf.bm.bean.IncNode;
import com.gy.hsxt.gpf.bm.common.BMRespCode;
import com.gy.hsxt.gpf.bm.common.Constants;
import com.gy.hsxt.gpf.bm.service.IncrementService;
import com.gy.hsxt.gpf.bm.utils.ParamsUtil;
import com.gy.hsxt.gpf.um.bean.RespInfo;
import com.gy.hsxt.gpf.um.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 增值节点操作控制类
 *
 * @Package :com.gy.hsxt.gpf.bm.controller
 * @ClassName : IncrementController
 * @Description : 增值节点操作控制类
 * @Author : chenm
 * @Date : 2015/9/30 13:17
 * @Version V3.0.0.0
 */
@Controller
@RequestMapping("/increment")
public class IncrementController extends BaseController {

    /**
     * 增值节点关系Service接口
     */
    @Resource
    private IncrementService incrementService;


    /**
     * 增值系统图形显示数据
     *
     * @param resNo 系统资源号
     * @return {@link Map}
     * @throws Exception
     */
    @RequestMapping(value = "/queryIncrementByResNo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RespInfo<Map<String, IncNode>> queryIncrementByResNo(@RequestParam String resNo) throws Exception {
        Map<String, IncNode> map = incrementService.queryFiveLevelNodesByResNo(resNo);
        return RespInfo.bulid(map);
    }

    /**
     * 查询资源号最长节点
     *
     * @param resNo 资源号
     * @param area  区域(left || right)
     * @return List<String>
     */
    @RequestMapping(value = "/queryLongNodeByResNo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RespInfo<List<String>> queryLongNodeByResNo(@RequestParam String resNo, @RequestParam String area) throws Exception {
        List<String> list = incrementService.queryLongNodesByResNo(resNo, area);
        return RespInfo.bulid(list);
    }

    /**
     * 根据当前节点查询所有父节点
     *
     * @param resNo 资源号
     * @return List<String>
     */
    @RequestMapping(value = "/queryParentListByResNo", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public RespInfo<List<String>> queryParentListByResNo(@RequestParam String resNo) throws Exception {
        List<String> list = incrementService.queryParentListByResNo(resNo);
        return RespInfo.bulid(list);
    }

    /**
     * 添加申报节点
     *
     * @param apply 申报节点对象
     * @return {@link RespInfo}
     */
    @RequestMapping(value = "/addIncrementNode", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public RespInfo<Boolean> addIncrementNode(ApplyRecord apply) throws Exception {
        //判断是否已经存在
        IncNode node = incrementService.getValueByKey(apply.getPopCustId());

        HsAssert.isTrue(node == null || node.getIsActive().equals(Constants.INCREMENT_ISACTIVE_N), BMRespCode.BM_ICN_NODE_EXIST, "有效增值节点已存在");

        ParamsUtil.validateApplyRecord(apply);//参数校验和处理

        //添加申报节点
        boolean success = incrementService.addNode(apply);

        return RespInfo.bulid(success);
    }
}

	