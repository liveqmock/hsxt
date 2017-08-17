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

package com.gy.hsxt.tc.batch.runnable;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Package: com.gy.hsxt.tc.batch.runnable
 * @ClassName: IDataHandler
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-11-13 下午2:08:45
 * @version V1.0
 */
public interface IDataHandler {
    /**
     * 存放 对账数据对应的入库表 传递给数据库dao
     */
    public static final String TABLE = "TABLE";
    /**
     * 存放 对账数据对 入库时对应入库字段 传递给数据库dao
     */
    public static final String COLUMNS = "COLUMNS";
    /**
     * 存放待入库数据列表 传递给数据库dao
     */
    public static final String DATA_LIST = "DATA_LIST";

    /**
     * 把数据存入数据库及redis
     * @param dataList 待入库数据
     * @param checkKeyList 待存入redis的对账要素
     * @param totalCheckKeyList 若非null，把 checkKeyList存进来
     * @return
     */
    public int saveData(List<Map<String, String>> dataList, List<String> checkKeyList, List<String> totalCheckKeyList);

    /**
     * 解析一行数据文本
     * @param dataList 保存解析出来的待入库数据
     * @param checkKeyList 保存解析出来的对账要素数据
     * @param oneLineText 一行待解析的数据文本
     */
    public void proccessRow(List<Map<String, String>> dataList, List<String> checkKeyList, String oneLineText);

    /**
     * 初始化从数据文件读取的总记录数=0
     * 
     * @param count
     */
    public void reSetDataCount();

    /**
     * 返回从文件读取的总记录数
     * 
     * @return
     */
    public long getDataCount();
}
