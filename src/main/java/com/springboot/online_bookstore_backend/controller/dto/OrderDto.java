package com.springboot.online_bookstore_backend.controller.dto;

import com.springboot.online_bookstore_backend.domain.Order_Detail;

import java.util.ArrayList;
import java.util.List;

public class OrderDto {
    private int payment_type = 0;
    private String buyer_message = "";
    private long addr_id;
    private List<Order_Detail> orderDetailList = new ArrayList<>();

    public int getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(int payment_type) {
        this.payment_type = payment_type;
    }

    public String getBuyer_message() {
        return buyer_message;
    }

    public void setBuyer_message(String buyer_message) {
        this.buyer_message = buyer_message;
    }

    public long getAddr_id() {
        return addr_id;
    }

    public void setAddr_id(long addr_id) {
        this.addr_id = addr_id;
    }

    public List<Order_Detail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<Order_Detail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
