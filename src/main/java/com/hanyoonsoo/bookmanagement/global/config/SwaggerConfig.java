package com.hanyoonsoo.bookmanagement.global.config;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    static {
        io.swagger.v3.core.jackson.ModelResolver.enumsAsRef = true;
    }

    @Bean
    public GroupedOpenApi allOpenAPI() {
        return GroupedOpenApi.builder()
                .group("all")
                .pathsToMatch("/**") // 전체 경로 포함
                .addOpenApiCustomizer(openApi -> openApi.info(allInfo()))
                .build();
    }

    private Info allInfo() {
        return new Info()
                .title("All APIs")
                .version("1.0")
                .description("API Documentation for all endpoints");
    }

    @Bean
    public GroupedOpenApi authorOpenAPI() {
        return GroupedOpenApi.builder()
                .group("authors")
                .pathsToMatch("/authors/**")
                .addOpenApiCustomizer(openApi -> openApi.info(authorInfo()))
                .build();
    }

    private Info authorInfo() {
        return new Info()
                .title("Author API")
                .version("1.0")
                .description("API Author documentation");
    }

    @Bean
    public GroupedOpenApi bookOpenAPI() {
        return GroupedOpenApi.builder()
                .group("books")
                .pathsToMatch("/books/**")
                .addOpenApiCustomizer(openApi -> openApi.info(bookInfo()))
                .build();
    }

    private Info bookInfo() {
        return new Info()
                .title("Book API")
                .version("1.0")
                .description("API Book documentation");
    }
}
