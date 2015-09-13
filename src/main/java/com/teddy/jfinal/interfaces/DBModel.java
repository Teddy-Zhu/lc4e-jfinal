package com.teddy.jfinal.interfaces;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.TableMapping;
import com.teddy.jfinal.tools.CustomTool;
import com.teddy.lc4e.core.config.Key;

import java.util.ArrayList;
import java.util.Collection;
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
            this.tableName = TableMapping.me().getTable(this.getClass()).getName();
        }
        return this.tableName;
    }

    public List<M> findAll() {
        return find("select " + getTbName() + ".* from " + getTbName());
    }

    public boolean exist(String param, String columnName) {
        return StrKit.notBlank(param) && findFirst("select count(1)>0 as result from " + getTbName() + " where " + columnName + " =?", param).getToBoolean(Key.RESULT);
    }

    public List<M> findInByColumn(String[] objs, String column) {
        if (objs.length == 0) {
            return new ArrayList<>();
        }
        String objStr = "";
        for (int i = 0, len = objs.length; i < len; i++) {
            objStr += "?,";
        }
        return find("select " + getTbName() + ".* from " + getTbName() + " where " + column + " in(" + objStr.substring(0, objStr.length() - 1) + ")", objs);
    }

    public List<M> findInByColumn(Collection<String> objs, String column) {
        if (objs.size() == 0) {
            return new ArrayList<>();
        }
        String objStr = "";
        for (int i = 0, len = objs.size(); i < len; i++) {
            objStr += "?,";
        }
        return find("select " + getTbName() + ".* from " + getTbName() + " where " + column + " in(" + objStr.substring(0, objStr.length() - 1) + ")", objs.toArray());
    }

    public boolean getToBoolean(String attr) {
        Object value = get(attr);
        Boolean convertValue = false;
        try {
            convertValue = Long.valueOf(value.toString()) == 1;
        } catch (Exception e) {
            return Boolean.valueOf(value.toString());
        }
        return convertValue;
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


}
