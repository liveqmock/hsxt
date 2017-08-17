/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.scs.services.companyInfo.imp;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.scs.services.companyInfo.IEntBaseInfoService;
import com.gy.hsxt.common.utils.JsonUtil;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo;

/***
 * 企业基本信息的接口实现 类
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.companyInfo.imp  
 * @ClassName: EntbaseInfoServiceImpl 
 * @Description: TODO
 *
 * @author: liuxy 
 * @date: 2015-11-17 下午12:06:42 
 * @version V1.0
 */

@Service
public class EntbaseInfoServiceImpl implements IEntBaseInfoService {
    @Autowired
    private IUCAsEntService ucService=null;
    @Override
    public AsEntBaseInfo findEntBaseInfo(String entCustId) {
       
        
        AsEntBaseInfo info = ucService.searchEntBaseInfo(entCustId);
        
        return info;
    }

    @Override
    public void updateEntBaseInfo(AsEntBaseInfo info,String operator) {
        ucService.updateEntBaseInfo(info, operator);
    }

}
