package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("replyDao")
public interface ReplyDao {
    void insert(Reply reply);
    List<Reply> findAllReplies(long commentId); //某评论的全部回复
}
