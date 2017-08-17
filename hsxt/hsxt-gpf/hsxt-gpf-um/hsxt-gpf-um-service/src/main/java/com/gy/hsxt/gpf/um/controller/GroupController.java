/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.controller;

import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.gpf.um.bean.*;
import com.gy.hsxt.gpf.um.service.IGroupService;
import com.gy.hsxt.gpf.um.service.IOperatorGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 用户组控制中心
 *
 * @Package : com.gy.hsxt.gpf.um.controller
 * @ClassName : GroupController
 * @Description : 用户组控制中心
 * @Author : chenm
 * @Date : 2016/2/4 11:28
 * @Version V3.0.0.0
 */
@Controller
@RequestMapping("/group")
public class GroupController extends BaseController {

    /**
     * 用户组业务层接口
     */
    @Resource
    private IGroupService groupService;

    /**
     * 操作员用户组关系业务接口
     */
    @Resource
    private IOperatorGroupService operatorGroupService;


    @ResponseBody
    @RequestMapping("/save")
    public RespInfo<Group> saveGroup(Group group) throws HsException {
        //保存操作员
        groupService.saveBean(group);
        //返回操作结果
        return RespInfo.bulid(group);
    }

    /**
     * 修改用户组
     *
     * @param group 用户组
     * @return 操作结果
     */
    @ResponseBody
    @RequestMapping("/edit")
    public RespInfo<Boolean> editGroup(Group group) throws HsException {
        //保存操作员
        int count = groupService.modifyBean(group);
        //返回操作结果
        return RespInfo.bulid(count == 1);
    }

    /**
     * 删除用户组
     *
     * @param groupId 用户组ID
     * @return 操作结果
     */
    @ResponseBody
    @RequestMapping("/delete")
    public RespInfo<Boolean> delGroup(@RequestParam("groupId") String groupId) throws HsException {
        //保存操作员
        int count = groupService.removeBeanById(groupId);
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
    @RequestMapping("/delOperatorId")
    public RespInfo<Boolean> delOperatorId(OperatorGroup operatorGroup) throws HsException {
        //保存操作员
        int count = operatorGroupService.removeBean(operatorGroup);
        //返回操作结果
        return RespInfo.bulid(count == 1);
    }

    /**
     * 分页查询用户组列表
     *
     * @param gridPage   分页实体
     * @param groupQuery 查询实体
     * @return {@link GridData}
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryListForPage")
    public GridData<Group> queryGroupList(GridPage gridPage, GroupQuery groupQuery) throws HsException {
        //返回操作结果
        return groupService.queryBeanListForPage(gridPage, groupQuery);
    }

    /**
     * 查询所有用户组
     *
     * @return list
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryAllList")
    public RespInfo<List<Group>> queryAllList() throws HsException {
        //查询所有用户组
        List<Group> groups = groupService.queryBeanListByQuery(null);
        //返回操作结果
        return RespInfo.bulid(groups);
    }

    /**
     * 分页查询是否关联的操作者列表
     *
     * @param gridPage           分页实体
     * @param operatorGroupQuery 查询实体
     * @return {@link GridData}
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/queryOperatorList")
    public GridData<Operator> queryOperatorList(GridPage gridPage, OperatorGroupQuery operatorGroupQuery) throws HsException {
        //返回操作结果
        return operatorGroupService.queryOperatorListForPage(gridPage, operatorGroupQuery);
    }

    /**
     * 用户组-保存关联的操作员
     *
     * @param group 用户组
     * @return {@code RespInfo}
     * @throws HsException
     */
    @ResponseBody
    @RequestMapping("/saveOperatorIds")
    public RespInfo<Boolean> saveOperatorIds(Group group) throws HsException {
        //返回操作结果
        int count = operatorGroupService.batchSaveBeanForGroup(group);
        return RespInfo.bulid(count > 0);
    }

}
