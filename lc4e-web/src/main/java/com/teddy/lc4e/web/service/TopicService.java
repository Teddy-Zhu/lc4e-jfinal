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
        sql.select(VwTopicPw.id
                , VwTopicPw.areaabbr
                , VwTopicPw.areaname
                , VwTopicPw.title
                , VwTopicPw.author
                , VwTopicPw.authorid
                , VwTopicPw.count
                , VwTopicPw.lastcommentid
                , VwTopicPw.lastcommentorder
                , VwTopicPw.lastuser
                , VwTopicPw.lastusernick
                , VwTopicPw.authoravatar)
                .from(VwTopicPw.TABLE_NAME)
                .leftJoin(SysComment.TABLE_NAME,SysComment.topicid + " = " + VwTopicPw.id)
                .where(" AND ",
                        SQLTool.OR("curTagUser = ?", "ISNULL(curTagUser)")
                        , SQLTool.NOTIN(VwTopicPw.id, SQLTool.SELECT(UserTopicBlocked.topicid) + SQLTool.FROM(UserTopicBlocked.TABLE_NAME) + SQLTool.WHERE(UserTopicBlocked.userid + " = ?"))
                        , SQLTool.NOTIN(VwTopicPw.authorid, SQLTool.SELECT(UserBlocked.blockeduserid) + SQLTool.FROM(UserBlocked.TABLE_NAME) + SQLTool.WHERE(UserBlocked.userid + " = ?"))
                        , SysComment.userid + " =  ?")
                .groupBy(VwTopicPw.id, VwTopicPw.areaabbr
                        , VwTopicPw.areaname
                        , VwTopicPw.title
                        , VwTopicPw.author
                        , VwTopicPw.authorid
                        , VwTopicPw.count
                        , VwTopicPw.lastcommentid
                        , VwTopicPw.lastcommentorder
                        , VwTopicPw.lastuser
                        , VwTopicPw.lastusernick
                        , VwTopicPw.authoravatar)
                .orderByDesc(VwTopicPw.utpw + " * ? + " + VwTopicPw.tspw + " * ? + " + VwTopicPw.count + " * ? + " + SQLTool.COUNT(SysComment.id) + " * ?");
        sql.appendParam(userId, userId, userId, userId, userTagPCT, topicStatusPCT, commentCountPCT, curCommentCountPCT);
        return VwTopicPw.dao.paginate(sql, page, size);
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
            userId = user.getId().toString();
        }
        SQLTool sql = new SQLTool();
        sql.select(VwTopic.id, VwTopic.areaabbr, VwTopic.areaname, VwTopic.title
                , VwTopic.author, VwTopic.authorid, VwTopic.count, VwTopic.lastcommentid
                , VwTopic.lastcommentorder, VwTopic.lastuser, VwTopic.lastusernick
                , VwTopic.authoravatar
        ).from(VwTopic.TABLE_NAME);
        if (isLogin) {
            sql.where(" AND ", SQLTool.NOTIN(VwTopic.id,
                    SQLTool.SELECT(UserTopicBlocked.topicid)
                            + SQLTool.FROM(UserTopicBlocked.TABLE_NAME)
                            + SQLTool.WHERE(UserTopicBlocked.userid + " = ? ")),
                    SQLTool.NOTIN(VwTopic.authorid,
                            SQLTool.SELECT(UserBlocked.blockeduserid)
                                    + SQLTool.FROM(UserBlocked.TABLE_NAME)
                                    + SQLTool.WHERE(UserBlocked.userid + " = ? ")));
            sql.appendParam(userId, userId);
        }
        switch (order) {
            case 1: {
                if (isLogin) {
                    return getTopicPw(userId, page, size, userTagPCT, topicStatusPCT, commentCountPCT, curCommentCountPCT);
                } else {
                    sql.orderByDesc(VwTopic.pubtime);
                }
                break;
            }
            case 3: {
                sql.orderByDesc(VwTopic.lastcommenttime);
                break;
            }
            default: {
                sql.orderByDesc(VwTopic.pubtime);
                break;
            }
        }
        return VwTopic.dao.paginate(sql, page, size);
    }

}
