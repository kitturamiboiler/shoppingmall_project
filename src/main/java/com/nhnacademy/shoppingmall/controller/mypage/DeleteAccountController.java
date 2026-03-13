package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
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

import java.util.Objects;

@RequestMapping(method = RequestMapping.Method.GET,value = "/mypage/deleteAccount.do")
public class DeleteAccountController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final PointHistoryService pointHistoryService = new PointHistoryServiceImpl(new PointHistoryRepositoryImpl());
    private final OrderService orderService = new OrderServiceImpl(new OrderRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if(Objects.isNull(session)) {
            req.setAttribute("message", "세션이 만료되었습니다.");
        }else {
            User user = (User) session.getAttribute("user");
            pointHistoryService.deleteHistory(user.getUserId());
            orderService.deleteOrder(user.getUserId());
            userService.deleteUser(user.getUserId());
            session.invalidate();
            req.setAttribute("message", "회원 탈퇴가 완료되었습니다.\r\n이용해주셔서 감사합니다.");
        }
        return "common/message";
    }
}
