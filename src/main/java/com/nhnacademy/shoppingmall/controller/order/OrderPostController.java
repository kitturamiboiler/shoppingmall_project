package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.domain.OrderItem;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.cart.domain.Cart;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/order/post.do")
public class OrderPostController implements BaseController {
    private OrderService orderService;

    public OrderPostController() {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        if (this.orderService == null) {
            this.orderService = (OrderService) request.getServletContext().getAttribute("orderService");
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        if (user == null) return "redirect:/login.do";
        if (cart == null || cart.getTotalItemCount() == 0) return "redirect:/cart/view.do";

        try {
            Order order = new Order();
            order.setUserId(user.getUserId());
            order.setCreatedAt(java.time.LocalDateTime.now());
            List<OrderItem> orderItems = new ArrayList<>();
            Map<Integer, Integer> cartItems = cart.getItems();

            for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
                OrderItem item = new OrderItem();
                item.setProductId(entry.getKey());
                item.setQuantity(entry.getValue());
                orderItems.add(item);
            }

            orderService.createOrder(order, orderItems);
            int orderId = order.getId();
            if (orderId == 0) throw new RuntimeException("주문 번호 생성 실패");

            session.removeAttribute("cart");

            return "redirect:/order/success.do?orderId=" + orderId;

        } catch (Exception e) {
            log.error("주문 처리 실패: {}", e.getMessage(), e);

            request.setAttribute("error", "주문 중 오류가 발생했습니다: " + e.getMessage());

            return "shop/cart/cart_view";
        }
    }
}