package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_topic_status", pk = {"id"})
public class Sys_Topic_Status extends DBModel<Sys_Topic_Status> {
    public static final Sys_Topic_Status dao = new Sys_Topic_Status();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_topic_status.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ABBR = "sys_topic_status.abbr";

    public static final String S_ABBR = "ABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NAME = "sys_topic_status.name";

    public static final String S_NAME = "NAME";

    /**
     * 
     * Type:DECIMAL
     * Remarks:
     */
    public static final String F_PW = "sys_topic_status.pw";

    public static final String S_PW = "PW";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ICON = "sys_topic_status.icon";

    public static final String S_ICON = "ICON";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_topic_status.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_topic_status.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_topic_status.*";

    public static final String TABLE_NAME = "sys_topic_status";

}