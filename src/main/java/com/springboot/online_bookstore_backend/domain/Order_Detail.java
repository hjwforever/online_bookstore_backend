package com.springboot.online_bookstore_backend.domain;

public class Order_Detail {
    private long order_detail_id;

    private long book_id;

    private long order_id;

    private int amount;

    public long getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(long order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
