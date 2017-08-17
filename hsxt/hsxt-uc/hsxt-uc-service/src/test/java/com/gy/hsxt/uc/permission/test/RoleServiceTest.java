package com.gy.hsxt.uc.permission.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.fastjson.JSON;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.auth.IUCAsRoleService;
import com.gy.hsxt.uc.as.bean.auth.AsRole;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class RoleServiceTest {
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
                vo.setRoleName("你好test12");
                roleService.addRole(vo, "lvyan");
                long end = System.currentTimeMillis();
                long cost = end - begin;
                log.info("addRole:1cost time 毫秒数=" + cost);
                vo = genVo();
                vo.setRoleName("你好test13");
                roleService.addRole(vo, "lvyan");
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
        {
            Assert.fail(e.getMessage());

        }
    }

    @Test
    public void deleteRole() {
        String roleId = "1";
        String operator = "lvDelJunit";
        try
        {
            roleService.deleteRole(roleId, operator);
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void updateRole() {
        AsRole vo = new AsRole();
        vo.setRoleId("169629510255616");

        vo.setRoleName("test");

        vo.setRoleDesc("修改日期" + (new Date()));

//        vo.setRoleType("2");

        roleService.updateRole(vo, "lvUpTest");
        System.out.print("testupdateRole");
    }

    @Test
    public void listRoleByType() {
        String platformCode = null;
        String subSystemCode = "COMPANY";
        Short roleType = null;
        String entCustId="06002110000164063559693312";
        String entCustType="2";
        roleType=null;
        List<AsRole> ret = roleService.listRole(platformCode, subSystemCode, roleType, entCustId, entCustType);
        for (AsRole vo : ret)
        {
            System.out.println("test listRoleByType"+JSON.toJSONString(vo));
        }
    }

    @Test
    public void listRoleByCustId() {
        String custId = "lvyan";

        List<AsRole> ret = roleService.listRoleByCustId(null, null, custId);
        if (ret == null)
        {
            log.error("listRoleByCustId return null");
            return;
        }
        for (AsRole vo : ret)
        {
            log.info(JSON.toJSONString(vo));
        }
    }

    @Test
    public void grantRole() {
        String custId = "06002110000164063559726080";
        List<String> list = new ArrayList<String>();
        String operator = "lvJunit";

        list.add("301");
//        list.add("2");
//        list.add("3");
        // list.add(4l);
        roleService.grantRole(custId, list, operator);
    }

    @Test
    public void revokeRole() {
        String custId = "lvyan";
        List<String> list = new ArrayList<String>();
        String operator = "lvJunit";

        list.add("2");
        roleService.revokeRole(custId, list, operator);
    }
    @Test
    public void getRoleTree() {
    	List<Map<String, Object>> list= roleService.getRoleTree(null,
    			null, null, "899161733139451903");
    	log.info(list);
    }
    
    @Test
    public void getCustRole(){
    	String custId="06006000000000020151230";
    	Set<String> roleIds = roleService.getCustRoleIdSet(custId);
    	log.info(roleIds);
    	
    	try {
			Thread.currentThread().sleep(5555);
		} catch (InterruptedException e) {
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
}
