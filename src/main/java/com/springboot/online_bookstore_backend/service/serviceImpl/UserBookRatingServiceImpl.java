package com.springboot.online_bookstore_backend.service.serviceImpl;

import com.springboot.online_bookstore_backend.domain.User_Book_Ratings;
import com.springboot.online_bookstore_backend.repository.User_Book_RatingsDao;
import com.springboot.online_bookstore_backend.service.UserBookRatingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserBookRatingServiceImpl implements UserBookRatingService {
    @Resource
    private User_Book_RatingsDao userBookRatingsDao;

    @Override
    public void insertUserBookRate(User_Book_Ratings userBookRatings) {
        if(userBookRatings.getRate() == -1){
            return ;
        }
        userBookRatingsDao.insert(userBookRatings);
    }
}
