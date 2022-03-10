package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.Privilege;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("privilegeDao")
public interface PrivilegeDao {
    List<Privilege> selectAll();
    Privilege findById(long priv_id);
}
