package com.nhnacademy.shoppingmall.controller.admin.user;

import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class AdminUserPointController {
    private final UserService userService;

    @Autowired
    public AdminUserPointController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/admin/user/point.do", method = RequestMethod.GET)
    public String showPointForm(@RequestParam("id") String userId, Model model) {
        User user = userService.getUser(userId);
        if (user == null) {
            return "redirect:/admin/user/list.do";
        }
        model.addAttribute("user", user);
        return "admin/admin_point_form";
    }
    @RequestMapping(value = "/admin/user/point.do", method = RequestMethod.POST)
    public String updatePoint(@RequestParam("userId") String userId,
                              @RequestParam("amount") String amountStr,
                              Model model) {
        try {
            User user = userService.getUser(userId);
            if (user == null) {
                throw new RuntimeException("존재하지 않는 사용자입니다.");
            }

            int amount = Integer.parseInt(amountStr);

            if (user.getUserPoint() + amount < 0) {
                model.addAttribute("user", user);
                model.addAttribute("error", "포인트는 0원 미만이 될 수 없습니다. (현재: " + user.getUserPoint() + ")");
                return "admin/admin_point_form";
            }

            userService.updateUserPoint(userId, amount);

            log.info("관리자가 유저({})에게 포인트 {} 조절 완료", userId, amount);
            return "redirect:/admin/user/list.do";

        } catch (NumberFormatException e) {
            model.addAttribute("error", "수정할 포인트 금액에 숫자만 입력해주세요.");
            return "admin/admin_point_form";
        } catch (Exception e) {
            model.addAttribute("error", "오류 발생: " + e.getMessage());
            return "admin/admin_point_form";
        }
    }
}
