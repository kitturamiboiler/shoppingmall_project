package com.nhnacademy.shoppingmall.config;

import com.nhnacademy.shoppingmall.RootBase;
import com.nhnacademy.shoppingmall.controller.LayoutInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableWebMvc
@Import(RootConfig.class)
@ComponentScan(
        basePackageClasses = {RootBase.class},
        includeFilters = @ComponentScan.Filter(
                type = FilterType.ANNOTATION,
                classes = Controller.class
        ),
        useDefaultFilters = false
)
public class WebConfig implements WebMvcConfigurer {

    private final LayoutInterceptor layoutInterceptor = new LayoutInterceptor();

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        registry.viewResolver(viewResolver);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(layoutInterceptor)
                .addPathPatterns("/**/*.do") // .do로 끝나는 모든 요청에 적용
                .excludePathPatterns("/resources/**"); // 정적 파일은 제외
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}