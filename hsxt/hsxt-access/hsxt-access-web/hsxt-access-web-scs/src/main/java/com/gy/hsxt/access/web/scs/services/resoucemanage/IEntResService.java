/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.resoucemanage;

import java.util.Map;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.entstatus.MemberQuit;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.bean.ReportsEnterprisResource;
import com.gy.hsxt.uc.as.bean.ent.AsBelongEntInfo;

/***
 * 
 * 
 * @Package: com.gy.hsxt.access.web.scs.services.sysres
 * @ClassName: IEntResService
 * @Description: 企业资源管理接口类
 * @author: wangjg
 * @date: 2015-11-4 上午11:48:56
 * @version V1.0
 */
@SuppressWarnings("rawtypes")
public interface IEntResService extends IBaseService {
	
	/**
     * 
     * 方法名称：查询成员企业资格维护
     * 方法描述：查询成员企业资格维护
     * @param filterMap 查询条件
     * @param sortMap 排序条件
     * @param page 分页条件
     * @return
     * @throws HsException
     */
    public PageData<ReportsEnterprisResource> findQualMainList(Map filterMap, Map sortMap, Page page) throws HsException;
    
    /**
     * 
     * 方法名称：注销成员企业
     * 方法描述：注销成员企业
     * @param memberQuit 注销信息
     * @throws HsException
     * @throws Exception 
     */
	public void createMemberQuit(MemberQuit memberQuit) throws HsException, Exception;

}
