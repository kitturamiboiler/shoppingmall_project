package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminProductUpdateFormController {
    private final ProductService productService;

    @Autowired
    public AdminProductUpdateFormController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/admin/product/edit.do", method = RequestMethod.GET)
    public String execute(Model model, @RequestParam("id") String idStr) {

        if (idStr == null || idStr.isEmpty()) {
            throw new RuntimeException("수정할 상품 ID가 없습니다.");
        }

        int id = Integer.parseInt(idStr);

        Product product = productService.getProduct(id);
        model.addAttribute("product", product);

        return "admin/admin_product_register_form";
    }
}