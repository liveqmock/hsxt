/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.aps.services.companyInfo.imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.aps.services.companyInfo.ISystemRegisterService;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.bean.ent.AsEntBaseInfo;

@Service
public class SystemRegisterServiceImpl extends BaseServiceImpl implements ISystemRegisterService {


	@Override
	public Object findById(Object id) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("companyId", "123456789");
		map.put("registerDate","1900-01-01");
		map.put("conpanyName_cn","深圳市互生科技有限公司" );
		map.put("conpanyName_en","Shen Zhen Hu Sheng Technologies Co.");
		map.put("address", "深圳");
		map.put("currency", "人民币");
		return map;
	}

	@Override
	public String save(String entityJsonStr) {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public PageData findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AsEntBaseInfo  findSysRegisterInfo(String custId) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("companyId", "123456789");
        map.put("registerDate","1900-01-01");
        map.put("conpanyName_cn","深圳市互生科技有限公司" );
        map.put("conpanyName_en","Shen Zhen Hu Sheng Technologies Co.");
        map.put("address", "深圳");
        map.put("currency", "人民币");
        
        
        AsEntBaseInfo    info = new AsEntBaseInfo  ();
        
//        map.put("registerDate",info.getBaseInfo().getBuildDate());
//        map.put("conpanyName_cn",info.getMainInfo().getEntName());
//        map.put("conpanyName_en",info.getMainInfo().getEntNameEn());
//        map.put("address", info.getMainInfo().getEntRegAddr());
//        map.put("currency",info.get);  获得平台结算币种
        
          info.setEntCustId("213512156151");
          info.setBuildDate("1900-01-01");
          info.setContactAddr("深圳市互生科技有限公司");
          info.setEntName("深圳市互生科技有限公司");
          info.setEntNameEn("Shen Zhen Hu Sheng Technologies Co.");
//          info.set
        
        return info;
    }

}
