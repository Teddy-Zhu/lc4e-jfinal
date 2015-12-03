package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_dynamic_info", pk = {"id"})
public class Sys_Dynamic_Info extends DBModel<Sys_Dynamic_Info> {
    public static final Sys_Dynamic_Info dao = new Sys_Dynamic_Info();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_dynamic_info.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "sys_dynamic_info.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_INFO = "sys_dynamic_info.info";

    public static final String S_INFO = "INFO";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_dynamic_info.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_dynamic_info.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_dynamic_info.*";

    public static final String TABLE_NAME = "sys_dynamic_info";

}