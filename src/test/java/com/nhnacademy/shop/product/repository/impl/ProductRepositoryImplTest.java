package com.nhnacademy.shop.product.repository.impl;

import com.nhnacademy.shoppingmall.config.RootConfig; // 어제 만든 설정 파일
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.PropertyPermission;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = {RootConfig.class}) // 1. 스프링 컨테이너 실행 및 설정 로드
@Transactional
class ProductRepositoryImplTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 저장 및 단건 조회 테스트")
    void saveAndFindById_Success(){
        Product product = new Product(
                0,1,"카카오 톡", 15000, 50, "EAN-12345", 0.0,"카카오", LocalDateTime.now()
        );
        int result = productRepository.save(product);
        assertEquals(1, result, "상품 정상 저장 되어야 함");
        Product saveProduct = productRepository.findById(product.getId());
        assertNotNull(saveProduct);
        assertEquals("카카오 톡", saveProduct.getTitle());
    }

    @Test
    @DisplayName("상품 페이징 목록 조회 및 제한 동작 확인")
    void findAll_Pagincation_Check(){
        int offset = 0;
        int limit =10;
        List<Product> productList = productRepository.findAll(offset, limit);
        assertNotNull(productList, "조회된 리스트는 null값일 수 없습니다.");
        assertTrue(productList.size() <= 10);
    }
    @Test
    @DisplayName("Offset < 0 검증")
    void findAll_Negative_Offset_Test(){
        int invalidOffset = -1;
        int limit = 5;
        assertDoesNotThrow(()-> {
            productRepository.findAll(invalidOffset, limit);
        }, "음수 Offset 방어 검증 테스트");
    }
}