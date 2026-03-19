//package com.nhnacademy.shop.Controller.oder;
//
//import com.nhnacademy.shoppingmall.cart.domain.Cart;
//import com.nhnacademy.shoppingmall.controller.order.OrderPostController;
//import com.nhnacademy.shoppingmall.controller.order.OrderSuccessController;
//import com.nhnacademy.shoppingmall.order.domain.Order;
//import com.nhnacademy.shoppingmall.order.service.OrderService;
//import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
//import com.nhnacademy.shoppingmall.product.domain.Product;
//import com.nhnacademy.shoppingmall.product.service.ProductService;
//import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
//import com.nhnacademy.shoppingmall.thread.request.ChannelRequest;
//import com.nhnacademy.shoppingmall.user.domain.User;
//import com.nhnacademy.shoppingmall.user.service.UserService;
//import jakarta.servlet.ServletContext;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.util.ReflectionTestUtils;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class OrderControllerTest {
//
//    @Mock private HttpServletRequest request;
//    @Mock private HttpServletResponse response;
//    @Mock private HttpSession session;
//    @Mock private ServletContext servletContext;
//    @Mock private RequestChannel requestChannel;
//
//    @Mock private OrderService orderService;
//    @Mock private ProductService productService;
//    @Mock private UserService userService;
//    @Mock private PointHistoryService pointHistoryService;
//
//    private OrderPostController orderPostController;
//
//    @BeforeEach
//    void setUp() {
//        orderPostController = new OrderPostController();
//        ReflectionTestUtils.setField(orderPostController, "orderService", orderService);
//        ReflectionTestUtils.setField(orderPostController, "productService", productService);
//        ReflectionTestUtils.setField(orderPostController, "userService", userService);
//        ReflectionTestUtils.setField(orderPostController, "pointHistoryService", pointHistoryService);
//
//        lenient().when(request.getSession()).thenReturn(session);
//        lenient().when(request.getServletContext()).thenReturn(servletContext);
//        lenient().when(servletContext.getAttribute("requestChannel")).thenReturn(requestChannel);
//    }
//
//    @Test
//    @DisplayName("주문 성공 테스트 - 재고/포인트/채널요청 통합 검증")
//    void orderPost_Success() throws Exception {
//        User user = new User("user1", "홍길동", "1234", "1990", User.Auth.ROLE_USER, 100000, LocalDateTime.now(), null);
//        Cart cart = new Cart();
//        cart.addItem(100, 2, 10);
//
//        Product mockProduct = new Product(100, 1, "테스트상품", 5000, 10, "EAN", 0.0, "V", LocalDateTime.now());
//
//        when(session.getAttribute("user")).thenReturn(user);
//        when(session.getAttribute("cart")).thenReturn(cart);
//        when(productService.getProduct(100)).thenReturn(mockProduct);
//
//        String viewName = orderPostController.execute(request, response);
//        assertEquals("redirect:/mypage/orderList.do", viewName);
//        verify(productService).updateStock(100, 2);
//        verify(orderService).createOrder(any(Order.class));
//        verify(userService).updateUserPoint("user1", -10000);
//        verify(pointHistoryService).recordHistory(eq("user1"), eq(-10000), anyString());
//        verify(requestChannel).addRequest(any(ChannelRequest.class));
//        verify(session).setAttribute(eq("user"), any(User.class));
//        verify(session).removeAttribute("cart");
//        assertEquals(91000, user.getUserPoint());
//    }
//
//    @Test
//    @DisplayName("주문 실패 - 세션에 유저가 없을 때 로그인 페이지로")
//    void orderPost_NoUser_RedirectLogin() {
//        when(session.getAttribute("user")).thenReturn(null);
//        assertEquals("redirect:/login.do", orderPostController.execute(request, response));
//    }
//
//    @Test
//    @DisplayName("주문 실패 - 카트가 비어있을 때 장바구니로")
//    void orderPost_EmptyCart_RedirectCart() {
//        when(session.getAttribute("user")).thenReturn(mock(User.class));
//        when(session.getAttribute("cart")).thenReturn(null);
//        assertEquals("redirect:/cart/view.do", orderPostController.execute(request, response));
//    }
//
//    @Test
//    @DisplayName("주문 성공 페이지 이동 테스트")
//    void orderSuccessController_Test() {
//        OrderSuccessController controller = new OrderSuccessController();
//        assertEquals("shop/order/order_complete", controller.execute(request, response));
//    }
//}