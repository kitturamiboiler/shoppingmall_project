package com.nhnacademy.shop.Controller.view;

import com.nhnacademy.shoppingmall.controller.view.ProductViewController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductViewControllerTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private ServletContext servletContext;
    @Mock private ProductService productService;

    private ProductViewController productViewController;

    @BeforeEach
    void setUp() {
        productViewController = new ProductViewController();

        lenient().when(request.getServletContext()).thenReturn(servletContext);
        lenient().when(servletContext.getAttribute("productService")).thenReturn(productService);
        lenient().when(request.getSession()).thenReturn(session);
    }

    @Test
    @DisplayName("상품 상세 조회 성공 - 상품 정보 및 최근 본 상품 목록 세팅 확인")
    void productView_Success() {
        int productId = 100;
        Product mockProduct = mock(Product.class);
        List<Integer> recentIds = new ArrayList<>(List.of(101, 102));

        when(request.getParameter("productId")).thenReturn("100");
        when(productService.getProduct(productId)).thenReturn(mockProduct);
        when(session.getAttribute("recentProducts")).thenReturn(recentIds);

        when(productService.getProduct(101)).thenReturn(mock(Product.class));
        when(productService.getProduct(102)).thenReturn(mock(Product.class));

        String viewName = productViewController.execute(request, response);

        assertEquals("shop/product/product_view", viewName);
        verify(request).setAttribute("product", mockProduct);
        verify(request).setAttribute(eq("recentProductList"), anyList());
        verify(session).setAttribute(eq("recentProducts"), anyList());
    }

    @Test
    @DisplayName("상품 상세 조회 실패 - 파라미터가 없을 때 인덱스로 리다이렉트")
    void productView_NoParam_RedirectIndex() {
        when(request.getParameter("productId")).thenReturn(null);

        String viewName = productViewController.execute(request, response);

        assertEquals("redirect:/index.do", viewName);
    }

    @Test
    @DisplayName("상품 상세 조회 실패 - 존재하지 않는 상품일 때 리다이렉트")
    void productView_ProductNotFound_RedirectIndex() {
        when(request.getParameter("productId")).thenReturn("999");
        when(productService.getProduct(999)).thenReturn(null);

        String viewName = productViewController.execute(request, response);

        assertEquals("redirect:/index.do", viewName);
    }
}