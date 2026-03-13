package com.nhnacademy.shoppingmall.controller.admin.user;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.point.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/user/delete.do")
public class AdminUserDeleteController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        UserService userService = (UserService) req.getServletContext().getAttribute("userService");
        PointHistoryService pointHistoryService = (PointHistoryService) req.getServletContext().getAttribute("pointHistoryService");
        OrderService orderService = (OrderService) req.getServletContext().getAttribute("orderService");
        String userId = req.getParameter("id");
        pointHistoryService.deleteHistory(userId);
        orderService.deleteOrder(userId);
        userService.deleteUser(userId);
        return "redirect:/admin/user/list.do";
    }
}