package com.nuedu.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;
import javax.servlet.FilterRegistration;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class BaseConfig {
    @Resource
    TokenFliter tokenFliter;
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    CorsFilter getCorsFilter(){
        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration=new CorsConfiguration();
        configuration.addAllowedMethod("*");
        configuration.addAllowedOrigin("*");//域名
        configuration.addAllowedHeader("*");//请求头
        source.registerCorsConfiguration("/**",configuration);
        return new CorsFilter(source);
    }
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
    @Bean
    FilterRegistrationBean<TokenFliter> getTokenFilter(){
        List<String> urls=new ArrayList<String>();
        urls.add("*");
        FilterRegistrationBean<TokenFliter> filterRegistrationBean=new FilterRegistrationBean<TokenFliter>();
        //设置顺序
        filterRegistrationBean.setOrder(1);
        filterRegistrationBean.setFilter(tokenFliter);
        filterRegistrationBean.setName("tokenFliter");
        filterRegistrationBean.setUrlPatterns(urls);
        return filterRegistrationBean;
    }
}
