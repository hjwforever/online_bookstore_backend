package com.springboot.online_bookstore_backend.domain;

import com.springboot.online_bookstore_backend.annotation.CreateTime;

import java.sql.Timestamp;
import java.util.Date;

public class Comment {
    private long comment_id;

    private long book_id;

    private long user_id;

    private String nickname;

    //private Date date;

    //自动创建
    @CreateTime
    private Timestamp date;

    private String content;

    public long getComment_id() {
        return comment_id;
    }

    public void setComment_id(long comment_id) {
        this.comment_id = comment_id;
    }

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
