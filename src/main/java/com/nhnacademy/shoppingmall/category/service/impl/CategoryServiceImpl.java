package com.nhnacademy.shoppingmall.category.service.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Category> getCategoryList() {
        return categoryRepository.findAll();
    }
    @Override
    @Transactional
    public void addCategory(String name) {
        categoryRepository.save(name);
    }
    @Override
    @Transactional
    public void removeCategory(String name) {
        categoryRepository.delete(name);
    }
    @Override
    @Transactional
    public void modifyCategory(String oldName, String newName) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("카테고리 이름은 비어있을수 없습니다.");
        }
        boolean isDuplicate = categoryRepository.findAll().stream()
                .anyMatch(category -> category.getCategoryName().equals(newName));

        if (isDuplicate){
            throw new IllegalStateException("이미 존재하는 카테고리 이름입니다.");
        }
        categoryRepository.update(oldName, newName);
    }
}