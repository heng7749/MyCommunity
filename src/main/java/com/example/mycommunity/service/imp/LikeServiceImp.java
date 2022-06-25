package com.example.mycommunity.service.imp;

import com.example.mycommunity.mapper.LikeMapper;
import com.example.mycommunity.mapper.TopicMapper;
import com.example.mycommunity.service.LikeService;
import com.example.mycommunity.utils.LikeConstants;
import com.example.mycommunity.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImp implements LikeService {
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void like(int entityType, int entityId, int userId) {
        String hashKey = entityId + ":" + userId;
        // 话题
        if (entityType == LikeConstants.TOPIC_LIKE) {
            boolean isMember = redisTemplate.opsForHash().hasKey(RedisConstants.TOPIC_LIKE_KEY,hashKey);
            // Redis内存中是否存储点赞状态
            if (isMember) {
                boolean result = (boolean) redisTemplate.opsForHash().get(RedisConstants.TOPIC_LIKE_KEY, hashKey);
                redisTemplate.opsForHash().put(RedisConstants.TOPIC_LIKE_KEY,hashKey,!result);
            } else {
                if (topicMapper.isExistById(entityId) != null) {
                    // 判断是否已给该话题点赞
                    Integer topicLike = likeMapper.isTopicHadLike(entityId, userId);
                    if (topicLike == null) {
                        likeMapper.addTopicLike(entityId,userId,1);
                        redisTemplate.opsForHash().put(RedisConstants.TOPIC_LIKE_KEY,hashKey,true);
                    } else {
                        // 判断是否已点赞
                        if (topicLike == 1) {
                            redisTemplate.opsForHash().put(RedisConstants.TOPIC_LIKE_KEY,hashKey,false);
                        } else {
                            redisTemplate.opsForHash().put(RedisConstants.TOPIC_LIKE_KEY,hashKey,true);
                        }
                    }
                    // 记录like数量发生变化的topicId，后边更新topic表中的点赞数量
                    redisTemplate.opsForSet().add(RedisConstants.TOPIC_LIKE_CHANGE_KEY,entityId);
                }
            }
        } else if (entityType == LikeConstants.COMMENT_LIKE) {

        } else if (entityType == LikeConstants.COMMENT_REPLY_LIKE) {

        } else if (entityType == LikeConstants.NEWS_LIKE) {

        }
    }

    @Override
    public int findEntityLikeCount(int entityType, int entityId) {
        return 0;
    }

    @Override
    public boolean updateEntityLickCount(int entityType, int entityId) {
        return false;
    }

    @Override
    public boolean isEntityHadLike(int entityType, int entityId, int userId) {
        boolean result = false;
        String hashKey = entityId + ":" + userId;
        // 话题
        if (entityType == LikeConstants.TOPIC_LIKE) {
            // 查找Redis内存是否存有记录
            boolean isMember = redisTemplate.opsForHash().hasKey(RedisConstants.TOPIC_LIKE_KEY,hashKey);
            if (isMember) {
                return (boolean)redisTemplate.opsForHash().get(RedisConstants.TOPIC_LIKE_KEY,hashKey);
            } else {
                Integer topicHadLike = likeMapper.isTopicHadLike(entityId,userId);
                if (topicHadLike == null) {
                    likeMapper.addTopicLike(entityId,userId,0);
                    redisTemplate.opsForHash().put(RedisConstants.TOPIC_LIKE_KEY,hashKey,false);
                } else {
                    if (topicHadLike == 1) {
                        redisTemplate.opsForHash().put(RedisConstants.TOPIC_LIKE_KEY,hashKey,true);
                        result = true;
                    } else {
                        redisTemplate.opsForHash().put(RedisConstants.TOPIC_LIKE_KEY,hashKey,false);
                    }

                }
            }

        } else if (entityType == LikeConstants.COMMENT_LIKE) {

        } else if (entityType == LikeConstants.COMMENT_REPLY_LIKE) {

        } else if (entityType == LikeConstants.NEWS_LIKE) {

        }
        return result;
    }


}
