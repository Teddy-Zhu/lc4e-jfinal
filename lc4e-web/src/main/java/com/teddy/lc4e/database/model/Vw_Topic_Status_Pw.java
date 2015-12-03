package com.teddy.lc4e.database.model; /**
 * Created by lc4e Tool on 15/12/03.
 */
import com.teddy.jfinal.annotation.Model;
import com.teddy.jfinal.interfaces.DBModel;

/**
 * Created by lc4e Tool on 15/12/03.
 */
@Model(value = "vw_topic_status_pw")
public class Vw_Topic_Status_Pw extends DBModel<Vw_Topic_Status_Pw> {
    public static final Vw_Topic_Status_Pw dao = new Vw_Topic_Status_Pw();

    /**
     * 
     * Type:INT
     * Remarks:
     */
    public static final String F_ID = "vw_topic_status_pw.id";

    public static final String S_ID = "ID";

    /**
     * 
     * Type:DECIMAL
     * Remarks:
     */
    public static final String F_STATUSPW = "vw_topic_status_pw.statusPw";

    public static final String S_STATUSPW = "STATUSPW";

    public static final String ALL_FIELDS = "vw_topic_status_pw.*";

    public static final String TABLE_NAME = "vw_topic_status_pw";

}