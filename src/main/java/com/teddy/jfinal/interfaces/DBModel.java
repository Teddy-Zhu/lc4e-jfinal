package com.teddy.jfinal.interfaces;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.teddy.jfinal.tools.CustomTool;
import com.teddy.lc4e.core.config.Key;

import java.util.List;

/**
 * Created by teddy on 2015/7/28.
 */
public abstract class DBModel<M extends DBModel> extends Model<M> {
    private static final long serialVersionUID = -101956006715745792L;

    private String tableName;

    @SuppressWarnings("unchecked")
    public M enhancer() {
        return CustomTool.auto((M) this);
    }

    @SuppressWarnings("unchecked")
    public M custom() {
        return CustomTool.custom((M) this);
    }

    @SuppressWarnings("unchecked")
    public M transaction() {
        return CustomTool.transaction((M) this);
    }

    public String getTbName() {
        if (StrKit.isBlank(this.tableName)) {
            this.tableName = this.getClass().getAnnotation(com.teddy.jfinal.annotation.Model.class).value();
        }
        return this.tableName;
    }

    public List<M> findAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("select ").append(getTbName()).append(".* from " + getTbName());
        return find(sb.toString());
    }

    public boolean getToBoolean(String attr) {
        return getLong(attr) == 1;
    }

    public Integer getToInteger(String attr) {
        return Integer.valueOf(getStr(attr));
    }


    public Long getToLong(String attr) {
        return Long.valueOf(getStr(attr));
    }

    public Double getToDouble(String attr) {
        return Double.valueOf(getStr(attr));
    }

    public Float getToFloat(String attr) {
        return Float.valueOf(getStr(attr));
    }

    public boolean exist(String param, String columnName) {
        return StrKit.notBlank(param) && findFirst("select count(1)>0 as result from " + getTbName() + " where " + columnName + " =?", param).getToBoolean(Key.RESULT);
    }
}
