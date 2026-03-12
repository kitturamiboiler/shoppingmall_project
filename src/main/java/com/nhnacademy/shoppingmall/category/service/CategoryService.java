package com.nhnacademy.shoppingmall.category.service;

import java.util.List;

public interface CategoryService {
    List<String> getCategoryList();
    void addCategory(String name);
    void removeCategory(String name);
    void modifyCategory(String oldName, String newName);
}