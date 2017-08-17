package com.gy.hsxt.lcs.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.lcs.bean.Currency;
import com.gy.hsxt.lcs.bean.Version;
import com.gy.hsxt.lcs.common.Constant;
import com.gy.hsxt.lcs.interfaces.ICurrencyService;
import com.gy.hsxt.lcs.interfaces.IVersionService;
import com.gy.hsxt.lcs.mapper.CurrencyMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.service
 * 
 *  File Name       : CurrencyService.java
 * 
 *  Creation Date   : 2015-7-12
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 币种CurrencyService实现类
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/

@Service("currencyService")
public class CurrencyService implements ICurrencyService {

    @Resource(name = "currencyMapper")
    private CurrencyMapper currencyMapper;

    @Autowired
    private IVersionService veresionService;

    /**
     * 批量更新数据
     * 
     * @param list
     *            获取变的版本号的数据表示 必须
     * @param version
     *            版本号
     * @return 返回int 1成功，其他失败
     */
    @Transactional
    public int addOrUpdateCurrency(List<Currency> list, Long version) {
        veresionService.addOrUpdateVersion4SyncData(new Version(Constant.GL_CURRENCY, version, true));
        List<Currency> cityListAdd = new ArrayList<Currency>();
        List<Currency> cityListUpdate = new ArrayList<Currency>();

        for (Currency obj : list)
        {
            if ("1".equals(currencyMapper.existCurrency(obj.getCurrencyNo())))
            {
                cityListUpdate.add(obj);
            }
            else
            {
                cityListAdd.add(obj);
            }
        }

        if (cityListUpdate.size() > 0)
        {
            currencyMapper.batchUpdate(cityListUpdate);
        }

        if (cityListAdd.size() > 0)
        {
            currencyMapper.batchInsert(cityListAdd);
        }
        return 1;
    }

    /**
     * 查询所有货币信息
     * 
     * @return
     * @see com.gy.hsxt.lcs.interfaces.ICurrencyService#queryCurrencyAll()
     */
    public List<Currency> queryCurrencyAll() {
        return currencyMapper.queryCurrencyAll();
    }

    /**
     * 根据货币代码查询货币信息
     * 
     * @param currencyCode
     * @return
     * @see com.gy.hsxt.lcs.interfaces.ICurrencyService#queryCurrencyByCode(java.lang.String)
     */
    public Currency queryCurrencyByCode(String currencyCode) {
        return currencyMapper.queryCurrencyByCode(currencyCode);
    }

    @Override
    public Currency getCurrencyById(String currencyNo) {
        return currencyMapper.getCurrencyById(currencyNo);
    }

}
