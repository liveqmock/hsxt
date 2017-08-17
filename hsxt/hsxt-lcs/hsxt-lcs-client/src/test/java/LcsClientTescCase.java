import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gy.hsxt.lcs.bean.Bank;
import com.gy.hsxt.lcs.bean.City;
import com.gy.hsxt.lcs.bean.Country;
import com.gy.hsxt.lcs.bean.CountryCurrency;
import com.gy.hsxt.lcs.bean.Currency;
import com.gy.hsxt.lcs.bean.EnumItem;
import com.gy.hsxt.lcs.bean.Language;
import com.gy.hsxt.lcs.bean.LocalInfo;
import com.gy.hsxt.lcs.bean.PayBank;
import com.gy.hsxt.lcs.bean.Plat;
import com.gy.hsxt.lcs.bean.Province;
import com.gy.hsxt.lcs.bean.Unit;
import com.gy.hsxt.lcs.client.LcsClient;
import com.gy.hsxt.lcs.client.OpenCountry;
import com.gy.hsxt.lcs.client.ProvinceTree;

/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */

/**
 * 
 * @Package:
 * @ClassName: LcsClientTescCase
 * @Description: TODO
 * 
 * @author: yangjianguo
 * @date: 2015-12-11 下午3:02:11
 * @version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/spring-global.xml")
public class LcsClientTescCase {

    @Autowired
    LcsClient lcsClient;

    @Before
    public void setUp() {
        // lcsClient.getRedisUtil().flushDB();
    }

    @Test
    public void findContryAll() {
        List<Country> list = lcsClient.findContryAll();
        System.out.println(list);
    }

    @Test
    public void queryProvinceByParent() {
        List<Province> list = lcsClient.queryProvinceByParent("156");
        System.out.println(list);
    }

    @Test
    public void queryCityByParent() {
        List<City> list = lcsClient.queryCityByParent("156", "11");
        System.out.println(list);
    }

    @Test
    public void getLocalInfo() {
        LocalInfo localInfo = lcsClient.getLocalInfo();
        System.out.println(localInfo);
    }

    @Test
    public void queryUnitForLang() {
        List<Unit> list = lcsClient.queryUnitByLanguage("zh_CN");
        System.out.println(list);
    }

    @Test
    public void queryEnumGroupForLang() {
        List<EnumItem> list = lcsClient.queryEnumItemByGroup("zh_CN", "POINT_ASSIGN_RULE");
        System.out.println(list);
    }

    @Test
    public void queryPayBankAll() {
        List<PayBank> list = lcsClient.queryPayBankAll();
        System.out.println(list);
    }

    @Test
    public void queryTransBankAll() {
        List<Bank> list = lcsClient.queryBankAll();
        System.out.println(list);
    }

    @Test
    public void queryPayBankByCode() {
        PayBank payBank = lcsClient.queryPayBankByCode("0056");
        System.out.println(payBank);
    }

    @Test
    public void queryCurrencyAll() {
        List<Currency> list = lcsClient.queryCurrencyAll();
        System.out.println(list);
    }

    @Test
    public void queryCurrencyByCode() {
        Currency currency = lcsClient.queryCurrencyByCode("CNY");
        System.out.println(currency);
    }

    @Test
    public void getCurrencyById() {
        Currency currency = lcsClient.getCurrencyById("156");
        System.out.println(currency);
    }

    @Test
    public void getPromptMsg() {
        String message = lcsClient.getPromptMsg("zh_CN", "ddd");
        System.out.println(message);
    }

    @Test
    public void getErrorMsg() {
        String message = lcsClient.getErrorMsg("zh_CN", 22222);
        System.out.println(message);
    }

    @Test
    public void queryCountryCurrency() {
        List<CountryCurrency> list = lcsClient.queryCountryCurrency();
        System.out.println(list);
    }

    @Test
    public void queryLanguageAll() {
        List<Language> list = lcsClient.queryLanguageAll();
        System.out.println(list);
    }

    @Test
    public void queryPlatAll() {
        List<Plat> list = lcsClient.queryPlatAll();
        System.out.println("全部平台信息列表：" + list);
    }

    @Test
    public void queryAreaPlatAll() {
        List<Plat> list = lcsClient.queryAreaPlatAll();
        System.out.println("全部地区平台信息列表：" + list);
    }

    @Test
    public void queryOpenCountry() {
        List<OpenCountry> list = lcsClient.queryOpenCountryAll();
        System.out.println("全部已开启平台的国家路由信息：" + list);
    }

    @Test
    public void queryProvinceTree() {
        long start = new Date().getTime();
        List<ProvinceTree> list = lcsClient.queryProvinceTreeOfCountry("156");
        System.out.println(list);
        System.out.println("共耗时：" + (new Date().getTime() - start) + "毫秒");
    }

    @Test
    public void queryResRoute() {
        System.out.println(lcsClient.getResNoRouteMap());
    }

    @Test
    public void isAcrossPlat() {
        System.out.println("06001010001 is remote "+lcsClient.isAcrossPlat("06001010001"));
    }

    @Test
    public void findCountryForDelta() {
        System.out.println(lcsClient.findCountryForDelta(1));
    }
    
    @Test
    public void isAcrossPlatMultiThread() {
        for(int i=0;i<50;i++){
            new Thread(new Runnable() {
                
                @Override
                public void run() {
                        System.out.println("06001010001 is remote "+lcsClient.isAcrossPlat("06001010001"));
                }
            }).start();
        }
        
        try
        {
            Thread.currentThread().sleep(10000);
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
