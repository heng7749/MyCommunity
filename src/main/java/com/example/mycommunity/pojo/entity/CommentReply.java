package com.example.mycommunity.pojo.entity;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentReply {
    private int id;
    private int topicId;
    // 被回复的评论的ID
    private int commentId;
    // 自关联Id,用于判断是否为三级评论（当此id为空时为二级评论，否则为三级及以上评论）
    private int commentReplyId;
    private int userId;
    // 用户名
    private String userName;
    // 被回复的人ID
    private int replyUserId;
    // 被回复的人名
    private String replyUserName;
    // 长度255
    private String content;
    // 用户头像
    private String avatar;
    private int likeCount;
    private int status;
    private Date createTime;
    private Date modifyTime;
}
