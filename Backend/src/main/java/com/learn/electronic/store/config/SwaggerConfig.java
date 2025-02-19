package com.learn.electronic.store.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {




    @Bean
    public OpenAPI openAPI(){

        String schemeName="bearerScheme";

            return new OpenAPI()
                    .addSecurityItem(new SecurityRequirement()
                            .addList(schemeName)
                    )
                    .components(new Components()
                            .addSecuritySchemes(schemeName,new SecurityScheme()
                                    .name(schemeName)
                                    .type(SecurityScheme.Type.HTTP)
                                    .bearerFormat("JWT")
                                    .scheme("bearer")
                            )
                    )


                    .info(new Info().title("Electronic Store API")
                            .description("These is electronic store project api developed by preet")
                            .version("v0.0.1")
                            .contact(new Contact().name("Preet").email("preet@gmail.com"))
                            .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                    ).externalDocs(new ExternalDocumentation()
                            .description("SpringShop Wiki Documentation")
                            .url("https://springshop.wiki.github.org/docs"));



    }



//    @Bean
//    public Docket docket() {
//        Docket docket = new Docket(DocumentationType.SWAGGER_2);
//        docket.apiInfo(getApiInfo());
//
//
//        docket.securityContexts(Arrays.asList(getSecurityContext()));
//        docket.securitySchemes(Arrays.asList(getSchemes()));
//
//        ApiSelectorBuilder select = docket.select();
//        select.apis(RequestHandlerSelectors.any());
//        select.paths(PathSelectors.any());
//        Docket build = select.build();
//        return build;
//    }
//
//
//    private SecurityContext getSecurityContext() {
//
//        SecurityContext context = SecurityContext
//                .builder()
//                .securityReferences(getSecurityReferences())
//                .build();
//        return context;
//    }
//
//    private List<SecurityReference> getSecurityReferences() {
//        AuthorizationScope[] scopes = {new AuthorizationScope("Global", "Access Every Thing")};
//        return Arrays.asList(new SecurityReference("JWT", scopes));
//
//    }
//
//    private ApiKey getSchemes() {
//        return new ApiKey("JWT", "Authorization", "header");
//    }
//
//
//    private ApiInfo getApiInfo() {
//
//        ApiInfo apiInfo = new ApiInfo(
//                "Electronic Store Backend : APIS ",
//                "This is backend project created by LCWD",
//                "1.0.0V",
//                "https://www.learncodewithdurgesh.com",
//                new Contact("Durgesh", "https://www.instagram.com/durgesh_k_t", "learncodewithdurgesh@gmail.com"),
//                "License of APIS",
//                "https://www.learncodewithdurgesh.com/about",
//                new ArrayDeque<>()
//        );
//
//        return apiInfo;
//
//    }


}
