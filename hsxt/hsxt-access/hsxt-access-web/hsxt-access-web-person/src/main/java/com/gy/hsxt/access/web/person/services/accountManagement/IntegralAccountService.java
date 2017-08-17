package com.gy.hsxt.access.web.person.services.accountManagement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gy.hsxt.ac.api.IAccountSearchService;
import com.gy.hsxt.ac.bean.AccountEntry;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;

/***************************************************************************
 * <PRE>
 * Description 		: 用户积分处理
 * 
 * Project Name   	: hsxt-access-web-person
 * 
 * Package Name     : com.gy.hsxt.access.web.person.integration.service
 * 
 * File Name        : IntegrationServiceImpl
 * 
 * Author           : LiZhi Peter
 * 
 * Create Date      : 2015-8-18 下午3:10:41  
 * 
 * Update User      : LiZhi Peter
 * 
 * Update Date      : 2015-8-18 下午3:10:41
 * 
 * UpdateRemark 	: 说明本次修改内容
 * 
 * Version          : v1.0.0
 * 
 * </PRE>
 ***************************************************************************/
@Service
public class IntegralAccountService extends BaseServiceImpl implements IIntegralAccountService {
    //账户系统查询服务
    @Autowired
    private IAccountSearchService accountSearchService;
    


	@Override
    public PageData<AccountEntry> findScrollResult(Map filterMap, Map sortMap, Page page) throws HsException {
	  //构造数据
        PageData<AccountEntry> pd = new PageData<>();
        AccountEntry accountEntry = null;
        List<AccountEntry> list = new ArrayList<AccountEntry>();
        String strs[] ={"消费积分","消费积分","消费积分撤单","积分转货币","积分转互生币","积分投资"};
        Random rd = new Random();
        for(int i = 1 ; i <= 10 ;i++){
            accountEntry = new AccountEntry(); 
            accountEntry.setEntryNo("5000400"+i);
            accountEntry.setTransNo("W1111102154566489423"+ (i<10?("0"+i):i));      //交易流水号
            accountEntry.setTransDate("2015-07-"+ (i<10?("0"+i):i) +" 10:11:53");   //交易时间
            accountEntry.setBusinessType(i%2==0?1:2);                               //业务类型  (1：收入，2：支出)
            accountEntry.setTransType(strs[rd.nextInt(5)]);                         //交易类型
            accountEntry.setAmount("42"+ (i<10?("0"+i):i));                         //本笔分红合计
            accountEntry.setAccBalanceNew("425"+(i<10?("0"+i):i));                  //积分账户余额
            list.add(accountEntry); //发生积分数
        }
        pd.setCount(21);
        pd.setResult(list);
        return pd;
        
    }
	
	

   

}
