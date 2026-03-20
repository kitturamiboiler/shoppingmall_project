package com.nhnacademy.shoppingmall.controller.admin.category;

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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminCategoryUpdateController {
    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryUpdateController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/admin/category/update.do", method = RequestMethod.POST)
    public String execute(@RequestParam("oldName") String oldName,
                          @RequestParam(name = "newName", required = false) String newName, Model model) {
        List<Category> categories = categoryService.getCategoryList();
        model.addAttribute("categories", categories);

        try {
            categoryService.modifyCategory(oldName, newName);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "카테고리 수정 실패: " + e.getMessage());
            return "admin/admin_category_list";
        }
        return "redirect:/admin/category/list.do";
    }
}
