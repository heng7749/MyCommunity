package com.example.mycommunity.pojo.entity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
    private int id;
    private int userId;
    private int topicId;
    // 长度255
    private String content;
    // 用户名
    private String userName;
    // 用户头像
    private String avatar;
    private int likeCount;
    private int status;
    private Date createTime;
    private Date modifyTime;
}
