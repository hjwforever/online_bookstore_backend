package com.springboot.online_bookstore_backend.service;

import com.github.pagehelper.PageInfo;
import com.springboot.online_bookstore_backend.controller.dto.GeneratedOrderDto;
import com.springboot.online_bookstore_backend.controller.dto.OrderDto;
import com.springboot.online_bookstore_backend.domain.Orders;

import java.util.List;

public interface OrdersService {

    /**
     * 生成订单
     * @return
     */
    GeneratedOrderDto generateOrders(long user_id, OrderDto orderDto);


    /**
     * 管理员获取数据库所有订单
     * @return
     */
    List<Orders> findAllOrdersService();

    /**
     * 查询某用户所有订单
     * @param userId
     * @return
     */
    List<Orders> findUserAllOrdersService(long userId);

    /**
     * 查询用户某状态下所有订单
     * @param status
     * @param userId
     * @return
     */
    List<Orders> findUserStatusOrderService(int status, long userId);

    /**
     * 系统管理员查询某状态下所有订单
     * @param status
     * @return
     */
    List<Orders> findStatusOrderService(int status);

    /**
     * 根据关键字查询用户订单
     * @param keyword
     * @param userId
     * @return
     */
    List<Orders> findUserKeywordOrderService(String keyword, long userId);

    List<Orders> findKeywordOrderService(String keyword);

    /**
     * 在某订单状态下根据关键字查询用户订单
     * @param status
     * @param keyword
     * @param userId
     * @return
     */
    List<Orders> findUserKeywordStatusOrderService(int status, String keyword, long userId);


    /**
     * 系统管理员在某订单状态下根据关键字查询用户订单
     * @param status
     * @param keyword
     * @return
     */
    List<Orders> findKeywordStatusOrderService(int status, String keyword);

    /**
     * 更新订单状态
     * @param order_id
     * @param status
     */
    boolean updateOrderStatus(long order_id, int status);

    /**
     * 判断支付时间是否超时
     * @param order_id
     * @return
     */
    boolean isOrderPaymentDelay(long order_id);

    /**
     * 发货
     * @param order_id
     * @param shipping_name
     * @param shipping_code
     */
    void ship(long order_id, String shipping_name, String shipping_code);

    /**
     * 判断是否能发货
     * @param order_id
     */
    boolean canShip(long order_id);

    /**
     * 将订单列表转换为分页
     * @param ordersList
     * @return
     */
    PageInfo<GeneratedOrderDto> converListToPage(List<Orders> ordersList,int pageNum,int pageSize);

    /**
     * 获取用户的某个订单
     * @param user_id
     * @param order_id
     * @return
     */
    GeneratedOrderDto getUsersOrder(long user_id, long order_id);

}
