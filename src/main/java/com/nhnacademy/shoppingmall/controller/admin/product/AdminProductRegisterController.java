package com.nhnacademy.shoppingmall.controller.admin.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/register.do")
public class AdminProductRegisterController implements BaseController {

    private static final String UPLOAD_DIR = "resources/images/products";

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ProductService productService = (ProductService) req.getServletContext().getAttribute("productService");

        try {
            String title = req.getParameter("title");
            String category = req.getParameter("category");
            int price = Integer.parseInt(req.getParameter("price"));
            Integer quantity = Integer.parseInt(req.getParameter("quantity"));
            String vendor = req.getParameter("vendor");
            String ean = req.getParameter("ean");

            Part filePart = req.getPart("productImage");
            String fileName = saveFile(req, filePart);

            Product product = new Product(
                    null, // Auto Increment
                    category,
                    title,
                    price,
                    quantity,
                    ean,
                    0.0,
                    vendor,
                    LocalDateTime.now()
            );

            productService.saveProduct(product);

        } catch (Exception e) {
            throw new RuntimeException("상품 등록 중 오류 발생: " + e.getMessage(), e);
        }

        return "redirect:/admin/product/list.do";
    }

    private String saveFile(HttpServletRequest req, Part filePart) throws IOException {
        if (filePart == null || filePart.getSize() == 0) return "no-image.png";

        String uploadPath = req.getServletContext().getRealPath("") + File.separator + UPLOAD_DIR;
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String originalFileName = filePart.getSubmittedFileName();
        String fileName = System.currentTimeMillis() + "_" + originalFileName;

        filePart.write(uploadPath + File.separator + fileName);
        return fileName;
    }
}