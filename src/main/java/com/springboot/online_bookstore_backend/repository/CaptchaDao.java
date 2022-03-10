package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.Captcha;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Mapper
@Repository("captchaDao")
public interface CaptchaDao {
    void insert(Captcha record);
    void registerInsert(Captcha record);

    void update(Captcha record);//修改邮箱时用到的update
    void updatePasswordCaptcha(Captcha record);//修改密码时用到的update
    void registerUpdate(String captcha, Date ddl_time, String keyword);

    Captcha findByUserId(long user_id);
    Captcha findByUserIdForPasswordChange(long user_id);
    Captcha findByEmail(String email);

    void emailDelete(long user_id);
    void passwordDelete(long user_id);

}
