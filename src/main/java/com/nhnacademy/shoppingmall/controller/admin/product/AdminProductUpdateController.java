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

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST, value = "/admin/product/update.do")
public class AdminProductUpdateController implements BaseController {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        ProductService productService = (ProductService) req.getServletContext().getAttribute("productService");

        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String title = req.getParameter("title");
            Integer categoryId = Integer.parseInt(req.getParameter("categoryId"));
            int price = Integer.parseInt(req.getParameter("price"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            String vendor = req.getParameter("vendor");
            String ean = req.getParameter("ean");
            Product originalProduct = productService.getProduct(id);

            Part filePart = req.getPart("productImage");
            if (filePart != null && filePart.getSize() > 0) {
                saveImage(req, id, filePart);
                log.info("상품 ID {}의 이미지가 갱신되었습니다.", id);
            }
            Product updatedProduct = new Product(
                    id,
                    categoryId,
                    title,
                    price,
                    quantity,
                    ean,
                    originalProduct.getRating(),
                    vendor,
                    originalProduct.getCreatedAt()
            );

            productService.updateProduct(updatedProduct);

        } catch (Exception e) {
            log.error("상품 수정 중 오류 발생: {}", e.getMessage());
            throw new RuntimeException("상품 수정 실패", e);
        }

        return "redirect:/admin/product/list.do";
    }

    private void saveImage(HttpServletRequest req, int id, Part filePart) throws Exception {
        String uploadPath = req.getServletContext().getRealPath("") + File.separator + "resources/images/products";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();
        filePart.write(uploadPath + File.separator + id + ".jpg");
    }
}