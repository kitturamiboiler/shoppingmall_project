package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping(method = RequestMapping.Method.POST,value = "/mypage/passwordCheckAction.do")
public class PasswordCheckActionController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        User user = (User)session.getAttribute("user");
        String pwInput = req.getParameter("current_password");

        if(user.getUserPassword().equals(pwInput)){
            return "shop/mypage/edit_account";
        }else {
            req.setAttribute("errorMessage", "옳지 않은 비밀번호 입니다 다시 입력해주세요.");
            return "shop/mypage/password_check";
        }
    }
}
