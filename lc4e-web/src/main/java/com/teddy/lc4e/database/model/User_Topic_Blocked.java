package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "user_topic_blocked", pk = {"id"})
public class User_Topic_Blocked extends DBModel<User_Topic_Blocked> {
    public static final User_Topic_Blocked dao = new User_Topic_Blocked();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "user_topic_blocked.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_TOPICID = "user_topic_blocked.topicId";

    public static final String S_TOPICID = "TOPICID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "user_topic_blocked.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "user_topic_blocked.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "user_topic_blocked.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "user_topic_blocked.*";

    public static final String TABLE_NAME = "user_topic_blocked";

}