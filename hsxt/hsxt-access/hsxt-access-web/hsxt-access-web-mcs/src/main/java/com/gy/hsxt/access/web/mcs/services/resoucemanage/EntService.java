/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.access.web.mcs.services.resoucemanage;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.uc.as.api.common.IUCAsBankAcctInfoService;
import com.gy.hsxt.uc.as.api.ent.IUCAsEntService;
import com.gy.hsxt.uc.as.api.enumerate.UserTypeEnum;
import com.gy.hsxt.uc.as.bean.common.AsBankAcctInfo;
import com.gy.hsxt.uc.as.bean.ent.AsEntAllInfo;

/***
 * 企业实现类
 * @Package: com.gy.hsxt.access.web.mcs.services.resoucemanage  
 * @ClassName: EntService 
 * @Description: TODO
 *
 * @author: wangjg 
 * @date: 2016-1-21 下午6:06:42 
 * @version V1.0
 */
@Service
public class EntService extends BaseServiceImpl implements IEntService {
    /**银行账户服务 **/
    @Resource
    private IUCAsBankAcctInfoService iucAsBanckAcctService;
    /**企业相关服务**/
    @Resource
    private IUCAsEntService iuCAsEntService;
    
    /**
     * 获取企业明细(服务公司、托管企业、服务公司....)
     * @param entCustId
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.resoucemanage.IEntService#getEntAllInfo(java.lang.String)
     */
    @Override
    public AsEntAllInfo getEntAllInfo(String entCustId) throws HsException {
      return  iuCAsEntService.searchEntAllInfo(entCustId);
    }

    /**
     * 获取企业银行信息
     * @param entCustId
     * @return
     * @throws HsException 
     * @see com.gy.hsxt.access.web.mcs.services.resoucemanage.IEntService#getEntBankList(java.lang.String)
     */
    @Override
    public List<AsBankAcctInfo> getEntBankList(String entCustId) throws HsException {
        return iucAsBanckAcctService.listBanksByCustId(entCustId, UserTypeEnum.ENT.getType());
    }

}
