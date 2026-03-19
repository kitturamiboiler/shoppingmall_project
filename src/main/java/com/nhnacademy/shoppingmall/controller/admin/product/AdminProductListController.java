package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AdminProductListController {
    private final ProductService productService;

    @Autowired
    public AdminProductListController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/admin/product/list.do", method = RequestMethod.GET)
    public String execute(Model model) {
        Page<Product> productPage = productService.getProductPage(1);
        model.addAttribute("products", productPage.getContent());

        return "admin/admin_product_list";
    }
}