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
public class AdminCategoryDeleteController {
    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryDeleteController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/admin/category/delete.do", method = RequestMethod.POST)
    public String execute(@RequestParam("name") String name) {
        if (name != null && !name.trim().isEmpty()) {
            categoryService.removeCategory(name);
        }

        return "redirect:/admin/category/list.do";
    }
}