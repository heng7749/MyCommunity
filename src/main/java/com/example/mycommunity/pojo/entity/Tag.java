package com.example.mycommunity.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Tag {
    private int id;
//    2-16
    private String name;
    private int topicCount;
}
