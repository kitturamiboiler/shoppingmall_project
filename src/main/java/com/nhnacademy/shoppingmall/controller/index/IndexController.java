package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = {"/index.do", "/"}, method = RequestMethod.GET)
    public String execute(Model model, HttpSession session,
                          @RequestParam(value = "category", required = false) String category,
                          @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
        List<String> categoryList = productService.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        List<Product> productList = new ArrayList<>();
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            productList = productService.getProductsByTitle(searchKeyword, 0, 100);
        } else if (category != null && !category.trim().isEmpty() && !category.equals("ALL")) {
            productList = productService.getProductsByCategory(category, 0, 100);
        } else {
            Page<Product> productPage = productService.getProductPage(1);
            if (productPage != null) {
                productList = (List<Product>) productPage.getContent();
            }
        }
        model.addAttribute("productList", productList);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchKeyword", searchKeyword);

        List<Integer> recentIds = (List<Integer>) session.getAttribute("recentProducts");
        List<Product> recentProductList = new ArrayList<>();

        if (recentIds != null) {
            for (int id : recentIds) {
                Product p = productService.getProduct(id);
                if (p != null) {
                    recentProductList.add(p);
                }
                if (recentProductList.size() >= 5) break;
            }
        }
        model.addAttribute("recentProductList", recentProductList);

        model.addAttribute("layout_content_holder", "/WEB-INF/views/shop/main/index.jsp");
        return "layout/shop";

    }
}