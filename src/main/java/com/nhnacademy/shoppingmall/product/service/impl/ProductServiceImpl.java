package com.nhnacademy.shoppingmall.product.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.NotEnoughStockException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private static final int PAGE_SIZE = 10;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(int id) {
        Product product = productRepository.findById(id);
        if (Objects.isNull(product)) {
            throw new RuntimeException("조회된 상품이 없습니다.");
        }
        return product;
    }

    @Override
    @Transactional(readOnly = true)
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
    @Transactional
    public void saveProduct(Product product) {
        int result = productRepository.save(product);
        if (result < 1) {
            throw new RuntimeException("상품 등록 실패");
        }
    }
    @Override
    @Transactional
    public void updateProduct(Product product) {
        if (Objects.isNull(product.getId())) {
            throw new RuntimeException("수정할 상품의 ID가 없습니다.");
        }
        int result = productRepository.update(product);
        if (result < 1) {
            throw new RuntimeException("상품 수정 실패: 해당 상품이 존재하지 않습니다. (ID: " + product.getId() + ")");
        }
    }

    @Override
    @Transactional
    public void deleteProduct(int id) {
        int result = productRepository.deleteById(id);
        if (result < 1) {
            throw new RuntimeException("상품 삭제 실패: 해당 상품이 존재하지 않습니다. (ID: " + id + ")");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> getAllCategories() {
        return productRepository.getProductsByCategory();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(String category, int offset, int limit) {
        if (category == null || category.trim().isEmpty() || category.equals("ALL")) {
            return productRepository.findAll(offset, limit);
        }
        return productRepository.findAllCategory(category, offset, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByTitle(String title, int offset, int limit) {
        if (title == null || title.trim().isEmpty()) {
            return productRepository.findAll(offset, limit);
        }
        return productRepository.getProductsByTitle(title, offset, limit);
    }

    @Override
    @Transactional
    public void updateStock(int productId, int quantity) {
        productRepository.updateStock(productId, quantity);

    }
}
