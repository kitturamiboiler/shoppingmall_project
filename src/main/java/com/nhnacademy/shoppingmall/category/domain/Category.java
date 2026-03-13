package com.nhnacademy.shoppingmall.category.domain;

import lombok.Getter;

@Getter
public class Category {
    private final Integer categoryId;
    private final String categoryName;

    public Category(Integer categoryId, String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("카테고리 이름 필수");
        }
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}