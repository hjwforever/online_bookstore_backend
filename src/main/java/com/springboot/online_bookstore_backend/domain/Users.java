package com.springboot.online_bookstore_backend.domain;

import com.springboot.online_bookstore_backend.annotation.CreateTime;
import com.springboot.online_bookstore_backend.annotation.UpdateTime;

import java.sql.Timestamp;

public class Users {
    private long user_id;

    // 用户名属性varchar对应String
    private String username;

    private String nickname;

    // 密码属性varchar对应String
    private String password;

    private boolean gender;

    private String email;

    private int age;

    private boolean active;

    //自动更新时间
    @UpdateTime
    private Timestamp updated;

    //自动创建
    @CreateTime
    private Timestamp created;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Timestamp getUpdated() {
        return updated;
    }

    public void setUpdated(Timestamp updated) {
        this.updated = updated;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }
}
