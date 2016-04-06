package com.teddy.jfinal.interfaces;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Table;
import com.jfinal.plugin.activerecord.TableMapping;
import com.teddy.jfinal.common.Const;
import com.teddy.jfinal.tools.SQLTool;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by teddy on 2015/7/28.
 */
public abstract class DBModel<M extends DBModel> extends Model<M> {
    private static final long serialVersionUID = -101956006715745792L;

    private Table me;

    private Class getUsefulClass() {
        Class c = getClass();
        return !c.getName().contains("EnhancerByCGLIB") ? c : c.getSuperclass();
    }

    public String getTbName() {
        return getTable().getName();
    }

    public Table getTable() {
        if (me == null)
            me = TableMapping.me().getTable(getUsefulClass());
        return me;
    }

    public List<M> find(SQLTool sql) {
        Object[] objs = sql.getParams();
        return objs.length > 0 ? find(sql.toString(), objs) : find(sql.toString());
    }

    public List<M> findAll() {
        return find("select " + getTbName() + ".* from " + getTbName());
    }

    public M findFirst(SQLTool sql) {
        Object[] objs = sql.getParams();
        return objs.length > 0 ? findFirst(sql.toString(), objs) : findFirst(sql.toString());
    }

    public boolean exist(String param, String columnName) {
        return StrKit.notBlank(param) && findFirst("select count(1)>0 as RESULT from " + getTbName() + " where " + columnName + " =?", param).getToBoolean("RESULT");
    }

    public Page<M> paginate(SQLTool sql, int page, int size) {
        return paginate(page, size, sql.getSelectSQL(), sql.getExceptSQL(), sql.getParams());
    }

    public List<M> findInByColumn(String[] objs, String column) {
        if (objs.length == 0) {
            return new ArrayList<>();
        }
        String objStr = "";
        for (String obj : objs) {
            objStr += "?,";
        }
        return find("select " + getTbName() + ".* from " + getTbName() + " where " + column + " in(" + objStr.substring(0, objStr.length() - 1) + ")", objs);
    }

    public List<M> findInByColumn(Collection<String> objs, String column) {
        if (objs.size() == 0) {
            return new ArrayList<>();
        }
        final StringBuilder objStr = new StringBuilder();
        objs.forEach(obj -> objStr.append("?,"));
        return find("select " + getTbName() + ".* from " + getTbName() + " where " + column + " in(" + objStr.substring(0, objStr.length() - 1) + ")", objs.toArray());
    }

    public boolean getToBoolean(String attr) {
        Object value = get(attr);
        Boolean convertValue;
        try {
            convertValue = Long.valueOf(value.toString()) == 1;
        } catch (Exception e) {
            return Boolean.valueOf(value.toString());
        }
        return convertValue;
    }

    public String getIntToString(String attr) {
        return getInt(attr).toString();
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


    @Override
    public boolean save() {
        Date dt = new Date();
        if (getTable().hasColumnLabel(Const.DB_UPDATETIME)) this.set(Const.DB_UPDATETIME, dt);
        if (getTable().hasColumnLabel(Const.DB_CREATETIME)) this.set(Const.DB_CREATETIME, dt);
        return super.save();
    }

    @Override
    public boolean update() {
        Date dt = new Date();
        if (getTable().hasColumnLabel(Const.DB_UPDATETIME)) this.set(Const.DB_UPDATETIME, dt);
        return super.update();
    }

}
