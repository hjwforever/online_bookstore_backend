package com.springboot.online_bookstore_backend.repository;

import com.springboot.online_bookstore_backend.domain.Shopping_Cart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("shopping_cartDao")
public interface Shopping_CartDao {
    //添加商品到购物车数据库
    boolean insertBook(Shopping_Cart shoppingCart);
    //从购物车数据库删除商品
    boolean deleteBook(long user_id, @Param("bookidlist")List<Long> book_id);
    //在购物车数据库中调整某个商品的数量
    boolean changeAmount(long user_id, long book_id, int amount);
    //查找购物车数据库中的一个商品的数量
    Integer findBookAmount(long user_id, long book_id);
    //获取购物车数据库商品列表
    List<Shopping_Cart> findAllShoppingCard(long user_id);
}
