package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "vw_topic_no_pw")
public class Vw_Topic_No_Pw extends DBModel<Vw_Topic_No_Pw> {
    public static final Vw_Topic_No_Pw dao = new Vw_Topic_No_Pw();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "vw_topic_no_pw.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_AREAID = "vw_topic_no_pw.areaId";

    public static final String S_AREAID = "AREAID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_AUTHORID = "vw_topic_no_pw.authorId";

    public static final String S_AUTHORID = "AUTHORID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_TITLE = "vw_topic_no_pw.title";

    public static final String S_TITLE = "TITLE";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_PUBTIME = "vw_topic_no_pw.pubTime";

    public static final String S_PUBTIME = "PUBTIME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AREAABBR = "vw_topic_no_pw.areaAbbr";

    public static final String S_AREAABBR = "AREAABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AREANAME = "vw_topic_no_pw.areaName";

    public static final String S_AREANAME = "AREANAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AUTHOR = "vw_topic_no_pw.author";

    public static final String S_AUTHOR = "AUTHOR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AUTHORAVATAR = "vw_topic_no_pw.authorAvatar";

    public static final String S_AUTHORAVATAR = "AUTHORAVATAR";

    /**
     * 
     * Type:BIGINT
     * Remarks:
     */
    public static final String F_COUNT = "vw_topic_no_pw.count";

    public static final String S_COUNT = "COUNT";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_LASTCOMMENTID = "vw_topic_no_pw.lastCommentId";

    public static final String S_LASTCOMMENTID = "LASTCOMMENTID";

    public static final String ALL_FIELDS = "vw_topic_no_pw.*";

    public static final String TABLE_NAME = "vw_topic_no_pw";

}