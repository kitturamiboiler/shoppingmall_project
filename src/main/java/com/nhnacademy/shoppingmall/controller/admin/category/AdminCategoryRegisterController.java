package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminCategoryRegisterController {
    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryRegisterController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/admin/category/register.do", method = RequestMethod.POST)
    public String execute(@RequestParam("categoryName") String categoryName) {
        if (categoryName != null && !categoryName.trim().isEmpty()) {
            categoryService.addCategory(categoryName.trim());
        }
        return "redirect:/admin/category/list.do";
    }
}