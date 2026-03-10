package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Objects;

@RequestMapping(method = RequestMapping.Method.GET,value = "/signup.do")
public class SignupController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession(false);
        if (Objects.nonNull(httpSession)) {
            httpSession.invalidate();
        }

        return "shop/signup/signup_form";
    }
}
