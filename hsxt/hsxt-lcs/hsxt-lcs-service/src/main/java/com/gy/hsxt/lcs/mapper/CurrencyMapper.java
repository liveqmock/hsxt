package com.gy.hsxt.lcs.mapper;

import java.util.List;

import com.gy.hsxt.lcs.bean.Currency;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.mapperl
 * 
 *  File Name       : CurrencyMapper.java
 * 
 *  Creation Date   : 2015-7-12
 * 
 *  Author          : liuhq
 * 
 *  Purpose         : 币种CurrencyMapper接口
 * 
 * 
 *  History         : TODO
 * 
 * </PRE>
 ***************************************************************************/
public interface CurrencyMapper {

    /**
     * 判断是否已存在相同
     * 
     * @param currencyNo
     *            币种代码 必须 非空
     * @return 返回String,1已经存在，0不存在
     */
    public String existCurrency(String currencyNo);

    /**
     * 批量更新数据
     * 
     * @param currencyListUpdate
     *            总平台城市变更的数据列表 必须 非空， 无返回值
     */
    public void batchUpdate(List<Currency> currencyListUpdate);

    /**
     * 批量插入数据
     * 
     * @param currencyListAdd
     *            总平台城市变更的数据列表 必须 非空， 无返回值
     */
    public void batchInsert(List<Currency> currencyListAdd);

    /**
     * 查询所有货币信息
     * 
     * @return
     * @see com.gy.hsxt.lcs.interfaces.ICurrencyService#queryCurrencyAll()
     */
    public List<Currency> queryCurrencyAll();

    /**
     * 根据货币代码查询货币信息
     * 
     * @param currencyCode
     * @return
     * @see com.gy.hsxt.lcs.interfaces.ICurrencyService#queryCurrencyByCode(java.lang.String)
     */
    public Currency queryCurrencyByCode(String currencyCode);

    /**
     * 根据货币编号查询货币信息
     * 
     * @param currencyNo
     * @return
     */
    public Currency getCurrencyById(String currencyNo);
}
