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

@RequestMapping(method = RequestMapping.Method.POST, value = "/cart/add.do")
public class CartController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productIdStr = req.getParameter("productId");

        if (productIdStr == null || productIdStr.trim().isEmpty()) {
            return "redirect:/product/list.do";
        }

        try {
            int productId = Integer.parseInt(productIdStr);
            HttpSession httpSession = req.getSession(true);
            Cart cart = (Cart) httpSession.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                httpSession.setAttribute("cart", cart);
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