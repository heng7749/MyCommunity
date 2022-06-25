package com.example.mycommunity.service;

import com.example.mycommunity.pojo.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> findTopTenTags();
}
