package com.example.mycommunity.pojo.dto;

import com.example.mycommunity.annotation.TagCheck;
import com.example.mycommunity.pojo.entity.Tag;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


@Data
public class TopicPublishDTO {
    @NotBlank(message = "标题不能为空")
    @Length(min = 2,max = 30,message = "标题长度应在2-30之间")
    private String title;
    @NotBlank
    @Length(min = 2,max = 1000,message = "内容长度应在2-1000之间")
    private String content;
    @TagCheck(min = 1,max = 5,message = "标签个数应在1-5之间，各标签长度应在2-16之间")
    private Tag[] tags;
}
