package com.example.mycommunity.pojo.vo;

import com.example.mycommunity.pojo.entity.Topic;
import com.github.pagehelper.PageInfo;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class TopicDetailVO {
    private Topic topic;
    private boolean hadLike;
    private boolean hadFavour;
    private PageInfo pageInfo;
}
