package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/category/register.do")
public class AdminCategoryRegisterController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        CategoryService categoryService = (CategoryService) req.getServletContext().getAttribute("categoryService");
        String categoryName = req.getParameter("categoryName");

        if (categoryName != null && !categoryName.trim().isEmpty()) {
            categoryService.addCategory(categoryName.trim());
        }
        return "redirect:/admin/category/list.do";
    }
}