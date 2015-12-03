package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_address", pk = {"id"})
public class Sys_Address extends DBModel<Sys_Address> {
    public static final Sys_Address dao = new Sys_Address();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_address.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NAME = "sys_address.name";

    public static final String S_NAME = "NAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_DESCRIPTION = "sys_address.description";

    public static final String S_DESCRIPTION = "DESCRIPTION";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_address.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_address.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_address.*";

    public static final String TABLE_NAME = "sys_address";

}