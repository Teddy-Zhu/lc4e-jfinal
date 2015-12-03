package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "vw_user_role_permission")
public class Vw_User_Role_Permission extends DBModel<Vw_User_Role_Permission> {
    public static final Vw_User_Role_Permission dao = new Vw_User_Role_Permission();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "vw_user_role_permission.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NAME = "vw_user_role_permission.name";

    public static final String S_NAME = "NAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_MAIL = "vw_user_role_permission.mail";

    public static final String S_MAIL = "MAIL";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NICK = "vw_user_role_permission.nick";

    public static final String S_NICK = "NICK";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_PASSWORD = "vw_user_role_permission.password";

    public static final String S_PASSWORD = "PASSWORD";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_PASSSALT = "vw_user_role_permission.passsalt";

    public static final String S_PASSSALT = "PASSSALT";

    /**
     * 
     * Type:BIT
     * Remarks:
     */
    public static final String F_LOCKED = "vw_user_role_permission.locked";

    public static final String S_LOCKED = "LOCKED";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "vw_user_role_permission.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "vw_user_role_permission.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ROLEABBR = "vw_user_role_permission.roleAbbr";

    public static final String S_ROLEABBR = "ROLEABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_PERMISSIONABBR = "vw_user_role_permission.permissionAbbr";

    public static final String S_PERMISSIONABBR = "PERMISSIONABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ROLENAME = "vw_user_role_permission.roleName";

    public static final String S_ROLENAME = "ROLENAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ROLEDESCRIPTION = "vw_user_role_permission.roleDescription";

    public static final String S_ROLEDESCRIPTION = "ROLEDESCRIPTION";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_ROLEAVAILABLE = "vw_user_role_permission.roleAvailable";

    public static final String S_ROLEAVAILABLE = "ROLEAVAILABLE";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_PERMISSIONNAME = "vw_user_role_permission.permissionName";

    public static final String S_PERMISSIONNAME = "PERMISSIONNAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_PERMISSIONDESCRIPTION = "vw_user_role_permission.permissionDescription";

    public static final String S_PERMISSIONDESCRIPTION = "PERMISSIONDESCRIPTION";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_PERMISSIONAVAILABLE = "vw_user_role_permission.permissionAvailable";

    public static final String S_PERMISSIONAVAILABLE = "PERMISSIONAVAILABLE";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_ROLEENDTIME = "vw_user_role_permission.roleEndTime";

    public static final String S_ROLEENDTIME = "ROLEENDTIME";

    public static final String ALL_FIELDS = "vw_user_role_permission.*";

    public static final String TABLE_NAME = "vw_user_role_permission";

}