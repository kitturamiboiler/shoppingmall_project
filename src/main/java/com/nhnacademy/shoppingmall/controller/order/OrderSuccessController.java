package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OrderSuccessController{
    @RequestMapping(value = "/order/success.do", method = RequestMethod.GET)
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "shop/order/order_complete";
    }
}