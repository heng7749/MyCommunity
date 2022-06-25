package com.example.mycommunity.controller;

import com.example.mycommunity.pojo.entity.Tag;
import com.example.mycommunity.response.CommonResponse;
import com.example.mycommunity.service.imp.TagServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("tag")
public class TagController {
    @Autowired
    private TagServiceImp tagServiceImp;

    @GetMapping("topTen")
    public CommonResponse findTopTags() {
        CommonResponse commonResponse = new CommonResponse();
        List<Tag> topTenTags = tagServiceImp.findTopTenTags();
        commonResponse.setData(topTenTags);
        return commonResponse;
    }

}
