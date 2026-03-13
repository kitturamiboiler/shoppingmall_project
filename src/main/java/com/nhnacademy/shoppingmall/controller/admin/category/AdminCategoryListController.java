package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import com.nhnacademy.shoppingmall.category.domain.Category;
@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/category/list.do")
public class AdminCategoryListController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        CategoryService categoryService = (CategoryService) req.getServletContext().getAttribute("categoryService");

        List<Category> categories = categoryService.getCategoryList();
        req.setAttribute("categories", categories);

        return "admin/admin_category_list";
    }
}