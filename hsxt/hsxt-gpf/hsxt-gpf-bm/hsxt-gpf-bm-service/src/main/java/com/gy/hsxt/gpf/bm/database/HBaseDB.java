package com.gy.hsxt.gpf.bm.database;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.gy.hsxt.gpf.bm.utils.ObjectByteUtils;
import com.gy.hsxt.gpf.um.bean.GridData;
import com.gy.hsxt.gpf.um.bean.GridPage;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * HBase 操作工具类
 *
 * @author zhucy
 * @version v0.0.1
 * @category HBase 操作工具类
 * @projectName apply-incurement
 * @package com.gy.apply.web.increment.database.HbaseUtils.java
 * @className HBaseDB
 * @description HBase 操作工具类
 * @createDate 2014-7-1 下午4:08:54
 * @updateUser zhucy
 * @updateDate 2014-7-1 下午4:08:54
 * @updateRemark 修改（添加log日志）
 */
public class HBaseDB {

    private static final Logger logger = LoggerFactory.getLogger(HBaseDB.class);

    /**
     * 数据库链接
     */
    private Connection connection = null;

    /**
     * 构造函数
     *
     * @param hbaseServer 服务器地址
     * @param clientPort  端口
     * @param master      master
     */
    public HBaseDB(String hbaseServer, String clientPort, String master) {

        try {
            Configuration config = new Configuration();

            config.set("hbase.zookeeper.quorum", hbaseServer);

            if (StringUtils.isNotBlank(clientPort)) {
                config.set("hbase.zookeeper.property.clientPort", clientPort);
            }
            if (StringUtils.isNotBlank(master)) {
                config.set("hbase.master", master);
            }
            //hbase 连接池的最大线程数
            config.set("hbase.hconnection.threads.max","256");
            //hbase 连接池的核心线程数
            config.set("hbase.hconnection.threads.core","20");

            Configuration configuration = HBaseConfiguration.create(config);
            connection = ConnectionFactory.createConnection(configuration);
        } catch (Exception ex) {
            logger.error("==========初始化Hbase Configuration →  Connection失败==============", ex);
        }
    }

