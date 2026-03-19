package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartUpdateController{
    private final ProductService productService;

    @Autowired
    public CartUpdateController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/cart/update.do", method = RequestMethod.POST)
    public String execute(HttpSession session,
                          @RequestParam(name = "productId") String productIdStr,
                          @RequestParam(name = "quantity") String quantityStr) {
        try {
            int productId = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);

            if (session.getAttribute("cart") != null) {
                Cart cart = (Cart) session.getAttribute("cart");
                Product product = productService.getProduct(productId);
                if (product != null) {
                    cart.updateQuantity(productId, quantity, product.getQuantity());
                }
            }
        } catch (Exception e) {
            System.err.println("수량 업데이트 실패: " + e.getMessage());
        }
        return "redirect:/cart.do";
    }
}