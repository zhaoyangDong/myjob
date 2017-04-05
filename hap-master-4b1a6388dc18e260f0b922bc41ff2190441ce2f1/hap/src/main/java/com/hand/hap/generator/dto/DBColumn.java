package com.hand.hap.generator.dto;

/**
 * Created by jialong.zuo@hand-china.com on 2016/10/24.
 */
public class DBColumn {

    private String name;

    private boolean isId = false;

    private boolean isNotEmpty = false;

    private boolean isNotNull = false;

    private String javaType;

    private String jdbcType;

    private boolean isMultiLanguage = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isId() {
        return isId;
    }

    public void setId(boolean id) {
        isId = id;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getJdbcType() {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType) {
        this.jdbcType = jdbcType;
    }

    public boolean isMultiLanguage() {
        return isMultiLanguage;
    }

    public void setMultiLanguage(boolean multiLanguage) {
        isMultiLanguage = multiLanguage;
    }

    public boolean isNotEmpty() {
        return isNotEmpty;
    }

    public void setNotEmpty(boolean notEmpty) {
        isNotEmpty = notEmpty;
    }

    public boolean isNotNull() {
        return isNotNull;
    }

    public void setNotNull(boolean notNull) {
        isNotNull = notNull;
    }
}
