package com.example.mycommunity.service.imp;

import com.example.mycommunity.mapper.CommentMapper;
import com.example.mycommunity.pojo.dto.CommentReplyDTO;
import com.example.mycommunity.pojo.entity.Comment;
import com.example.mycommunity.pojo.entity.CommentReply;
import com.example.mycommunity.pojo.vo.CommentVO;
import com.example.mycommunity.service.CommentService;
import com.example.mycommunity.utils.RedisConstants;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<CommentVO> findComments(int topicId) {
        return commentMapper.listTopicComment(topicId);
    }

    @Override
    public boolean addComment(int topicId, String content) {
        Session session = SecurityUtils.getSubject().getSession();
        Comment comment = Comment.builder()
                .userId((int) session.getAttribute("userId"))
                .userName((String) session.getAttribute("userName"))
                .avatar((String) session.getAttribute("avatar"))
                .topicId(topicId)
                .content(content)
                .createTime(new Date()).build();
        // 更新文章评论数量
        updateTopicCommentCount(topicId);
        return commentMapper.addComment(comment);
    }

    // 评论回复
    @Override
    public boolean addCommentReply(CommentReplyDTO dto) {
        boolean isSuccess = false;
        Integer topicId = null;
        CommentReply commentReply = new CommentReply();
        // 对一级评论的回复
        if (dto.getType() == 1) {
            Comment c = commentMapper.getCommentById(dto.getCommentId());
            if (c != null) {
                commentReply.setCommentId(c.getId());
                topicId = c.getTopicId();
                isSuccess = true;
            }
        } else {
            CommentReply c = commentMapper.getCommentReplyById(dto.getCommentId());
            if (c != null) {
                topicId = c.getTopicId();
                commentReply.setCommentId(c.getCommentId());
                commentReply.setReplyUserId(c.getUserId());
                commentReply.setReplyUserName(c.getUserName());
                // 自关联Id,用于判断是否为三级评论（当此id为空时为二级评论，否则为三级及以上评论）
                commentReply.setCommentReplyId(c.getId());
                isSuccess = true;
            }
        }
        // 传来的评论id有效时执行
        if (isSuccess) {
            Session session = SecurityUtils.getSubject().getSession();
            commentReply.setUserId((Integer) session.getAttribute("userId"));
            commentReply.setUserName((String) session.getAttribute("userName"));
            commentReply.setAvatar((String) session.getAttribute("avatar"));
            commentReply.setContent(dto.getContent());
            commentReply.setCreateTime(new Date());
            commentReply.setTopicId(topicId);

            isSuccess = commentMapper.addCommentReply(commentReply);
            // 更新 topic 的评论数量
            updateTopicCommentCount(topicId);
        }
        return isSuccess;
    }

    @Override
    public List<CommentVO> findCommentChildren(int commentId) {
        return commentMapper.listCommentReply(commentId);
    }

    @Override
    public boolean updateTopicCommentCount(int topicId) {
        try {
            redisTemplate.opsForHash().increment(RedisConstants.TOPIC_COMMENT_COUNT_KEY, Integer.toString(topicId),1);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
