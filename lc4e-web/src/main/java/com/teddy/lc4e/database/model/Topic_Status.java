package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "topic_status", pk = {"id"})
public class Topic_Status extends DBModel<Topic_Status> {
    public static final Topic_Status dao = new Topic_Status();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "topic_status.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_TOPICID = "topic_status.topicId";

    public static final String S_TOPICID = "TOPICID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_STATUSID = "topic_status.statusId";

    public static final String S_STATUSID = "STATUSID";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "topic_status.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "topic_status.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "topic_status.*";

    public static final String TABLE_NAME = "topic_status";

}