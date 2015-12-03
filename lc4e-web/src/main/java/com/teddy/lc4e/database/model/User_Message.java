package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "user_message", pk = {"id"})
public class User_Message extends DBModel<User_Message> {
    public static final User_Message dao = new User_Message();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "user_message.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "user_message.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_DESTUSER = "user_message.destUser";

    public static final String S_DESTUSER = "DESTUSER";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_READ = "user_message.read";

    public static final String S_READ = "READ";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_TITLE = "user_message.title";

    public static final String S_TITLE = "TITLE";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_BODY = "user_message.body";

    public static final String S_BODY = "BODY";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "user_message.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "user_message.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "user_message.*";

    public static final String TABLE_NAME = "user_message";

}