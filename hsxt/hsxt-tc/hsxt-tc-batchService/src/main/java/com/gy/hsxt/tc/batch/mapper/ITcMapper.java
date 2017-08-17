package com.gy.hsxt.tc.batch.mapper;

import java.util.Map;

public interface ITcMapper {
    /**
     * 清空临时表数据
     * @param tableName 表名
     * @return
     */
    public int deleteTableData(String tableName);

    /**
     * 数据文件入库
     * 
     * @param map
     *            keys:TABLE,COLUMNS,DATA_LIST
     * @return
     */
    public int insertFileDataToDb(Map<String, Object> map);

    /**
     * 尝试使用长款数据消除存疑账单
     * @param map
     * @return
     */
    public int deleteDoubtByMore(Map<String, Object> map);
    /**
     * 尝试使用长款数据更新对账要素不一致账单
     * @param map
     * @return
     */
    public int updateDoubtByMore(Map<String, Object> map);
    /**
     * 长款数据写入存疑表
     * @param map
     * @return
     */
    public int insertDoubtByMore(Map<String, Object> map);
    
    /**
     * 尝试使用短款数据消除存疑账单
     * @param map
     * @return
     */
    public int deleteDoubtByLack(Map<String, Object> map);
    /**
     * 尝试使用短款数据更新对账要素不一致账单
     * @param map
     * @return
     */
    public int updateDoubtByLack(Map<String, Object> map);
    /**
     * 短款数据写入存疑表
     * @param map
     * @return
     */
    public int insertDoubtByLack(Map<String, Object> map);

    /**
     * 存疑表数据存入不平衡表
     * @param map
     * @return
     */
    public int insertImbalanceByDoubt(Map<String, Object> map);
    /**
     * 删除存疑表数据（在存疑表数据存入不平衡表后）
     * @param map
     * @return
     */
    public int deleteDoubtAfterMoveToImbalance(Map<String, Object> map);   

}
