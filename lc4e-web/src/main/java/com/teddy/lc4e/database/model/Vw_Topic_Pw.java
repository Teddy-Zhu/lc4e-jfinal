package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "vw_topic_pw")
public class Vw_Topic_Pw extends DBModel<Vw_Topic_Pw> {
    public static final Vw_Topic_Pw dao = new Vw_Topic_Pw();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "vw_topic_pw.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AREAABBR = "vw_topic_pw.areaAbbr";

    public static final String S_AREAABBR = "AREAABBR";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AREANAME = "vw_topic_pw.areaName";

    public static final String S_AREANAME = "AREANAME";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_TITLE = "vw_topic_pw.title";

    public static final String S_TITLE = "TITLE";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AUTHOR = "vw_topic_pw.author";

    public static final String S_AUTHOR = "AUTHOR";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_AUTHORID = "vw_topic_pw.authorId";

    public static final String S_AUTHORID = "AUTHORID";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_CURTAGUSER = "vw_topic_pw.curTagUser";

    public static final String S_CURTAGUSER = "CURTAGUSER";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_LASTUSERNICK = "vw_topic_pw.lastUserNick";

    public static final String S_LASTUSERNICK = "LASTUSERNICK";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_LASTUSER = "vw_topic_pw.lastUser";

    public static final String S_LASTUSER = "LASTUSER";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_LASTCOMMENTORDER = "vw_topic_pw.lastCommentOrder";

    public static final String S_LASTCOMMENTORDER = "LASTCOMMENTORDER";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_LASTCOMMENTID = "vw_topic_pw.lastCommentId";

    public static final String S_LASTCOMMENTID = "LASTCOMMENTID";

    /**
     * 
     * Type:DECIMAL
     * Remarks:
     */
    public static final String F_TSPW = "vw_topic_pw.tsPw";

    public static final String S_TSPW = "TSPW";

    /**
     * 
     * Type:DECIMAL
     * Remarks:
     */
    public static final String F_UTPW = "vw_topic_pw.utPw";

    public static final String S_UTPW = "UTPW";

    /**
     * 
     * Type:BIGINT
     * Remarks:
     */
    public static final String F_UTVAPW = "vw_topic_pw.utvaPw";

    public static final String S_UTVAPW = "UTVAPW";

    /**
     * 
     * Type:BIGINT
     * Remarks:
     */
    public static final String F_COUNT = "vw_topic_pw.count";

    public static final String S_COUNT = "COUNT";

    /**
     * 
     * Type:DATETIME
     * Remarks:
     */
    public static final String F_PUBTIME = "vw_topic_pw.pubTime";

    public static final String S_PUBTIME = "PUBTIME";

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_AREAID = "vw_topic_pw.areaId";

    public static final String S_AREAID = "AREAID";

    /**
     * 
     * Type:VARCHAR
     * Remarks:
     */
    public static final String F_AUTHORAVATAR = "vw_topic_pw.authorAvatar";

    public static final String S_AUTHORAVATAR = "AUTHORAVATAR";

    public static final String ALL_FIELDS = "vw_topic_pw.*";

    public static final String TABLE_NAME = "vw_topic_pw";

}