package com.example.util.vo;

import java.io.Serializable;

/**
 * 重写equeals 和 hashCode 的实体类
 */
public class SysFieldVo implements Serializable {

    /**
     * 字段名称
     */
    String field;

    /**
     * 字段描述
     */
    String describe;

    /**
     * 字段类型
     */
    String dataType;

    /**
     * 字段长度
     */
    Integer len;

    /**
     * 小数点位数
     */
    Integer pot;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Integer getLen() {
        return len;
    }

    public void setLen(Integer len) {
        this.len = len;
    }

    public Integer getPot() {
        return pot;
    }

    public void setPot(Integer pot) {
        this.pot = pot;
    }

    @Override
    public int hashCode() {
        StringBuilder sb = new StringBuilder();
        sb.append(field);
        char[] charArr = sb.toString().toCharArray();
        int hash = 0;
        for (char c : charArr) {
            hash = hash * 131 + c;
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof SysFieldVo)) {
            return false;
        }
        SysFieldVo field = (SysFieldVo) obj;

        return (this.field.equals(field.field));

    }
}
