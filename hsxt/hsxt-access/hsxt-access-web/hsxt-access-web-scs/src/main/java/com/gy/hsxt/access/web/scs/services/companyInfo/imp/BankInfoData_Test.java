/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
 */
package com.gy.hsxt.access.web.scs.services.companyInfo.imp;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BankInfoData_Test {

    private static List<Map<String, String>> bankList = new ArrayList<>();

    private static List<Map<String, String>> provinceList = new ArrayList<>();

    private static List<Map<String, String>> cityList = new ArrayList<>();
    
    private static List<Object> accountList = new ArrayList<>();

    static
    {
        Map<String, String> map1 = new HashMap<String, String>();
        Map<String, String> map2 = new HashMap<String, String>();
        Map<String, String> map3 = new HashMap<String, String>();
        Map<String, String> map4 = new HashMap<String, String>();
        Map<String, String> map5 = new HashMap<String, String>();

        map1.put("value", "1");
        map1.put("name", "工商银行");
        map2.put("value", "2");
        map2.put("name", "农业银行");
        map3.put("value", "3");
        map3.put("name", "建设银行");
        map4.put("value", "4");
        map4.put("name", "平安银行");
        map5.put("value", "5");
        map5.put("name", "交通银行");
        bankList.add(map1);
        bankList.add(map2);
        bankList.add(map3);
        bankList.add(map4);
        bankList.add(map5);

        map1 = new HashMap<String, String>();
        map2 = new HashMap<String, String>();
        map3 = new HashMap<String, String>();

        map1.put("value", "1");
        map1.put("name", "广东省");
        map2.put("value", "2");
        map2.put("name", "湖南省");
        map3.put("value", "3");
        map3.put("name", "江西省");
        provinceList.add(map1);
        provinceList.add(map2);
        provinceList.add(map3);

        map1 = new HashMap<String, String>();
        map2 = new HashMap<String, String>();
        map3 = new HashMap<String, String>();

        map1.put("value", "11");
        map1.put("name", "深圳市");
        map1.put("provinceCode", "1");
        map2.put("value", "22");
        map2.put("name", "长沙市");
        map2.put("provinceCode", "2");
        map3.put("value", "33");
        map3.put("name", "南昌市");
        map3.put("provinceCode", "3");
        cityList.add(map2);
        cityList.add(map1);
        cityList.add(map3);
        
        
        
      
        
  
        
    }

    public static Object getBankList() {
        return bankList;
    }
    public static Object getprovinceList() {
        return provinceList;
    }
    public static Object getcityList(Object obj) {
        List<Map<String,String>> list=new ArrayList<>();
        for(Map<String,String> map:cityList){
            if(obj.equals(map.get("provinceCode"))){
                list.add(map); 
            }
        }
        return list;
    }
   
    
    
    
   
    
    
    public static Object getAccountList(){
        return accountList;
    }
    
   public static void main(String[] args) {
       try
    {
      Class clazz=  Class.forName("com.gy.hsxt.uc.as.bean.ent.AsEntMainInfo");
      
      for(Method m:clazz.getDeclaredMethods()){
          if(m.getName().contains("set")){
              System.out.println("info."+m.getName()+"(\"\");");
          }
      }
    }
    catch (ClassNotFoundException e)
    {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
   }

}
