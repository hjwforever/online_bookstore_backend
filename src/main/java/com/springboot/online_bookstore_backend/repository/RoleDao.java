package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("roleDao")
public interface RoleDao {
    void insertRole(Role role);
    void deleteRole(long role_id);
    void update(Role role);
    long findRoleIdByRolename(String rolename);
    Role findById(long role_id);
    Role findByRoleName(String rolename);
    List<Role> findAllRole();
}
