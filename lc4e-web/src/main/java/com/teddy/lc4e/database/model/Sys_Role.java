package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "sys_role", pk = {"id"})
public class Sys_Role extends DBModel<Sys_Role> {
    public static final Sys_Role dao = new Sys_Role();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "sys_role.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_ABBR = "sys_role.abbr";

    public static final String S_ABBR = "ABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_NAME = "sys_role.name";

    public static final String S_NAME = "NAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_DESCRIPTION = "sys_role.description";

    public static final String S_DESCRIPTION = "DESCRIPTION";

    /**
     * 
     * Type:TINYINT
     * Remarks:
     */
    public static final String F_AVAILABLE = "sys_role.available";

    public static final String S_AVAILABLE = "AVAILABLE";

    public static final String ALL_FIELDS = "sys_role.*";

    public static final String TABLE_NAME = "sys_role";

}