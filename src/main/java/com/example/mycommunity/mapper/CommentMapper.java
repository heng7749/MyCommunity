package com.example.mycommunity.mapper;

import com.example.mycommunity.pojo.entity.Comment;
import com.example.mycommunity.pojo.entity.CommentReply;
import com.example.mycommunity.pojo.vo.CommentVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CommentMapper {
    // status=0 表示评论状态正常
    @Results(value = {
            @Result(property = "userName",column = "user_name"),
            @Result(property = "likeCount",column = "like_count"),
            @Result(property = "createTime",column = "create_time"),
    })
    @Select("select * from comment where topic_id=#{topicId} and status=0")
    List<CommentVO> listTopicComment(int topicId);

    @Insert("insert into comment(user_id,topic_id,content,user_name,avatar,create_time) values(#{userId},#{topicId},#{content},#{userName},#{avatar},#{createTime})")
    boolean addComment(Comment comment);

    @Results(value = {
            @Result(property = "userId",column = "user_id"),
            @Result(property = "userName",column = "user_name"),
            @Result(property = "topicId",column = "topic_id")
    })
    @Select("select id,topic_id,user_id,user_name from comment where id=#{commentId}")
    Comment getCommentById(int commentId);


    @Results(value = {
            @Result(property = "userName",column = "user_name"),
            @Result(property = "replyUserName",column = "reply_user_name"),
            @Result(property = "likeCount",column = "like_count"),
            @Result(property = "createTime",column = "create_time")
    })
    @Select("select * from comment_reply where comment_id=#{commentId} and status=0")
    List<CommentVO> listCommentReply(int commentId);

    @Insert("insert into comment_reply(topic_id,comment_id,comment_reply_id,user_id,user_name,reply_user_id,reply_user_name,content,avatar,create_time) values(#{topicId},#{commentId},#{commentReplyId},#{userId},#{userName},#{replyUserId},#{replyUserName},#{content},#{avatar},#{createTime})")
    boolean addCommentReply(CommentReply commentReply);

    @Results(value = {
            @Result(property = "userId",column = "user_id"),
            @Result(property = "commentId",column = "comment_id"),
            @Result(property = "userName",column = "user_name"),
            @Result(property = "topicId",column = "topic_id")
    })
    @Select("select id,topic_id,comment_id,user_id,user_name from comment_reply where id=#{commentReplyId}")
    CommentReply getCommentReplyById(int commentReplyId);

    // 更新topic的评论数量
    @Update("update topic set comment_count=comment_count+#{count} where id=#{topicId}")
    boolean updateTopicComment(int topicId, int count);
}
