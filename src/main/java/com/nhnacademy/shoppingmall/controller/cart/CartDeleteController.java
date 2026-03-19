package com.nhnacademy.shoppingmall.controller.cart;

import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartDeleteController {

    @RequestMapping(value = "/cart/delete.do", method = RequestMethod.POST)
    public String execute(@RequestParam(name = "productId", required = false) String productIdStr,
                          HttpSession session) {

        if (productIdStr != null) {
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                cart.removeItem(Integer.parseInt(productIdStr));
            }
        }
        return "redirect:/cart.do";
    }
}
