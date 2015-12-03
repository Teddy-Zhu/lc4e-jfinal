package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_comment", pk = {"id"})
public class Sys_Comment extends DBModel<Sys_Comment> {
    public static final Sys_Comment dao = new Sys_Comment();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_comment.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_TOPICID = "sys_comment.topicId";

    public static final String S_TOPICID = "TOPICID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "sys_comment.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ORDER = "sys_comment.order";

    public static final String S_ORDER = "ORDER";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_TITLE = "sys_comment.title";

    public static final String S_TITLE = "TITLE";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_BODY = "sys_comment.body";

    public static final String S_BODY = "BODY";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_TCSTATUSID = "sys_comment.tcStatusId";

    public static final String S_TCSTATUSID = "TCSTATUSID";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_comment.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_comment.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_comment.*";

    public static final String TABLE_NAME = "sys_comment";

}