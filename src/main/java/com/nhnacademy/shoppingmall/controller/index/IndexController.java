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
        Page<Product> productPage = productService.getProductPage(1);
        if (productPage != null) {
            req.setAttribute("productList", productPage.getContent());
        }
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