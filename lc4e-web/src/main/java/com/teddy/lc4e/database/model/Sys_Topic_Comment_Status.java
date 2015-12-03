package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_topic_comment_status", pk = {"id"})
public class Sys_Topic_Comment_Status extends DBModel<Sys_Topic_Comment_Status> {
    public static final Sys_Topic_Comment_Status dao = new Sys_Topic_Comment_Status();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_topic_comment_status.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ABBR = "sys_topic_comment_status.abbr";

    public static final String S_ABBR = "ABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NAME = "sys_topic_comment_status.name";

    public static final String S_NAME = "NAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_DESCRIPTION = "sys_topic_comment_status.description";

    public static final String S_DESCRIPTION = "DESCRIPTION";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_VISIBLE = "sys_topic_comment_status.visible";

    public static final String S_VISIBLE = "VISIBLE";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_EDITABLE = "sys_topic_comment_status.editable";

    public static final String S_EDITABLE = "EDITABLE";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_LOCK = "sys_topic_comment_status.lock";

    public static final String S_LOCK = "LOCK";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_RELEASE = "sys_topic_comment_status.release";

    public static final String S_RELEASE = "RELEASE";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_DELETE = "sys_topic_comment_status.delete";

    public static final String S_DELETE = "DELETE";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_MOVE = "sys_topic_comment_status.move";

    public static final String S_MOVE = "MOVE";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_COMMENT = "sys_topic_comment_status.comment";

    public static final String S_COMMENT = "COMMENT";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_topic_comment_status.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_topic_comment_status.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_topic_comment_status.*";

    public static final String TABLE_NAME = "sys_topic_comment_status";

}