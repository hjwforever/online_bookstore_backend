package com.springboot.online_bookstore_backend.controller.dto;

import com.springboot.online_bookstore_backend.domain.Book_Info;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomePageDto {
    private List<CategorysDto> categorysDto;
    private List<Book_Info> recommandBooks;
    private Map<String, List<Book_Info>> popularCategories;

    public HomePageDto(List<CategorysDto> categorysDto, List<Book_Info> recommandBooks, Map<String, List<Book_Info>> popularCategories){
        this.categorysDto = categorysDto;
        this.recommandBooks = recommandBooks;
        this.popularCategories = popularCategories;
    }

    public List<CategorysDto> getCategorysDto() {
        return categorysDto;
    }

    public void setCategorysDto(List<CategorysDto> categorysDto) {
        this.categorysDto = categorysDto;
    }

    public List<Book_Info> getRecommandBooks() {
        return recommandBooks;
    }

    public void setRecommandBooks(List<Book_Info> recommandBooks) {
        this.recommandBooks = recommandBooks;
    }

    public Map<String, List<Book_Info>> getPopularCategories() {
        return popularCategories;
    }

    public void setPopularCategories(Map<String, List<Book_Info>> popularCategories) {
        this.popularCategories = popularCategories;
    }
}
