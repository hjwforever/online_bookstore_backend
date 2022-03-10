package com.springboot.online_bookstore_backend.service;

import com.springboot.online_bookstore_backend.controller.dto.CommentReplyDto;
import com.springboot.online_bookstore_backend.domain.Comment;

import java.util.List;

public interface CommentService {

    /**
     * 插入一条comment
     * @param comment
     * @return
     */
    List<Comment> insertCommentService(Comment comment);

    /**
     * 根据前端返回的book_id把本书的评论及回复发送至前端
     * @param bookId
     * @param user_id
     * @param nickname
     * @return
     */
    List<CommentReplyDto> findAllByBookId(long bookId);
}
