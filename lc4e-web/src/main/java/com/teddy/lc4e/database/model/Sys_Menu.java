package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_menu", pk = {"id"})
public class Sys_Menu extends DBModel<Sys_Menu> {
    public static final Sys_Menu dao = new Sys_Menu();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_menu.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_PARENTID = "sys_menu.parentId";

    public static final String S_PARENTID = "PARENTID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ORDER = "sys_menu.order";

    public static final String S_ORDER = "ORDER";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ABBR = "sys_menu.abbr";

    public static final String S_ABBR = "ABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NAME = "sys_menu.name";

    public static final String S_NAME = "NAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_CSS = "sys_menu.css";

    public static final String S_CSS = "CSS";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ICON = "sys_menu.icon";

    public static final String S_ICON = "ICON";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "sys_menu.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "sys_menu.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "sys_menu.*";

    public static final String TABLE_NAME = "sys_menu";

}