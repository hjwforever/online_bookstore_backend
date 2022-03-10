package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.Book_Info;
import com.springboot.online_bookstore_backend.domain.Order_Detail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Book;
import java.util.List;

@Mapper
@Repository("book_infoDao")
public interface Book_InfoDao {
    Boolean insertBook(Book_Info bookInfo);
    Book_Info findBookInfo(long book_id);
    List<Book_Info> findAllBookInfo();
    List<Long> fuzzyFindBookName(String keyword);
    List<Book_Info> selectBooksRandomly(int bookNum);
    List<Book_Info> selectTopBooksByCategory(long cate_id, int topNum);
    void increaseLookAmount(long book_id);

    List<Book_Info> complexQuery(String keyword, @Param("cateIdList") List<Long> cateIdList, double lowPrice, double highPrice);
    Boolean changeStoreAmountToZero(long book_id);
    List<String> findIsbnList();
    List<Book_Info> findByOrderDetailList(@Param("orderDetailList") List<Order_Detail> orderDetailList);

    void increaseStoreAmount(long book_id, int increaseNum);
    void decreaseStoreAmount(long book_id, int decreaseNum);
}
