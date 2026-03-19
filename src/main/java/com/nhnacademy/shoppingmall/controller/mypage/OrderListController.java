package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class OrderListController{
    private final OrderService orderService;

    @Autowired
    public OrderListController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value = {"/mypage/orderList.do"}, method = RequestMethod.GET)
    public String execute(Model model, HttpSession session,
                          @RequestParam(name = "page", defaultValue = "1") int page) {
        User user = (User)session.getAttribute("user");
        if(user == null) {
            model.addAttribute("message", "세션이 만료되었습니다.");
            return "common/message";
        }

        int pageSize = 10;
        Page<Order> orderPage = orderService.getOrdersByUserId(user.getUserId(), page, pageSize);
        long totalCount = orderPage.getTotalCount();
        long totalPages = (totalCount > 0) ? (totalCount + pageSize - 1) / pageSize : 1;

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "shop/mypage/order_list";
    }
}
