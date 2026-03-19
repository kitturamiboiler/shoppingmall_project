package com.nhnacademy.shoppingmall.controller.view;

import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.util.RecentProductManager;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

//최근 본 상품
@Controller
public class ProductViewController{

    private final ProductService productService;

    @Autowired
    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/product/view.do", method = RequestMethod.GET)
    public String execute(Model model, @RequestParam("productId") String idStr, HttpSession session) {
        if (idStr == null) return "redirect:/index.do";

        int productId = Integer.parseInt(idStr);
        Product product = productService.getProduct(productId);
        if (product == null) {
            return "redirect:/index.do";
        }

        List<Integer> recentProductIds = (List<Integer>) session.getAttribute("recentProducts");

        recentProductIds = RecentProductManager.addRecentProduct(recentProductIds, productId);
        session.setAttribute("recentProducts", recentProductIds);

        java.util.List<Product> recentProductList = new java.util.ArrayList<>();
        if (recentProductIds != null) {
            for (Integer id : recentProductIds) {
                Product p = productService.getProduct(id);
                if (p != null) {
                    recentProductList.add(p);
                }
            }
        }
        model.addAttribute("recentProductList", recentProductList);
        model.addAttribute("product", product);

        return "shop/product/product_view";
    }
}