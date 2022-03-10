package com.springboot.online_bookstore_backend.service;


import com.springboot.online_bookstore_backend.controller.dto.UserMsgDto;
import com.springboot.online_bookstore_backend.domain.Role;
import com.springboot.online_bookstore_backend.domain.Users;

import java.util.List;

public interface UserService {
    /**
     * 登录业务逻辑
     * @param username 账户名
     * @param password 密码
     * @return
     */
    UserMsgDto loginService(String username, String password);

    /**
     * 注册业务逻辑
     * @param user 要注册的User对象，属性中主键uid要为空，若uid不为空可能会覆盖已存在的user
     * @return
     */
    Users registService(UserMsgDto user);

    /**
     * 模糊搜索用户名、昵称、邮箱
     * @param keyword
     * @return
     */
    List<Users> getUsersListService(String keyword);

    /**
     * 为用户分配角色
     * @param
     * @return
     */
    List<Role> addUsersRoleList(long user_id, List<Long> roleIdList);
}
