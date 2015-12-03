package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "topic_tag", pk = {"id"})
public class Topic_Tag extends DBModel<Topic_Tag> {
    public static final Topic_Tag dao = new Topic_Tag();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "topic_tag.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_TAGID = "topic_tag.tagId";

    public static final String S_TAGID = "TAGID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_TOPICID = "topic_tag.topicId";

    public static final String S_TOPICID = "TOPICID";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_CREATETIME = "topic_tag.createTime";

    public static final String S_CREATETIME = "CREATETIME";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_UPDATETIME = "topic_tag.updateTime";

    public static final String S_UPDATETIME = "UPDATETIME";

    public static final String ALL_FIELDS = "topic_tag.*";

    public static final String TABLE_NAME = "topic_tag";

}