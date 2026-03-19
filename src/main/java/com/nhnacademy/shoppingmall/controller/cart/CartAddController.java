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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartAddController{
    private final ProductService productService;

    @Autowired
    public CartAddController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/cart/add.do", method = RequestMethod.POST)
    public String execute(HttpSession session,
                          @RequestParam(name = "productId", required = false) String productIdStr) {

        if (productIdStr == null || productIdStr.trim().isEmpty()) {
            return "redirect:/product/list.do";
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
            Product product = productService.getProduct(productId);
            if (product == null) {
                return "redirect:/product/list.do";
            }

            cart.addItem(productId, 1, product.getQuantity());

        } catch (Exception e) {

            System.err.println("Cart 추가 에러: " + e.getMessage());
            return "redirect:/product/list.do";
        }
        return "redirect:/cart.do";
    }
}