package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "user_tag", pk = {"id"})
public class User_Tag extends DBModel<User_Tag> {
    public static final User_Tag dao = new User_Tag();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "user_tag.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "user_tag.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_TAGID = "user_tag.tagId";

    public static final String S_TAGID = "TAGID";

    /**
     * 
     * Type:DECIMAL
     * Remarks:
     */
    public static final String F_PW = "user_tag.pw";

    public static final String S_PW = "PW";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "user_tag.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "user_tag.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "user_tag.*";

    public static final String TABLE_NAME = "user_tag";

}