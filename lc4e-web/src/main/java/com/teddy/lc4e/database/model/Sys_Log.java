package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_log", pk = {"id"})
public class Sys_Log extends DBModel<Sys_Log> {
    public static final Sys_Log dao = new Sys_Log();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_log.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_OPERATETYPEID = "sys_log.operateTypeId";

    public static final String S_OPERATETYPEID = "OPERATETYPEID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "sys_log.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_DESCRIPTION = "sys_log.description";

    public static final String S_DESCRIPTION = "DESCRIPTION";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_IP = "sys_log.ip";

    public static final String S_IP = "IP";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AGANT = "sys_log.agant";

    public static final String S_AGANT = "AGANT";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_log.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_log.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_log.*";

    public static final String TABLE_NAME = "sys_log";

}