package com.nhnacademy.shoppingmall.controller.admin.category;

import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminCategoryUpdateController {
    private final CategoryService categoryService;

    @Autowired
    public AdminCategoryUpdateController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/admin/category/update.do", method = RequestMethod.POST)
    public String execute(@RequestParam("oldName") String oldName,
                          @RequestParam(name = "newName", required = false) String newName) {
        if (newName != null && !newName.trim().isEmpty()) {
            categoryService.modifyCategory(oldName, newName);
        }
        return "redirect:/admin/category/list.do";
    }
}
