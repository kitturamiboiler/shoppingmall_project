package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;

@Controller
public class AdminProductDeleteController {
    private final ProductService productService;
    private final ServletContext context;

    @Autowired
    public AdminProductDeleteController(ProductService productService, ServletContext context) {
        this.productService = productService;
        this.context = context;
    }

    @RequestMapping(value = "/admin/product/delete.do", method = RequestMethod.POST)
    public String execute(@RequestParam("id") int id) {
        productService.deleteProduct(id);

        String uploadPath = context.getRealPath("") + File.separator + "resources/images/products";
        File imageFile = new File(uploadPath + File.separator + id + ".jpeg");
        if (imageFile.exists()) {
            imageFile.delete();
        }
        return "redirect:/admin/product/list.do";
    }
}