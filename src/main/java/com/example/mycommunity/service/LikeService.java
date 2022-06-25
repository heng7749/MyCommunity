package com.example.mycommunity.service;

public interface LikeService {
    void like(int entityType, int entityId, int userId);
    int findEntityLikeCount(int entityType, int entityId);
    boolean updateEntityLickCount(int entityType, int entityId);
    boolean isEntityHadLike(int entityType, int entityId, int userId);
}
