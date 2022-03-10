package com.springboot.online_bookstore_backend.domain;

import com.springboot.online_bookstore_backend.annotation.CreateTime;

import java.sql.Timestamp;

public class User_Book_Ratings {
    private long id;
    private long user_id;
    private long book_id;
    private int rate;
    @CreateTime
    private Timestamp time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
