package com.teddy.lc4e.core.database.mapping;

/**
 * Created by lc4e Tool on 15/07/29.
 */
public class T_User_Followed {
    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String ID = "user_followed.id";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String USERID = "user_followed.userId";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String FOLLOWEDUSERID = "user_followed.followedUserId";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String CREATETIME = "user_followed.createTime";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String UPDATETIME = "user_followed.updateTime";

    public static final String ALL_FIELDS = "user_followed.*";

    public static final String Table_NAME = "user_followed";

}