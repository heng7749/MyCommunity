package com.example.mycommunity.utils;

public interface RedisConstants {
    String TOPIC_VIEW_COUNT_KEY = "topic:viewCount";
    // hash中的键名为（topicID:userID）   值为boolean值

    String TOPIC_COMMENT_COUNT_KEY = "topic:commentCount";

    String TOPIC_LIKE_KEY = "topic:like";

    String TOPIC_LIKE_CHANGE_KEY = "topic:like:hasChange";

    String TOPIC_COLLECT_KEY = "topic:collect";

    String TOPIC_COLLECT_CHANGE_KEY = "topic:collect:hasChange";
}
