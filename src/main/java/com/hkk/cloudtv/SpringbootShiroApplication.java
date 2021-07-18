package com.hkk.cloudtv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * <p>
 *     Title: SpringbootShiroApplication.java
 * </p>
 *
 * <p>
 *     Description: Springboot启动类，springBoot整合Mybatis、Shiro
 * </p>
/*
 *//*

@SpringBootApplication
public class SpringbootShiroApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootShiroApplication.class, args);
    }

}
*/

/**
 * 使用war包的方式部署项目
 */
@SpringBootApplication
@ServletComponentScan(basePackages ={ "com.hkk.cloudtv.filter"})//只用注解配置时，需要扫描包
public class SpringbootShiroApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        Long time=System.currentTimeMillis();
        SpringApplication.run(SpringbootShiroApplication.class);
        System.out.println("===应用启动耗时："+(System.currentTimeMillis()-time)+"===");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

}

