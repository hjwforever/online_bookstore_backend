package com.springboot.online_bookstore_backend.service;

import com.springboot.online_bookstore_backend.domain.Book_Info;
import com.springboot.online_bookstore_backend.domain.UserRecommend;

import java.util.List;

public interface UserRecommendService {
    /**
     * 获取用户的推荐书籍
     * @param user_id
     * @return
     */
    List<Book_Info> getUserRecommendsList(long user_id, int bookNum);
}
