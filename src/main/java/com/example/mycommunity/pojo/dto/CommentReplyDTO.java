package com.example.mycommunity.pojo.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CommentReplyDTO {
    // 用于判断是否是对一级评论的回复（1：是 0：不是）
    @NotNull
    @Range(min = 0,max = 1)
    private int type;
    @NotNull
    private int commentId;
    @NotBlank
    @Length(min = 2,max = 255,message = "评论长度应在2-255个字符之间")
    private String content;
}
