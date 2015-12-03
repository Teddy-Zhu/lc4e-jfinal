package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "vw_topic_tag_count_pw")
public class Vw_Topic_Tag_Count_Pw extends DBModel<Vw_Topic_Tag_Count_Pw> {
    public static final Vw_Topic_Tag_Count_Pw dao = new Vw_Topic_Tag_Count_Pw();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "vw_topic_tag_count_pw.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AREAABBR = "vw_topic_tag_count_pw.areaAbbr";

    public static final String S_AREAABBR = "AREAABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AREANAME = "vw_topic_tag_count_pw.areaName";

    public static final String S_AREANAME = "AREANAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_TITLE = "vw_topic_tag_count_pw.title";

    public static final String S_TITLE = "TITLE";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AUTHOR = "vw_topic_tag_count_pw.author";

    public static final String S_AUTHOR = "AUTHOR";

    /**
     * 
     * Type:DECIMAL
     * Remarks:
     */
    public static final String F_UTPW = "vw_topic_tag_count_pw.utPw";

    public static final String S_UTPW = "UTPW";

    /**
     * 
     * Type:BIGINT
     * Remarks:
     */
    public static final String F_COUNT = "vw_topic_tag_count_pw.count";

    public static final String S_COUNT = "COUNT";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_LASTCOMMENTID = "vw_topic_tag_count_pw.lastCommentId";

    public static final String S_LASTCOMMENTID = "LASTCOMMENTID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_CURTAGUSER = "vw_topic_tag_count_pw.curTagUser";

    public static final String S_CURTAGUSER = "CURTAGUSER";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AUTHORAVATAR = "vw_topic_tag_count_pw.authorAvatar";

    public static final String S_AUTHORAVATAR = "AUTHORAVATAR";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_PUBTIME = "vw_topic_tag_count_pw.pubTime";

    public static final String S_PUBTIME = "PUBTIME";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_AREAID = "vw_topic_tag_count_pw.areaId";

    public static final String S_AREAID = "AREAID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_AUTHORID = "vw_topic_tag_count_pw.authorId";

    public static final String S_AUTHORID = "AUTHORID";

    public static final String ALL_FIELDS = "vw_topic_tag_count_pw.*";

    public static final String TABLE_NAME = "vw_topic_tag_count_pw";

}