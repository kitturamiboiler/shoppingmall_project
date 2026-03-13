package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.time.LocalDateTime;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/register.do")
public class AdminProductRegisterController implements BaseController {

    private static final String UPLOAD_DIR = "resources/images/products";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ProductService productService = (ProductService) req.getServletContext().getAttribute("productService");

        try {
            Integer categoryId = Integer.parseInt(req.getParameter("categoryId"));
            String title = req.getParameter("title");
            int price = Integer.parseInt(req.getParameter("price"));
            Integer quantity = Integer.parseInt(req.getParameter("quantity"));
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
            throw new RuntimeException("상품 등록 중 오류 발생: " + e.getMessage(), e);
        }

        return "redirect:/admin/product/list.do";
    }
}