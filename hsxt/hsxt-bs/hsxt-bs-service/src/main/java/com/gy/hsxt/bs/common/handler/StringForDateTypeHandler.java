/*
* Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
*
* 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的 
*/
package com.gy.hsxt.bs.common.handler;

import com.gy.hsxt.common.utils.DateUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * 数据库日期类型转换器
 * 日期格式：yyyy-MM-dd
 *
 * @Package :com.gy.hsxt.bs.common.handler
 * @ClassName : StringForDateTypeHandler
 * @Description : 数据库日期类型转换器
 * @Author : chenm
 * @Date : 2015/11/24 11:46
 * @Version V3.0.0.0
 */
@MappedJdbcTypes(JdbcType.DATE)
public class StringForDateTypeHandler extends BaseTypeHandler<String> {

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
        preparedStatement.setDate(i, new java.sql.Date(date.getTime()));
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
        Date date = resultSet.getDate(s);
        return date == null ? null : DateUtil.DateToString(date);
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
        Date date = resultSet.getDate(i);
        return date == null ? null : DateUtil.DateToString(date);
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
        Date date = callableStatement.getDate(i);
        return date == null ? null : DateUtil.DateToString(date);
    }
}
