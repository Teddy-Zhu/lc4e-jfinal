package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "user_topic_view_attitude", pk = {"id"})
public class User_Topic_View_Attitude extends DBModel<User_Topic_View_Attitude> {
    public static final User_Topic_View_Attitude dao = new User_Topic_View_Attitude();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "user_topic_view_attitude.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_TOPICID = "user_topic_view_attitude.topicId";

    public static final String S_TOPICID = "TOPICID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_USERID = "user_topic_view_attitude.userId";

    public static final String S_USERID = "USERID";

    /**
     * 
     * Type:INT
     * Remarks:0-default,1-agree,-1-disagree
     */
    public static final String F_ATTITUDE = "user_topic_view_attitude.attitude";

    public static final String S_ATTITUDE = "ATTITUDE";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "user_topic_view_attitude.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDAETIME = "user_topic_view_attitude.updaeTime";

    public static final String S_UPDAETIME = "UPDAETIME";

    public static final String ALL_FIELDS = "user_topic_view_attitude.*";

    public static final String TABLE_NAME = "user_topic_view_attitude";

}