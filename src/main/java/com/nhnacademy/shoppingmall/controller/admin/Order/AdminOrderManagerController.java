package com.nhnacademy.shoppingmall.controller.admin.Order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.domain.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/order/manager.do")
public class AdminOrderManagerController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        OrderService orderService = (OrderService) req.getServletContext().getAttribute("orderService");

        if (orderService == null) {
            throw new RuntimeException("OrderService를 찾을 수 없습니다.");
        }

//        List<Order> orderList = orderService.getOrderList();
//        req.setAttribute("orderList", orderList);

        return "admin/admin_order_manager";
    }
}