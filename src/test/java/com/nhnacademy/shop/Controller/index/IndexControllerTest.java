package com.nhnacademy.shop.Controller.index;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.controller.index.IndexController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IndexControllerTest {

    private MockMvc mockMvc;
    @Mock
    private ProductService productService;
    @InjectMocks
    private IndexController indexController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @Test
    @DisplayName("메인 페이지 로드 - 기본 상품 목록 및 카테고리 목록 확인")
    void index_Default_Success() throws Exception {
        List<String> mockCategories = List.of("전자제품", "도서");
        Page<Product> mockPage = new Page<>(List.of(mock(Product.class)), 1);

        when(productService.getAllCategories()).thenReturn(mockCategories);
        when(productService.getProductPage(1)).thenReturn(mockPage);

        mockMvc.perform(get("/index.do"))
                .andExpect(status().isOk())
                .andExpect(view().name("layout/shop"))
                .andExpect(model().attribute("categoryList", mockCategories))
                .andExpect(model().attributeExists("productList"))
                .andExpect(model().attribute("layout_content_holder", "/WEB-INF/views/shop/main/index.jsp"));
    }

    @Test
    @DisplayName("검색어로 상품 조회 테스트")
    void index_Search_Success() throws Exception {
        String keyword = "Mac";
        when(productService.getAllCategories()).thenReturn(List.of());
        when(productService.getProductsByTitle(eq(keyword), anyInt(), anyInt()))
                .thenReturn(List.of(mock(Product.class)));

        mockMvc.perform(get("/").param("searchKeyword", keyword))
                .andExpect(status().isOk())
                .andExpect(model().attribute("searchKeyword", keyword))
                .andExpect(model().attributeExists("productList"));
    }

    @Test
    @DisplayName("카테고리 필터링 테스트")
    void index_CategoryFilter_Success() throws Exception {
        String category = "전자제품";
        when(productService.getAllCategories()).thenReturn(List.of(category));
        when(productService.getProductsByCategory(eq(category), anyInt(), anyInt()))
                .thenReturn(List.of(mock(Product.class)));
        mockMvc.perform(get("/index.do").param("category", category))
                .andExpect(status().isOk())
                .andExpect(model().attribute("selectedCategory", category))
                .andExpect(model().attributeExists("productList"));
    }

    @Test
    @DisplayName("최근 본 상품 세션 로직 테스트")
    void index_RecentProducts_FromSession() throws Exception {
        List<Integer> recentIds = List.of(1, 2);
        Product p1 = mock(Product.class);
        Product p2 = mock(Product.class);

        when(productService.getAllCategories()).thenReturn(List.of());
        when(productService.getProductPage(1)).thenReturn(new Page<>(List.of(), 0));
        when(productService.getProduct(1)).thenReturn(p1);
        when(productService.getProduct(2)).thenReturn(p2);

        mockMvc.perform(get("/index.do").sessionAttr("recentProducts", recentIds))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recentProductList"))
                .andExpect(model().hasNoErrors());
    }
}