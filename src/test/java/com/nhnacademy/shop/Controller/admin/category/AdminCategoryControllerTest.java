//package com.nhnacademy.shop.Controller.admin.category;
//
//import com.nhnacademy.shoppingmall.category.domain.Category;
//import com.nhnacademy.shoppingmall.category.service.CategoryService;
//import com.nhnacademy.shoppingmall.controller.admin.category.*;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.List;
//
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class AdminCategoryControllerTest {
//    @Mock
//    private HttpServletRequest request;
//    @Mock
//    private HttpServletResponse response;
//    @Mock
//    private ServletContext servletContext;
//    @Mock
//    private CategoryService categoryService;
//
//    @BeforeEach
//    void setUp() {
//        lenient().when(request.getServletContext()).thenReturn(servletContext);
//        lenient().when(servletContext.getAttribute("categoryService")).thenReturn(categoryService);
//    }
//
//    @Test
//    @DisplayName("카테고리 목록 조회 테스트")
//    void listController_Test() {
//        AdminCategoryListController controller = new AdminCategoryListController();
//        List<Category> mockCategories = List.of(new Category(1, "전자제품"));
//        when(categoryService.getCategoryList()).thenReturn(mockCategories);
//
//        String viewName = controller.execute(request, response);
//        assertEquals("admin/admin_category_list", viewName);
//        verify(request).setAttribute("categories", mockCategories);
//    }
//    @Test
//    @DisplayName("카테고리 등록 테스트 - 서비스 호출 후 리다이렉트 확인")
//    void registerController_Test() {
//        AdminCategoryRegisterController controller = new AdminCategoryRegisterController();
//        when(request.getParameter("categoryName")).thenReturn(" 신규카테고리 ");
//        String viewName = controller.execute(request, response);
//        assertEquals("redirect:/admin/category/list.do", viewName);
//        verify(categoryService).addCategory("신규카테고리");
//    }
//    @Test
//    @DisplayName("카테고리 삭제 테스트 - 서비스 호출 확인")
//    void deleteController_Test() {
//        AdminCategoryDeleteController controller = new AdminCategoryDeleteController();
//        when(request.getParameter("name")).thenReturn("삭제대상");
//        String viewName = controller.execute(request, response);
//        assertEquals("redirect:/admin/category/list.do", viewName);
//        verify(categoryService).removeCategory("삭제대상");
//    }
//
//    @Test
//    @DisplayName("카테고리 수정 테스트 - 서비스 호출 검증")
//    void updateController_Test() {
//        AdminCategoryUpdateController controller = new AdminCategoryUpdateController();
//        when(request.getParameter("oldName")).thenReturn("옛날이름");
//        when(request.getParameter("newName")).thenReturn("새이름");
//        String viewName = controller.execute(request, response);
//        assertEquals("redirect:/admin/category/list.do", viewName);
//        verify(categoryService).modifyCategory("옛날이름", "새이름");
//    }
//
//    @Test
//    @DisplayName("수정 폼 이동 테스트 - 속성 전달 검증")
//    void updateFormController_Test() {
//        AdminCategoryUpdateFormController controller = new AdminCategoryUpdateFormController();
//        when(request.getParameter("name")).thenReturn("수정할놈");
//        String viewName = controller.execute(request, response);
//        assertEquals("admin/admin_category_edit_form", viewName);
//        verify(request).setAttribute("targetName", "수정할놈");
//    }
//}
