package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CartViewController{
    private final ProductService productService;

    @Autowired
    public CartViewController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/cart.do", method = RequestMethod.GET)
    public String execute(Model model, HttpSession session) {
        Map<Product, Integer> cartItems = new HashMap<>();
        long totalPrice = 0;

        if (session.getAttribute("cart") != null) {
            Cart cart = (Cart) session.getAttribute("cart");

            for (Map.Entry<Integer, Integer> entry : cart.getItems().entrySet()) {
                Product product = productService.getProduct(entry.getKey());
                if (product != null) {
                    cartItems.put(product, entry.getValue());
                    totalPrice += (long) product.getPrice() * entry.getValue();
                }
            }
        }
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "shop/cart/cart_view";
    }
}

