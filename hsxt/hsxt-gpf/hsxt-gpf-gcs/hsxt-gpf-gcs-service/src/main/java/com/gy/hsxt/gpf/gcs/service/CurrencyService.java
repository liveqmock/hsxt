package com.gy.hsxt.gpf.gcs.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gy.hsxt.gpf.gcs.bean.Currency;
import com.gy.hsxt.gpf.gcs.common.Constant;
import com.gy.hsxt.gpf.gcs.interfaces.ICurrencyService;
import com.gy.hsxt.gpf.gcs.interfaces.IVersionService;
import com.gy.hsxt.gpf.gcs.mapper.CurrencyMapper;

/***************************************************************************
 * <PRE>
 *  Project Name    : hsxt-gpf-gcs-service
 * 
 *  Package Name    : com.gy.hsxt.gpf.gcs.service
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
@Transactional(readOnly = true)
public class CurrencyService implements ICurrencyService {

    @Resource(name = "currencyMapper")
    private CurrencyMapper currencyMapper;

    @Autowired
    private IVersionService veresionService;

    /**
     * 插入记录
     * 
     * @param currency
     *            实体类 必须，非null
     * @return 返int类型 1成功，其他失败
     */
    @Transactional
    public int insert(Currency currency) {
        long version = veresionService.addOrUpdateVersion(Constant.GL_CURRENCY);
        currency.setVersion(version);
        return currencyMapper.insert(currency);
    }

    /**
     * 读取某条记录
     * 
     * @param currencyNo
     *            币种代码 必须，非null
     * @return 返回实体类Country
     */
    public Currency getCurrency(String currencyNo) {
        return currencyMapper.getCurrency(currencyNo);
    }

    /**
     * 获取数据分页列表
     * 
     * @param currency实体类
     *            必须，非null
     * @return 返回List<Currency>,数据为空，返回空List<Currency>
     */
    public List<Currency> getCurrencyListPage(Currency currency) {
        return currencyMapper.getCurrencyListPage(currency);
    }

    /**
     * 获取全部有效货币
     * 
     * @return 返回List<Currency>,数据为空，返回空List<Currency>
     */
    public List<Currency> getCurrencyAll() {
        return currencyMapper.getCurrencyAll();
    }

    /**
     * 更新某条记录
     * 
     * @param currency实体类
     *            必须，非null
     * @return 返回int类型 1成功，其他失败
     */
    @Transactional
    public int update(Currency currency) {
        long version = veresionService.addOrUpdateVersion(Constant.GL_CURRENCY);
        currency.setVersion(version);
        return currencyMapper.update(currency);
    }

    /**
     * 删除某条记录
     * 
     * @param currency
     *            实体类 必须，非null
     * @return 返int类型 1成功，其他失败
     */
    @Transactional
    public int delete(String currencyNo) {
        long version = veresionService.addOrUpdateVersion(Constant.GL_CURRENCY);
        Currency currency = new Currency();
        currency.setCurrencyNo(currencyNo);
        currency.setVersion(version);
        currency.setDelFlag(1);
        return currencyMapper.delete(currency);
    }

    /**
     * 判断是否已存在相同
     * 
     * @param currencyNo
     *            币种代码 必须，非null
     * @return 返回String 大于等于1存在，0不存在
     */
    public boolean existCurrency(String currencyNo) {
        boolean isExist = "1".equals(currencyMapper.existCurrency(currencyNo));
        return isExist;
    }

    /**
     * 读取大于某个版本号的数据
     * 
     * @param version
     *            版本号 必须，非null
     * @return 返回List<Currency>,数据为空，返回空List<Currency>
     */
    public List<Currency> queryCurrencySyncData(Long version) {
        return currencyMapper.queryCurrencySyncData(version);
    }
}
