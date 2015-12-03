package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "user_basicinfo", pk = {"id"})
public class User_Basicinfo extends DBModel<User_Basicinfo> {
    public static final User_Basicinfo dao = new User_Basicinfo();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "user_basicinfo.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "user_basicinfo.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_PHONENUMBER = "user_basicinfo.phoneNumber";

    public static final String S_PHONENUMBER = "PHONENUMBER";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_SIGN = "user_basicinfo.sign";

    public static final String S_SIGN = "SIGN";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AVATAR = "user_basicinfo.avatar";

    public static final String S_AVATAR = "AVATAR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_WEBSITE = "user_basicinfo.webSite";

    public static final String S_WEBSITE = "WEBSITE";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_BIRTH = "user_basicinfo.birth";

    public static final String S_BIRTH = "BIRTH";

    /**
     * 
     * Type:DECIMAL
     * Remarks:
     */
    public static final String F_BALANCE = "user_basicinfo.balance";

    public static final String S_BALANCE = "BALANCE";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "user_basicinfo.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "user_basicinfo.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "user_basicinfo.*";

    public static final String TABLE_NAME = "user_basicinfo";

}