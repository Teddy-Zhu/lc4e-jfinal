package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "user_area_collected", pk = {"id"})
public class User_Area_Collected extends DBModel<User_Area_Collected> {
    public static final User_Area_Collected dao = new User_Area_Collected();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "user_area_collected.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "user_area_collected.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_AREAID = "user_area_collected.areaId";

    public static final String S_AREAID = "AREAID";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "user_area_collected.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "user_area_collected.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "user_area_collected.*";

    public static final String TABLE_NAME = "user_area_collected";

}