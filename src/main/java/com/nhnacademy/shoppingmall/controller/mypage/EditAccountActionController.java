package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.time.LocalDateTime;

@RequestMapping(method = RequestMapping.Method.POST,value = "/mypage/editAccountAction.do")
public class EditAccountActionController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User)session.getAttribute("user");
        String editName = req.getParameter("user_name");
        String editPassword = req.getParameter("user_password");
        String editBirth = req.getParameter("user_birth");

        user.setUserName(editName);
        user.setUserPassword(editPassword);
        user.setUserBirth(editBirth);

        userService.updateUser(user);
        session.setAttribute("user", user);
        req.setAttribute("message", "회원 정보 수정 완료!");

        return "common/message";
    }
}
