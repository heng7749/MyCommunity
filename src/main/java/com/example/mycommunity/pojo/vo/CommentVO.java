package com.example.mycommunity.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentVO {
    private int id;
    private String content;
    private String userName;
    private String replyUserName;
    private String avatar;
    private int likeCount;
    private Date createTime;
    private List<CommentVO> children;
}
