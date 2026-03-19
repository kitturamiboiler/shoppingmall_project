//package com.nhnacademy.shop.Controller.admin.product;
//
//import com.nhnacademy.shoppingmall.category.domain.Category;
//import com.nhnacademy.shoppingmall.category.service.CategoryService;
//import com.nhnacademy.shoppingmall.common.page.Page;
//import com.nhnacademy.shoppingmall.controller.admin.product.*;
//import com.nhnacademy.shoppingmall.product.domain.Product;
//import com.nhnacademy.shoppingmall.product.service.ProductService;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.Part;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class AdminProductControllerTest {
//    @Mock private HttpServletRequest request;
//    @Mock private HttpServletResponse response;
//    @Mock private ServletContext servletContext;
//    @Mock private ProductService productService;
//    @Mock private CategoryService categoryService;
//    @Mock private Part filePart;
//    @Mock private Page<Product> mockPage;
//
//    @BeforeEach
//    void setUp() {
//        lenient().when(request.getServletContext()).thenReturn(servletContext);
//        lenient().when(servletContext.getAttribute("productService")).thenReturn(productService);
//        lenient().when(servletContext.getAttribute("categoryService")).thenReturn(categoryService);
//        lenient().when(servletContext.getRealPath("")).thenReturn("");
//    }
//
//    @Test
//    @DisplayName("상품 목록 조회 1페이지")
//    void listController_Test() {
//        AdminProductListController controller = new AdminProductListController();
//        List<Product> products = List.of(mock(Product.class));
//        when(productService.getProductPage(1)).thenReturn(mockPage);
//        when(mockPage.getContent()).thenReturn(products);
//        String viewName = controller.execute(request, response);
//        assertEquals("admin/admin_product_list", viewName);
//        verify(request).setAttribute("products", products);
//    }
//    @Test
//    @DisplayName("상품 등록 -> 이미지 포함 등록 성공 테스트")
//    void registerController_WithImage_Success() throws Exception {
//        AdminProductRegisterController controller = new AdminProductRegisterController();
//        when(request.getParameter("categoryId")).thenReturn("1");
//        when(request.getParameter("title")).thenReturn("신규 상품");
//        when(request.getParameter("price")).thenReturn("10000");
//        when(request.getParameter("quantity")).thenReturn("50");
//        when(request.getParameter("vendor")).thenReturn("NHN");
//        when(request.getParameter("ean")).thenReturn("EAN-123");
//        when(request.getPart("productImage")).thenReturn(filePart);
//        when(filePart.getSize()).thenReturn(1024L);
//
//        String viewName = controller.execute(request, response);
//        assertEquals("redirect:/admin/product/list.do", viewName);
//        verify(productService).saveProduct(any(Product.class));
//        verify(filePart).write(anyString());
//    }
//
//    @Test
//    @DisplayName("상품 삭제 -> 서비스 호출 및 이미지 삭제 로직 통과 검증")
//    void deleteController_Success() {
//        AdminProductDeleteController controller = new AdminProductDeleteController();
//        when(request.getParameter("id")).thenReturn("99");
//
//        String viewName = controller.execute(request, response);
//
//        assertEquals("redirect:/admin/product/list.do", viewName);
//        verify(productService).deleteProduct(99);
//    }
//
//    @Test
//    @DisplayName("상품 수정 폼 -> 기존 상품 정보 로드 검증")
//    void updateFormController_Success() {
//        AdminProductUpdateFormController controller = new AdminProductUpdateFormController();
//        Product mockProduct = mock(Product.class);
//        when(request.getParameter("id")).thenReturn("10");
//        when(productService.getProduct(10)).thenReturn(mockProduct);
//
//        String viewName = controller.execute(request, response);
//
//        assertEquals("admin/admin_product_register_form", viewName);
//        verify(request).setAttribute("product", mockProduct);
//    }
//
//    @Test
//    @DisplayName("상품 정보 수정 -> 이미지 없이 텍스트 정보만")
//    void updateController_NoImage_Success() throws Exception {
//        AdminProductUpdateController controller = new AdminProductUpdateController();
//        Product originalProduct = new Product(10, 1, "옛날", 100, 10, "EAN", 4.0, "V", LocalDateTime.now());
//        when(request.getParameter("id")).thenReturn("10");
//        when(request.getParameter("title")).thenReturn("새상품명");
//        when(request.getParameter("categoryId")).thenReturn("1");
//        when(request.getParameter("price")).thenReturn("200");
//        when(request.getParameter("quantity")).thenReturn("5");
//        when(request.getParameter("vendor")).thenReturn("NewV");
//        when(request.getParameter("ean")).thenReturn("NewEAN");
//        when(productService.getProduct(10)).thenReturn(originalProduct);
//        when(request.getPart("productImage")).thenReturn(null);
//        String viewName = controller.execute(request, response);
//        assertEquals("redirect:/admin/product/list.do", viewName);
//        verify(productService).updateProduct(argThat(p -> p.getTitle().equals("새상품명")));
//    }
//}
