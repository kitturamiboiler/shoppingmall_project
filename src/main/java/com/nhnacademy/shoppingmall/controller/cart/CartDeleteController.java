package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping(method = RequestMapping.Method.POST, value = "/cart/delete.do")
public class CartDeleteController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productIdStr = req.getParameter("productId");
        HttpSession httpSession = req.getSession(false);

        if (productIdStr != null && httpSession != null) {
            Cart cart = (Cart) httpSession.getAttribute("cart");
            if (cart != null) {
                cart.removeItem(Integer.parseInt(productIdStr));
            }
        }
        return "redirect:/cart.do";
    }
}
