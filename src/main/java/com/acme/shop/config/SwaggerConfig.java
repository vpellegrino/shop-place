package com.acme.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket buildDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.acme.shop"))
                .paths(regex("/shop-place/.*"))
                .build()
                .apiInfo(buildApiInfo());
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfo(
                "Shop Place Application",
                "REST/JSON web service in Java to manage products and orders",
                "1.0",
                "Terms of Service",
                new Contact("Valentino Pellegrino", "https://www.linkedin.com/in/valentinopellegrino/",
                        "pellegrino.valentino@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html");
    }

}
