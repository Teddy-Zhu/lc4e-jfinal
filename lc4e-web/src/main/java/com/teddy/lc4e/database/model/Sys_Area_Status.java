package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_area_status", pk = {"id"})
public class Sys_Area_Status extends DBModel<Sys_Area_Status> {
    public static final Sys_Area_Status dao = new Sys_Area_Status();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_area_status.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ABBR = "sys_area_status.abbr";

    public static final String S_ABBR = "ABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NAME = "sys_area_status.name";

    public static final String S_NAME = "NAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_DESCRIPTION = "sys_area_status.description";

    public static final String S_DESCRIPTION = "DESCRIPTION";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_VISIBLE = "sys_area_status.visible";

    public static final String S_VISIBLE = "VISIBLE";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_CLOSE = "sys_area_status.close";

    public static final String S_CLOSE = "CLOSE";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_MOVE = "sys_area_status.move";

    public static final String S_MOVE = "MOVE";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_PUBTOPIC = "sys_area_status.pubTopic";

    public static final String S_PUBTOPIC = "PUBTOPIC";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_PUBCOMMENT = "sys_area_status.pubComment";

    public static final String S_PUBCOMMENT = "PUBCOMMENT";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_area_status.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDAETTIME = "sys_area_status.updaetTime";

    public static final String S_UPDAETTIME = "UPDAETTIME";

    public static final String ALL_FIELDS = "sys_area_status.*";

    public static final String TABLE_NAME = "sys_area_status";

}