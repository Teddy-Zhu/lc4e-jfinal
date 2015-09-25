package com.teddy.lc4e.core.web.service;

import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.interfaces.DBModel;
import com.teddy.lc4e.core.database.mapping.*;
import com.teddy.lc4e.core.database.model.User;
import com.teddy.lc4e.core.database.model.Vw_Topic;
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
    public List<? extends DBModel> getTopicPw(String userId, int page, int size, double userTagPCT, double topicStatusPCT, double commentCountPCT) {
        return Vw_Topic_Pw.dao.find("select " + T_Vw_Topic_Pw.ID + "," + T_Vw_Topic_Pw.AREAABBR + "," + T_Vw_Topic_Pw.AREANAME + "," + T_Vw_Topic_Pw.TITLE + "," + T_Vw_Topic_Pw.AUTHOR + "," + T_Vw_Topic_Pw.AUTHORID + "," + T_Vw_Topic_Pw.COUNT + "," + T_Vw_Topic_Pw.LASTCOMMENTID + "," + T_Vw_Topic_Pw.LASTCOMMENTORDER + "," + T_Vw_Topic_Pw.LASTUSER + "," + T_Vw_Topic_Pw.LASTUSERNICK + " from " + T_Vw_Topic_Pw.TABLE_NAME + T_Vw_Topic_Pw.AUTHORAVATAR + " Where (curTagUser = ? or ISNULL(curTagUser))" + " and " + T_Vw_Topic_Pw.ID + " not in (select " + T_User_Topic_Blocked.TOPICID + " from " + T_User_Topic_Blocked.TABLE_NAME + " where " + T_User_Topic_Blocked.USERID + " = ? )" + " and " + T_Vw_Topic_Pw.AUTHORID + " not in (select " + T_User_Blocked.BLOCKEDUSERID + " from " + T_User_Blocked.TABLE_NAME + " where " + T_User_Blocked.USERID + " = ? ) " + " ORDER BY (" + T_Vw_Topic_Pw.UTPW + " * ? + " + T_Vw_Topic_Pw.TSPW + " * ? + " + T_Vw_Topic_Pw.COUNT + " * ? ) DESC" + " limit " + (page - 1) * size + "," + size, userId, userId, userId, userTagPCT, topicStatusPCT, commentCountPCT);
    }

    /**
     * all topic in time range(3 month)
     *
     * @param order 1:  by user favorite tag
     *              2: by topic publish time
     *              3: by last reply time
     * @return
     */
    public List<? extends DBModel> getTopic(int order, int page, int size, double userTagPCT, double topicStatusPCT, double commentCountPCT) {
        boolean isLogin = false;
        page = page < 1 ? 1 : page;
        String userId = "";
        User user = CurUserService.service.getCurrentUser();
        isLogin = user != null;
        if (isLogin) {
            userId = user.getIntToString(T_User.id);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("select ")
                .append(T_Vw_Topic.ID + ",")
                .append(T_Vw_Topic.AREAABBR + ",")
                .append(T_Vw_Topic.AREANAME + ",")
                .append(T_Vw_Topic.TITLE + ",")
                .append(T_Vw_Topic.AUTHOR + ",")
                .append(T_Vw_Topic.AUTHORID + ",")
                .append(T_Vw_Topic.COUNT + ",")
                .append(T_Vw_Topic.LASTCOMMENTID + ",")
                .append(T_Vw_Topic.LASTCOMMENTORDER + ",")
                .append(T_Vw_Topic.LASTUSER + ",")
                .append(T_Vw_Topic.LASTUSERNICK+ ",")
                .append(T_Vw_Topic.AUTHORAVATAR)
                .append(" from " + T_Vw_Topic.TABLE_NAME);
        if (isLogin) {
            sb.append(" Where ")
                    .append(T_Vw_Topic.ID + " not in (select " + T_User_Topic_Blocked.TOPICID + " from " + T_User_Topic_Blocked.TABLE_NAME + " where " + T_User_Topic_Blocked.USERID + " = ? )")
                    .append(" and ")
                    .append(T_Vw_Topic.AUTHORID + " not in (select " + T_User_Blocked.BLOCKEDUSERID + " from " + T_User_Blocked.TABLE_NAME + " where " + T_User_Blocked.USERID + " = ? ) ");
        }
        switch (order) {
            case 1: {
                if (isLogin) {
                    return getTopicPw(userId, page, size, userTagPCT, topicStatusPCT, commentCountPCT);
                } else {
                    sb.append(" order by ").append(T_Vw_Topic.PUBTIME).append(" desc");
                }
                break;
            }
            case 3: {
                sb.append(" order by ").append(T_Vw_Topic.LASTCOMMENTTIME).append(" desc");
                break;
            }
            default: {
                sb.append(" order by ").append(T_Vw_Topic.PUBTIME).append(" desc");
                break;
            }
        }
        sb.append(" limit ").append((page - 1) * size).append(",").append(size);
        return isLogin ? Vw_Topic.dao.find(sb.toString(), userId, userId) : Vw_Topic.dao.find(sb.toString());
    }

}
