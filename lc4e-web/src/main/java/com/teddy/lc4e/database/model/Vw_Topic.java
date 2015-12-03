package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "vw_topic")
public class Vw_Topic extends DBModel<Vw_Topic> {
    public static final Vw_Topic dao = new Vw_Topic();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "vw_topic.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_AREAID = "vw_topic.areaId";

    public static final String S_AREAID = "AREAID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_AUTHORID = "vw_topic.authorId";

    public static final String S_AUTHORID = "AUTHORID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_TITLE = "vw_topic.title";

    public static final String S_TITLE = "TITLE";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_PUBTIME = "vw_topic.pubTime";

    public static final String S_PUBTIME = "PUBTIME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AREAABBR = "vw_topic.areaAbbr";

    public static final String S_AREAABBR = "AREAABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AREANAME = "vw_topic.areaName";

    public static final String S_AREANAME = "AREANAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AUTHOR = "vw_topic.author";

    public static final String S_AUTHOR = "AUTHOR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AUTHORAVATAR = "vw_topic.authorAvatar";

    public static final String S_AUTHORAVATAR = "AUTHORAVATAR";

    /**
     * 
     * Type:BIGINT
     * Remarks:
     */
    public static final String F_COUNT = "vw_topic.count";

    public static final String S_COUNT = "COUNT";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_LASTCOMMENTID = "vw_topic.lastCommentId";

    public static final String S_LASTCOMMENTID = "LASTCOMMENTID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_LASTUSER = "vw_topic.lastUser";

    public static final String S_LASTUSER = "LASTUSER";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_LASTUSERNICK = "vw_topic.lastUserNick";

    public static final String S_LASTUSERNICK = "LASTUSERNICK";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_LASTCOMMENTORDER = "vw_topic.lastCommentOrder";

    public static final String S_LASTCOMMENTORDER = "LASTCOMMENTORDER";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_LASTCOMMENTTIME = "vw_topic.lastCommentTime";

    public static final String S_LASTCOMMENTTIME = "LASTCOMMENTTIME";

    public static final String ALL_FIELDS = "vw_topic.*";

    public static final String TABLE_NAME = "vw_topic";

}