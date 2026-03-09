package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.List;

public interface ProductRepository {
    // 상품 등록
    int save(Product product);

    // 상품 조회
    Product findById(int id);

     List<Product> findAll(int offset, int limit);
     int countAll();
}
