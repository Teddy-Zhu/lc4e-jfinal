package com.teddy.jfinal.tools;

import com.jfinal.kit.StrKit;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teddy on 2015/9/24.
 */
public class SQLTool {

    private String tableName;

    private StringBuilder sql;

    private List<Object> paras;

    public SQLTool() {
        this.sql = new StringBuilder();
        this.paras = new ArrayList<>();
    }

    public SQLTool(String sql, List<Object> paras) {
        this.sql = new StringBuilder().append(sql);
        this.paras = paras;
    }

    public String getSqlSelect() {
        return this.sql.substring(0, this.sql.indexOf("from"));
    }

    public String getSqlExceptSelect() {
        return this.sql.substring(this.sql.indexOf("from"), this.sql.length());
    }

    public String getSql() {
        return sql.indexOf("where") == -1 && StrKit.notBlank(this.tableName) ? from().toString() : sql.toString();
    }

    public void setSql(String sql) {
        this.sql = new StringBuilder().append(sql);
    }

    public Object[] getParas() {
        return paras.toArray();
    }

    public void setParas(List<Object> paras) {
        this.paras = paras;
    }

    public SQLTool append(String sql) {
        this.sql.append(sql);
        return this;
    }

    public SQLTool addParam(Object object) {
        this.paras.add(object);
        return this;
    }

    public SQLTool addParams(Object... objects) {
        for (Object object : objects) {
            this.paras.add(object);
        }
        return this;
    }

    public SQLTool select(String... columns) {
        this.sql.append("select ").append(StringUtils.join(columns, ","));
        return this;
    }

    public SQLTool from() {
        this.sql.append(" from ").append(this.tableName);
        return this;
    }

    public SQLTool from(String tableName) {
        this.sql.append(" from ").append(tableName);
        return this;
    }

    public SQLTool where(String conditions) {
        this.sql.append(" where ( ").append(conditions).append(" ) ");
        return this;
    }

    public SQLTool where(String logic, String... conditions) {
        this.sql.append(" where ").append(StringUtils.join(conditions, logic));
        return this;
    }

    public SQLTool join(String direct, String tableName) {
        this.sql.append(" ").append(direct).append(" join ").append(tableName);
        return this;
    }

    public SQLTool leftJoin(String tableName) {
        return join("left", tableName);
    }

    public SQLTool rightJoin(String tableName) {
        return join("right", tableName);
    }

    public SQLTool on(String condition) {
        this.sql.append(" on ( ").append(condition).append(" ) ");
        return this;
    }

    public SQLTool groupBy(String... columns) {
        this.sql.append(" group by ").append(StringUtils.join(columns, ","));
        return this;
    }

    public SQLTool orderByDesc(String... columns) {
        return orderBy("DESC", columns);
    }

    public SQLTool orderByAsc(String... columns) {
        return orderBy("ASC", columns);
    }

    public SQLTool orderBy(String order, String... columns) {
        this.sql.append(" order by ").append(StringUtils.join(columns, " " + order + " ,")).append(" ").append(order).append(" ");
        return this;
    }

    public SQLTool limit(int size, int page) {
        this.sql.append(" limit ").append((page - 1) * size).append(",").append(size);
        return this;
    }

    @Override
    public String toString() {
        return this.sql.toString();
    }

    public static String NOTIN(String column, String condition) {
        return column + " NOT IN (" + condition + ")";
    }

    public static String IN(String column, String condition) {
        return column + " IN (" + condition + ")";
    }

    public static String SELECT(String... columns) {
        return " select " + StringUtils.join(columns, ",");
    }

    public static String FROM(String tableName) {
        return " from " + tableName;
    }

    public static String WHERE(String logic, String... conditions) {
        return " where ( " + StringUtils.join(conditions, " " + logic + " ") + " ) ";
    }

    public static String WHERE(String conditions) {
        return " where ( " + conditions + " ) ";
    }

    public static String OR(String... conditions) {
        return " ( " + StringUtils.join(conditions, " OR ") + " ) ";
    }

    public static String AND(String... conditions) {
        return " ( " + StringUtils.join(conditions, " AND ") + " ) ";
    }

    public static String COUNT(String column) {
        return " count(" + column + ")";
    }

    public static String AS(String column, String newColumn) {
        return column + " as " + newColumn;
    }
}
