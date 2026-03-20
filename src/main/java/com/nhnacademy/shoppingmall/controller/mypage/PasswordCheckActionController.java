package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PasswordCheckActionController{

    @RequestMapping(value = {"/mypage/passwordCheckAction.do"}, method = RequestMethod.POST)
    public String execute(Model model, HttpSession session,
                          @RequestParam("current_password") String inputPw) {
        User user = (User)session.getAttribute("user");

        if(user == null) {
            model.addAttribute("message", "세션이 만료되었습니다.");
            return "common/message";
        }else if(user.getUserPassword().equals(inputPw)){
            User editUser = new User((User)session.getAttribute("user"));
            model.addAttribute("editUser", editUser);
            return "shop/mypage/edit_account";
        }else {
            model.addAttribute("errorMessage", "옳지 않은 비밀번호 입니다 다시 입력해주세요.");
            return "shop/mypage/password_check";
        }
    }
}

