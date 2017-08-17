package com.gy.hsxt.uc.uf;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.gy.hsxt.uc.MyJunit4ClassRunner;
import com.gy.hsxt.uf.api.IUFRegionPacketService;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketData;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketDataBody;
import com.gy.hsxt.uf.bean.packet.data.RegionPacketDataHeader;

@RunWith(MyJunit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-uc.xml" })
public class UFRegionPacketDataServiceTest {
    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private UFRegionPacketDataService service;

    /**
     * 向地区平台通知开启管理公司,目标子系统：UC ** TO_REGION_INIT_M_ENT,
     * 
     */
    @Test
    public void addM() {
        try
        {

            int mNo = 13;
            // 跨平台业务代码
            String bizCode = "TO_REGION_INIT_M_ENT";
            String entResNo = mNo + "000000000";
            String entCustName = "test" + mNo;
            String email =  "lvyan@gyist.com";
            JSONObject json = new JSONObject();

            json.put("entResNo", entResNo);
            json.put("entCustName", entCustName);
            json.put("email", email);
            json.put("optCustId", entResNo);

            Object ret = this.senMsg(bizCode, json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail(e.getMessage());

        }
    }

    /**
     * 向地区平台通知开启平台企业信息,目标子系统：UC TO_REGION_INIT_PLAT_ENT,
     **/
//    @Test
    public void addPlat() {
        try
        {

            int mNo = 12341;
            // 跨平台业务代码
            String bizCode = "TO_REGION_INIT_PLAT_ENT";
            String entResNo = mNo + "000000";
            String entCustName = "test" + mNo;
            String emailA = "lvyan@gyist.com";
            String emailB =  "lixuan@gyist.com";
            JSONObject json = new JSONObject();

            json.put("entResNo", entResNo);
            json.put("entCustName", entCustName);
            json.put("emailA", emailA);
            json.put("emailB", emailB);
            json.put("optCustId", entResNo);
            

            Object ret = this.senMsg(bizCode, json);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.fail(e.getMessage());

        }
    }

    Object senMsg(String bizCode, JSONObject json) {
        RegionPacketDataHeader header = RegionPacketDataHeader.build();
        RegionPacketDataBody body = RegionPacketDataBody.build(json.toJSONString());

        header.setBizCode(bizCode);

        RegionPacketData regionPacketData = new RegionPacketData(header, body);
        Object ret = service.handleReceived(regionPacketData);
        log.info(header.getBizCode()+" "+ret);
        return ret;
    }
    
    public static void main(String args[]){
        IUFRegionPacketService  serv=null;
        if(serv!=null)
        serv.sendSyncRegionPacket(null,null);
    }

}
