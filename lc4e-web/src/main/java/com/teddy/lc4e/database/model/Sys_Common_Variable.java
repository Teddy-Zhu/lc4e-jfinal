package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_common_variable", pk = {"id"})
public class Sys_Common_Variable extends DBModel<Sys_Common_Variable> {
    public static final Sys_Common_Variable dao = new Sys_Common_Variable();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_common_variable.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NAME = "sys_common_variable.name";

    public static final String S_NAME = "NAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_VALUE = "sys_common_variable.value";

    public static final String S_VALUE = "VALUE";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ERROR = "sys_common_variable.error";

    public static final String S_ERROR = "ERROR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_DESCRIPTION = "sys_common_variable.description";

    public static final String S_DESCRIPTION = "DESCRIPTION";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_common_variable.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_common_variable.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_common_variable.*";

    public static final String TABLE_NAME = "sys_common_variable";

}