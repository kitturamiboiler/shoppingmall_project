package com.nhnacademy.shop.Controller.cart;


import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.controller.cart.CartAddController;
import com.nhnacademy.shoppingmall.controller.cart.CartDeleteController;
import com.nhnacademy.shoppingmall.controller.cart.CartUpdateController;
import com.nhnacademy.shoppingmall.controller.cart.CartViewController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
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

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartControllerTest {

    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private HttpSession session;
    @Mock private ProductService productService;
    @Mock private Cart cart;

    @BeforeEach
    void setUp() {
        lenient().when(request.getSession(anyBoolean())).thenReturn(session);
    }

    @Test
    @DisplayName("장바구니 추가 테스트 - 새로운 장바구니 생성 및 아이템 추가")
    void cartAddController_Success() {
        CartAddController controller = new CartAddController();
        ReflectionTestUtils.setField(controller, "productService", productService);
        Product mockProduct = mock(Product.class);
        when(request.getParameter("productId")).thenReturn("1");
        when(session.getAttribute("cart")).thenReturn(null);
        when(productService.getProduct(1)).thenReturn(mockProduct);
        when(mockProduct.getQuantity()).thenReturn(10);
        String viewName = controller.execute(request, response);
        assertEquals("redirect:/cart.do", viewName);
        verify(session).setAttribute(eq("cart"), any(Cart.class));
    }

    @Test
    @DisplayName("장바구니 삭제 테스트 - 세션에서 아이템 제거 확인")
    void cartDeleteController_Success() {
        CartDeleteController controller = new CartDeleteController();
        when(request.getParameter("productId")).thenReturn("1");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("cart")).thenReturn(cart);
        String viewName = controller.execute(request, response);
        assertEquals("redirect:/cart.do", viewName);
        verify(cart).removeItem(1);
    }

    @Test
    @DisplayName("장바구니 수량 업데이트 테스트 - 재고 확인 후 업데이트")
    void cartUpdateController_Success() {
        CartUpdateController controller = new CartUpdateController();
        ReflectionTestUtils.setField(controller, "productService", productService);
        Product mockProduct = mock(Product.class);
        when(request.getParameter("productId")).thenReturn("1");
        when(request.getParameter("quantity")).thenReturn("5");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("cart")).thenReturn(cart);
        when(productService.getProduct(1)).thenReturn(mockProduct);
        when(mockProduct.getQuantity()).thenReturn(10);
        String viewName = controller.execute(request, response);
        assertEquals("redirect:/cart.do", viewName);
        verify(cart).updateQuantity(1, 5, 10);
    }

    @Test
    @DisplayName("장바구니 보기 테스트 - 총 금액 계산 로직 검증")
    void cartViewController_Test() {
        CartViewController controller = new CartViewController(productService);
        Cart realCart = new Cart();
        realCart.addItem(1, 2, 10);
        Product mockProduct = mock(Product.class);
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("cart")).thenReturn(realCart);
        when(productService.getProduct(1)).thenReturn(mockProduct);
        when(mockProduct.getPrice()).thenReturn(5000);
        String viewName = controller.execute(request, response);
        assertEquals("shop/cart/cart_view", viewName);
        verify(request).setAttribute(eq("totalPrice"), eq(10000L));
        verify(request).setAttribute(eq("cartItems"), any(Map.class));
    }
}
