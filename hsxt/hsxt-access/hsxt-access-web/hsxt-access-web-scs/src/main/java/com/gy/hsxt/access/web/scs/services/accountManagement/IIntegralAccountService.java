package com.gy.hsxt.access.web.scs.services.accountManagement;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.IBaseService;
import com.gy.hsxt.bs.bean.bm.BmlmDetail;
import com.gy.hsxt.bs.bean.bm.MlmTotal;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.ps.bean.AllocDetailSum;

/***************************************************************************
 * <PRE>
 * Description 		: 用户积分服务类
 * 
 * Project Name   	: hsxt-access-web-person
 * 
 * Package Name     : com.gy.hsxt.access.web.integration.service.AccountS.java
 * 
 * File Name        :AccountS
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-8-18 下午2:31:09  
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-8-18 下午2:31:09
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v0.0.1
 * 
 * </PRE>
 ***************************************************************************/
@Service
public interface IIntegralAccountService extends IBaseService {
    
    // 分页查询互生积分分配列表
    public PageData<MlmTotal> queryMlmListPage(Map filterMap, Map sortMap, Page page) throws HsException;
    // 分页查询再增值积分汇总列表
    public PageData<BmlmDetail> queryBmlmListPage(Map filterMap, Map sortMap, Page page) throws HsException;
    // 分页查询消费积分分配汇总
    public PageData<AllocDetailSum> queryPointDetailSumPage(Map filterMap, Map sortMap, Page page) throws HsException;

}
