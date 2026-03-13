package com.nhnacademy.shoppingmall.product.domain;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class Product {
    private Integer id;
    private Integer categoryId;

    private final String title;
    private final int price;
    private final int quantity;
    private final String ean;
    private final double rating;
    private final String vendor;
    private final LocalDateTime createdAt;

    @Builder
    public Product(Integer categoryId, String title, int price, int quantity, String ean, String vendor) {
        this(null, categoryId, title, price, quantity, ean, 0.0, vendor, LocalDateTime.now());
    }

    public Product(Integer id, Integer categoryId, String title, int price, int quantity,
                   String ean, double rating, String vendor, LocalDateTime createdAt) {

        validate(categoryId, title, price, quantity);

        this.id = id;
        this.categoryId = categoryId;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.ean = ean;
        this.rating = rating;
        this.vendor = vendor;
        this.createdAt = createdAt;
    }

    private void validate(Integer categoryId, String title, int price, int quantity) {
        if (categoryId == null || categoryId <= 0) {
            throw new IllegalArgumentException("상품은 반드시 하나의 카테고리에 속해야 합니다.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("상품명(title)은 필수입니다.");
        }
        if (price < 0 || quantity < 0) {
            throw new IllegalArgumentException("가격과 재고(quantity)는 음수일 수 없습니다.");
        }
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResolvedImageUrl() {
        return "/resources/images/products/" + this.id + ".jpeg";
    }
}