package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_tag", pk = {"id"})
public class Sys_Tag extends DBModel<Sys_Tag> {
    public static final Sys_Tag dao = new Sys_Tag();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_tag.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_TAG = "sys_tag.tag";

    public static final String S_TAG = "TAG";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_DESCRIPTION = "sys_tag.description";

    public static final String S_DESCRIPTION = "DESCRIPTION";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_tag.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_tag.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_tag.*";

    public static final String TABLE_NAME = "sys_tag";

}