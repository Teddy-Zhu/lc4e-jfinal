package com.teddy.lc4e.web.service;

import com.jfinal.plugin.activerecord.Page;
import com.teddy.jfinal.annotation.Service;
import com.teddy.jfinal.interfaces.DBModel;
import com.teddy.jfinal.tools.SQLTool;
import com.teddy.lc4e.database.model.*;

/**
 * Created by teddy on 2015/9/23.
 */
@Service
public class TopicService {
    public static TopicService service;

    /**
     * filter topic with user like by pw calc
     *
     * @param userId
     * @param page
     * @param size
     * @param userTagPCT
     * @param topicStatusPCT
     * @param commentCountPCT
     * @param curCommentCountPCT
     * @return topic page
     */
    public Page<? extends DBModel> getTopicPw(String userId, int page, int size, double userTagPCT, double topicStatusPCT, double commentCountPCT, double curCommentCountPCT) {
        SQLTool sql = new SQLTool();
        sql.select(Vw_Topic_Pw.F_ID
                , Vw_Topic_Pw.F_AREAABBR
                , Vw_Topic_Pw.F_AREANAME
                , Vw_Topic_Pw.F_TITLE
                , Vw_Topic_Pw.F_AUTHOR
                , Vw_Topic_Pw.F_AUTHORID
                , Vw_Topic_Pw.F_COUNT
                , Vw_Topic_Pw.F_LASTCOMMENTID
                , Vw_Topic_Pw.F_LASTCOMMENTORDER
                , Vw_Topic_Pw.F_LASTUSER
                , Vw_Topic_Pw.F_LASTUSERNICK
                , Vw_Topic_Pw.F_AUTHORAVATAR)
                .from(Vw_Topic_Pw.TABLE_NAME)
                .leftJoin(Sys_Comment.TABLE_NAME)
                .on(Sys_Comment.F_TOPICID + " = " + Vw_Topic_Pw.F_ID)
                .where(" AND ",
                        SQLTool.OR("curTagUser = ?", "ISNULL(curTagUser)")
                        , SQLTool.NOTIN(Vw_Topic_Pw.F_ID, SQLTool.SELECT(User_Topic_Blocked.F_TOPICID) + SQLTool.FROM(User_Topic_Blocked.TABLE_NAME) + SQLTool.WHERE(User_Topic_Blocked.F_USERID + " = ?"))
                        , SQLTool.NOTIN(Vw_Topic_Pw.F_AUTHORID, SQLTool.SELECT(User_Blocked.F_BLOCKEDUSERID) + SQLTool.FROM(User_Blocked.TABLE_NAME) + SQLTool.WHERE(User_Blocked.F_USERID + " = ?"))
                        , Sys_Comment.F_USERID + " =  ?")
                .groupBy(Vw_Topic_Pw.F_ID, Vw_Topic_Pw.F_AREAABBR
                        , Vw_Topic_Pw.F_AREANAME
                        , Vw_Topic_Pw.F_TITLE
                        , Vw_Topic_Pw.F_AUTHOR
                        , Vw_Topic_Pw.F_AUTHORID
                        , Vw_Topic_Pw.F_COUNT
                        , Vw_Topic_Pw.F_LASTCOMMENTID
                        , Vw_Topic_Pw.F_LASTCOMMENTORDER
                        , Vw_Topic_Pw.F_LASTUSER
                        , Vw_Topic_Pw.F_LASTUSERNICK
                        , Vw_Topic_Pw.F_AUTHORAVATAR)
                .orderByDesc(Vw_Topic_Pw.F_UTPW + " * ? + " + Vw_Topic_Pw.F_TSPW + " * ? + " + Vw_Topic_Pw.F_COUNT + " * ? + " + SQLTool.COUNT(Sys_Comment.F_ID) + " * ?");
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
            userId = user.getIntToString(User.S_ID);
        }
        SQLTool sql = new SQLTool();
        sql.select(Vw_Topic.F_ID, Vw_Topic.F_AREAABBR, Vw_Topic.F_AREANAME, Vw_Topic.F_TITLE
                , Vw_Topic.F_AUTHOR, Vw_Topic.F_AUTHORID, Vw_Topic.F_COUNT, Vw_Topic.F_LASTCOMMENTID
                , Vw_Topic.F_LASTCOMMENTORDER, Vw_Topic.F_LASTUSER, Vw_Topic.F_LASTUSERNICK
                , Vw_Topic.F_AUTHORAVATAR
        ).from(Vw_Topic.TABLE_NAME);
        if (isLogin) {
            sql.where(" AND ", SQLTool.NOTIN(Vw_Topic.F_ID,
                    SQLTool.SELECT(User_Topic_Blocked.F_TOPICID)
                            + SQLTool.FROM(User_Topic_Blocked.TABLE_NAME)
                            + SQLTool.WHERE(User_Topic_Blocked.F_USERID + " = ? ")),
                    SQLTool.NOTIN(Vw_Topic.F_AUTHORID,
                            SQLTool.SELECT(User_Blocked.F_BLOCKEDUSERID)
                                    + SQLTool.FROM(User_Blocked.TABLE_NAME)
                                    + SQLTool.WHERE(User_Blocked.F_USERID + " = ? ")));
            sql.addParams(userId, userId);
        }
        switch (order) {
            case 1: {
                if (isLogin) {
                    return getTopicPw(userId, page, size, userTagPCT, topicStatusPCT, commentCountPCT, curCommentCountPCT);
                } else {
                    sql.orderByDesc(Vw_Topic.F_PUBTIME);
                }
                break;
            }
            case 3: {
                sql.orderByDesc(Vw_Topic.F_LASTCOMMENTTIME);
                break;
            }
            default: {
                sql.orderByDesc(Vw_Topic.F_PUBTIME);
                break;
            }
        }
        return Vw_Topic.dao.paginate(sql, page, size);
    }

}
