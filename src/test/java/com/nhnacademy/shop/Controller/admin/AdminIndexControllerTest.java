//package com.nhnacademy.shop.Controller.admin;
//
//import com.nhnacademy.shoppingmall.controller.admin.AdminIndexController;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@ExtendWith(MockitoExtension.class)
//class AdminIndexControllerTest {
//
//    @Mock
//    private HttpServletRequest request;
//    @Mock
//    private HttpServletResponse response;
//
//    @Test
//    @DisplayName("관리자 대시보드 페이지 이동 테스트 - 리턴 값 검증")
//    void adminIndexController_Test() {
//        AdminIndexController controller = new AdminIndexController();
//        String viewName = controller.execute(request, response);
//        assertEquals("admin/dashboard", viewName);
//    }
//}