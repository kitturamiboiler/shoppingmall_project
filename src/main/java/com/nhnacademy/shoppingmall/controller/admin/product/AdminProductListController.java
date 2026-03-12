package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/admin_product/list.do")
public class AdminProductListController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ProductService productService = (ProductService) req.getServletContext().getAttribute("productService");
        if (productService == null) {
            throw new RuntimeException("ProductService를 로드할 수 없습니다.");
        }

        Page<Product> productPage = productService.getProductPage(1);
        req.setAttribute("products", productPage.getContent());

        return "admin/admin_product_list";
    }
}