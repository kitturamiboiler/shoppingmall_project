package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping(method = RequestMapping.Method.POST, value = "/cart/update.do")
public class CartUpdateController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productIdStr = req.getParameter("productId");
        String quantityStr = req.getParameter("quantity");
        try {
            int productId = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);

            HttpSession session = req.getSession(false);
            if (session != null && session.getAttribute("cart") != null) {
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