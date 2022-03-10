package com.springboot.online_bookstore_backend.service;

import com.springboot.online_bookstore_backend.controller.dto.ShoppingCartDto;
import com.springboot.online_bookstore_backend.domain.Shopping_Cart;

import java.util.List;


public interface ShoppingCartService {
    /**
     * 获取购物车商品列表
     * @param user_id
     * @return
     */
    List<ShoppingCartDto> showShoppingCartListService(long user_id);

    /**
     * 添加商品到购物车
     * @param user_id
     * @param book_id
     */
    Shopping_Cart addBookToShoppingCartService(long user_id, long book_id, int amount);

    /**
     * 在购物车中调整某个商品的数量
     * @param user_id
     * @param book_id
     * @param amount
     */
    Shopping_Cart changeAmountFromShoppingCartService(long user_id, long book_id, int amount);

    /**
     *从购物车删除商品
     * @param user_id
     * @param book_id
     */
    Boolean deleteBookFromShoppingCartService(long user_id, List<Long> book_id);
}
