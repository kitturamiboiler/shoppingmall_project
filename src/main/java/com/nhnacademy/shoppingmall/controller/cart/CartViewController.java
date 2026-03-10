package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

@RequestMapping(method = RequestMapping.Method.GET, value = "/cart.do")
public class CartViewController implements BaseController {
    private ProductService productService;

    public CartViewController() {
        this(new ProductServiceImpl(new ProductRepositoryImpl()));
    }
    public CartViewController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession(false);
        Map<Product, Integer> cartItems = new HashMap<>();
        long totalPrice = 0;

        if (httpSession != null && httpSession.getAttribute("cart") != null) {
            Cart cart = (Cart) httpSession.getAttribute("cart");

            for (Map.Entry<Integer, Integer> entry : cart.getItems().entrySet()) {
                Product product = productService.getProduct(entry.getKey());
                if (product != null) {
                    cartItems.put(product, entry.getValue());
                    totalPrice += (long) product.getPrice() * entry.getValue();
                }
            }
        }
        req.setAttribute("cartItems", cartItems);
        req.setAttribute("totalPrice", totalPrice);
        return "shop/cart/cart_view";
    }
}

