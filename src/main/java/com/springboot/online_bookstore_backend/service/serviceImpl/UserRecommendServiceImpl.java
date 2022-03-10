package com.springboot.online_bookstore_backend.service.serviceImpl;

import com.springboot.online_bookstore_backend.domain.Book_Info;
import com.springboot.online_bookstore_backend.domain.UserRecommend;
import com.springboot.online_bookstore_backend.repository.Book_InfoDao;
import com.springboot.online_bookstore_backend.repository.UserRecommendDao;
import com.springboot.online_bookstore_backend.service.UserRecommendService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserRecommendServiceImpl implements UserRecommendService {
    @Resource
    private UserRecommendDao userRecommendDao;

    @Resource
    private Book_InfoDao bookInfoDao;

    @Override
    public List<Book_Info> getUserRecommendsList(long user_id, int bookNum) {
        List<UserRecommend> userRecommendList = userRecommendDao.findByUserId(user_id,bookNum);
        List<Book_Info> resultList = new ArrayList<>();
        for (UserRecommend userRecommend : userRecommendList){
            resultList.add(bookInfoDao.findBookInfo(userRecommend.getBook_id()));
        }
        return resultList;
    }
}
