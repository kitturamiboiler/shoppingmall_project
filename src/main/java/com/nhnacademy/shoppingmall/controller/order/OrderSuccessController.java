package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/order/success.do")
public class OrderSuccessController implements BaseController {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return "shop/order/order_complete";
    }
}