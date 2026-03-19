package com.nhnacademy.shop.product.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("상품 조회 성공 테스트")
    void getProduct_Success() {
        Product mockProduct = new Product(
                1,
                10,
                "집",
                25000,
                100,
                "880123456789",
                4.8,
                "밥",
                LocalDateTime.now()
        );

        when(productRepository.findById(1)).thenReturn(mockProduct);

        Product result = productService.getProduct(1);

        assertNotNull(result);
        assertEquals("집", result.getTitle());
        assertEquals(25000, result.getPrice());
        verify(productRepository, times(1)).findById(1);
    }

    @Test
    @DisplayName("상품 페이지 조회 - 로직 및 데이터 검증")
    void getProductPage_Check() {
        int totalCount = 15;
        Product p1 = new Product(
                1,
                1,
                "테1",
                100,
                10,
                "E1",
                0.0,
                "V1",
                LocalDateTime.now());

        when(productRepository.countAll()).thenReturn(totalCount);
        when(productRepository.findAll(0, 10)).thenReturn(List.of(p1));

        Page<Product> resultPage = productService.getProductPage(1);

        assertEquals(totalCount, resultPage.getTotalCount());
        assertFalse(resultPage.getContent().isEmpty());
    }

    @Test
    @DisplayName("상품 조회 실패 시 예외 발생 확인")
    void getProduct_Fail_NotFound() {
        when(productRepository.findById(999)).thenReturn(null);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.getProduct(999));
        assertEquals("조회된 상품이 없습니다.", exception.getMessage());
    }
    @Test
    @DisplayName("상품 페이지 조회 로직 검증")
    void getProductPage_CheckLogic() {
        int totalCount = 25;
        when(productRepository.countAll()).thenReturn(totalCount);
        when(productRepository.findAll(0, 10)).thenReturn(List.of(mock(Product.class)));
        Page<Product> resultPage = productService.getProductPage(1);
        assertEquals(totalCount, resultPage.getTotalCount());
        verify(productRepository).findAll(0, 10);
    }

    @Test
    @DisplayName("재고 업데이트 호출 확인")
    void updateStock_CallRepository() {
        productService.updateStock(1, 5);
        verify(productRepository).updateStock(1, 5);
    }
}