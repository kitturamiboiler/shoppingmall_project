package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
public class AdminProductRegisterController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private static final String UPLOAD_DIR = "resources/images/products";

    @Autowired
    public AdminProductRegisterController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @RequestMapping(value = "/admin/product/register.do", method = RequestMethod.POST)
    public String execute(HttpServletRequest req, Model model) {
        try {
            Integer categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String title = req.getParameter("title");
            int price = Integer.parseInt(req.getParameter("price"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            String vendor = req.getParameter("vendor");
            String ean = req.getParameter("ean");

            Product product = new Product(
                    0,
                    categoryId,
                    title,
                    price,
                    quantity,
                    ean,
                    0.0,
                    vendor,
                    LocalDateTime.now()
            );
            model.addAttribute("product", product);

            productService.saveProduct(product);

            Part filePart = req.getPart("productImage");
            if (filePart != null && filePart.getSize() > 0) {
                String uploadPath = req.getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdirs();
                String fileName = product.getId() + ".jpeg";
                filePart.write(uploadPath + File.separator + fileName);

                log.info("상품 등록 및 이미지 저장 완료: {}", fileName);
            }

        } catch (Exception e) {
            log.error("상품 등록 실패: {}", e.getMessage());
            model.addAttribute("errorMessage", "상품 등록 실패: " + e.getMessage());
            List<Category> categoryList = categoryService.getCategoryList();
            model.addAttribute("categories", categoryList);
            return "admin/admin_product_register_form";
        }

        return "redirect:/admin/product/list.do";
    }
}