package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_history_top", pk = {"id"})
public class Sys_History_Top extends DBModel<Sys_History_Top> {
    public static final Sys_History_Top dao = new Sys_History_Top();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_history_top.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_TOPICIDS = "sys_history_top.topicIds";

    public static final String S_TOPICIDS = "TOPICIDS";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_history_top.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_history_top.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_history_top.*";

    public static final String TABLE_NAME = "sys_history_top";

}