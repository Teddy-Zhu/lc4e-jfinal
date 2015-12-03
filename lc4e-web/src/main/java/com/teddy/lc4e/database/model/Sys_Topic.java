package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_topic", pk = {"id"})
public class Sys_Topic extends DBModel<Sys_Topic> {
    public static final Sys_Topic dao = new Sys_Topic();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_topic.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_AREAID = "sys_topic.areaId";

    public static final String S_AREAID = "AREAID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "sys_topic.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_URL = "sys_topic.url";

    public static final String S_URL = "URL";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_TITLE = "sys_topic.title";

    public static final String S_TITLE = "TITLE";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_BODY = "sys_topic.body";

    public static final String S_BODY = "BODY";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_TCSTATUSID = "sys_topic.tcStatusId";

    public static final String S_TCSTATUSID = "TCSTATUSID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_COUNT = "sys_topic.count";

    public static final String S_COUNT = "COUNT";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_topic.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_topic.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_topic.*";

    public static final String TABLE_NAME = "sys_topic";

}