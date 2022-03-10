package com.springboot.online_bookstore_backend.repository;


import com.springboot.online_bookstore_backend.controller.dto.UserMsgDto;
import com.springboot.online_bookstore_backend.domain.Users;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("userDao")
public interface UserDao {
    Users findByUsername(String username); //通过用户名uname查找用户
    Users findByUserId(long user_id);//通过userId查找用户
    Users findByUsernameAndPassword(String username, String password);//通过用户名uname和密码查找用户
    void save(Users user);
    void updateEmail(long user_id, String email);
    void updatePassword(long user_id, String password);
    List<Users> findByEmail(String email);//查找该邮箱是否已经被注册
    //模糊搜索用户名、昵称、邮箱
    List<Users> findSpecificUserList(String keyword);
}
