package com.example.mycommunity.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class News {
    private int id;
    private int userId;
    private int category;
    private String title;
    private String content;
    private int status;
    private String thumbnail;
    private int thumbs = 0;
    private int comments = 0;
    private int collects = 0;
    private int view = 0;
    // 置顶
    private Boolean top = false;
    // 加精
    private Boolean essence = false;
    private Date createTime;
    private Date modifyTime;
}
