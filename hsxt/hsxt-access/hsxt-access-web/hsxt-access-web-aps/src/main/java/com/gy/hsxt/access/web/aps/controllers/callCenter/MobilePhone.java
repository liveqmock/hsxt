package com.gy.hsxt.access.web.aps.controllers.callCenter;


import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * User:leiyt
 * Create:2016/2/29-10:57
 */
public class MobilePhone {



    /**
     * 手机号码归属地查询
     *
     * @param num
     * @return
     */
    public static String getMobilePhoneCity(String num) {
        String httpUrl = "http://apis.baidu.com/showapi_open_bus/mobile/find";
        String httpArg = "num=" + num;
        String jsonResult = request(httpUrl, httpArg);
        return JSONObject.parseObject(jsonResult).getString("showapi_res_body");
    }


    /**
     * @param httpUrl :请求接口
     * @param httpArg :参数
     * @return 返回结果
     */
    public static String request(String httpUrl, String httpArg) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        httpUrl = httpUrl + "?" + httpArg;
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", "240855096e66ec021b8b63cf68080b89");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
