package com.example.mycommunity.schedule;

import com.example.mycommunity.mapper.CollectMapper;
import com.example.mycommunity.mapper.CommentMapper;
import com.example.mycommunity.mapper.LikeMapper;
import com.example.mycommunity.service.imp.TopicServiceImp;
import com.example.mycommunity.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class RedisSchedule {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TopicServiceImp topicServiceImp;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private CommentMapper commentMapper;

    // 刷新话题的阅读量
    @Scheduled(cron = "0/5 * * * * *")  // 每五秒刷新
    public void refreshViews() {
        Map<String,Integer> map = redisTemplate.opsForHash().entries(RedisConstants.TOPIC_VIEW_COUNT_KEY);
        map.forEach((topicId,count)->{
            topicServiceImp.incrTopicViewCount(Integer.parseInt(topicId),count);
            redisTemplate.opsForHash().delete(RedisConstants.TOPIC_VIEW_COUNT_KEY,topicId);
        });
    }

    // 刷新topic_like表中的点赞信息
    @Scheduled(cron = "0/5 * * * * *")  // 每五秒刷新
    public void refreshTopicLike() {
        Map<String,Boolean> map = redisTemplate.opsForHash().entries(RedisConstants.TOPIC_LIKE_KEY);
        map.forEach((key,value)->{
            String[] ids = key.split(":");
            likeMapper.updateTopicLike(Integer.parseInt(ids[0]), Integer.parseInt(ids[1]), value ? 1 : 0);
            redisTemplate.opsForHash().delete(RedisConstants.TOPIC_LIKE_KEY, key);
        });
    }

    // 刷新topic表中的点赞数
    @Scheduled(cron = "0/6 * * * * *")  // 每五秒刷新
    public void refreshTopicLikeCount() {
        Set<Integer> members = redisTemplate.opsForSet().members(RedisConstants.TOPIC_LIKE_CHANGE_KEY);
        members.forEach((topicId) ->{
            likeMapper.updateTopicLikeCount(topicId);
            redisTemplate.opsForSet().remove(RedisConstants.TOPIC_LIKE_CHANGE_KEY, topicId);
        });
    }

    // 刷新topic_favour表中的收藏信息
    @Scheduled(cron = "0/5 * * * * *")  // 每五秒刷新
    public void refreshTopicFavour() {
        Map<String,Boolean> map = redisTemplate.opsForHash().entries(RedisConstants.TOPIC_COLLECT_KEY);
        map.forEach((key,value)->{
            String[] ids = key.split(":");
            collectMapper.updateTopicCollect(Integer.parseInt(ids[0]), Integer.parseInt(ids[1]), value ? 1 : 0);
            redisTemplate.opsForHash().delete(RedisConstants.TOPIC_COLLECT_KEY, key);
        });
    }

    // 刷新topic表中的收藏数
    @Scheduled(cron = "0/6 * * * * *")  // 每五秒刷新
    public void refreshTopicCollectCount() {
        Set<Integer> members = redisTemplate.opsForSet().members(RedisConstants.TOPIC_COLLECT_CHANGE_KEY);
        members.forEach((topicId) ->{
            collectMapper.updateTopicCollectCount(topicId);
            redisTemplate.opsForSet().remove(RedisConstants.TOPIC_COLLECT_CHANGE_KEY, topicId);
        });
    }

    // 刷新 topic 中的评论数量
    @Scheduled(cron = "0/6 * * * * *")  // 每五秒刷新
    public void refreshTopicCommentCount() {
        Map<String,Integer> map = redisTemplate.opsForHash().entries(RedisConstants.TOPIC_COMMENT_COUNT_KEY);
        map.forEach((topicId,count) ->{
            commentMapper.updateTopicComment(Integer.parseInt(topicId),count);
            redisTemplate.opsForHash().delete(RedisConstants.TOPIC_COMMENT_COUNT_KEY,topicId);
        });
    }
}
