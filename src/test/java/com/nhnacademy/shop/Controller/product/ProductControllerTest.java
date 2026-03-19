//package com.nhnacademy.shop.Controller.product;
//
//import com.nhnacademy.shoppingmall.common.page.Page;
//import com.nhnacademy.shoppingmall.controller.product.ProductController;
//import com.nhnacademy.shoppingmall.product.domain.Product;
//import com.nhnacademy.shoppingmall.product.service.ProductService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class ProductControllerTest {
//
//    @Mock private HttpServletRequest request;
//    @Mock private HttpServletResponse response;
//    @Mock private ProductService productService;
//    @Mock private Page<Product> mockPage;
//
//    private ProductController productController;
//
//    @BeforeEach
//    void setUp() {
//        productController = new ProductController();
//        ReflectionTestUtils.setField(productController, "productService", productService);
//    }
//
//    @Test
//    @DisplayName("상품 목록 조회 성공 - 기본 페이지(1) 로드")
//    void productList_DefaultPage_Success() {
//        when(request.getParameter("page")).thenReturn(null);
//        when(productService.getProductPage(1)).thenReturn(mockPage);
//        when(mockPage.getTotalCount()).thenReturn(15L);
//
//        String viewName = productController.execute(request, response);
//
//        assertEquals("shop/product/product_list", viewName);
//        verify(request).setAttribute("productPage", mockPage);
//        verify(request).setAttribute("currentPage", 1);
//        verify(request).setAttribute("totalPages", 2L);
//    }
//
//    @Test
//    @DisplayName("상품 목록 조회 성공 - 특정 페이지(3) 요청")
//    void productList_SpecificPage_Success() {
//        when(request.getParameter("page")).thenReturn("3");
//        when(productService.getProductPage(3)).thenReturn(mockPage);
//        when(mockPage.getTotalCount()).thenReturn(35L);
//
//        productController.execute(request, response);
//
//        verify(request).setAttribute("currentPage", 3);
//        verify(request).setAttribute("totalPages", 4L);
//        verify(productService).getProductPage(3);
//    }
//
//    @Test
//    @DisplayName("잘못된 페이지 파라미터(문자열) 입력 시 1페이지로 보정")
//    void productList_InvalidPageParam_FallbackToPage1() {
//        when(request.getParameter("page")).thenReturn("abc");
//        when(productService.getProductPage(1)).thenReturn(mockPage);
//        when(mockPage.getTotalCount()).thenReturn(5L);
//
//        productController.execute(request, response);
//
//        verify(request).setAttribute("currentPage", 1);
//        verify(productService).getProductPage(1);
//    }
//
//    @Test
//    @DisplayName("음수 페이지 번호 입력 시 1페이지로 보정")
//    void productList_NegativePageParam_CorrectToPage1() {
//        when(request.getParameter("page")).thenReturn("-5");
//        when(productService.getProductPage(1)).thenReturn(mockPage);
//        when(mockPage.getTotalCount()).thenReturn(5L);
//
//        productController.execute(request, response);
//
//        verify(request).setAttribute("currentPage", 1);
//    }
//}
