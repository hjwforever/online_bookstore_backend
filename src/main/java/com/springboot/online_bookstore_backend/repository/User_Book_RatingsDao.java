package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.User_Book_Ratings;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository("user_book_ratingsDao")
public interface User_Book_RatingsDao {
    void insert(User_Book_Ratings userBookRatings);
}
