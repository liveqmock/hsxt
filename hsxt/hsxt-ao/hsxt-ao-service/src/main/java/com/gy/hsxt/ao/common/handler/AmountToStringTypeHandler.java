/*
 * Copyright (c) 2015-2018 SHENZHEN GUIYI SCIENCE AND TECHNOLOGY DEVELOP CO., LTD. All rights reserved.
 *
 * 注意：本内容仅限于深圳市归一科技研发有限公司内部传阅，禁止外泄以及用于其他的商业目的
 */
package com.gy.hsxt.ao.common.handler;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;

import com.gy.hsxt.common.utils.BigDecimalUtils;

/**
 * 数据库金额类型转换处理器
 * 
 * @Package: com.gy.hsxt.ao.common.handler
 * @ClassName: AmountToStringTypeHandler
 * @Description: TODO
 * 
 * @author: kongsl
 * @date: 2015-12-21 下午7:39:17
 * @version V3.0.0
 */
@MappedJdbcTypes(JdbcType.NUMERIC)
public class AmountToStringTypeHandler extends BaseTypeHandler<String> {

    /**
     * PreparedStatement设置非空参数
     * 
     * @param preparedStatement
     *            SQL执行对象
     * @param i
     *            参数位置
     * @param s
     *            参数值
     * @param jdbcType
     *            参数对应的数据库类型
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType)
            throws SQLException {
        preparedStatement.setBigDecimal(i, BigDecimalUtils.ceiling(s));
    }

    /**
     * 从结果集中获取结果
     * 
     * @param resultSet
     *            结果集
     * @param s
     *            参数名称
     * @return 结果值
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        BigDecimal bigDecimal = resultSet.getBigDecimal(s);
        return bigDecimal == null ? null : bigDecimal.toPlainString();
    }

    /**
     * 从结果集中获取结果
     * 
     * @param resultSet
     *            结果集
     * @param i
     *            值位置
     * @return 结果值
     * @throws SQLException
     */
    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        BigDecimal bigDecimal = resultSet.getBigDecimal(i);
        return bigDecimal == null ? null : bigDecimal.toPlainString();
    }

    /**
     * 从存储过程中获取结果
     * 
     * @param callableStatement
     *            SQL执行对象
     * @param i
     *            值位置
     * @return 结果值
     * @throws SQLException
     */
    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        BigDecimal bigDecimal = callableStatement.getBigDecimal(i);
        return bigDecimal == null ? null : bigDecimal.toPlainString();
    }
}
