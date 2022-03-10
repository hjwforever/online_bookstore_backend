package com.springboot.online_bookstore_backend.repository;


import com.springboot.online_bookstore_backend.domain.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository("ordersDao")
public interface OrdersDao {
    void insertOrder(Orders order);
    void updateStatus(long order_id, int status);
    void updateShippingMsg(long order_id, String shipping_name, String shipping_code);
    List<Orders> findAll();//管理员获取数据库所有订单
    List<Orders> findByUserId(long user_id);//获取某用户所有订单
    Orders findByUserIdAndOrderId(long user_id,long order_id);//获取当前用户在orderlist中的order对象
    Orders findByOrderId(long order_id);//系统管理员获取在orderlist中的order对象
    Orders findByUserIdAndOrderIdAndStatus(long user_id,long order_id,int status);
    Orders findByOrderIdAndStatus(long order_id,int status);//系统管理员
    List<Orders> findByUserIdAndStatus(int status, long user_id);//获取用户某状态下所有订单
    List<Orders> findByStatus(int status);//系统管理员获取某状态下订单
    List<Orders> findByUserIdAndKeyword(long user_id, String keyword);//根据关键字在订单号中查询获取用户所有状态下订单
    List<Orders> findByKeyword(String keyword);//系统管理员根据关键字在订单号中查询获取用户所有状态下订单
    List<Orders> findByUserIdAndKeywordAndStatus(long user_id, String keyword, int status);//根据关键字在订单号中查询获取用户某状态下订单
    List<Orders> findByKeywordAndStatus(String keyword, int status);//系统管理员根据关键字在订单号中查询获取某状态下订单
}
