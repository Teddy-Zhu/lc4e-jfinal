package com.teddy.jfinal.tools;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by teddy on 2015/9/24.
 */
public class SQLTool {

    private static String EmptySplit = " ";
    private String tableName;

    private boolean cached = false;

    private String sql = "";

    private StringBuilder selectSQL = new StringBuilder();

    private StringBuilder exceptSelectSQL = new StringBuilder();

    private List<String> selectList = new ArrayList<>();

    private List<String> joinList = new ArrayList<>();

    private List<String> whereList = new ArrayList<>();

    private List<String> orderList = new ArrayList<>();

    private List<String> groupList = new ArrayList<>();

    private List<Object> params = new ArrayList<>();

    private String limit = "";

    public SQLTool() {

    }

    public SQLTool(String tableName) {
        this.tableName = tableName;
    }

    public SQLTool(String sql, List<Object> paras) {
        this.sql = sql;
        this.params = paras;
    }


    public String getSelectSQL() {
        return " select " + StringUtils.join(selectList, ",");
    }

    public String getExceptSQL() {

        return " from " + tableName + getWhereSQL() + getGroupSQL() + getOrderSQL() + getLimit();
    }

    public String getSQL() {
        return getSelectSQL() + getExceptSQL();
    }

    public String getWhereSQL() {
        return getListSQL(whereList, " where ( ", " ) ", " AND ", EmptySplit);
    }

    public String getGroupSQL() {
        return getListSQL(groupList, " group by ", "", ",", EmptySplit);
    }

    public String getOrderSQL() {
        return getListSQL(orderList, " order by ", "", ",", EmptySplit);
    }

    public String getLimit() {
        return limit;
    }

    private String getListSQL(List<String> list, String prefix, String suffix, String split, String empty) {
        return list.size() > 0 ? prefix + StringUtils.join(list, split) + suffix : empty;
    }

    public Object[] getParams() {
        return params.toArray();
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }

    public SQLTool appendParam(Object... objects) {
        Collections.addAll(this.params, objects);
        return this;
    }

    public SQLTool select(String... columns) {
        Collections.addAll(this.selectList, columns);
        return this;
    }


    public SQLTool from(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public SQLTool where(String... conditions) {
        Collections.addAll(this.whereList, conditions);
        return this;
    }

    private String join(String direct, String tableName) {
        return EmptySplit + direct + " join " + tableName;
    }

    public SQLTool leftJoin(String tableName, String condition) {
        this.joinList.add(join("left", tableName) + " on " + condition);
        return this;
    }

    public SQLTool rightJoin(String tableName, String condition) {
        this.joinList.add(join("right", tableName) + " on " + condition);
        return this;
    }

    public SQLTool join(String direct, String tableName, String condition) {
        this.joinList.add(join(direct, tableName) + " on " + condition);
        return this;
    }

    public SQLTool groupBy(String... columns) {
        Collections.addAll(this.groupList, columns);
        return this;
    }

    public SQLTool orderByDesc(String... columns) {
        Collections.addAll(this.orderList, orderBy(" DESC ", columns));
        return this;
    }

    public SQLTool orderByAsc(String... columns) {
        Collections.addAll(this.orderList, orderBy(" ASC ", columns));
        return this;
    }

    private String[] orderBy(String order, String... columns) {
        for (int i = 0; i < columns.length; i++) {
            columns[i] = EmptySplit + columns[i] + EmptySplit + order;
        }
        return columns;
    }

    public SQLTool limit(int size, int page) {
        this.limit = " limit " + (page - 1) * size + "," + size;
        return this;
    }

    @Override
    public String toString() {
        return getSQL();
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
