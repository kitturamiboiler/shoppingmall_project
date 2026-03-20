package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Objects;

@Controller
public class SignupPostController{

    private final UserService userService;

    @Autowired
    public SignupPostController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/signupAction.do"}, method = RequestMethod.POST)
    public String execute(@RequestParam("user_id") String userId,
                          @RequestParam("user_name") String userName,
                          @RequestParam("user_password") String userPassword,
                          @RequestParam("user_birth") String userBirth,
                          Model model) {
        User user = new User(userId, userName, userPassword, userBirth, User.Auth.ROLE_USER,
                1_000_000, LocalDateTime.now(), null);

        try {
            userService.saveUser(user);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user_id", userId);
            model.addAttribute("user_name", userName);
            model.addAttribute("user_password", userPassword);
            model.addAttribute("user_birth", userBirth);
            return "shop/signup/signup_form";
        }

        return "shop/login/login_form";
    }
}
