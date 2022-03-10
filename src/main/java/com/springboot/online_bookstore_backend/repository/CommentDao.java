package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("commentDao")
public interface CommentDao {
    void insert(Comment comment);
    List<Comment> findBookComment(long bookId);//查询某书的所有评论
}
