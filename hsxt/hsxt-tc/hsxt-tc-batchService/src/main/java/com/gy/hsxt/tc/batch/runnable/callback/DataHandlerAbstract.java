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

package com.gy.hsxt.tc.batch.runnable.callback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.redis.core.RedisTemplate;

import com.gy.hsxt.tc.batch.mapper.ITcMapper;
import com.gy.hsxt.tc.batch.runnable.IDataHandler;

/**
 * 对账文件入库公共类
 * @Package: com.gy.hsxt.tc.batch.runnable.callback
 * @ClassName: DataHandler4bsgpBS
 * @Description: TODO
 * 
 * @author: lvyan
 * @date: 2015-11-13 下午2:18:48
 * @version V1.0
 */
public abstract class DataHandlerAbstract implements IDataHandler {
    /**
     * 账单入库表
     */
    private  String mTableName ;

    /**
     * 账单入库表字段 (与数据文件字段顺序保持一致)
     */
    private String[] mTableColumns;

    ITcMapper batchMapper;

    RedisTemplate redisTemplate;

    HashMap<String, Object> daoParams;
    
    long mDataCountFromFile=0l;

    public DataHandlerAbstract(ITcMapper batchMapper, RedisTemplate redisTemplate,String mTableName,String[] mTableColumns) {
        this.batchMapper = batchMapper;
        this.redisTemplate = redisTemplate;
        this.mTableName = mTableName;
        this.mTableColumns = mTableColumns;
        daoParams = new HashMap<String, Object>();
        daoParams.put(TABLE, mTableName);
        daoParams.put(COLUMNS, mTableColumns);

    }
    /**
     * 生成对账要素
     * @param args 一行数据
     * @return
     */
    public abstract String generateCheckKey(String[] args) ;

    /**
     * 把数据存入数据库及redis
     * @param dataList 待入库数据
     * @param checkKeyList 待存入redis数据
     * @param totalCheckKeyList 可选传入容器，以便保存 checkKeyList,totalCheckKeyList.addAll(checkKeyList)
     * @return 
     * @see com.gy.hsxt.tc.batch.runnable.IDataHandler#saveData(java.util.List, java.util.List, java.util.List)
     */
    public synchronized int saveData(List<Map<String, String>> dataList, List<String> checkKeyList,
            List<String> totalCheckKeyList) {
        int insertDbRecords = 0;
        Long redisSaveCount = 0l;
        if (dataList != null && !dataList.isEmpty())
        {
            daoParams.put(DATA_LIST, dataList);
            insertDbRecords = batchMapper.insertFileDataToDb(daoParams);
        }else{
        	System.out.println(""+mDataCountFromFile+mTableName+ "dataList="+dataList);
        }
        if (checkKeyList != null && !checkKeyList.isEmpty())
        {
        	redisSaveCount= redisTemplate.opsForSet().add(mTableName, checkKeyList.toArray());
            if (totalCheckKeyList != null )
            {
                totalCheckKeyList.addAll(checkKeyList);
            }
            if(insertDbRecords!=redisSaveCount){ 
            	//数据库入库条数和redis存储条数不一致，打印异常信息
            	System.out.println(""+mDataCountFromFile+mTableName+ " insertDbRecords="+insertDbRecords+",redisSaveCount="+redisSaveCount);
            }
        }else{
        	System.out.println("checkKeyList="+checkKeyList);
        }
        mDataCountFromFile += insertDbRecords;
        return insertDbRecords;

    }

    /**
     * 拆解一行文本数据
     * @param dataList 传入容器，保存拆解后数据
     * @param checkKeyList 传入容器，保存拆解后对账要素字符串
     * @param oneLineText 待拆解数据文本
     * @see com.gy.hsxt.tc.batch.runnable.IDataHandler#proccessRow(java.util.List, java.util.List, java.lang.String)
     */
    public void proccessRow(List<Map<String, String>> dataList, List<String> checkKeyList, String oneLineText) {
        String[] lDataValues = oneLineText.split("\\|");
        // 每个 dataMap 存放一条数据，key=数据库字段名
        HashMap<String, String> lDataMap = new HashMap<String, String>();
        int lColumns = lDataValues.length;
        for (int colIndex = 0; colIndex < lColumns; colIndex++)
        { // 把一行数据存入map，以便后续入库
            lDataMap.put(mTableColumns[colIndex], lDataValues[colIndex]);
        }
        dataList.add(lDataMap);
        if(checkKeyList!=null){
        	checkKeyList.add(generateCheckKey(lDataValues));
        }
    }
    
    /**
     * 初始化从数据文件读取的总记录数=0
     * 
     * @param count
     */
    public synchronized void reSetDataCount() {
        mDataCountFromFile = 0l;
    }

    /**
     * 返回从文件读取的总记录数
     * @return
     */
    public long getDataCount() {
        return mDataCountFromFile;
    }
}
