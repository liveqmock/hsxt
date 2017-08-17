package com.gy.hsxt.access.web.aps.services.accountPerNoCard;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.gy.hsi.common.utils.StringUtils;
import com.gy.hsxt.access.web.common.service.BaseServiceImpl;
import com.gy.hsxt.common.bean.Page;
import com.gy.hsxt.common.bean.PageData;
import com.gy.hsxt.common.exception.HsException;
import com.gy.hsxt.rp.api.IReportsAccountEntryService;
import com.gy.hsxt.rp.bean.ReportsAccountEntry;
import com.gy.hsxt.rp.bean.ReportsAccountEntryInfo;

@Service("accountNoCarService")
public class AccountNoCarService extends BaseServiceImpl<AccountNoCarService> implements
        IAccountNoCarService {

    // 账户流水查询服务
    @Resource
    private IReportsAccountEntryService iReportsAccountEntryService;

    /**
     * 非持卡人账户流水分页查询
     * 
     * @param filterMap
     * @param sortMap
     * @param page
     * @return PageData<ReportsAccountEntry> 非持卡人账户流水结果集
     * @throws HsException
     * @see com.gy.hsxt.access.web.aps.services.accountPerNoCard.IAccountNoCarService#searchPerAccountEntry(java.util.Map,
     *      java.util.Map, com.gy.hsxt.common.bean.Page)
     */
    @Override
    public PageData<ReportsAccountEntry> searchPerNoCardAccPage(Map filterMap, Map sortMap, Page page)
            throws HsException {
        ReportsAccountEntryInfo raei = this.createAccountEntryInfo(filterMap);
        return iReportsAccountEntryService.searchNoCarAccountEntry(raei, page);
    }

    /**
     * 创建账户明细查询类
     * 
     * @param filter
     * @return
     */
    ReportsAccountEntryInfo createAccountEntryInfo(Map filter) {
        ReportsAccountEntryInfo aei = new ReportsAccountEntryInfo();
        // 手机号
        String mobile = (String) filter.get("mobile");
        if (!StringUtils.isEmpty(mobile))
        {
            aei.setMobile(mobile);
        }
        // 账户类型编码
        String accType = (String) filter.get("accType");
        if (!StringUtils.isEmpty(accType))
        {
            String[] accTypes = { accType };
            aei.setAccTypes(accTypes);
        }
        // 业务类型
        String businessType = (String) filter.get("businessType");
        if (!StringUtils.isEmpty(businessType))
        {
            aei.setBusinessType(Integer.parseInt(businessType));
        }
        // 非持卡人名称
        String custName = (String) filter.get("perName");
        if (!StringUtils.isEmpty(custName))
        {
            aei.setCustName(custName);
        }
        // 开始时间
        String beginDate = (String) filter.get("beginDate");
        if (!StringUtils.isEmpty(beginDate))
        {
            aei.setBeginDate(beginDate);
        }
        // 结束时间
        String endDate = (String) filter.get("endDate");
        if (!StringUtils.isEmpty(endDate))
        {
            aei.setEndDate(endDate);
        }
        return aei;
    }

}
