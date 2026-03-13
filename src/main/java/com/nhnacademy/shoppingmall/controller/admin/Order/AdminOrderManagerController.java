package com.nhnacademy.shoppingmall.controller.admin.Order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.domain.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/order/manager.do")
public class AdminOrderManagerController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        OrderService orderService = (OrderService) req.getServletContext().getAttribute("orderService");

        if (orderService == null) {
            throw new RuntimeException("OrderService를 찾을 수 없습니다.");
        }

      int page = 1;
        String pageParam = req.getParameter("page");

        try {
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            }
        } catch (NumberFormatException e) {
            log.warn("잘못된 페이지 파라미터 접근: {}", pageParam);
            page = 1;
        }

        int pageSize = 10;
        Page<Order> orderPage = orderService.getOrders(page, pageSize);
        long totalCount = orderPage.getTotalCount();
        long totalPages = (totalCount > 0) ? (totalCount + pageSize - 1) / pageSize : 1;

        req.setAttribute("orderPage", orderPage);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);

        return "admin/admin_order_manager";
    }
}