package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping(method = RequestMapping.Method.GET, value = "/admin/product/edit.do")
public class AdminProductUpdateFormController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ProductService productService = (ProductService) req.getServletContext().getAttribute("productService");

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            throw new RuntimeException("수정할 상품 ID가 없습니다.");
        }

        int id = Integer.parseInt(idStr);

        Product product = productService.getProduct(id);
        req.setAttribute("product", product);

        return "admin/admin_product_register_form";
    }
}