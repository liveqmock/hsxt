/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.controller;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.*;
import com.gy.hsxt.gpf.um.service.IOperatorGroupService;
import com.gy.hsxt.gpf.um.service.IOperatorRoleService;
import com.gy.hsxt.gpf.um.service.IOperatorService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 操作员控制中心
 *
 * @Package : com.gy.hsxt.gpf.um.controller
 * @ClassName : OperatorController
 * @Description : 操作员控制中心
 * @Author : chenm
 * @Date : 2016/1/27 9:52
 * @Version V3.0.0.0
 */
@Controller
@RequestMapping("/operator")
public class OperatorController extends BaseController {

    /**
     * 操作员业务接口
     */
    @Resource
    private IOperatorService operatorService;

    /**
     * 操作员用户组关系业务接口
     */
    @Resource
    private IOperatorGroupService operatorGroupService;

    /**
     * 操作员角色关系业务接口
     */
    @Resource
    private IOperatorRoleService operatorRoleService;

    /**
     * 保存操作员
     *
     * @param operator 操作员
     * @return 操作结果
     */
    @ResponseBody
    @RequestMapping("/save")
    public RespInfo<String> saveOperator(Operator operator) throws HsException {
        //保存操作员
        operatorService.saveBean(operator);
        //返回操作结果
        return RespInfo.bulid(operator.getOperatorId());
    }

    /**
     * 分页查询操作员列表
     *
     * @param gridPage      分页实体
     * @param operatorQuery 查询实体
     * @return {@link GridData}
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryListForPage")
    public GridData<Operator> queryOperatorList(GridPage gridPage, OperatorQuery operatorQuery) throws HsException {
        //返回操作结果
        return operatorService.queryBeanListForPage(gridPage, operatorQuery);
    }

    /**
     * 修改操作员
     *
     * @param operator 操作员
     * @return 操作结果
     */
    @ResponseBody
    @RequestMapping("/edit")
    public RespInfo<Boolean> editOperator(Operator operator, OperatorGroup operatorGroup) throws HsException {
        //保存操作员
        boolean success = operatorService.editOperator(operator, operatorGroup);
        //返回操作结果
        return RespInfo.bulid(success);
    }

    /**
     * 删除操作员
     *
     * @param operatorId 操作员ID
     * @return 操作结果
     */
    @ResponseBody
    @RequestMapping("/delete")
    public RespInfo<Boolean> delOperator(@RequestParam("operatorId") String operatorId) throws HsException {
        //保存操作员
        int count = operatorService.removeBeanById(operatorId);
        //返回操作结果
        return RespInfo.bulid(count == 1);
    }

    /**
     * 删除操作员用户组关联
     *
     * @param operatorGroup 操作员用户组关联
     * @return 操作结果
     */
    @ResponseBody
    @RequestMapping("/delGroupId")
    public RespInfo<Boolean> delGroupId(OperatorGroup operatorGroup) throws HsException {
        //保存操作员
        int count = operatorGroupService.removeBean(operatorGroup);
        //返回操作结果
        return RespInfo.bulid(count == 1);
    }

    /**
     * 分页查询是否关联的用户组列表
     *
     * @param gridPage           分页实体
     * @param operatorGroupQuery 查询实体
     * @return {@link GridData}
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryGroupList")
    public GridData<Group> queryGroupList(GridPage gridPage, OperatorGroupQuery operatorGroupQuery) throws HsException {
        //返回操作结果
        return operatorGroupService.queryGroupListForPage(gridPage, operatorGroupQuery);
    }

    /**
     * 处理操作员角色关系列表
     *
     * @param operatorRole 关系实体
     * @return boolean
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/dealOperRoleList")
    public RespInfo<Boolean> dealOperRoleList(OperatorRole operatorRole) throws HsException {
        //处理业务
        boolean success = operatorRoleService.dealOperRoleList(operatorRole);
        //返回操作结果
        return RespInfo.bulid(success);
    }

    /**
     * 处理操作员用户组关系列表
     *
     * @param operatorGroup 关系实体
     * @return boolean
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/dealOperatorGroupList")
    public RespInfo<Boolean> dealOperatorGroupList(OperatorGroup operatorGroup) throws HsException {
        //处理业务
        boolean success = operatorGroupService.dealOperatorGroupList(operatorGroup);
        //返回操作结果
        return RespInfo.bulid(success);
    }

    /**
     * 修改登录密码
     *
     * @param operator 操作者
     * @param oldPassword 旧密码
     * @return boolean
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/editLoginPassword")
    public RespInfo<Boolean> editLoginPassword(Operator operator,String oldPassword) throws HsException {
        //处理业务
        boolean success = operatorService.editLoginPassword(operator,oldPassword);
        //返回操作结果
        return RespInfo.bulid(success);
    }
}
