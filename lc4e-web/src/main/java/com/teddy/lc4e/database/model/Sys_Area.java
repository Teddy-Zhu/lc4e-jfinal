package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_area", pk = {"id"})
public class Sys_Area extends DBModel<Sys_Area> {
    public static final Sys_Area dao = new Sys_Area();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_area.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_PARENTID = "sys_area.parentId";

    public static final String S_PARENTID = "PARENTID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ABBR = "sys_area.abbr";

    public static final String S_ABBR = "ABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NAME = "sys_area.name";

    public static final String S_NAME = "NAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_DESCRIPTION = "sys_area.description";

    public static final String S_DESCRIPTION = "DESCRIPTION";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_CSS = "sys_area.css";

    public static final String S_CSS = "CSS";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ICON = "sys_area.icon";

    public static final String S_ICON = "ICON";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_AREASTATUSID = "sys_area.areaStatusId";

    public static final String S_AREASTATUSID = "AREASTATUSID";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_area.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_area.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_area.*";

    public static final String TABLE_NAME = "sys_area";

}