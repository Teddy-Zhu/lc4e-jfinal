package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_permission", pk = {"id"})
public class Sys_Permission extends DBModel<Sys_Permission> {
    public static final Sys_Permission dao = new Sys_Permission();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_permission.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ABBR = "sys_permission.abbr";

    public static final String S_ABBR = "ABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NAME = "sys_permission.name";

    public static final String S_NAME = "NAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_DESCRIPTION = "sys_permission.description";

    public static final String S_DESCRIPTION = "DESCRIPTION";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_AVAILABLE = "sys_permission.available";

    public static final String S_AVAILABLE = "AVAILABLE";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_permission.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_permission.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_permission.*";

    public static final String TABLE_NAME = "sys_permission";

}