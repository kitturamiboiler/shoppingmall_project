package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import com.nhnacademy.shoppingmall.category.domain.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminCategoryListController {
    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryListController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/admin/category/list.do", method = RequestMethod.GET)
    public String execute(Model model) {
        List<Category> categories = categoryService.getCategoryList();
        model.addAttribute("categories", categories);

        return "admin/admin_category_list";
    }
}