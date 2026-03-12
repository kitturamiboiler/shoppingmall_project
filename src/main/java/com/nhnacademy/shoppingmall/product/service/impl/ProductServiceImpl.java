package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.NotEnoughStockException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private static final int PAGE_SIZE = 10;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    @Override
    public Product getProduct(int id) {
        Product product = productRepository.findById(id);
        if (Objects.isNull(product)) {
            throw new RuntimeException("조회된 상품이 없습니다.");
        }
        return product;
    }
    @Override
    public Page<Product> getProductPage(int page) {
        int safePage = Math.max(1, page);
        int totalCount = productRepository.countAll();
        if (totalCount == 0) {
            return new Page<>(Collections.emptyList(), totalCount);
        }
        int totalPages = (int)Math.ceil((double) totalCount / PAGE_SIZE);
        if (safePage > totalPages) {
            safePage = totalPages;
        }
        int offset = (safePage-1) * PAGE_SIZE;
        List<Product> content = productRepository.findAll(offset, PAGE_SIZE);
        return new Page<>(content, totalCount);
    }
    @Override
    public void saveProduct(Product product) {
        int result = productRepository.save(product);
        if (result < 1) {
            throw new RuntimeException("상품 등록 실패");
        }
    }

    @Override
    public void updateStock(int productId, int quantity) {
        int result = productRepository.updateStock(productId, quantity);
        if(result == 0){
            throw new NotEnoughStockException(productId);
        }
    }
}
