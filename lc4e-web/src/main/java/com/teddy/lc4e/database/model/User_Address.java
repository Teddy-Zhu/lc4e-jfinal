package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "user_address", pk = {"id"})
public class User_Address extends DBModel<User_Address> {
    public static final User_Address dao = new User_Address();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "user_address.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "user_address.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_PROVINCE = "user_address.province";

    public static final String S_PROVINCE = "PROVINCE";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_CITY = "user_address.city";

    public static final String S_CITY = "CITY";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_REGION = "user_address.region";

    public static final String S_REGION = "REGION";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_STREET = "user_address.street";

    public static final String S_STREET = "STREET";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "user_address.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "user_address.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "user_address.*";

    public static final String TABLE_NAME = "user_address";

}