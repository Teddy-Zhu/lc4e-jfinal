package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_role_permission", pk = {"id"})
public class Sys_Role_Permission extends DBModel<Sys_Role_Permission> {
    public static final Sys_Role_Permission dao = new Sys_Role_Permission();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_role_permission.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ROLEID = "sys_role_permission.roleId";

    public static final String S_ROLEID = "ROLEID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_PERMISSIONID = "sys_role_permission.permissionId";

    public static final String S_PERMISSIONID = "PERMISSIONID";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_role_permission.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_role_permission.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_role_permission.*";

    public static final String TABLE_NAME = "sys_role_permission";

}