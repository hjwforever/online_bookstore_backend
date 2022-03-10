package com.springboot.online_bookstore_backend.domain;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Book_Info {
    private long book_id;

    private long cate_id;

    private String bookname;

    private String press;

    private String author;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publish_date;

    private String isbn;

    private double price;

    private int pages;

    private long deal_amount;

    private long look_amount;

    private long store_amount;

    private String s_image;

    private String b_image;

    private String description;

    private double rate;

    private long rate_num;

    public long getBook_id() {
        return book_id;
    }

    public void setBook_id(long book_id) {
        this.book_id = book_id;
    }

    public long getCate_id() {
        return cate_id;
    }

    public void setCate_id(long cate_id) {
        this.cate_id = cate_id;
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

    public Date getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(Date publish_date) {
        this.publish_date = publish_date;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public long getDeal_amount() {
        return deal_amount;
    }

    public void setDeal_amount(long deal_amount) {
        this.deal_amount = deal_amount;
    }

    public long getLook_amount() {
        return look_amount;
    }

    public void setLook_amount(long look_amount) {
        this.look_amount = look_amount;
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

    public String getB_image() {
        return b_image;
    }

    public void setB_image(String b_image) {
        this.b_image = b_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public long getRate_num() {
        return rate_num;
    }

    public void setRate_num(long rate_num) {
        this.rate_num = rate_num;
    }
}
