package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class AdminProductRegisterFormController {
    private final CategoryService categoryService;

    @Autowired
    public AdminProductRegisterFormController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/admin/product/register_form.do", method = RequestMethod.GET)
    public String execute(Model model) {
        List<Category> categoryList = categoryService.getCategoryList();
        model.addAttribute("categories", categoryList);
        return "admin/admin_product_form";
    }
}