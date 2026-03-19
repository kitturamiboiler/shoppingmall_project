package com.nhnacademy.shop.Controller.admin.order;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.controller.admin.order.AdminOrderManagerController;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AdminOrderManagerControllerTest {
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private ServletContext servletContext;
    @Mock private OrderService orderService;
    @Mock private Page<Order> mockPage;

    private AdminOrderManagerController controller;

    @BeforeEach
    void setUp() {
        controller = new AdminOrderManagerController();
        lenient().when(request.getServletContext()).thenReturn(servletContext);
        lenient().when(servletContext.getAttribute("orderService")).thenReturn(orderService);
    }
    @Test
    @DisplayName("주문 관리 페이지 - 기본 페이지 1")
    void managerController_DefaultPage_Success() {
        when(request.getParameter("page")).thenReturn(null);
        when(orderService.getOrders(1, 10)).thenReturn(mockPage);
        when(mockPage.getTotalCount()).thenReturn(25L);
        String viewName = controller.execute(request, response);
        assertEquals("admin/admin_order_manager", viewName);
        verify(request).setAttribute("orderPage", mockPage);
        verify(request).setAttribute("currentPage", 1);
        verify(request).setAttribute("totalPages", 3L);
    }
    @Test
    @DisplayName("주문 관리 페이지 - 특정 페이지(2) 요청 테스트")
    void managerController_SpecificPage_Success() {
        when(request.getParameter("page")).thenReturn("2");
        when(orderService.getOrders(2, 10)).thenReturn(mockPage);
        when(mockPage.getTotalCount()).thenReturn(15L);
        controller.execute(request, response);
        verify(request).setAttribute("currentPage", 2);
        verify(request).setAttribute("totalPages", 2L);
    }

    @Test
    @DisplayName("주문 관리 페이지 - 잘못된 페이지 파라미터 입력 시 1페이지로 복구")
    void managerController_InvalidPageParam_FallbackToPage1() {
        when(request.getParameter("page")).thenReturn("abc");
        when(orderService.getOrders(1, 10)).thenReturn(mockPage);
        when(mockPage.getTotalCount()).thenReturn(5L);
        controller.execute(request, response);
        verify(request).setAttribute("currentPage", 1);
        verify(orderService).getOrders(1, 10);
    }

    @Test
    @DisplayName("주문 관리 페이지 - 페이지 번호가 1보다 작을 때 1페이지로 보정")
    void managerController_NegativePageParam_CorrectToPage1() {
        when(request.getParameter("page")).thenReturn("0");
        when(orderService.getOrders(1, 10)).thenReturn(mockPage);
        when(mockPage.getTotalCount()).thenReturn(5L);
        controller.execute(request, response);
        verify(request).setAttribute("currentPage", 1);
    }
}
