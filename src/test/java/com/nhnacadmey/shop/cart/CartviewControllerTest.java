package com.nhnacadmey.shop.cart;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.controller.cart.CartViewController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

@ExtendWith(MockitoExtension.class)
class CartViewControllerTest {
    @InjectMocks
    private CartViewController cartViewController;
    @Mock
    private ProductService productService;
    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private HttpSession httpSession;

    @Test
    @DisplayName("세션이 없는 경우")
    public void execute_whenSessionIsNull() {
        when(req.getSession(false)).thenReturn(null);
        String viewName = cartViewController.execute(req, resp);
        assertEquals("shop/cart/cart_view", viewName);
        verify(req).setAttribute(eq("cartItems"), anyMap());
        verify(req).setAttribute(eq("totalPrice"), eq(0L));
    }
    @Test
    @DisplayName("상품이 삭제된 경우 리스트에서 삭제되고 정상 실행 가능")
    public void execute_whenProductIsDeleted() {
        Cart cart = new Cart();
        cart.addItem(1, 1, 10);

        when(req.getSession(false)).thenReturn(httpSession);
        when(httpSession.getAttribute("cart")).thenReturn(cart);
        when(productService.getProduct(1)).thenReturn(null);
        cartViewController.execute(req, resp);
        verify(req).setAttribute(argThat(name -> name.equals("cartItems")),
                argThat(map -> ((Map<?,?>)map).isEmpty()));
        verify(req).setAttribute("totalPrice", 0L);
    }
    @Test
    @DisplayName("합계 검증")
    public void execute_totalPriceSuccess() {
        Cart cart = new Cart();
        cart.addItem(1, 2, 100);
        Product product = new Product("Test", "Desc", 1000, 100, "code", "brand");
        product.setId(1);
        when(req.getSession(false)).thenReturn(httpSession);
        when(httpSession.getAttribute("cart")).thenReturn(cart);
        when(productService.getProduct(1)).thenReturn(product);
        cartViewController.execute(req, resp);
        verify(req).setAttribute("totalPrice", 2000L);
    }
}
