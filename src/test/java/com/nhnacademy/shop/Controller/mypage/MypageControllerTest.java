package com.nhnacademy.shop.Controller.mypage;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.controller.mypage.*;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MypageControllerTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private UserService userService;
    @Mock private OrderService orderService;
    @Mock private PointHistoryService pointHistoryService;
    @Mock private User mockUser;

    @BeforeEach
    void setUp() {
        lenient().when(request.getSession(false)).thenReturn(session);
        lenient().when(session.getAttribute("user")).thenReturn(mockUser);
        lenient().when(mockUser.getUserId()).thenReturn("testUser");
    }

    @Test
    @DisplayName("마이페이지 인덱스 - 단순 폼 반환 확인")
    void mypageController_Test() {
        MypageController controller = new MypageController();
        assertEquals("shop/mypage/mypage_form", controller.execute(request, response));
    }

    @Test
    @DisplayName("비밀번호 확인 - 성공 시 수정 페이지로 이동")
    void passwordCheckAction_Success() {
        PasswordCheckActionController controller = new PasswordCheckActionController();
        when(request.getParameter("current_password")).thenReturn("1234");
        when(mockUser.getUserPassword()).thenReturn("1234");

        String viewName = controller.execute(request, response);

        assertEquals("shop/mypage/edit_account", viewName);
    }

    @Test
    @DisplayName("비밀번호 확인 - 실패 시 에러 메시지 세팅")
    void passwordCheckAction_Fail() {
        PasswordCheckActionController controller = new PasswordCheckActionController();
        when(request.getParameter("current_password")).thenReturn("wrong");
        when(mockUser.getUserPassword()).thenReturn("1234");

        String viewName = controller.execute(request, response);

        assertEquals("shop/mypage/password_check", viewName);
        verify(request).setAttribute(eq("errorMessage"), anyString());
    }

    @Test
    @DisplayName("정보 수정 - 서비스 호출 및 세션 갱신 확인")
    void editAccountAction_Success() {
        EditAccountActionController controller = new EditAccountActionController();
        ReflectionTestUtils.setField(controller, "userService", userService);

        when(request.getParameter("user_name")).thenReturn("새이름");
        when(request.getParameter("user_password")).thenReturn("newPw");
        when(request.getParameter("user_birth")).thenReturn("20000101");

        String viewName = controller.execute(request, response);

        assertEquals("common/message", viewName);
        verify(userService).updateUser(mockUser);
        verify(session).setAttribute("user", mockUser);
    }

    @Test
    @DisplayName("주문 내역 - 페이징 처리 및 유저 아이디 기준 조회 확인")
    void orderListController_Success() {
        OrderListController controller = new OrderListController();
        ReflectionTestUtils.setField(controller, "orderService", orderService);

        Page<Order> mockPage = new Page<>(List.of(), 50);
        when(request.getParameter("page")).thenReturn("2");
        when(orderService.getOrdersByUserId("testUser", 2, 10)).thenReturn(mockPage);

        String viewName = controller.execute(request, response);

        assertEquals("shop/mypage/order_list", viewName);
        verify(request).setAttribute("orderPage", mockPage);
        verify(request).setAttribute("totalPages", 5L);
    }

    @Test
    @DisplayName("회원 탈퇴 - 데이터 연쇄 삭제 및 세션 무효화 확인")
    void deleteAccountController_Success() {
        DeleteAccountController controller = new DeleteAccountController();
        ReflectionTestUtils.setField(controller, "userService", userService);
        ReflectionTestUtils.setField(controller, "pointHistoryService", pointHistoryService);
        ReflectionTestUtils.setField(controller, "orderService", orderService);

        String viewName = controller.execute(request, response);

        assertEquals("common/message", viewName);
        verify(pointHistoryService).deleteHistory("testUser");
        verify(orderService).deleteOrder("testUser");
        verify(userService).deleteUser("testUser");
        verify(session).invalidate();
    }
}
