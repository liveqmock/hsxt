package com.gy.hsxt.uc.permission.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.rpc.RpcException;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.bean.auth.AsRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class RoleServiceTest_ADD {
    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private IUCAsRoleService roleService;

    @Test
    public void addRole() {
        try
        {
            AsRole vo = genVo();
            try
            {
                long begin = System.currentTimeMillis();
                roleService.addRole(genVo(), "lvyan");
                long end = System.currentTimeMillis();
                long cost = end - begin;
                log.info("addRole:1cost time 毫秒数=" + cost);
                roleService.addRole(genVo(), "lvyan");
                end = System.currentTimeMillis();
                cost = end - begin - cost;
                log.info("addRole:2cost time 毫秒数=" + cost);
            }
            catch (HsException hse)
            {
                log.error(vo);
                hse.printStackTrace();
                Assert.fail(hse.getMessage());
            }
            catch (RpcException rpce)
            {
                log.error(vo);
                throw new HsException(rpce.getCode(), "调用用户中心用户管理服务 RPC异常!" + rpce.getStackTrace());
            }

        }
        catch (Exception e)
        {   e.printStackTrace();
            Assert.fail(e.getMessage());

        }
    }


    AsRole genVo() {
        long ltime = System.currentTimeMillis();
        AsRole vo = new AsRole();

        vo.setRoleName("lvRoleName" + ltime);
        vo.setRoleDesc("desc gen" + (new Date()));
        vo.setEntCustId("lv_cust");
        vo.setRoleType("1");
        vo.setSubSystemCode("test_sys_lvy");
        vo.setPlatformCode("test_junit_lvy");
        return vo;
    }
    
    public static void main(String[] args){
        ArrayList l= new ArrayList();
        l.add("1");
        l.add("2");
        System.out.println(1234/1000);
        System.out.println(l.subList(0, 0));
        System.out.println(l.subList(0, 1));
        System.out.println(l.subList(1, 2));
        System.out.println(l.subList(2, 2));
        HashMap m= new HashMap();
        m.put(1, "a1");
        m.put(new Integer(2), "a2");
        m.put("a3", 3l);
        for(Object o:m.keySet()){
            System.out.println(o.getClass().getName());
        }
        
        System.out.println(m.get((new Integer(1))));
        System.out.println(m.get(2));
        
        for(Object o:m.values()){
            System.out.println(o.getClass().getName());
        }
    }
    
    
}
