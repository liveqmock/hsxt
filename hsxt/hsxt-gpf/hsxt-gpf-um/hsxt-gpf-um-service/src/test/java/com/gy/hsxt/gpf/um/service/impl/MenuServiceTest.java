package com.gy.hsxt.gpf.um.service.impl;/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/

import com.alibaba.fastjson.JSON;
import com.gy.hsxt.gpf.um.bean.LoginInfo;
import com.gy.hsxt.gpf.um.bean.Menu;
import com.gy.hsxt.gpf.um.bean.MenuQuery;
import com.gy.hsxt.gpf.um.bean.MenuTree;
import com.gy.hsxt.gpf.um.enums.MenuType;
import com.gy.hsxt.gpf.um.service.IMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Package : com.gy.hsxt.gpf.um.service.impl
 * @ClassName : MenuServiceTest
 * @Description : TODO
 * @Author : chenm
 * @Date : 2016/2/1 15:04
 * @Version V3.0.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-global.xml")
public class MenuServiceTest {

    @Resource
    private IMenuService menuService;

    @Test
    public void testSaveBean() throws Exception {

        Menu menu = new Menu();
//        menu.setMenuName("管理公司资源");
//        menu.setMenuName("地区平台资源");
//        menu.setMenuName("增值信息查询");
//        menu.setMenuName("系统管理");
//        menu.setMenuName("安全设置");
//        menu.setMenuName("一级资源配额配置");
//        menu.setMenuName("一级资源配额申请审批");
//        menu.setMenuName("管理公司资源号维护");
//        menu.setMenuName("管理公司信息维护");
//        menu.setMenuName("操作员管理");
        menu.setMenuName("增值点位置查询");
//        menu.setMenuType(MenuType.BRANCH.ordinal());
        menu.setMenuType(MenuType.LEAF.ordinal());
//        menu.setDescription("管理公司资源");
//        menu.setDescription("地区平台资源");
//        menu.setDescription("增值信息查询");
//        menu.setDescription("系统管理");
//        menu.setDescription("安全设置");
//        menu.setDescription("一级资源配额配置");
//        menu.setDescription("一级资源配额申请审批");
//        menu.setDescription("管理公司资源号维护");
//        menu.setDescription("管理公司信息维护");
//        menu.setDescription("操作员管理");
        menu.setDescription("增值点位置查询");
        menu.setParentNo("003");
        menu.setMenuCode("zzdwzcx");
        menuService.saveBean(menu);

        menu.setMenuName("互生分配查询");
        menu.setMenuType(MenuType.LEAF.ordinal());
        menu.setDescription("互生分配查询");
        menu.setParentNo("003");
        menu.setMenuCode("hsfpcx");
        menuService.saveBean(menu);

        menu.setMenuName("再增值分配查询");
        menu.setMenuType(MenuType.LEAF.ordinal());
        menu.setDescription("再增值分配查询");
        menu.setParentNo("003");
        menu.setMenuCode("zzzfpcx");
        menuService.saveBean(menu);

//        Menu m = new Menu();
//        m.setMenuName("用户组管理");
//        m.setMenuType(MenuType.LEAF.ordinal());
//        m.setDescription("用户组管理");
//        m.setParentNo("004");
//        menuService.saveBean(m);
//        Menu m1 = new Menu();
//        m1.setMenuName("角色管理");
//        m1.setMenuType(MenuType.LEAF.ordinal());
//        m1.setDescription("角色管理");
//        m1.setParentNo("004");
//        menuService.saveBean(m1);

    }

    @Test
    public void testRemoveBeanById() throws Exception {

    }

    @Test
    public void testModifyBean() throws Exception {

    }

    @Test
    public void testQueryBeanById() throws Exception {

    }

    @Test
    public void testQueryBeanByQuery() throws Exception {

    }

    @Test
    public void testQueryBeanListByQuery() throws Exception {

        MenuQuery query = new MenuQuery();
//        query.setParentNo("000");
        List<Menu> menuList = menuService.queryBeanListByQuery(query);
        for (Menu menu : menuList) {
            System.out.println(MenuTree.bulid(menu));
        }
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Test
    public void testRedis() {
        String key = "loginInfo";
        LoginInfo info = new LoginInfo();
        info.setName("you are who");

        BoundValueOperations<String, String> valueOperations = stringRedisTemplate.boundValueOps(key);
        valueOperations.set(info.toString());
//        valueOperations.set("test111111111122");
        System.out.println(valueOperations.get());
//        valueOperations.expire(0, TimeUnit.SECONDS);
        System.out.println(valueOperations.get());
        LoginInfo loginInfo = JSON.parseObject(valueOperations.get(), LoginInfo.class);

        System.out.println(loginInfo);
//
//        BoundListOperations<String,String> listOperations = stringRedisTemplate.boundListOps("list");
//
//
////        listOperations.leftPush("list1");
////        listOperations.leftPush("list2");
////        listOperations.leftPush("list3");
//
//        for (int i = 0; i < listOperations.size(); i++) {
//            System.out.println(listOperations.index(i));
//        }
//        System.out.println(listOperations.range(0,listOperations.size()));
//        listOperations.remove(3, "list3");
//                listOperations.leftPush("list4");

//        BoundHashOperations<String,String,String> hashOperations = stringRedisTemplate.boundHashOps("map1");
//        hashOperations.put("key1","value1");
//        hashOperations.put("key2","value2");
//        hashOperations.put("key3","value3");
//        hashOperations.put("key4","value4");
//
//        for (Map.Entry<String, String> entry : hashOperations.entries().entrySet()) {
//            System.out.println(entry.getKey() + " ---- >" + entry.getValue());
//        }

    }
}