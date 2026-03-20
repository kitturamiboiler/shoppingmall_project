package com.nhnacademy.shoppingmall.controller.mypage;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
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

import java.time.LocalDateTime;

@Controller
public class EditAccountActionController {

    private final UserService userService;

    @Autowired
    public EditAccountActionController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = {"/mypage/editAccountAction.do"}, method = RequestMethod.POST)
    public String execute(@RequestParam("user_name") String editName,
                          @RequestParam("user_password") String editPassword,
                          @RequestParam("user_birth") String editBirth,
                          Model model, HttpSession session) {
        User user = (User)session.getAttribute("user");
        if(user == null){
            model.addAttribute("message", "세션이 만료되었습니다.");
        }else {
            User editUser = new User(user);
            editUser.setUserName(editName);
            editUser.setUserPassword(editPassword);
            editUser.setUserBirth(editBirth);

            try {
                userService.updateUser(editUser);
            }catch (Exception e){
                model.addAttribute("errorMessage", e.getMessage());
                model.addAttribute("editUser", editUser);
                return "shop/mypage/edit_account";
            }
            session.setAttribute("user", editUser);
            model.addAttribute("message", "회원 정보 수정 완료!");
        }

        return "common/message";
    }
}
