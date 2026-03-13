package com.nhnacademy.shoppingmall.category.service.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }
    @Override
    public void addCategory(String name) {
        categoryRepository.save(name);
    }
    @Override
    public void removeCategory(String name) {
        categoryRepository.delete(name);
    }
    @Override
    public void modifyCategory(String oldName, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("카테고리 이름은 비어있을 수 없습니다.");
        }
        if (categoryRepository.findAll().contains(newName)) {
            throw new IllegalStateException("이미 존재하는 카테고리 이름입니다.");
        }

        categoryRepository.update(oldName, newName);
    }
}