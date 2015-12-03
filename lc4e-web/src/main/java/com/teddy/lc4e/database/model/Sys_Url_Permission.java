package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_url_permission", pk = {"id"})
public class Sys_Url_Permission extends DBModel<Sys_Url_Permission> {
    public static final Sys_Url_Permission dao = new Sys_Url_Permission();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_url_permission.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ACTIONKEY = "sys_url_permission.actionKey";

    public static final String S_ACTIONKEY = "ACTIONKEY";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_PERMISSION = "sys_url_permission.permission";

    public static final String S_PERMISSION = "PERMISSION";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_url_permission.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_url_permission.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_url_permission.*";

    public static final String TABLE_NAME = "sys_url_permission";

}