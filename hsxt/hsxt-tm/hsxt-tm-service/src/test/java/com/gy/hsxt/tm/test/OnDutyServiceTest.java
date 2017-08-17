/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.tm.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gy.hsxt.tm.api.ITMOnDutyService;
import com.gy.hsxt.tm.bean.Group;
import com.gy.hsxt.tm.bean.Operator;
import com.gy.hsxt.tm.enumtype.GroupType;

public class OnDutyServiceTest extends BaseTest {
    @Autowired
    ITMOnDutyService itmOperatorService;

    Group group = new Group();

    @Test
    public void testSaveGroup() {
        // 第1步：添加值班组，组中添加值班员
        List<Operator> operators = new ArrayList<Operator>();
        Operator operator = null;

        group.setEntCustId("0600000000920160119");
        // group.setEntCustId(getCustId());
        group.setGroupName("管理公司测试组");
        group.setGroupType(GroupType.BIZ_GROUP.getCode());
        group.setOpened(true);

        operator = new Operator();
        // 设置任务编号
        operator.setOptCustId("06000000000222320160113");
        operator.setOperatorName("管理公司操作员1");
        operator.setOptWorkNo(getHsResNo());
        operator.setChief(true);
        operators.add(operator);

        operator = new Operator();
        // 设置任务编号
        operator.setOptCustId("06000000000000120160107");
        operator.setOperatorName("管理公司操作员2");
        operator.setOptWorkNo(getHsResNo());
        operator.setChief(false);
        operators.add(operator);

        itmOperatorService.saveGroup(group, operators);

        // 第2步：添加值班计划
    }

    @Test
    public void testModifyGroup() {
        List<Operator> operators = new ArrayList<Operator>();
        Operator operator = null;

        group.setEntCustId(getCustId());
        group.setGroupName("葫芦娃");
        group.setGroupType(GroupType.SERVICE_GROUP.getCode());
        group.setOpened(true);
        // 新增时注释掉设置值班组
        group.setGroupId("120120160104093545000000");

        operator = new Operator();
        // 设置任务编号
        operator.setOptCustId("00000000003");
        operator.setOperatorName("张妹妹2");
        operator.setOptWorkNo(getHsResNo());
        operator.setChief(true);
        operators.add(operator);

        operator = new Operator();
        // 设置任务编号
        operator.setOptCustId("00000000001");
        operator.setOperatorName("张妹妹1");
        operator.setOptWorkNo(getHsResNo());
        operator.setChief(false);
        operators.add(operator);

        // itmOperatorService.modifyGroup(group, operators);
    }

    @Test
    public void testGetEditGroupInfo() {
        List<Group> groupList = new ArrayList<Group>();
        groupList = itmOperatorService.getGroupInfo("120120151113080434000000");
        for (Group group : groupList)
        {
            System.out.println("---------------" + group);
            System.out.println("---------------" + group.getOperators());
        }
    }

    @Test
    public void testgetGroupList() {
        List<Group> groupList = itmOperatorService.getOpenedGroupList("0558700874420151112");
        for (Group group : groupList)
        {
            System.out.println(group);
        }
    }

    @Test
    public void testChangeChief() {
        itmOperatorService.updateChief(true, "120120151112103550000000");
    }

    @Test
    public void testgetOperatorList() {
        List<Operator> operatorList = itmOperatorService.getOperatorList("120120151112100250000000");
        for (Operator operator : operatorList)
        {
            System.out.println(operator);
        }
    }

    // @Test
    // // public void testgetOperatorInfo() {
    // // List<CustomOperator> operator =
    // itmOperatorService.getOperatorInfo("00000000001");
    // // for (CustomOperator customOperator : operator)
    // // {
    // // System.out.println(customOperator);
    // // }
    // // }

    @Test
    public void testRemoveOperator() {
        itmOperatorService.removeOperator("120120151112100250000000", "120120151112104727000000");
    }

    @Test
    public void testGetOperatorListByEntCustId() {
        System.out.println(itmOperatorService.getOperatorListByCustId("00000000156163438270977024", null));

    }

    @Test
    public void testGetOperatorSchedule() {
        System.out.println(itmOperatorService.getOperatorSchedule("120120151113080434000000", "2015", "12"));

    }

    @Test
    public void testGetGroupInfo() {
        List<Group> listGroup = itmOperatorService.getGroupInfo("06000000000163439192367104");
        System.out.println(listGroup);
    }

    @Test
    public void testTaskGroupInfo() {
        List<Group> listGroup = itmOperatorService.getTaskGroupInfo("00000000156163438270977024", true, false);

        System.out.println("组的大小 是：" + listGroup.size());
    }

    @Test
    public void testSynOperatorName() {
        itmOperatorService.synOperatorName("0000000015633330000", "黄伟");
    }

    @Test
    public void testOpteratorIsCheif() {
        System.out.println(itmOperatorService.isChief("0000000015630140000") ? "-------主任好啊" : "......不要装了你不是主任");
    }
}
