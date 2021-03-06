/**
 * adx-common#com.gy.hsi.ds.param.ub.generic.dao.operator.Modify.java
 * 下午8:04:36 created by Darwin(Tianxin)
 */
package com.gy.hsi.ds.common.thirds.unbiz.common.genericdao.operator;

/**
 * 一个字段的update操作
 *
 * @author Darwin(Tianxin)
 */
public class Modify implements Pair {

    /**
     * @param column
     * @param value
     */
    public Modify(String column, Object value) {
        this.column = column;
        this.value = value;
    }

    private String column;
    private Object value;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
