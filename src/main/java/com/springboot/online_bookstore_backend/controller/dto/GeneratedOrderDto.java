package com.springboot.online_bookstore_backend.controller.dto;

import com.springboot.online_bookstore_backend.domain.Orders;

import java.util.List;

public class GeneratedOrderDto {
    private Orders initOrder;
    private List<OrderItemDto> orderItemList;

    public Orders getInitOrder() {
        return initOrder;
    }

    public void setInitOrder(Orders initOrder) {
        this.initOrder = initOrder;
    }

    public List<OrderItemDto> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<OrderItemDto> orderItemList) {
        this.orderItemList = orderItemList;
    }
}
