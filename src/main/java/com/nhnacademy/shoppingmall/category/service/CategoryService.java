package com.nhnacademy.shoppingmall.category.service;

import com.nhnacademy.shoppingmall.category.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategoryList();
    void addCategory(String name);
    void removeCategory(String name);
    void modifyCategory(String oldName, String newName);
}