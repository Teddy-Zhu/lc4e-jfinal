package com.teddy.lc4e.core.database.mapping;

/**
 * Created by lc4e Tool on 15/07/29.
 */
public class T_User_Message {
    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String ID = "user_message.id";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String USERID = "user_message.userId";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String DESTUSER = "user_message.destUser";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String READ = "user_message.read";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String TITLE = "user_message.title";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String BODY = "user_message.body";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String CREATETIME = "user_message.createTime";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String UPDATETIME = "user_message.updateTime";

    public static final String ALL_FIELDS = "user_message.*";

    public static final String Table_NAME = "user_message";

}