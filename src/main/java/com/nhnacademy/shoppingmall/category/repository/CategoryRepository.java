package com.nhnacademy.shoppingmall.category.repository;

import com.nhnacademy.shoppingmall.category.domain.Category;

import java.util.List;

public interface CategoryRepository {
    List<Category> findAll(); // 모든 카테고리 이름 조회
    int save(String categoryName); // 신규 카테고리 저장
    int delete(String categoryName); // 카테고리 삭제
    int update(String oldName, String newName);
}