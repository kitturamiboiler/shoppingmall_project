package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.util.List;

public interface ProductService {
    Product getProduct(int id);
    Page<Product> getProductPage(int page);
    void saveProduct(Product product);
    List<Product> getProductsByCategory(String category, int offset, int limit);
    List<String> getAllCategories();
    List<Product> getProductsByTitle(String title, int offset, int limit);
}
