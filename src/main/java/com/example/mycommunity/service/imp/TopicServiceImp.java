package com.example.mycommunity.service.imp;

import com.example.mycommunity.mapper.TopicMapper;
import com.example.mycommunity.mapper.TagTopicMapper;
import com.example.mycommunity.mapper.TagMapper;
import com.example.mycommunity.pojo.dto.TopicPublishDTO;
import com.example.mycommunity.pojo.entity.Topic;
import com.example.mycommunity.pojo.entity.Tag;
import com.example.mycommunity.service.TopicService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TopicServiceImp implements TopicService {
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private TagTopicMapper tagTopicMapper;

    @Override
    @Transactional
    public boolean addTopic(TopicPublishDTO dto) {
        boolean isSuccess = true;
        Session session = SecurityUtils.getSubject().getSession();
        try {
            // 添加话题
            Topic topic = Topic.builder()
                    .userId((int) session.getAttribute("userId"))
                    .userName((String) session.getAttribute("userName"))
                    .avatar((String) session.getAttribute("avatar"))
                    .title(dto.getTitle())
                    .content(dto.getContent())
                    .createTime(new Date()).build();
            int topicId = topicMapper.addTopic(topic);
            // 添加标签
            for (Tag tag: dto.getTags()) {
                Tag t = tagMapper.getTagByName(tag.getName());
                if(t == null) {
                    Integer tagId = tagMapper.addTag(tag);
                    tagTopicMapper.add(topicId,tagId);
                } else {
                    tagTopicMapper.add(topicId,t.getId());
                    tagMapper.topicCountIncrement(t.getId());
                }
            }
        } catch (Exception e){
            isSuccess = false;
            e.printStackTrace();
        }
        return isSuccess;
    }

    @Override
    public List<Topic> getRecentTopic() {
        return topicMapper.getRecent();
    }

    @Override
    public Topic getTopicById(int topicId) {
        return topicMapper.getTopicById(topicId);
    }

    @Override
    public boolean incrTopicViewCount(int topicId, int count) {
        return topicMapper.incrementTopicViewCount(topicId,count);
    }

    @Override
    public boolean isTopicExist(int topicId) {
        return topicMapper.isExistById(topicId) != null;
    }
}

