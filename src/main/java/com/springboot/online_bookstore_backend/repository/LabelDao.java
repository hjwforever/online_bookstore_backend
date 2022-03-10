package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.Label;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("labelDao")
public interface LabelDao {
    void insert(Label label);

}
