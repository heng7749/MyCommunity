package com.example.mycommunity.service;

import com.example.mycommunity.pojo.dto.CommentReplyDTO;
import com.example.mycommunity.pojo.vo.CommentVO;

import java.util.List;

public interface CommentService {
    List<CommentVO> findComments(int topicId);
    boolean addComment(int topicId,String content);

    boolean addCommentReply(CommentReplyDTO dto);

    List<CommentVO> findCommentChildren(int commentId);

    boolean updateTopicCommentCount(int topicId);
}
