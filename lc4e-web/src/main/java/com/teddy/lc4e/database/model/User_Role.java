package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "user_role", pk = {"id"})
public class User_Role extends DBModel<User_Role> {
    public static final User_Role dao = new User_Role();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "user_role.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "user_role.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ROLEID = "user_role.roleId";

    public static final String S_ROLEID = "ROLEID";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_ENDTIME = "user_role.endTime";

    public static final String S_ENDTIME = "ENDTIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "user_role.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "user_role.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "user_role.*";

    public static final String TABLE_NAME = "user_role";

}