package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "user_extend", pk = {"id"})
public class User_Extend extends DBModel<User_Extend> {
    public static final User_Extend dao = new User_Extend();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "user_extend.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "user_extend.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_WEBSITE = "user_extend.webSite";

    public static final String S_WEBSITE = "WEBSITE";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_GITHUB = "user_extend.github";

    public static final String S_GITHUB = "GITHUB";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_TWITTER = "user_extend.twitter";

    public static final String S_TWITTER = "TWITTER";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_FACEBOOK = "user_extend.facebook";

    public static final String S_FACEBOOK = "FACEBOOK";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_GOOGLE = "user_extend.google";

    public static final String S_GOOGLE = "GOOGLE";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_QQ = "user_extend.qq";

    public static final String S_QQ = "QQ";

    public static final String ALL_FIELDS = "user_extend.*";

    public static final String TABLE_NAME = "user_extend";

}