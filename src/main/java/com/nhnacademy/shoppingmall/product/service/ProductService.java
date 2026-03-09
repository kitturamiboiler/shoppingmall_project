package com.nhnacademy.shoppingmall.product.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;

public interface ProductService {
    Product getProduct(int id);
    Page<Product> getProductPage(int page);
    void saveProduct(Product product);
}
