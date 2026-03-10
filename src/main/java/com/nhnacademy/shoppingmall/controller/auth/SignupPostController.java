package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.Objects;

@RequestMapping(method = RequestMapping.Method.POST,value = "/signupAction.do")
public class SignupPostController implements BaseController {

    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String userId = req.getParameter("user_id");
        String userName = req.getParameter("user_name");
        String userPassword = req.getParameter("user_password");
        String userBirth = req.getParameter("user_birth");
        User user = new User(userId, userName, userPassword, userBirth, User.Auth.ROLE_USER,
                1_000_000, LocalDateTime.now(), null);

        try {
            DbConnectionThreadLocal.initialize();
            userService.saveUser(user);
        } catch (Exception e) {
            DbConnectionThreadLocal.setSqlError(true);
            if(e instanceof UserAlreadyExistsException){
                req.setAttribute("errorMessage", e.getMessage());
                return "shop/signup/signup_form";
            }
        } finally {
            DbConnectionThreadLocal.reset();
        }

        return "shop/login/login_form";
    }
}
