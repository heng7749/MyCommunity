package com.example.mycommunity.service.imp;

import com.example.mycommunity.mapper.CollectMapper;
import com.example.mycommunity.mapper.TopicMapper;
import com.example.mycommunity.service.CollectService;
import com.example.mycommunity.utils.CollectConstants;
import com.example.mycommunity.utils.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CollectServiceImp implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private TopicMapper topicMapper;

    @Override
    public void collect(int entityType, int entityId, int userId) {
        String hashKey = entityId + ":" + userId;
        if (entityType == CollectConstants.TOPIC_COLLECT) {
            boolean isMember = redisTemplate.opsForHash().hasKey(RedisConstants.TOPIC_COLLECT_KEY,hashKey);
            // Redis内存中是否存储用户收藏状态
            if (isMember) {
                boolean result = (boolean) redisTemplate.opsForHash().get(RedisConstants.TOPIC_COLLECT_KEY, hashKey);
                redisTemplate.opsForHash().put(RedisConstants.TOPIC_COLLECT_KEY,hashKey,!result);
            } else {
                if (topicMapper.isExistById(entityId) != null) {
                    // 判断是否已收藏该话题
                    Integer topicHadFavour = collectMapper.isTopicHadCollect(entityId, userId);
                    if (topicHadFavour == null) {
                        collectMapper.addTopicCollect(entityId,userId,1);
                        redisTemplate.opsForHash().put(RedisConstants.TOPIC_COLLECT_KEY,hashKey,true);
                    } else {
                        // 判断是否已收藏
                        if (topicHadFavour == 1) {
                            redisTemplate.opsForHash().put(RedisConstants.TOPIC_COLLECT_KEY,hashKey,false);
                        } else {
                            redisTemplate.opsForHash().put(RedisConstants.TOPIC_COLLECT_KEY,hashKey,true);
                        }
                    }
                    // 记录favour数量发生变化的topicId，后边更新topic表中的收藏数量
                    redisTemplate.opsForSet().add(RedisConstants.TOPIC_COLLECT_CHANGE_KEY,entityId);
                }
            }
        } else if (entityType == CollectConstants.NEWS_COLLECT) {

        }
    }

    @Override
    public int findEntityCollectCount(int entityType, int entityId) {
        return 0;
    }

    @Override
    public boolean updateEntityCollectCount(int entityType, int entityId) {
        return false;
    }

    @Override
    public boolean isEntityHadCollect(int entityType, int entityId, int userId) {
        boolean result = false;
        String hashKey = entityId + ":" + userId;
        if (entityType == CollectConstants.TOPIC_COLLECT) {
            // 查找Redis内存是否存有记录
            boolean isMember = redisTemplate.opsForHash().hasKey(RedisConstants.TOPIC_COLLECT_KEY,hashKey);
            if (isMember) {
                return (boolean)redisTemplate.opsForHash().get(RedisConstants.TOPIC_COLLECT_KEY,hashKey);
            } else {
                Integer topicHadFavour = collectMapper.isTopicHadCollect(entityId,userId);
                if (topicHadFavour == null) {
                    collectMapper.addTopicCollect(entityId,userId,0);
                    redisTemplate.opsForHash().put(RedisConstants.TOPIC_COLLECT_KEY,hashKey,false);
                } else {
                    if (topicHadFavour == 1) {
                        redisTemplate.opsForHash().put(RedisConstants.TOPIC_COLLECT_KEY,hashKey,true);
                        result = true;
                    } else {
                        redisTemplate.opsForHash().put(RedisConstants.TOPIC_COLLECT_KEY,hashKey,false);
                    }

                }
            }
        } else if (entityType == CollectConstants.NEWS_COLLECT) {

        }
        return result;
    }
}
