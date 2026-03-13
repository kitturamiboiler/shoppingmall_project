package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/product/register_form.do")
public class AdminProductRegisterFormController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        CategoryService categoryService = (CategoryService) req.getServletContext().getAttribute("categoryService");
        List<Category> categoryList = categoryService.getCategoryList();
        req.setAttribute("categories", categoryList);
        return "admin/admin_product_form";
    }
}