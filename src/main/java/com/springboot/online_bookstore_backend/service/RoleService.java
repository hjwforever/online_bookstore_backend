package com.springboot.online_bookstore_backend.service;

import com.springboot.online_bookstore_backend.controller.dto.RoleDto;
import com.springboot.online_bookstore_backend.domain.Role;

import java.util.List;

public interface RoleService {
    /**
     * 系统管理员获取角色列表
     * @return
     */

    List<Role> getAllRoles();

    String addRole(RoleDto newRole);

    String editRole(RoleDto newRole);
}
