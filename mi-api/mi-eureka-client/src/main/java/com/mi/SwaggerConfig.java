package com.mi;

import com.mi.common.annotation.Dev;
import com.mi.common.annotation.Pro;
import com.mi.common.swagger.BaseSwaggerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.Annotation;
import java.util.Collections;


/**
 * Swagger配置模版（各自项目可粘帖到自个项目使用）
 * @author yesh
 *         (M.M)!
 *         Created by 2017/5/7.
 */
@Configuration
@EnableSwagger2
@Profile({"pro"})  //区别生成环境和产品环境
public class SwaggerConfig extends BaseSwaggerConfig {

//    @Value("${server.port}")
//    private String serverPort;
//
//    private String contentPath;
//
//    private Docket createApiDoc(String groupName, Class<? extends Annotation> annotation){
//        return  new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(initApiInfo())
//                .groupName(groupName)
//                .consumes(Collections.singleton(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
//                .produces(Collections.singleton(MediaType.APPLICATION_JSON_UTF8_VALUE))
//                .select()
//                .apis(RequestHandlerSelectors.withClassAnnotation(annotation)) //多种方式选择扫描区别分组
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    @Bean
//    public Docket createRestApi() {
//        return this.createApiDoc("dev", Dev.class);
//    }
//
//    @Bean
//    public Docket createRestAp2i() {
//        return this.createApiDoc("pro",Pro.class);
//    }
//
//    private ApiInfo initApiInfo() {
//        return new ApiInfoBuilder()
//                .title("MI-XX 项目 Platform API")
//                .description(initContextInfo())
//                .version("1.0.0")
//                .termsOfServiceUrl("http://blog.csdn.net/fjnpysh")
//                .contact("MIYAOW")
//                .license("请联系我的博客")
//                .licenseUrl("http://blog.csdn.net/fjnpysh")
//                .build();
//    }
//
//    private String initContextInfo() {
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("REST API 设计在细节上有很多自己独特的需要注意的技巧，并且对开发人员在构架设计能力上比传统 API 有着更高的要求。")
//                .append("<br/>")
//                .append("本文通过翔实的叙述和一系列的范例，从整体结构，到局部细节，分析和解读了为了提高易用性和高效性，REST API 设计应该注意哪些问题以及如何解决这些问题。");
//        return stringBuilder.toString();
//    }


}
