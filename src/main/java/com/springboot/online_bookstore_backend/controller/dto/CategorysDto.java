package com.springboot.online_bookstore_backend.controller.dto;

import com.springboot.online_bookstore_backend.domain.Book_Category;

import java.util.ArrayList;
import java.util.List;

public class CategorysDto {
    private long cate_id;
    private long parent_id;
    private String catename;
    private long look_amount;
    private boolean is_parent;

    List<CategorysDto> children = new ArrayList<>();

    public CategorysDto(Book_Category bookCategory){
        this.cate_id = bookCategory.getCate_id();
        this.parent_id = bookCategory.getParent_id();
        this.catename = bookCategory.getCatename();
        this.look_amount = bookCategory.getLook_amount();
        this.is_parent = bookCategory.isIs_parent();
    }

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

    public List<CategorysDto> getChildren() {
        return children;
    }

    public void setChildren(List<CategorysDto> children) {
        this.children = children;
    }
}
