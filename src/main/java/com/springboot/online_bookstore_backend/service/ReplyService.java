package com.springboot.online_bookstore_backend.service;

import com.springboot.online_bookstore_backend.domain.Reply;

import java.util.List;

public interface ReplyService {
    /**
     * 新增回复
     * @param reply
     * @return
     */
    List<Reply> insertReplyService(Reply reply);
}
