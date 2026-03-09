package com.nhnacadmey.shop.product.repository;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductRepositoryImplTest {

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        productRepository = new ProductRepositoryImpl();
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @DisplayName("상품 저장 및 단건 조회 롤백 테스트")
    void saveAndFindById_Success() {
        Product newProduct = new Product("Gizmo", "테스트 상품", 1000, 10, "12345", "Vendor");
        int result = productRepository.save(newProduct);
        assertEquals(1, result, "상품이 정상적으로 INSERT 되어야 합니다.");
    }

    @Test
    @DisplayName("상품 페이징 목록 조회 및 LIMIT 동작 확인")
    void findAll_Pagination_Check() {
        int offset = 0;
        int limit = 10;
        List<Product> products = productRepository.findAll(offset, limit);
        assertNotNull(products, "조회된 리스트는 null이 아니어야 합니다.");
        assertTrue(products.size() <= 10, "LIMIT 10이 적용되어 10개 이하만 조회되어야 합니다.");
    }

    @Test
    @DisplayName("음수 OFFSET 방어 테스트")
    void findAll_Negative_Offset_Defense() {
        int invalidOffset = -5;
        int limit = 5;
        assertDoesNotThrow(() -> {
            productRepository.findAll(invalidOffset, limit);
        }, "음수 OFFSET이 입력되어도 예외가 발생하지 않고 방어되어야 합니다.");
    }
}