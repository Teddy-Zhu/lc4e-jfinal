package com.teddy.jfinal.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teddy on 2015/9/24.
 */
public class SqlTool {

    private StringBuilder sql;

    private List<Object> paras;

    public SqlTool() {
        this.sql = new StringBuilder();
        this.paras = new ArrayList<>();
    }

    public SqlTool(String sql, List<Object> paras) {
        this.sql = new StringBuilder().append(sql);
        this.paras = paras;
    }

    public String getSql() {
        return sql.toString();
    }

    public void setSql(String sql) {
        this.sql = new StringBuilder().append(sql);
    }

    public List<Object> getParas() {
        return paras;
    }

    public void setParas(List<Object> paras) {
        this.paras = paras;
    }

    public SqlTool append(String sql) {
        this.sql.append(sql);
        return this;
    }

    public SqlTool addParam(Object object) {
        this.paras.add(object);
        return this;
    }
}
