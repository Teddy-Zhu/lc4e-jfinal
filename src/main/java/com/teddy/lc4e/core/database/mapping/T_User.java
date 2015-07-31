package com.teddy.lc4e.core.database.mapping;

/**
 * Created by lc4e Tool on 15/07/29.
 */
public class T_User {
    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String ID = "user.id";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String NAME = "user.name";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String MAIL = "user.mail";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String NICK = "user.nick";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String PASSWORD = "user.password";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String PASSSALT = "user.passsalt";

    /**
     * 
     * Type:BIT
     * Remarks:
     */
    public static final String LOCKED = "user.locked";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String CREATETIME = "user.createTime";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String UPDATETIME = "user.updateTime";

    public static final String ALL_FIELDS = "user.*";

    public static final String Table_NAME = "user";

}