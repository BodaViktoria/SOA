package com.ubbcluj.authentication.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@ComponentScan(basePackages = {
        "com.ubbcluj.authentication",
})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.ubbcluj.authentication")
@EntityScan(basePackages = "com.ubbcluj.authentication")
@EnableWebFlux
public class SpringApplicationConfig implements WebFluxConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .maxAge(3600);
    }
}