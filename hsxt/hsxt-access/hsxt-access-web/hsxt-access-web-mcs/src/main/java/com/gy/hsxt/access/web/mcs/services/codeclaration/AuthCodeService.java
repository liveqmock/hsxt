package com.gy.hsxt.access.web.mcs.services.codeclaration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.bs.api.apply.IBSDeclareService;
import com.gy.hsxt.bs.bean.apply.DeclareAuthCode;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/**
 * 
 * @projectName   : hsxt-access-web-mcs
 * @package       : com.gy.hsxt.access.web.mcs.services.codeclaration
 * @className     : AuthCodeService.java
 * @description   : 授权码查询服务类
 * @author        : maocy
 * @createDate    : 2015-12-08
 * @updateUser    : 
 * @updateDate    : 
 * @updateRemark  : 
 * @version       : v0.0.1
 */
@Service
public class AuthCodeService extends BaseServiceImpl implements IAuthCodeService {
	
    @Autowired
    private IBSDeclareService bsService;//企业申报服务类

	@Override
	public PageData<DeclareAuthCode> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
        String entResNo = (String) filterMap.get("pointNo");//管理公司互生号
		String entName = (String) filterMap.get("entName");//企业名称
		String linkman = (String) filterMap.get("linkman");//联系人
		String resNo = (String) filterMap.get("entOwnerResNo");//拟用互生号
		return this.bsService.manageQueryAuthCode(entResNo, resNo, entName, linkman, page);
	}
	
}
