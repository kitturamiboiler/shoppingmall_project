package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminCategoryUpdateFormController {

    @RequestMapping(value = "/admin/category/edit_form.do", method = RequestMethod.GET)
    public String execute(@RequestParam("name") String name,
                          Model model) {
        model.addAttribute("targetName", name); // 수정할 대상 이름을 JSP에 전달
        return "admin/admin_category_edit_form";
    }
}
