package com.nhnacademy.shoppingmall.controller.admin.user;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.point.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminUserDeleteController {
    private final UserService userService;
    private final PointHistoryService pointHistoryService;
    private final OrderService orderService;

    @Autowired
    public AdminUserDeleteController(UserService userService, PointHistoryService pointHistoryService, OrderService orderService) {
        this.userService = userService;
        this.pointHistoryService = pointHistoryService;
        this.orderService = orderService;
    }

    @RequestMapping(value = "/admin/user/delete.do", method = RequestMethod.POST)
    public String execute(@RequestParam("id") String userId) {
        pointHistoryService.deleteHistory(userId);
        orderService.deleteOrder(userId);
        userService.deleteUser(userId);
        return "redirect:/admin/user/list.do";
    }
}