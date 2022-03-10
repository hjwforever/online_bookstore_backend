package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("userRoleDao")
public interface UserRoleDao {
    Boolean insertUserRole(@Param("userRoleList") List<UserRole> userRole);
    List<UserRole> findByUserid(long userid);
    List<UserRole> findAllUserRole();
    void deleteUserRoleByUserId(long user_id);
}
