package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@RequestMapping(method = RequestMapping.Method.GET, value = "/product/list.do")
public class ProductController implements BaseController {

    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        int page = 1;
        String pageParam = req.getParameter("page");

        try {
            if (pageParam != null && !pageParam.trim().isEmpty()) {
                page = Integer.parseInt(pageParam);
                if (page < 1) page = 1;
            }
        } catch (NumberFormatException e) {
            log.warn("잘못된 페이지 파라미터 접근: {}", pageParam);
            page = 1;
        }
        Page<Product> productPage = productService.getProductPage(page);
        int pageSize = 10;
        long totalCount = productPage.getTotalCount();
        long totalPages = (totalCount > 0) ? (totalCount + pageSize - 1) / pageSize : 1;

        req.setAttribute("productPage", productPage);
        req.setAttribute("currentPage", page);
        req.setAttribute("totalPages", totalPages);
        return "shop/product/product_list";
    }
}
