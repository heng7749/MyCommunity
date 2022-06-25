package com.example.mycommunity.controller;

import com.example.mycommunity.response.CommonResponse;
import com.example.mycommunity.service.imp.LikeServiceImp;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeController {
    @Autowired
    private LikeServiceImp likeServiceImp;

    @PostMapping("like/addLike")
    @RequiresAuthentication
    public CommonResponse addLike(@RequestParam("entityType") int entityType, @RequestParam("entityId") int entityId) {
        int userId = (int) SecurityUtils.getSubject().getSession().getAttribute("userId");
        likeServiceImp.like(entityType,entityId,userId);
        return new CommonResponse();
    }
}
