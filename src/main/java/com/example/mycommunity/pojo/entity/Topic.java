package com.example.mycommunity.pojo.entity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Topic {
    private int id;
    private int userId;
    private String userName;
    private String avatar;
    private String title;
    private String content;
    private int status;
    private int commentCount;
    private int likeCount;
    private int collectCount;
    private int viewCount;
    // 置顶
    private Boolean top = false;
    // 加精
    private Boolean essence = false;
    private Date createTime;
    private Date modifyTime;
}
