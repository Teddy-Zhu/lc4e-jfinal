package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_ip_forbit", pk = {"id"})
public class Sys_Ip_Forbit extends DBModel<Sys_Ip_Forbit> {
    public static final Sys_Ip_Forbit dao = new Sys_Ip_Forbit();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_ip_forbit.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_IP = "sys_ip_forbit.ip";

    public static final String S_IP = "IP";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_DESCRIPTION = "sys_ip_forbit.description";

    public static final String S_DESCRIPTION = "DESCRIPTION";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_ip_forbit.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_ip_forbit.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_ip_forbit.*";

    public static final String TABLE_NAME = "sys_ip_forbit";

}