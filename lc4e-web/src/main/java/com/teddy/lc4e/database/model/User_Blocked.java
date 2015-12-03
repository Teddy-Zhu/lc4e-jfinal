package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "user_blocked", pk = {"id"})
public class User_Blocked extends DBModel<User_Blocked> {
    public static final User_Blocked dao = new User_Blocked();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "user_blocked.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "user_blocked.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_BLOCKEDUSERID = "user_blocked.blockedUserId";

    public static final String S_BLOCKEDUSERID = "BLOCKEDUSERID";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "user_blocked.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "user_blocked.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "user_blocked.*";

    public static final String TABLE_NAME = "user_blocked";

}