package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.Book_Category;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("book_categoryDao")
public interface Book_CategoryDao {
    void insert(Book_Category bookCategory);
    List<Book_Category> findAll();
    List<Book_Category> selectTopCategories(int topNum);
    Book_Category findByCateId(long cate_id);
    void increaseCategoryLookAmount(long cate_id);
    List<Book_Category> findByParentId(long parent_id);
    Boolean addbook_category(Book_Category bookCategory);
    List<String> findAllCatename();
    List<Book_Category> findAllLeafCategories();
}
