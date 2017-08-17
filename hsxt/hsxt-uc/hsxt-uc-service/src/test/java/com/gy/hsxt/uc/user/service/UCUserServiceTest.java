package com.gy.hsxt.uc.user.service;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.rpc.RpcException;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.bs.api.IUCUserService;
import com.gy.hsxt.uc.bs.bean.UserInfoVo;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-global.xml" })
public class UCUserServiceTest {
	@Resource
    private IUCUserService userService;
    
    @Test
    public void updateUser() {
        try{
        	UserInfoVo userInfo = new UserInfoVo(100, "aaa", 12);
            try{
            	userService.updateUser(userInfo);
            }catch(HsException hse){ 
            	hse.printStackTrace();
                throw new HsException(hse.getErrorCode(), "调用用户中心修改用户信息接口处理异常!传参内容为：" + userInfo.toString());
            }catch(RpcException rpce){
                throw new HsException(rpce.getCode(),"调用用户中心用户管理服务 RPC异常!" + rpce.getStackTrace());
            }
            
        }catch(Exception e){
            Assert.fail(e.getMessage());
           
        }
    }
}
