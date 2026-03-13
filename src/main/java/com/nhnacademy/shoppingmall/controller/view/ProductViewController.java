package com.nhnacademy.shoppingmall.controller.view;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.RecentProductManager;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.List;

//최근 본 상품
@RequestMapping(method = RequestMapping.Method.GET, value = "/product/view.do")
public class ProductViewController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ProductService productService = (ProductService) req.getServletContext().getAttribute("productService");
        String idStr = req.getParameter("productId");
        if (idStr == null) return "redirect:/index.do";

        int productId = Integer.parseInt(idStr);
        Product product = productService.getProduct(productId);
        if (product == null) {
            return "redirect:/index.do";
        }

        HttpSession session = req.getSession(true);
        List<Integer> recentProductIds = (List<Integer>) session.getAttribute("recentProducts");

        recentProductIds = RecentProductManager.addRecentProduct(recentProductIds, productId);
        session.setAttribute("recentProducts", recentProductIds);

        java.util.List<Product> recentProductList = new java.util.ArrayList<>();
        session.setAttribute("recentProductList", recentProductList);
        if (recentProductIds != null) {
            for (Integer id : recentProductIds) {
                Product p = productService.getProduct(id);
                if (p != null) {
                    recentProductList.add(p);
                }
            }
        }
        req.setAttribute("product", product);

        return "shop/product/product_view";
    }
}