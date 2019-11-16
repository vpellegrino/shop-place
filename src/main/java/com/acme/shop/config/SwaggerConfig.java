package com.acme.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
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
                .paths(regex("/api.*"))
                .build()
                .apiInfo(buildApiInfo())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo buildApiInfo() {
        return new ApiInfoBuilder()
                .title("Shop Place Application")
                .description("REST/JSON web service in Java to manage products and orders")
                .version("1.0")
                .termsOfServiceUrl("Terms of Service")
                .contact(new Contact("Valentino Pellegrino", "https://www.linkedin.com/in/valentinopellegrino/",
                        "pellegrino.valentino@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://www.apache.org/licesen.html")
                .build();
    }

}
