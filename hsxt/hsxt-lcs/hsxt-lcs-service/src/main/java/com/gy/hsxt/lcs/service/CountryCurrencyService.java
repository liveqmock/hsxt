package com.gy.hsxt.lcs.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gy.hsxt.lcs.bean.CountryCurrency;
import com.gy.hsxt.lcs.interfaces.ICountryCurrencyService;
import com.gy.hsxt.lcs.mapper.CountryCurrencyMapper;

/**
 * 六种常用货币信息
 * 
 * @Package: com.gy.hsxt.lcs.service
 * @ClassName: CountryCurrencyService
 * @Description: TODO
 * 
 * @author: liyh
 * @date: 2015-12-11 下午2:17:37
 * @version V1.0
 */

@Service("countryCurrencyService")
public class CountryCurrencyService implements ICountryCurrencyService {

    @Resource(name = "countryCurrencyMapper")
    private CountryCurrencyMapper countryCurrencyMapper;

    public List<CountryCurrency> queryCountryCurrency() {
        return countryCurrencyMapper.queryCountryCurrency();
    }

}