    /**
     * 保存一个对象
     *
     * @param tableName 表名
     * @param key       键
     * @param value     值
     * @param clazz     类
     * @param <T>       泛型
     * @throws Exception
     */
    public <T extends Serializable> void insertRow(String tableName, String key, T value, Class<T> clazz) throws Exception {
        Table table = null;
        try {
            //获取bean信息
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            //新建put对象
            Put put = new Put(key.getBytes());
            //遍历属性
            for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                if (null != propertyDescriptor.getWriteMethod()) {
                    //获取属性对应值
                    Object obj = propertyDescriptor.getReadMethod().invoke(value);
                    //添加column
                    put.addColumn(propertyDescriptor.getName().getBytes(), null, obj == null ? null : Bytes.toBytes(String.valueOf(obj)));
                }
            }
            //获取数据库表
            table = connection.getTable(TableName.valueOf(tableName));
            table.put(put);
        } finally {
            IOUtils.closeQuietly(table);
        }
    }


    /**
     * 保存多个同类对象
     *
     * @param tableName 表名
     * @param map       键值对
     * @param clazz     类
     * @param <T>       泛型
     * @throws Exception
     */
    public <T> void insertRows(String tableName, Map<String, T> map, Class<T> clazz) throws Exception {
        Table table = null;
        try {
            if (MapUtils.isNotEmpty(map)) {
                //获取bean信息
                BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
                List<Put> puts = new ArrayList<>();
                for (Map.Entry<String, T> entry : map.entrySet()) {
                    //新建put对象
                    Put put = new Put(entry.getKey().getBytes());
                    //遍历属性
                    for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                        if (null != propertyDescriptor.getWriteMethod()) {
                            //获取属性对应值
                            Object obj = propertyDescriptor.getReadMethod().invoke(entry.getValue());
                            //添加column
                            put.addColumn(propertyDescriptor.getName().getBytes(), null, obj == null ? null : Bytes.toBytes(String.valueOf(obj)));
                        }
                    }
                    puts.add(put);
                }
                //获取数据库表
                table = connection.getTable(TableName.valueOf(tableName));
                table.put(puts);
            }
        } finally {
            IOUtils.closeQuietly(table);
        }
    }


    /**
     * 全表查询
     *
     * @param tableName 表名
     * @param clazz     类
     * @param <T>       泛型
     * @return list
     * @throws Exception
     */
    public <T> List<T> queryAllRows(String tableName, Class<T> clazz) throws Exception {
        Table table = null;
        List<T> resultList = new ArrayList<>();
        ResultScanner resultScanner = null;
        try {
            //获取表对象
            table = connection.getTable(TableName.valueOf(tableName));
            //获取结果扫描器
            resultScanner = table.getScanner(new Scan());
            if (null != resultScanner) {
                //遍历单行结果集
                for (Result result : resultScanner) {
                    resultList.add(generateInstance(result, clazz));
                }
            }
        } finally {
            //关闭扫描器
            IOUtils.closeQuietly(resultScanner);
            IOUtils.closeQuietly(table);
        }
        return resultList;
    }

    /**
     * 查询全部数据（返回反序列化对象）
     *
     * @param tableName 表名
     * @return 返回List<Object> object 为反序列化对象
     */
    public List<Object> queryAllCells(String tableName) throws Exception {
        List<Object> resultList = new ArrayList<>();
        Table table = null;
        ResultScanner rs = null;
        try {
            //获取表对象
            table = connection.getTable(TableName.valueOf(tableName));
            //结果集
            rs = table.getScanner(new Scan());
            if (null != rs) {
                for (Result r : rs) {
                    List<Cell> cells = r.listCells();
                    if (CollectionUtils.isNotEmpty(cells)) {
                        for (Cell cell : cells) {
                            //获取值
                            Object value = Bytes.toString(CellUtil.cloneValue(cell));
                            resultList.add(value);
                        }
                    }
                }
            }
        } finally {
            //关闭扫描器
            IOUtils.closeQuietly(rs);
            IOUtils.closeQuietly(table);
        }
        return resultList;
    }

    /**
     * 查询全部数据
     *
     * @param tableName 表名
     * @return 返回Map
     */
    public <T> Map<String, T> queryListToMap(String tableName, Class<T> clazz) throws Exception {
        Map<String, T> resultMap = new HashMap<>();
        Table table = null;
        ResultScanner rs = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            rs = table.getScanner(new Scan());
            if (null != rs) {
                //遍历单行结果集
                for (Result r : rs) {
                    resultMap.put(new String(r.getRow()), generateInstance(r, clazz));
                }
            }
        } finally {
            IOUtils.closeQuietly(rs);
            IOUtils.closeQuietly(table);
        }
        return resultMap;
    }

    /**
     * 查询全部数据(返回反序列化对象)
     *
     * @param tableName
     * @return 返回Map<String,Object> Object 为反序列化对象
     */
    @SuppressWarnings("deprecation")
    public Map<String, Object> querySerListToMap(String tableName) throws Exception {
        Table table = null;
        ResultScanner rs = null;

        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            rs = table.getScanner(new Scan());
            if (null != rs) {
                Object values = null;
                for (Iterator<Result> it = rs.iterator(); it.hasNext(); ) {
                    Result result = it.next();
                    List<Cell> keyValueList = result.listCells();
                    if (null != keyValueList) {
                        for (int i = 0; i < keyValueList.size(); i++) {
                            Cell keyValue = keyValueList.get(i);
                            values = ObjectByteUtils.toObject(keyValue.getValue());
                        }
                        resultMap.put(new String(result.getRow()), values);
                    }
                }
            }
        } finally {
            IOUtils.closeQuietly(rs);
            IOUtils.closeQuietly(table);
        }
        return resultMap;
    }

    /**
     * 根据 rowkey 查询
     *
     * @param tableName
     * @param key
     */
    public <T> T queryByRowKey(String tableName, String key, Class<T> clazz) throws Exception {
        Table table = null;
        T instance = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Result result = table.get(new Get(key.getBytes()));
            instance = (result == null ? null : generateInstance(result, clazz));
        } finally {
            IOUtils.closeQuietly(table);
        }
        return instance;
    }


    /**
     * 解析对象
     *
     * @param result 一行结果
     * @param clazz  类
     * @param <T>    泛型
     * @return 对象
     */
    private <T> T generateInstance(Result result, Class<T> clazz) throws Exception {
        T instance = null;
        //获取bean信息
        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
        //遍历属性
        if (CollectionUtils.isNotEmpty(result.listCells())) {
            instance = clazz.newInstance();
            for (Cell cell : result.listCells()) {
                //属性名称
                String propertyName = Bytes.toString(CellUtil.cloneFamily(cell));
                String value = Bytes.toString(CellUtil.cloneValue(cell));
                //遍历属性
                for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
                    if (propertyDescriptor.getName().equals(propertyName)) {
                        //类型转换
                        Object val = ConvertUtils.convert(value, propertyDescriptor.getPropertyType());
                        propertyDescriptor.getWriteMethod().invoke(instance, val);
                        break;
                    }
                }
            }
        }
        return instance;
    }

    /**
     * 根据 rowkey 查询(获取序列化对象）
     * 存储格式  {"00000000001":{values:序列化对象值}}
     *
     * @param tableName
     * @param rowKey
     */
    public Object getByRowKey(String tableName, String rowKey) throws Exception {
        Table table = null;
        Object value = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Result result = table.get(new Get(rowKey.getBytes()));

            List<Cell> keyValueList = result.listCells();
            if (null != keyValueList) {
                for (Cell cell : keyValueList) {
                    value = ObjectByteUtils.toObject(CellUtil.cloneValue(cell));
                }
            }
        } finally {
            IOUtils.closeQuietly(table);
        }
        return value;
    }

    /**
     * 根据列族查询对应的value
     *
     * @param tableName
     * @param rowKey
     * @param family
     * @return 返回Class 对应的实体对象
     */
    public <T> T queryByFamily(String tableName, String rowKey, String family, Class<T> clazz) throws Exception {
        Table table = null;
        T instance = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(rowKey.getBytes());
            get.addFamily(family.getBytes());
            Result result = table.get(get);
            instance = generateInstance(result, clazz);
        } finally {
            IOUtils.closeQuietly(table);
        }
        return instance;
    }

    /**
     * 查询Filter
     *
     * @param tableName
     * @param family
     * @param qualifier
     * @param value
     */
    public <T> List<T> queryFilterList(String tableName, String family, String qualifier, String value, Class<T> clazz) throws Exception {
        return queryFilterList(tableName, new SingleColumnValueFilter(
                null == family ? null : family.getBytes(),
                null == qualifier ? null : qualifier.getBytes(),
                CompareOp.EQUAL,
                null == value ? null : value.getBytes()), clazz);
    }

    /**
     * 查询Filter
     *
     * @param tableName
     * @param family
     * @param qualifier
     * @param value
     */
    public <T> List<T> queryFilterList(String tableName, String family, String qualifier, String value, Class<T> clazz, CompareOp compareOp) throws Exception {
        return queryFilterList(tableName, new SingleColumnValueFilter(
                null == family ? null : family.getBytes(),
                null == qualifier ? null : qualifier.getBytes(),
                compareOp,
                null == value ? null : value.getBytes()), clazz);
    }

    /**
     * 查询Filter
     *
     * @param tableName
     * @param family
     * @param qualifier
     * @param value
     */
    public <T> Map<String, T> queryFilterMap(String tableName, String family, String qualifier, String value, Class<T> clazz, CompareOp compareOp) throws Exception {
        return queryFilterMap(tableName, new SingleColumnValueFilter(
                null == family ? null : family.getBytes(),
                null == qualifier ? null : qualifier.getBytes(),
                compareOp,
                null == value ? null : value.getBytes()), clazz);
    }

    /**
     * 查询Filter
     *
     * @param tableName
     * @param family
     * @param qualifier
     * @param value
     */
    public <T> Map<String, T> queryFilterMap(String tableName, String family, String qualifier, String value, Class<T> clazz) throws Exception {
        return queryFilterMap(tableName, new SingleColumnValueFilter(
                null == family ? null : family.getBytes(),
                null == qualifier ? null : qualifier.getBytes(),
                CompareOp.EQUAL,
                null == value ? null : value.getBytes()), clazz);
    }

    /**
     * 查询Filter
     *
     * @param tableName
     * @param filter
     * @return List<T>
     */
    public <T> List<T> queryFilterList(String tableName, Filter filter, Class<T> clazz) throws Exception {
        Table table = null;
        ResultScanner rs = null;
        List<T> list = new ArrayList<>();
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            scan.setFilter(filter);
            rs = table.getScanner(scan);
            for (Result r : rs) {
                list.add(generateInstance(r, clazz));
            }
        } finally {
            IOUtils.closeQuietly(rs);
            IOUtils.closeQuietly(table);
        }
        return list;
    }

    /**
     * 查询数据列表
     *
     * @param tableName   表名
     * @param startRowKey 起始key
     * @param endRowKey   结束key
     * @param clazz       类型
     * @return List<T>   返回类型
     * @throws Exception
     */
    public <T> List<T> queryScanList(String tableName, String startRowKey, String endRowKey, Class<T> clazz) throws Exception {
        return queryScanList(tableName, startRowKey, endRowKey, null, clazz);
    }

    /**
     * 查询数据列表
     *
     * @param tableName   表名
     * @param startRowKey 起始key
     * @param endRowKey   结束key
     * @param clazz       类型
     * @return List<T>   返回类型
     * @throws Exception
     */
    public <T> List<T> queryScanList(String tableName, String startRowKey, String endRowKey, Map<String, String> params, Class<T> clazz) throws Exception {
        Table table = null;
        ResultScanner rs = null;
        List<T> list = new ArrayList<>();
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            //行主键范围
            if (StringUtils.isNotBlank(startRowKey)) {
                scan.setStartRow(startRowKey.getBytes());
            }
            if (StringUtils.isNotBlank(endRowKey)) {
                scan.setStopRow(endRowKey.getBytes());
            }
            if (MapUtils.isNotEmpty(params)) {
                List<Filter> filters = new ArrayList<>();
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    Filter filter = new SingleColumnValueFilter(
                            entry.getKey().getBytes(),
                            null,
                            CompareOp.EQUAL,
                            entry.getValue().getBytes());
                    filters.add(filter);
                }
                scan.setFilter(new FilterList(filters));

            }
            rs = table.getScanner(scan);
            for (Result r : rs) {
                list.add(generateInstance(r, clazz));
            }
        } finally {
           IOUtils.closeQuietly(rs);
           IOUtils.closeQuietly(table);
        }
        return list;
    }

    /**
     * 查询Filter
     *
     * @param tableName
     * @param filter
     * @return Map<String,T>
     */
    public <T> Map<String, T> queryFilterMap(String tableName, Filter filter, Class<T> clazz) throws Exception {

        Table table = null;
        ResultScanner rs = null;
        Map<String, T> hashMap = new HashMap<>();
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            scan.setFilter(filter);
            rs = table.getScanner(scan);
            for (Result r : rs) {
                hashMap.put(Bytes.toString(r.getRow()), generateInstance(r, clazz));
            }
        } finally {
            IOUtils.closeQuietly(rs);
            IOUtils.closeQuietly(table);
        }
        return hashMap;
    }

    /**
     * 多 FilterList 查询（暂不实现，如有需要自己实现）
     *
     * @param tableName
     * @param values
     */
    public <T> List<T> queryFilterList(String tableName, List<String> values, List<CompareOp> compareOp, Class<T> clazz) throws Exception {
        Table table = null;
        ResultScanner rs = null;
        List<T> resultList = new ArrayList<>();

        try {
            table = connection.getTable(TableName.valueOf(tableName));

            FilterList filterList = new FilterList();
            Scan scan = new Scan();

            for (int i = 0; i < values.size(); i++) {
                String serVal = values.get(i);
                String[] s = serVal.split(",");

                // 各个条件之间是“与”的关系
                filterList.addFilter(
                        new SingleColumnValueFilter(
                                StringUtils.isBlank(s[0]) ? null : Bytes.toBytes(s[0]),
                                StringUtils.isBlank(s[1]) ? null : Bytes.toBytes(s[1]),
                                compareOp.get(i),
                                StringUtils.isBlank(s[2]) ? null : Bytes.toBytes(s[2])
                        )
                );

            }

            scan.setFilter(filterList);
            rs = table.getScanner(scan);

            for (Result r : rs) {
                resultList.add(generateInstance(r, clazz));
            }
        } finally {
            IOUtils.closeQuietly(rs);
            IOUtils.closeQuietly(table);
        }
        return resultList;
    }

    /**
     * 创建表
     *
     * @param tableName
     * @param families
     * @throws Exception
     */
    public void createHtable(String tableName, HColumnDescriptor[] families) throws Exception {
        try {
            Admin admin = connection.getAdmin();
            dropTable(tableName);
            HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
            if (null != families) {
                for (HColumnDescriptor family : families) {
                    tableDescriptor.addFamily(family);
                }
            }
            admin.createTable(tableDescriptor);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 删除表
     *
     * @param tableName
     * @throws IOException
     */
    public void dropTable(String tableName) throws IOException {
        dropTable(connection.getAdmin(), tableName);
    }

    /**
     * 删除表
     *
     * @param tableName
     * @throws IOException
     */
    public void dropTable(Admin admin, String tableName) throws IOException {
        if (null != admin) {
            if (admin.tableExists(TableName.valueOf(tableName))) {
                admin.disableTable(TableName.valueOf(tableName));
                admin.deleteTable(TableName.valueOf(tableName));
            }
        }
    }

    /**
     * 根据 RowKey 删除数据
     *
     * @param tableName
     * @param rowKey
     * @throws IOException
     */
    public void deleteByRowKey(String tableName, String rowKey) throws Exception {
        Table table = null;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            table.delete(new Delete(rowKey.getBytes()));
        }finally {
            IOUtils.closeQuietly(table);
        }
    }

    /**
     * 查询Filter
     *
     * @param tableName
     * @param clazz
     * @return List<T>
     */
    public <T> GridData<T> queryPageFilterList(String tableName, Class<T> clazz, GridPage page, com.gy.hsxt.gpf.bm.bean.Query query) {
        Table table = null;
        ResultScanner rs = null;
        List<T> list = new ArrayList<>();
        int total = 0;
        try {
            table = connection.getTable(TableName.valueOf(tableName));
            Scan scan = new Scan();
            //行主键范围
            if (StringUtils.isNotEmpty(query.getStartDate())) {
                scan.setStartRow(Bytes.toBytes(query.getStartDate()));
            }
            if (StringUtils.isNotEmpty(query.getEndDate())) {
                scan.setStopRow(Bytes.toBytes(query.getEndDate()));
            }
            //企业互生号过滤
            if (StringUtils.isNotEmpty(query.getResNo())) {
                Filter filter = new SingleColumnValueFilter(
                        Bytes.toBytes("resNo"),
                        null,
                        CompareOp.EQUAL,
                        Bytes.toBytes(query.getResNo()));
                scan.setFilter(filter);
            }
            rs = table.getScanner(scan);
            List<String> rowKeys = new ArrayList<>();
            for (Result r : rs) {
                String rowKey = Bytes.toString(r.getRow());
//                logger.info("======= all-row-key:{} ========", rowKey);
                rowKeys.add(rowKey);
                total++;
            }
            if (CollectionUtils.isNotEmpty(rowKeys)) {
                //总页数
                int pageTotal = total / page.getPageSize() + ((total % page.getPageSize() > 0) ? 1 : 0);
                if (page.getCurPage() <= pageTotal) {
                    List<Get> gets = new ArrayList<>();
                    int endNo = (page.getStartNo() + page.getPageSize()) >= total ? total : (page.getStartNo() + page.getPageSize());
                    List<String> subRowKeys = rowKeys.subList((total-endNo),(total-page.getStartNo()));
                    Collections.sort(subRowKeys, new Comparator<String>() {
                        @Override
                        public int compare(String key1, String key2) {
                            return key2.compareTo(key1);
                        }
                    });
                    for (String subRowKey : subRowKeys) {
                        Get get = new Get(Bytes.toBytes(subRowKey));
                        gets.add(get);
                    }
//                    logger.info("=========== total:{} =========", total);
                    Result[] results = table.get(gets);
                    for (Result r : results) {
//                        logger.info("======= sub-row-key:{} ========", Bytes.toString(r.getRow()));
                        list.add(generateInstance(r, clazz));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("======== 分页查询错误：{} ========", e);
        } finally {
            IOUtils.closeQuietly(rs);
            IOUtils.closeQuietly(table);
        }
        return GridData.bulid(true,total,page.getCurPage(),list);
    }

}