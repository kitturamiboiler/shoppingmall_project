package com.nhnacademy.shoppingmall.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LayoutInterceptor implements HandlerInterceptor {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView != null && modelAndView.hasView()) {
            String originalViewName = modelAndView.getViewName();

            if (!originalViewName.startsWith("redirect:")) {

                modelAndView.addObject("layout_content_holder", "/WEB-INF/views/" + originalViewName + ".jsp");

                if (originalViewName.startsWith("admin/")) {
                    modelAndView.setViewName("layout/admin_layout");
                }else {
                    modelAndView.setViewName("layout/shop");
                }
            }
        }
    }
}
