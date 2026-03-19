package com.nhnacademy.shoppingmall.controller.auth;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Objects;

@Controller
public class LogoutController{
    //todo#13-3 로그아웃 구현
    @RequestMapping(value = {"/logout.do"}, method = RequestMethod.GET)
    public String execute(HttpServletRequest req) {
        HttpSession httpSession = req.getSession(false);
            if (Objects.nonNull(httpSession)) {
                httpSession.invalidate();
            }
            return "redirect:/index.do";
        }
    }

