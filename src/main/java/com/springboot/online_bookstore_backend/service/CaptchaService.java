package com.springboot.online_bookstore_backend.service;

import java.util.Date;

public interface CaptchaService {

    String insertCaptchaService(long userId, String key, Date ddl_time, String captcha);

    boolean registerCaptchaService(String email);//注册时发送验证码

    boolean registerValidateCaptcha(String email,String captcha);//注册完成时验证码是否正确


    String passwordChangeService(String password,String captcha,long userId);

    String insertEmail(String email,String captcha,long userId);
}
