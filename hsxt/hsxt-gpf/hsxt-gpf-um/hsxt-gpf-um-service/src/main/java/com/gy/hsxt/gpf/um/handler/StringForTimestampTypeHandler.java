/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.gpf.um.handler;

import com.gy.hsxt.common.utils.DateUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.*;
import java.util.Date;

/**
 * 数据库时间戳类型转换器
 * 时间格式：yyyy-MM-dd HH:mm:ss
 *
 * @Package :com.gy.hsxt.gpf.um.handler
 * @ClassName : StringForTimestampTypeHandler
 * @Description : 数据库时间戳类型转换器
 * @Author : chenm
 * @Date : 2015/11/24 12:23
 * @Version V3.0.0.0
 */
@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class StringForTimestampTypeHandler extends BaseTypeHandler<String> {

    /**
     * PreparedStatement设置非空参数
     *
     * @param preparedStatement SQL执行对象
     * @param i                 参数位置
     * @param s                 参数值
     * @param jdbcType          参数对应的数据库类型
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        //日期解析 格式：yyyy-MM-dd HH:mm:ss
        Date date = DateUtil.StringToDateTime(s);
        //日期解析 格式：yyyy-MM-dd
        date = date == null ? DateUtil.StringToDate(s) : date;
        preparedStatement.setTimestamp(i, new Timestamp(date.getTime()));
    }

    /**
     * 从结果集中获取结果
     *
     * @param resultSet 结果集
     * @param s         参数名称
     * @return 结果值
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(s);
        return timestamp == null ? null : DateUtil.DateTimeToString(new Date(timestamp.getTime()));
    }

    /**
     * 从结果集中获取结果
     *
     * @param resultSet 结果集
     * @param i         值位置
     * @return 结果值
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        Timestamp timestamp = resultSet.getTimestamp(i);
        return timestamp == null ? null : DateUtil.DateTimeToString(new Date(timestamp.getTime()));
    }

    /**
     * 从存储过程中获取结果
     *
     * @param callableStatement SQL执行对象
     * @param i                 值位置
     * @return 结果值
     * @throws SQLException
     */
    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        Timestamp timestamp = callableStatement.getTimestamp(i);
        return timestamp == null ? null : DateUtil.DateTimeToString(new Date(timestamp.getTime()));
    }
}
