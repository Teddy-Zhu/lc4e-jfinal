package com.teddy.lc4e.database.generate;

import java.util.List;

/**
 * Created by teddy on 2015/7/25.
 */
public class DatabaseModel {

    public String tableName;

    public List<String> fields;

    /**
     * 主键
     */
    public List<String> pks;

    public List<String> remarks;

    public List<String> getRemarks() {
        return remarks;
    }

    public DatabaseModel(String tableName, List<String> fields, List<String> remarks) {
        this.tableName = tableName;
        this.fields = fields;
        this.remarks = remarks;
    }

    public DatabaseModel(String tableName, List<String> fields, List<String> pks, List<String> remarks) {
        this.tableName = tableName;
        this.fields = fields;
        this.pks = pks;
        this.remarks = remarks;
    }

    public List<String> getPks() {
        return pks;
    }

    public void setPks(List<String> pks) {
        this.pks = pks;
    }

    public void setRemarks(List<String> remarks) {
        this.remarks = remarks;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public DatabaseModel(String tableName) {
        this.tableName = tableName;
    }

    public DatabaseModel(String tableName, List<String> fields) {
        this.tableName = tableName;
        this.fields = fields;
    }
}
