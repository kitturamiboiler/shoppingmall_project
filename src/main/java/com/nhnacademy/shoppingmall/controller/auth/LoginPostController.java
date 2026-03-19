package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginPostController {

    private final UserService userService;

    @Autowired
    public LoginPostController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/loginAction.do"}, method = RequestMethod.POST)
    public String execute(@RequestParam("user_id") String userId,
                          @RequestParam("user_password") String userPw,
                          Model model, HttpSession session) {

        //todo#13-2 로그인 구현, session은 60분동안 유지됩니다.
        try {
            User user = userService.doLogin(userId, userPw);
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(3600);
            return "redirect:/index.do";

        } catch (Exception e) {
            model.addAttribute("error", "아이디 또는 비밀번호를 잘못 입력하셨습니다.");
            return "shop/login/login_form";
        }
    }
}

