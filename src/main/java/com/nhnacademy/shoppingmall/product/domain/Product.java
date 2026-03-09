package com.nhnacademy.shoppingmall.product.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode
public class Product {
    private final Integer id;             // id
    private final String category;        // category
    private final String title;           // title (상품명)
    private final int price;              // price
    private final int quantity;           // quantity
    private final String ean;             // ean (바코드/상품식별자)
    private final double rating;          // rating (평점)
    private final String vendor;          // vendor (제조사)
    private final LocalDateTime createdAt;// created_at

    public Product(String category, String title, int price, int quantity, String ean, String vendor) {
        this(null, category, title, price, quantity, ean, 0.0, vendor, LocalDateTime.now());
    }
    public Product(Integer id, String category, String title, int price, int quantity,
                   String ean, double rating, String vendor, LocalDateTime createdAt) {

        if (category == null || category.trim().isEmpty()) {
            throw new IllegalArgumentException("상품은 반드시 하나의 카테고리에 속해야 합니다.");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("상품명(title)은 필수입니다.");
        }
        if (price < 0 || quantity < 0) {
            throw new IllegalArgumentException("가격과 재고(quantity)는 음수일 수 없습니다.");
        }

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

    public String getResolvedImageUrl() {
        return "/resources/images/products/" + this.id + ".jpg";
    }
}