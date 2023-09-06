package tech.bonda.PaymentBackEnd.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CORSConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        // Set allowed origins
        registry.addMapping("/api/**")
                .allowedOrigins("*");

        // Set allowed methods
        registry.addMapping("/api/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE");

        // Set allowed headers
        registry.addMapping("/api/**")
                .allowedHeaders("Content-Type", "X-Requested-With", "accept, Origin", "Access-Control-Request-Method",
                        "Access-Control-Request-Headers");

        // Enable CORS for REST API
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("Content-Type", "X-Requested-With", "accept, Origin", "Access-Control-Request-Method",
                        "Access-Control-Request-Headers");

    }

}