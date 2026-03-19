//package com.nhnacademy.shop.Controller.admin.user;
//
//import com.nhnacademy.shoppingmall.controller.admin.user.AdminUserDeleteController;
//import com.nhnacademy.shoppingmall.controller.admin.user.AdminUserListController;
//import com.nhnacademy.shoppingmall.order.service.OrderService;
//import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
//import com.nhnacademy.shoppingmall.user.domain.User;
//import com.nhnacademy.shoppingmall.user.service.UserService;
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
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class AdminUserControllerTest {
//
//    @Mock private HttpServletRequest request;
//    @Mock private HttpServletResponse response;
//    @Mock private ServletContext servletContext;
//    @Mock private UserService userService;
//    @Mock private PointHistoryService pointHistoryService;
//    @Mock private OrderService orderService;
//
//    @BeforeEach
//    void setUp() {
//        lenient().when(request.getServletContext()).thenReturn(servletContext);
//        lenient().when(servletContext.getAttribute("userService")).thenReturn(userService);
//        lenient().when(servletContext.getAttribute("pointHistoryService")).thenReturn(pointHistoryService);
//        lenient().when(servletContext.getAttribute("orderService")).thenReturn(orderService);
//    }
//
//    @Test
//    @DisplayName("유저 목록 조회 테스트 - 전체 리스트 속성 설정 확인")
//    void listController_Success() {
//        AdminUserListController controller = new AdminUserListController();
//        List<User> mockUsers = List.of(mock(User.class), mock(User.class));
//        when(userService.getUsers()).thenReturn(mockUsers);
//        String viewName = controller.execute(request, response);
//        assertEquals("admin/admin_user_list", viewName);
//        verify(request).setAttribute("users", mockUsers);
//    }
//
//    @Test
//    @DisplayName("유저 삭제 테스트 - 포인트, 주문, 유저 순으로 연쇄 삭제 호출 확인")
//    void deleteController_Success() {
//        AdminUserDeleteController controller = new AdminUserDeleteController();
//        String targetUserId = "test_user_99";
//        when(request.getParameter("id")).thenReturn(targetUserId);
//        String viewName = controller.execute(request, response);
//        assertEquals("redirect:/admin/user/list.do", viewName);
//        verify(pointHistoryService).deleteHistory(targetUserId);
//        verify(orderService).deleteOrder(targetUserId);
//        verify(userService).deleteUser(targetUserId);
//    }
//}