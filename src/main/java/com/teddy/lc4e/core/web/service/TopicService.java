package com.teddy.lc4e.core.web.service;

import com.jfinal.plugin.activerecord.Page;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.interfaces.DBModel;
import com.teddy.jfinal.tools.SQLTool;
import com.teddy.lc4e.core.database.mapping.*;
import com.teddy.lc4e.core.database.model.User;
import com.teddy.lc4e.core.database.model.Vw_Topic;
import com.teddy.lc4e.core.database.model.Vw_Topic_Pw;

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
    public Page<? extends DBModel> getTopicPw(String userId, int page, int size, double userTagPCT, double topicStatusPCT, double commentCountPCT, double curCommentCountPCT) {
        SQLTool sql = new SQLTool();
        sql.select(T_Vw_Topic_Pw.ID
                , T_Vw_Topic_Pw.AREAABBR
                , T_Vw_Topic_Pw.AREANAME
                , T_Vw_Topic_Pw.TITLE
                , T_Vw_Topic_Pw.AUTHOR
                , T_Vw_Topic_Pw.AUTHORID
                , T_Vw_Topic_Pw.COUNT
                , T_Vw_Topic_Pw.LASTCOMMENTID
                , T_Vw_Topic_Pw.LASTCOMMENTORDER
                , T_Vw_Topic_Pw.LASTUSER
                , T_Vw_Topic_Pw.LASTUSERNICK
                , T_Vw_Topic_Pw.AUTHORAVATAR)
                .from(T_Vw_Topic_Pw.TABLE_NAME)
                .leftJoin(T_Sys_Comment.TABLE_NAME)
                .on(T_Sys_Comment.TOPICID + " = " + T_Vw_Topic_Pw.ID)
                .where(" AND ",
                        SQLTool.OR("curTagUser = ?", "ISNULL(curTagUser)")
                        , SQLTool.NOTIN(T_Vw_Topic_Pw.ID, SQLTool.SELECT(T_User_Topic_Blocked.TOPICID) + SQLTool.FROM(T_User_Topic_Blocked.TABLE_NAME) + SQLTool.WHERE(T_User_Topic_Blocked.USERID + " = ?"))
                        , SQLTool.NOTIN(T_Vw_Topic_Pw.AUTHORID, SQLTool.SELECT(T_User_Blocked.BLOCKEDUSERID) + SQLTool.FROM(T_User_Blocked.TABLE_NAME) + SQLTool.WHERE(T_User_Blocked.USERID + " = ?"))
                        , T_Sys_Comment.USERID + " =  ?")
                .groupBy(T_Vw_Topic_Pw.ID)
                .orderByDesc(T_Vw_Topic_Pw.UTPW + " * ? + " + T_Vw_Topic_Pw.TSPW + " * ? + " + T_Vw_Topic_Pw.COUNT + " * ? + " + SQLTool.COUNT(T_Sys_Comment.ID) + " * ?");
        sql.addParams(userId, userId, userId, userId, userTagPCT, topicStatusPCT, commentCountPCT, curCommentCountPCT);
        return Vw_Topic_Pw.dao.paginate(sql, page, size);
    }

    /**
     * all topic in time range(3 month)
     *
     * @param order 1:  by user favorite tag
     *              2: by topic publish time
     *              3: by last reply time
     * @return
     */
    public Page<? extends DBModel> getTopic(int order, int page, int size, double userTagPCT, double topicStatusPCT, double commentCountPCT, double curCommentCountPCT) {
        boolean isLogin;
        page = page < 1 ? 1 : page;
        String userId = "";
        User user = CurUserService.service.getCurrentUser();
        isLogin = user != null;
        if (isLogin) {
            userId = user.getIntToString(T_User.id);
        }
        SQLTool sql = new SQLTool();
        sql.select(T_Vw_Topic.ID, T_Vw_Topic.AREAABBR, T_Vw_Topic.AREANAME, T_Vw_Topic.TITLE
                , T_Vw_Topic.AUTHOR, T_Vw_Topic.AUTHORID, T_Vw_Topic.COUNT, T_Vw_Topic.LASTCOMMENTID
                , T_Vw_Topic.LASTCOMMENTORDER, T_Vw_Topic.LASTUSER, T_Vw_Topic.LASTUSERNICK
                , T_Vw_Topic.AUTHORAVATAR
        ).from(T_Vw_Topic.TABLE_NAME);
        if (isLogin) {
            sql.where(" AND ", SQLTool.NOTIN(T_Vw_Topic.ID,
                            SQLTool.SELECT(T_User_Topic_Blocked.TOPICID)
                                    + SQLTool.FROM(T_User_Topic_Blocked.TABLE_NAME)
                                    + SQLTool.WHERE(T_User_Topic_Blocked.USERID + " = ? ")),
                    SQLTool.NOTIN(T_Vw_Topic.AUTHORID,
                            SQLTool.SELECT(T_User_Blocked.BLOCKEDUSERID)
                                    + SQLTool.FROM(T_User_Blocked.TABLE_NAME)
                                    + SQLTool.WHERE(T_User_Blocked.USERID + " = ? ")));
            sql.addParams(userId, userId);
        }
        switch (order) {
            case 1: {
                if (isLogin) {
                    return getTopicPw(userId, page, size, userTagPCT, topicStatusPCT, commentCountPCT, curCommentCountPCT);
                } else {
                    sql.orderByDesc(T_Vw_Topic.PUBTIME);
                }
                break;
            }
            case 3: {
                sql.orderByDesc(T_Vw_Topic.LASTCOMMENTTIME);
                break;
            }
            default: {
                sql.orderByDesc(T_Vw_Topic.PUBTIME);
                break;
            }
        }
        return Vw_Topic.dao.paginate(sql, page, size);
    }

}
