package com.nhnacademy.shoppingmall.product.repository;

import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.List;

public interface ProductRepository {
    // 상품 등록
    int save(Product product);

    // 상품 조회
    Product findById(int id);

    List<Product> findAllCategory(String category, int offset, int limit);
    // 카테고리 별 상품 검색
    List<String> getProductsByCategory();
    // 제목 검색
    List<Product> getProductsByTitle(String title, int offset, int limit);
    List<Product> findAll(int offset, int limit);
    int countAll();
    void updateStock(int productId, int quantity);

    int update(Product product);
    int deleteById(int id);
}
