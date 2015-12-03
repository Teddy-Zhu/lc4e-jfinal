package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "user", pk = {"id"})
public class User extends DBModel<User> {
    public static final User dao = new User();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "user.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NAME = "user.name";

    public static final String S_NAME = "NAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_MAIL = "user.mail";

    public static final String S_MAIL = "MAIL";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NICK = "user.nick";

    public static final String S_NICK = "NICK";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_PASSWORD = "user.password";

    public static final String S_PASSWORD = "PASSWORD";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_PASSSALT = "user.passsalt";

    public static final String S_PASSSALT = "PASSSALT";

    /**
     * 
     * Type:BIT
     * Remarks:
     */
    public static final String F_LOCKED = "user.locked";

    public static final String S_LOCKED = "LOCKED";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "user.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "user.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "user.*";

    public static final String TABLE_NAME = "user";

}