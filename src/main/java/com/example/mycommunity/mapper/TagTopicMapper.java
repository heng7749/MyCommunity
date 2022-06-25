package com.example.mycommunity.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagTopicMapper {

    @Insert("insert into tag_topic(topic_id,tag_id) values(#{topicId},#{tagId})")
    boolean add(Integer topicId, Integer tagId);
}
