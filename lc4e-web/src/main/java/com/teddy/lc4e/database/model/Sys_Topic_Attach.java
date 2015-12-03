package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_topic_attach", pk = {"id"})
public class Sys_Topic_Attach extends DBModel<Sys_Topic_Attach> {
    public static final Sys_Topic_Attach dao = new Sys_Topic_Attach();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_topic_attach.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_TOPICID = "sys_topic_attach.topicId";

    public static final String S_TOPICID = "TOPICID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ATTACHPATH = "sys_topic_attach.attachPath";

    public static final String S_ATTACHPATH = "ATTACHPATH";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "sys_topic_attach.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_topic_attach.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_topic_attach.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_topic_attach.*";

    public static final String TABLE_NAME = "sys_topic_attach";

}