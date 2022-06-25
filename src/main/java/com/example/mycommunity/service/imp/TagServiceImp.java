package com.example.mycommunity.service.imp;


import com.example.mycommunity.mapper.TagMapper;
import com.example.mycommunity.pojo.entity.Tag;
import com.example.mycommunity.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImp implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> findTopTenTags() {
        return tagMapper.listTopTenTag();
    }
}
