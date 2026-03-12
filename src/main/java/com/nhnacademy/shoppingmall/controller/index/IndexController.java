package com.nhnacademy.shoppingmall.controller.index;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET, value = {"/index.do"})
public class IndexController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ProductService productService = (ProductService) req.getServletContext().getAttribute("productService");
        String category = req.getParameter("category");
        String searchKeyword = req.getParameter("searchKeyword");
        List<String> categoryList = productService.getAllCategories();
        req.setAttribute("categoryList", categoryList);
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
        req.setAttribute("productList", productList);
        req.setAttribute("selectedCategory", category);
        req.setAttribute("searchKeyword", searchKeyword);

        HttpSession session = req.getSession();
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
        req.setAttribute("recentProductList", recentProductList);
        return "shop/main/index";
    }
}