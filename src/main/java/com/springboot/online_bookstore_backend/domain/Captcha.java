package com.springboot.online_bookstore_backend.domain;

import java.util.Date;

public class Captcha {
    private long captcha_id;

    private long user_id;

    private String keyword;

    private Date ddl_time;

    private String captcha;

    public long getCaptcha_id() {
        return captcha_id;
    }

    public void setCaptcha_id(long captcha_id) {
        this.captcha_id = captcha_id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Date getDdl_time() {
        return ddl_time;
    }

    public void setDdl_time(Date ddl_time) {
        this.ddl_time = ddl_time;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
