package com.nhnacademy.shoppingmall.controller.admin.user;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminUserListController {
    private final UserService userService;

    @Autowired
    public AdminUserListController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/admin/user/list.do", method = RequestMethod.GET)
    public String execute(Model model) {

        List<User> userList = userService.getUsers();
        model.addAttribute("users", userList);

        return "admin/admin_user_list";
    }
}