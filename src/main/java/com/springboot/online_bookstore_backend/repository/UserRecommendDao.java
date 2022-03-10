package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.UserRecommend;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("userRecommendDao")
public interface UserRecommendDao {
    List<UserRecommend> findByUserId(long user_id, int bookNum);
}
