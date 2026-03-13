package com.nhnacademy.shoppingmall.common.initialize;

import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.service.CategoryService;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.common.mvc.controller.ControllerFactory;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import com.nhnacademy.shoppingmall.point.service.PointHistoryService;
import com.nhnacademy.shoppingmall.point.service.impl.PointHistoryServiceImpl;
import com.nhnacademy.shoppingmall.point.repository.impl.PointHistoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.category.service.impl.CategoryServiceImpl;

import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;
import java.util.Set;

@Slf4j
@HandlesTypes(value = {BaseController.class})
public class WebAppInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        UserService userService = new UserServiceImpl(userRepository);
        PointHistoryRepositoryImpl pointRepository = new PointHistoryRepositoryImpl();
        PointHistoryService pointHistoryService = new PointHistoryServiceImpl(pointRepository);
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
        ProductService productService = new ProductServiceImpl(productRepository);
        CategoryRepository categoryRepository = new CategoryRepositoryImpl();
        CategoryService categoryService = new CategoryServiceImpl(categoryRepository);

        OrderService orderService = new OrderServiceImpl(
                new OrderRepositoryImpl()
        );

        ctx.setAttribute("userService", userService);
        ctx.setAttribute("productService", productService);
        ctx.setAttribute("orderService", orderService);
        ctx.setAttribute("pointHistoryService", pointHistoryService);
        ctx.setAttribute("categoryService", categoryService);



        ControllerFactory controllerFactory = new ControllerFactory();
        controllerFactory.initialize(c, ctx);

        log.info("WebAppInitializer 완료: 모든 서비스 계층 등록 성공");
    }
}