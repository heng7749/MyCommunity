package com.example.mycommunity.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

// 公告
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Billboard {
    private int id;
    private String content;
    private int status;
    private Date createTime;
    private Date modifyTime;
}
