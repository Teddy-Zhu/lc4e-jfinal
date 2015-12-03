package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_job", pk = {"id"})
public class Sys_Job extends DBModel<Sys_Job> {
    public static final Sys_Job dao = new Sys_Job();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_job.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NAME = "sys_job.name";

    public static final String S_NAME = "NAME";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ORDER = "sys_job.order";

    public static final String S_ORDER = "ORDER";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_CRON = "sys_job.cron";

    public static final String S_CRON = "CRON";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_CLASSNAME = "sys_job.className";

    public static final String S_CLASSNAME = "CLASSNAME";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_ENABLE = "sys_job.enable";

    public static final String S_ENABLE = "ENABLE";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_job.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_job.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_job.*";

    public static final String TABLE_NAME = "sys_job";

}