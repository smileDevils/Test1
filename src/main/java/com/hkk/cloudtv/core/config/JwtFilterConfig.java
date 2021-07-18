package com.hkk.cloudtv.core.config;

import com.hkk.cloudtv.filter.LoginFilter;
import com.hkk.cloudtv.filter.Test1Filter;
import com.hkk.cloudtv.filter.TestFile;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>
 *     Title: JwtFilterConfig.java
 * </p>
 *
 * <p>
 *     Description: JWT 过滤器注册管理类。
 *     方式一：在入口类中注册过滤器
 *     方式二：创建FileConfiguration类注入SpringIOC容器中
 *     方式三：@WebFilter 标明该类是一个过滤器
 * </p>
 */
//@Configuration
public class JwtFilterConfig implements WebMvcConfigurer {

    /**
     *<bean id="filterRegistrationBean" class="org.springframework.boot.web.servlet.FilterRegistrationBean"></bean>
     * @return
     */
    /*@Bean
    public FilterRegistrationBean firstFilterRegistrationBean(){
        // 写法一：
       *//* FilterRegistrationBean registration= new FilterRegistrationBean(new TestFile());
        registration.addUrlPatterns("/jwt/*");*//*
        // 写法二：
        FilterRegistrationBean registration= new FilterRegistrationBean();
        registration.setFilter(new TestFile());
        registration.addUrlPatterns("/jwt/*");
        *//*
        registration.setFilter(new LoginFilter());
        registration.addUrlPatterns("jwt/*");
*//*
        return registration;
    }*/

    /*@Bean
    public FilterRegistrationBean LoginFilter(){
        FilterRegistrationBean registration= new FilterRegistrationBean();
        registration.setFilter(new Test1Filter());
        registration.addUrlPatterns("/jwt/*");
        registration.setOrder(2);
        return registration;
    }*/
}
