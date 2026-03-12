package com.nhnacademy.shoppingmall.controller.order;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.point.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/order/post.do")
public class OrderPostController implements BaseController {
    private OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());
    private ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private PointHistoryService pointHistoryService = new PointHistoryServiceImpl(new PointHistoryRepositoryImpl());

    public OrderPostController() {
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Cart cart = (Cart) session.getAttribute("cart");

        if (user == null) return "redirect:/login.do";
        if (cart == null || cart.getTotalItemCount() == 0) return "redirect:/cart/view.do";

        try {
            String userId = user.getUserId();
            LocalDateTime createdAt = LocalDateTime.now();
            Map<Integer, Integer> cartItems = cart.getItems();
            int totalAmount = 0;
            for(int productId : cartItems.keySet()){
                Product product = productService.getProduct(productId);
                int quantity = cartItems.get(productId);
                productService.updateStock(productId, quantity);

                int total = product.getPrice() * quantity;
                Order order = new Order(userId, productId, quantity, total, createdAt);
                orderService.createOrder(order);

                totalAmount += total;
            }
            userService.pointDeduction(userId, totalAmount);

            session.removeAttribute("cart");

            return "redirect:/mypage/orderList.do";

        } catch (Exception e) {
            log.error("주문 처리 실패: {}", e.getMessage(), e);

            request.setAttribute("error", "주문 중 오류가 발생했습니다: " + e.getMessage());

            return "shop/cart/cart_view";
        }
    }
}