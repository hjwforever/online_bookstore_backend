package com.springboot.online_bookstore_backend.controller.dto;

import com.github.pagehelper.PageInfo;
import com.springboot.online_bookstore_backend.domain.Book_Category;
import com.springboot.online_bookstore_backend.domain.Book_Info;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchResultDto {
    List<Book_Category> currentCategoriesList = new ArrayList<>();
    Map<Long, List<Book_Category>> levelMap = new HashMap<>();
    PageInfo<Book_Info> searchResultPage;

    public List<Book_Category> getCurrentCategoriesList() {
        return currentCategoriesList;
    }

    public void setCurrentCategoriesList(List<Book_Category> currentCategoriesList) {
        this.currentCategoriesList = currentCategoriesList;
    }

    public Map<Long, List<Book_Category>> getLevelMap() {
        return levelMap;
    }

    public void setLevelMap(Map<Long, List<Book_Category>> levelMap) {
        this.levelMap = levelMap;
    }

    public PageInfo<Book_Info> getSearchResultPage() {
        return searchResultPage;
    }

    public void setSearchResultPage(PageInfo<Book_Info> searchResultPage) {
        this.searchResultPage = searchResultPage;
    }
}
