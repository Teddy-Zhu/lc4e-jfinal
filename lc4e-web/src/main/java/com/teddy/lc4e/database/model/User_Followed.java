package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "user_followed", pk = {"id"})
public class User_Followed extends DBModel<User_Followed> {
    public static final User_Followed dao = new User_Followed();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "user_followed.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "user_followed.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_FOLLOWEDUSERID = "user_followed.followedUserId";

    public static final String S_FOLLOWEDUSERID = "FOLLOWEDUSERID";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "user_followed.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "user_followed.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "user_followed.*";

    public static final String TABLE_NAME = "user_followed";

}