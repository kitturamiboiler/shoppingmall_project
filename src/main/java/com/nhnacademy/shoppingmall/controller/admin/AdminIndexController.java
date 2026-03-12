package com.nhnacademy.shoppingmall.controller.admin;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/dashboard.do")
public class AdminIndexController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // views/admin/dashboard.jsp 를 찾아서 출력합니다.
        return "shop/admin/dashboard";
    }
}
