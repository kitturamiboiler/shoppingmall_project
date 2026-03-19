package com.nhnacademy.shoppingmall.controller.order;

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
import com.nhnacademy.shoppingmall.thread.channel.RequestChannel;
import com.nhnacademy.shoppingmall.thread.request.impl.PointChannelRequest;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.cart.domain.Cart;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@Controller
public class OrderPostController{
    private final OrderService orderService;
    private final ProductService productService;
    private final UserService userService;
    private final PointHistoryService pointHistoryService;
    private final RequestChannel requestChannel;
    private final TransactionTemplate transactionTemplate;
    private final String POINT_REASON = "상품 구매 금액 차감";

    @Autowired
    public OrderPostController(OrderService orderService, ProductService productService,
                               UserService userService, PointHistoryService pointHistoryService,
                               RequestChannel requestChannel, TransactionTemplate transactionTemplate) {
        this.orderService = orderService;
        this.productService = productService;
        this.userService = userService;
        this.pointHistoryService = pointHistoryService;
        this.requestChannel = requestChannel;
        this.transactionTemplate = transactionTemplate;
    }

    @RequestMapping(value = "/order/post.do", method = RequestMethod.POST)
    public String execute(Model model, HttpSession session) {

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
            userService.updateUserPoint(userId, totalAmount * (-1));
            pointHistoryService.recordHistory(userId, totalAmount * (-1), POINT_REASON);

            requestChannel.addRequest(new PointChannelRequest(userId, totalAmount, userService,
                    pointHistoryService, transactionTemplate));

            int updatePoint = user.getUserPoint() - totalAmount + (int)(totalAmount * 0.1);
            user.setUserPoint(updatePoint);

            session.setAttribute("user", user);
            session.removeAttribute("cart");

            return "redirect:/mypage/orderList.do";

        } catch (Exception e) {
            log.error("주문 처리 실패: {}", e.getMessage(), e);

            model.addAttribute("error", "주문 중 오류가 발생했습니다: " + e.getMessage());

            return "shop/cart/cart_view";
        }
    }
}