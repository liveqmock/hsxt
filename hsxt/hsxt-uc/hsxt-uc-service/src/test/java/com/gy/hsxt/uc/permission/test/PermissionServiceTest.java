package com.gy.hsxt.uc.permission.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.gy.hsxt.uc.as.api.auth.IUCAsPermissionService;
import com.gy.hsxt.uc.as.bean.auth.AsPermission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class PermissionServiceTest {
    private Log log=LogFactory.getLog(this.getClass());
    
    @Resource
    private IUCAsPermissionService permissionService;

    @Test
    public void addPerm() {
        try
        {
            AsPermission vo = genVo();
            try
            {
                long begin = System.currentTimeMillis();
                permissionService.addPerm(genVo(), "lvyan");
                long end = System.currentTimeMillis();
                long cost = end - begin;
                log.info("addPerm:1cost time 毫秒数=" + cost);
                permissionService.addPerm(genVo(), "lvyan");
                end = System.currentTimeMillis();
                cost = end - begin - cost;
                log.info("addPerm:2cost time 毫秒数=" + cost);
            }
            catch (HsException hse)
            {
                log.warn(vo);
                hse.printStackTrace();
                Assert.fail(hse.getMessage());
            }
            catch (RpcException rpce)
            {
                log.warn(vo);
                throw new HsException(rpce.getCode(), "调用用户中心用户管理服务 RPC异常!" + rpce.getStackTrace());
            }

        }
        catch (Exception e)
        {
            Assert.fail(e.getMessage());

        }
    }

    @Test
    public void deletePerm() {
        String permId = "1";
        String operator = "lvDelJunit";
        try
        {
            permissionService.deletePerm(permId, operator);
        }
        catch (HsException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void updatePerm() {
        AsPermission vo = new AsPermission();
        vo.setPermId("2");
        vo.setParentId("0");
        vo.setPermName("功能修改");
        vo.setPermDesc("修改日期" + (new Date()));
//        vo.setOperationUrl("updated");
        vo.setPermCode("" + 1444816752553l);
        vo.setPermType("2");

        permissionService.updatePerm(vo, "lvUpTest");
    }

    @Test
    public void listPerm() {
        String permId = "0";
        List<AsPermission> ret = permissionService.listPerm(null,null,null,permId,"2");
        for (AsPermission vo : ret)
        {   //getUpdatedby
            //getUpdatedBy
            log.error(JSON.toJSONString(vo));
        }
    }

    @Test
    public void listPermByType() {
        String platformCode=null;
        String subSystemCode=null;
        Short permType=1;
        permType=null;
        List<AsPermission> ret = permissionService.listPermByType(platformCode, subSystemCode, permType);
        for (AsPermission vo : ret)
        {
            log.info(JSON.toJSONString(vo));
        }
    }
    
    @Test
    public void listPermByCustId() throws Exception {
        String custId="0000000015640120000";
 //HSXT,COMPANY,0，06002110000164063559726080
        Short pt=0;
        List<AsPermission> ret = permissionService.listPermByCustId("HSXT","APS",pt,custId);
        if(ret==null){
            log.error("listPermByCustId return null");
            return;
        }
   
            log.error(JSON.toJSONString(ret));
            System.err.println();
            System.err.println();
            System.err.println();
            System.err.println();
        System.err.println("--------------can stop!!!!!!!!!!");
        System.err.println();
        System.err.println();
            Thread.sleep(111111);
            
    }
    
    @Test
    public void listPermByRoleId() {
        String roleId="1";
 
        List<AsPermission> ret = permissionService.listPermByRoleId(null,null,roleId);
        if(ret==null){
            log.error("listPermByRoleId return null");
            return;
        }
        for (AsPermission vo : ret)
        {
            log.error(JSON.toJSONString(vo));
        }
    }
    
    @Test
    public void grantPerm(){
        String roleId="1";
        List<String> list=new ArrayList<String>();
        String operator="lvJunit";
        
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        permissionService.grantPerm(roleId, list, operator);
    }
    
    @Test
    public void revokePerm(){
        String roleId="1";
        List<String> list=new ArrayList<String>();
        String operator="lvJunit";
             
        list.add("2");
        permissionService.revokePerm(roleId, list, operator);
    }
    
    @Test
    public void getRolePermTree(){
        String roleId="1";

        List l=permissionService.getRolePermTree(null,null,null,null,roleId);
        log.info(l);
    }

    AsPermission genVo() {
        long ltime = System.currentTimeMillis();
        AsPermission vo = new AsPermission();
        vo.setParentId("1");
        vo.setPermName("lvyan" + ltime);
        vo.setPermDesc("gen" + (new Date()));
        vo.setPermCode("p_code_" + ltime);
        vo.setPermType("2");
        vo.setSubSystemCode("sub_sys_code_lvy");
        vo.setPlatformCode("plat_code_lvy");
        vo.setPermUrl("perm_url "+ltime);
        vo.setLayout("3");
        vo.setSortNum("4");
        return vo;
    }
    
    public static void main(String[] agrs){
        System.out.println("hello worl!");
    }
}
