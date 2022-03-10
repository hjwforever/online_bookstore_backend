package com.springboot.online_bookstore_backend.controller.dto;

import com.springboot.online_bookstore_backend.domain.Book_Info;
import com.springboot.online_bookstore_backend.domain.Orders;

public class OrderItemDto {
    private long book_id;

    private int amount;

    private String bookname;

    private String press;

    private String author;

    private double price;

    private long store_amount;

    private String s_image;

    public OrderItemDto(Book_Info bookInfo){
        this.book_id = bookInfo.getBook_id();
        this.bookname = bookInfo.getBookname();
        this.press = bookInfo.getPress();
        this.price = bookInfo.getPrice();
        this.store_amount = bookInfo.getStore_amount();
        this.s_image = bookInfo.getS_image();
    }

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getPress() {
        return press;
    }

    public void setPress(String press) {
        this.press = press;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getStore_amount() {
        return store_amount;
    }

    public void setStore_amount(long store_amount) {
        this.store_amount = store_amount;
    }

    public String getS_image() {
        return s_image;
    }

    public void setS_image(String s_image) {
        this.s_image = s_image;
    }
}
