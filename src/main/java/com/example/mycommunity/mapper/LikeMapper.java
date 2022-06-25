package com.example.mycommunity.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface LikeMapper {
    @Select("select count(*) from topic_like where topic_id=#{topicId}")
    int getTopicLikeCount(int topicId);

    // 更新topic表中的like_count字段的值
    @Update("update topic set like_count=(select count(*) from topic_like where topic_id=#{topicId} and status=1) where id=#{topicId}")
    boolean updateTopicLikeCount(int topicId);

    @Insert("insert into topic_like(topic_id,user_id,status) values(#{topicId},#{userId},#{status})")
    boolean addTopicLike(int topicId, int userId, int status);

    // 取消话题点赞（逻辑删除）
    @Update("update topic_like set status=#{status} where topic_id=#{topicId} and user_id=#{userId}")
    boolean updateTopicLike(int topicId, int userId, int status);

    // 查看用户是否给当前话题点赞
    @Select("select status from topic_like where topic_id=#{topicId} and user_id=#{userId}")
    Integer isTopicHadLike(int topicId, int userId);
}
