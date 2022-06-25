package com.example.mycommunity.controller;

import com.example.mycommunity.pojo.dto.TopicPublishDTO;
import com.example.mycommunity.pojo.entity.Topic;
import com.example.mycommunity.pojo.vo.CommentVO;
import com.example.mycommunity.pojo.vo.TopicDetailVO;
import com.example.mycommunity.response.CommonResponse;
import com.example.mycommunity.response.ResponseCode;
import com.example.mycommunity.service.imp.CommentServiceImp;
import com.example.mycommunity.service.imp.CollectServiceImp;
import com.example.mycommunity.service.imp.LikeServiceImp;
import com.example.mycommunity.service.imp.TopicServiceImp;
import com.example.mycommunity.utils.CollectConstants;
import com.example.mycommunity.utils.LikeConstants;
import com.example.mycommunity.utils.RedisConstants;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("topic")
public class TopicController {
    private static final int PAGE_SIZE = 2;
    private static final int COMMENTS_SIZE = 2;

    @Autowired
    private TopicServiceImp topicServiceImp;
    @Autowired
    private CommentServiceImp commentServiceImp;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private LikeServiceImp likeServiceImp;
    @Autowired
    private CollectServiceImp collectServiceImp;

    @PostMapping("publishTopic")
    @RequiresAuthentication
    public CommonResponse publishDiscuss(@Validated @RequestBody TopicPublishDTO dto) {
        CommonResponse commonResponse = new CommonResponse();
        if (topicServiceImp.addTopic(dto)) {
            commonResponse.setStatus(ResponseCode.SUCCESS.getCode());
            commonResponse.setMsg("创建成功");
        } else {
            commonResponse.setStatus(ResponseCode.ERROR.getCode());
            commonResponse.setMsg("创建失败");
        }
        return commonResponse;
    }

    @GetMapping("recent")
    public CommonResponse getRecentTopic(@RequestParam("currentPage") int currentPage){
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatus(ResponseCode.SUCCESS.getCode());
        // 获取话题
        Page<Topic> page = PageHelper.startPage(currentPage, PAGE_SIZE);
        topicServiceImp.getRecentTopic();
        PageInfo<Topic> pageInfo = page.toPageInfo();
        commonResponse.setData(pageInfo);
        return commonResponse;
    }

    @GetMapping("topicDetail")
    public CommonResponse getTopicDetail(@RequestParam("topicId") int topicId) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.setStatus(ResponseCode.SUCCESS.getCode());
        commonResponse.setMsg("获取成功");
        // 封装返回值
        TopicDetailVO detailVO = new TopicDetailVO();
        Topic topic = topicServiceImp.getTopicById(topicId);
        detailVO.setTopic(topic);
        Integer userId = (Integer) SecurityUtils.getSubject().getSession().getAttribute("userId");
        if (topic != null) {
            // 更新阅读量
            redisTemplate.opsForHash().increment(RedisConstants.TOPIC_VIEW_COUNT_KEY,Integer.toString(topicId),1);
            if (userId != null) {
                // 用户是否点赞
                detailVO.setHadLike(likeServiceImp.isEntityHadLike(LikeConstants.TOPIC_LIKE,topicId,userId));
                // 用户是否收藏
                detailVO.setHadFavour(collectServiceImp.isEntityHadCollect(CollectConstants.TOPIC_COLLECT,topicId,userId));
            } else {
                detailVO.setHadLike(false);
            }


        }

        // 获取一级评论
        Page<CommentVO> page = PageHelper.startPage(1, COMMENTS_SIZE);
        List<CommentVO> commentList = commentServiceImp.findComments(topicId);
        commentList.forEach(comment -> comment.setChildren(commentServiceImp.findCommentChildren(comment.getId())));


        detailVO.setPageInfo(page.toPageInfo());
        commonResponse.setData(detailVO);
        return commonResponse;
    }
}
