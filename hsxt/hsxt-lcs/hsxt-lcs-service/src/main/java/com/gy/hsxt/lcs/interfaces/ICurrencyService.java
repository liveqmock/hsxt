package com.gy.hsxt.lcs.interfaces;

import java.util.List;

import com.gy.hsxt.lcs.bean.Currency;

/***************************************************************************
 * <PRE>
 *  Project Name    : com.gy.hsxt.lcs
 * 
 *  Package Name    : com.gy.hsxt.lcs.interfaces
 * 
 *  File Name       : ICurrencyService.java
 * 
 *  Creation Date   : 2015-7-12
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 币种ICurrencyService接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface ICurrencyService {

    /**
     * 批量更新数据
     * 
     * @param list
     *            获取变的版本号的数据表示 必须
     * @param version
     *            版本号
     * @return 返回int 1成功，其他失败
     */
    public int addOrUpdateCurrency(List<Currency> list, Long version);

    /**
     * 查询所有货币信息
     * 
     * @return
     */
    public List<Currency> queryCurrencyAll();

    /**
     * 根据货币代码查询货币信息
     * 
     * @param currencyCode
     * @return
     */
    public Currency queryCurrencyByCode(String currencyCode);

    /**
     * 根据货币数字编号获取货币信息
     * 
     * @param currencyNo
     * @return
     */
    Currency getCurrencyById(String currencyNo);
}
