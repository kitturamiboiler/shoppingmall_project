package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/delete.do")
public class AdminProductDeleteController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ProductService productService = (ProductService) req.getServletContext().getAttribute("productService");

        int id = Integer.parseInt(req.getParameter("id"));

        productService.deleteProduct(id);

        String uploadPath = req.getServletContext().getRealPath("") + File.separator + "resources/images/products";
        File imageFile = new File(uploadPath + File.separator + id + ".jpg");
        if (imageFile.exists()) {
            imageFile.delete();
        }
        return "redirect:/admin/product/list.do";
    }
}