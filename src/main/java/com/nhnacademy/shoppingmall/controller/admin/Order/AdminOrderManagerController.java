package com.nhnacademy.shoppingmall.controller.admin.Order;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.domain.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Controller
public class AdminOrderManagerController {
    private final OrderService orderService;

    @Autowired
    public AdminOrderManagerController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = "/admin/order/manager.do", method = RequestMethod.GET)
    public String execute(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {

        int pageSize = 10;
        Page<Order> orderPage = orderService.getOrders(page, pageSize);
        long totalCount = orderPage.getTotalCount();
        long totalPages = (totalCount > 0) ? (totalCount + pageSize - 1) / pageSize : 1;

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "admin/admin_order_manager";
    }
}