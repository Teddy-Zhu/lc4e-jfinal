package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_comment_attach", pk = {"id"})
public class Sys_Comment_Attach extends DBModel<Sys_Comment_Attach> {
    public static final Sys_Comment_Attach dao = new Sys_Comment_Attach();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_comment_attach.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_COMMENTID = "sys_comment_attach.commentId";

    public static final String S_COMMENTID = "COMMENTID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ATTACHPATH = "sys_comment_attach.attachPath";

    public static final String S_ATTACHPATH = "ATTACHPATH";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_comment_attach.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_comment_attach.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_comment_attach.*";

    public static final String TABLE_NAME = "sys_comment_attach";

}