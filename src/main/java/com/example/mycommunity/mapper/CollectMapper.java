package com.example.mycommunity.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CollectMapper {
    // 更新topic表中的favour_count字段的值
    @Update("update topic set collect_count=(select count(*) from topic_collect where topic_id=#{topicId} and status=1) where id=#{topicId}")
    boolean updateTopicCollectCount(int topicId);

    @Insert("insert into topic_collect(topic_id,user_id,status) values(#{topicId},#{userId},#{status})")
    boolean addTopicCollect(int topicId, int userId, int status);

    // 更新话题收藏（逻辑删除）
    @Update("update topic_collect set status=#{status} where topic_id=#{topicId} and user_id=#{userId}")
    boolean updateTopicCollect(int topicId, int userId, int status);

    // 查看用户是否收藏当前话题
    @Select("select status from topic_collect where topic_id=#{topicId} and user_id=#{userId}")
    Integer isTopicHadCollect(int topicId, int userId);
}
