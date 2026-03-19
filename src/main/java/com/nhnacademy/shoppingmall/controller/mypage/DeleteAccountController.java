package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.point.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Objects;

@Controller
public class DeleteAccountController{

    private final UserService userService;
    private final PointHistoryService pointHistoryService;
    private final OrderService orderService;

    @Autowired
    public DeleteAccountController(UserService userService, PointHistoryService pointHistoryService, OrderService orderService) {
        this.userService = userService;
        this.pointHistoryService = pointHistoryService;
        this.orderService = orderService;
    }

    @RequestMapping(value = {"/mypage/deleteAccount.do"}, method = RequestMethod.GET)
    public String execute(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if(Objects.isNull(user)) {
            model.addAttribute("message", "세션이 만료되었습니다.");
        }else {
            pointHistoryService.deleteHistory(user.getUserId());
            orderService.deleteOrder(user.getUserId());
            userService.deleteUser(user.getUserId());
            session.invalidate();
            model.addAttribute("message", "회원 탈퇴가 완료되었습니다.\r\n이용해주셔서 감사합니다.");
        }

        return "common/message";
    }
}
