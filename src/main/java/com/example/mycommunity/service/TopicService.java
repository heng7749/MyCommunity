package com.example.mycommunity.service;

import com.example.mycommunity.pojo.dto.TopicPublishDTO;
import com.example.mycommunity.pojo.entity.Topic;

import java.util.List;

public interface TopicService {
    boolean addTopic(TopicPublishDTO dto);
    List<Topic> getRecentTopic();
    Topic getTopicById(int topicId);
    boolean incrTopicViewCount(int topicId, int count);

    boolean isTopicExist(int topicId);
}
