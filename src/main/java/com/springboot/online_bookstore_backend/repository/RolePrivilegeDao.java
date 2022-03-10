package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.RolePrivilege;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("rolePrivilegeDao")
public interface RolePrivilegeDao {
    void insertRolePrivilege(RolePrivilege rolePrivilege);
    void deleteRolePrivilege(long role_id);
    List<RolePrivilege> findByRoleId(long roleid);
}
