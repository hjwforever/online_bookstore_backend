package com.springboot.online_bookstore_backend.domain;

public class Book_Category {
    private long cate_id;

    private long parent_id;

    private String catename;

    private long look_amount;

    private boolean is_parent;

    public long getCate_id() {
        return cate_id;
    }

    public void setCate_id(long cate_id) {
        this.cate_id = cate_id;
    }

    public long getParent_id() {
        return parent_id;
    }

    public void setParent_id(long parent_id) {
        this.parent_id = parent_id;
    }

    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }

    public long getLook_amount() {
        return look_amount;
    }

    public void setLook_amount(long look_amount) {
        this.look_amount = look_amount;
    }

    public boolean isIs_parent() {
        return is_parent;
    }

    public void setIs_parent(boolean is_parent) {
        this.is_parent = is_parent;
    }
}
