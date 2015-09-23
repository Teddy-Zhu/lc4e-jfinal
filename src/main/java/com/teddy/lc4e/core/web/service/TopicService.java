package com.teddy.lc4e.core.web.service;

import com.teddy.jfinal.annotation.Service;
import com.teddy.lc4e.core.database.mapping.T_User;
import com.teddy.lc4e.core.database.mapping.T_User_Blocked;
import com.teddy.lc4e.core.database.mapping.T_User_Topic_Blocked;
import com.teddy.lc4e.core.database.mapping.T_Vw_Topic_Pw;
import com.teddy.lc4e.core.database.model.User;
import com.teddy.lc4e.core.database.model.Vw_Topic_Pw;

import java.util.List;

/**
 * Created by teddy on 2015/9/23.
 */
@Service
public class TopicService {
    public static TopicService service;

    /**
     * filter topic with user like by pw calc
     *
     * @return
     */
    public List<Vw_Topic_Pw> getArticlesFilterPw(double userTagPCT, double topicStatusPCT, double commentCountPCT) {
        User user = CurUserService.service.getCurrentUser();
        if(user==null){

        }else{

        }
        String userId = user.getIntToString(T_User.id);
        StringBuilder sb = new StringBuilder();
        sb.append("select")
                .append(T_Vw_Topic_Pw.ID + ",")
                .append(T_Vw_Topic_Pw.AREAABBR + ",")
                .append(T_Vw_Topic_Pw.AREANAME + ",")
                .append(T_Vw_Topic_Pw.TITLE + ",")
                .append(T_Vw_Topic_Pw.AUTHOR + ",")
                .append(T_Vw_Topic_Pw.AUTHORID + ",")
                .append(T_Vw_Topic_Pw.COUNT + ",")
                .append(T_Vw_Topic_Pw.LASTCOMMENTID + ",")
                .append(T_Vw_Topic_Pw.LASTCOMMENTORDER + ",")
                .append(T_Vw_Topic_Pw.LASTUSER + ",")
                .append(T_Vw_Topic_Pw.LASTUSERNICK).append(" from " + T_Vw_Topic_Pw.TABLE_NAME)
                .append(T_Vw_Topic_Pw.AVATAR)
                .append(" Where (curTagUser = ? or ISNULL(curTagUser))")
                .append(" and ")
                .append(T_Vw_Topic_Pw.ID + " not in (select " + T_User_Topic_Blocked.TOPICID + " from " + T_User_Topic_Blocked.TABLE_NAME + " where " + T_User_Topic_Blocked.USERID + " = ? )")
                .append(" and ")
                .append(T_Vw_Topic_Pw.AUTHORID + " not in (select " + T_User_Blocked.BLOCKEDUSERID + " from " + T_User_Blocked.TABLE_NAME + " where " + T_User_Blocked.USERID + " = ? ) ")
                .append(" ORDER BY (" + T_Vw_Topic_Pw.UTPW + " * ? + " + T_Vw_Topic_Pw.TSPW + " * ? + " + T_Vw_Topic_Pw.COUNT + " * ? ) DESC");
        return Vw_Topic_Pw.dao.find(sb.toString(), userId, userId, userId, userTagPCT, topicStatusPCT, commentCountPCT);
    }

}
