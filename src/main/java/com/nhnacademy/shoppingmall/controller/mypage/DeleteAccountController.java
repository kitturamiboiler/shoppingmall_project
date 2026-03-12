package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
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

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if(Objects.isNull(session)) {
            req.setAttribute("message", "세션이 만료되었습니다.");
        }else {
            User user = (User) session.getAttribute("user");
            userService.deleteUser(user.getUserId());
            session.invalidate();
            req.setAttribute("message", "회원 탈퇴가 완료되었습니다.\r\n이용해주셔서 감사합니다.");
        }
        return "common/message";
    }
}
