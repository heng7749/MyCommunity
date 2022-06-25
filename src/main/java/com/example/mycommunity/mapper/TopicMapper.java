package com.example.mycommunity.mapper;

import com.example.mycommunity.pojo.entity.Topic;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TopicMapper {
    @Insert("insert into topic(user_id,title,content,user_name,avatar,create_time) values(#{userId},#{title},#{content},#{userName},#{avatar},#{createTime})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int addTopic(Topic topic);

    @Results(value = {
            @Result(property = "userId",column = "user_id"),
            @Result(property = "userName",column = "user_name"),
            @Result(property = "avatar",column = "avatar"),
            @Result(property = "commentCount",column = "comment_count"),
            @Result(property = "likeCount",column = "like_count"),
            @Result(property = "collectCount",column = "collect_count"),
            @Result(property = "viewCount",column = "view_count"),
            @Result(property = "createTime",column = "create_time")
    })
    // status=0 表示状态正常
    @Select("select * from topic where status=0")
    List<Topic> getRecent();

    @Results(value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "commentCount", column = "comment_count"),
            @Result(property = "likeCount", column = "like_count"),
            @Result(property = "collectCount", column = "collect_count"),
            @Result(property = "viewCount", column = "view_count"),
            @Result(property = "createTime", column = "create_time")
    })
    @Select("select * from topic where id=#{topicId} and status=0")
    Topic getTopicById(int topicId);

    @Update("update topic set view_count=view_count+#{count} where id=#{topicId}")
    boolean incrementTopicViewCount(int topicId, int count);

    // 判断当前id的话题是否存在
    @Select("select id from topic where id=#{topicId}")
    Integer isExistById(int topicId);

}
