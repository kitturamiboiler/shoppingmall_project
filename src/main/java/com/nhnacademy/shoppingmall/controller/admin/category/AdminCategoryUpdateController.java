package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/category/update.do")
public class AdminCategoryUpdateController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        CategoryService categoryService = (CategoryService) req.getServletContext().getAttribute("categoryService");
        String oldName = req.getParameter("oldName");
        String newName = req.getParameter("newName");

        if (newName != null && !newName.trim().isEmpty()) {
            categoryService.modifyCategory(oldName, newName);
        }
        return "redirect:/admin/category/list.do";
    }
}
