package com.example.mycommunity.service;

public interface CollectService {
    void collect(int entityType, int entityId, int userId);
    int findEntityCollectCount(int entityType, int entityId);
    boolean updateEntityCollectCount(int entityType, int entityId);
    boolean isEntityHadCollect(int entityType, int entityId, int userId);
}
