package com.example.mycommunity.mapper;

import com.example.mycommunity.pojo.entity.Tag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TagMapper {
    @Select("select id,name from tag where name=#{name}")
    Tag getTagByName(String name);

    @Insert("insert into tag(name) values(#{name})")
    @Options(useGeneratedKeys = true, keyColumn = "id")
    int addTag(Tag tag);

    @Update("update tag set topic_count=topic_count+1 where id=#{tagId}")
    boolean topicCountIncrement(Integer tagId);

    @Result(property = "topicCount", column = "topic_count")
    @Select("SELECT id,name,topic_count FROM tag ORDER BY topic_count DESC LIMIT 10")
    List<Tag> listTopTenTag();
}
