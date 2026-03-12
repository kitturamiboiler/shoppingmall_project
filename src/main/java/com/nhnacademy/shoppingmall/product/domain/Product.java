package com.nhnacademy.shoppingmall.product.domain;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class Product {
    private Integer id;
    private final String category;
    private final String title;
    private final int price;
    private final int quantity;
    private final String ean;
    private final double rating;
    private final String vendor;
    private final LocalDateTime createdAt;
    @Builder
    public Product(String category, String title, int price, int quantity, String ean, String vendor) {
        this(null, category, title, price, quantity, ean, 0.0, vendor, LocalDateTime.now());
    }

    public Product(Integer id, String category, String title, int price, int quantity,
                   String ean, double rating, String vendor, LocalDateTime createdAt) {

        validate(category, title, price, quantity);

        this.id = id;
        this.category = category;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.ean = ean;
        this.rating = rating;
        this.vendor = vendor;
        this.createdAt = createdAt;
    }

    private void validate(String category, String title, int price, int quantity) {
        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("상품은 반드시 하나의 카테고리에 속해야 합니다.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("상품명(title)은 필수입니다.");
        }
        if (price < 0 || quantity < 0) {
            throw new IllegalArgumentException("가격과 재고(quantity)는 음수일 수 없습니다.");
        }
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public String getResolvedImageUrl() {
        return "/resources/images/products/" + this.id + ".jpg";
    }
}