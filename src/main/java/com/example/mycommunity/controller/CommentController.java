package com.example.mycommunity.controller;

import com.example.mycommunity.pojo.dto.CommentReplyDTO;
import com.example.mycommunity.response.CommonResponse;
import com.example.mycommunity.response.ResponseCode;
import com.example.mycommunity.service.imp.CommentServiceImp;
import com.example.mycommunity.service.imp.TopicServiceImp;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment")
public class CommentController {
    @Autowired
    private CommentServiceImp commentServiceImp;
    @Autowired
    private TopicServiceImp topicServiceImp;

    @PostMapping("addComment")
    @RequiresAuthentication
    public CommonResponse addComment(@RequestParam("topicId") int topicId, @RequestParam("content") String content){
        CommonResponse commonResponse = new CommonResponse();
        // 判断文章是否存在
        if (topicServiceImp.isTopicExist(topicId)) {
            if (commentServiceImp.addComment(topicId,content)) {
                commonResponse.setStatus(ResponseCode.SUCCESS.getCode());
                commonResponse.setMsg("评论成功");
            }
        } else {
            commonResponse.setStatus(ResponseCode.ERROR.getCode());
            commonResponse.setMsg("评论失败");
        }

        return commonResponse;
    }

    @PostMapping("addCommentReply")
    @RequiresAuthentication
    public CommonResponse addCommentReply(@Validated @RequestBody CommentReplyDTO dto){
        CommonResponse commonResponse = new CommonResponse();
        if (commentServiceImp.addCommentReply(dto)) {
            commonResponse.setStatus(ResponseCode.SUCCESS.getCode());
            commonResponse.setMsg("评论成功");
        } else {
            commonResponse.setStatus(ResponseCode.ERROR.getCode());
            commonResponse.setMsg("评论失败");
        }
        return commonResponse;
    }
}
