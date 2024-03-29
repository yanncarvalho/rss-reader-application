package io.github.yanncarvalho.rssreader.rss.configuration;

import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.APP_LICENSE;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.JWT_BEARER;
import static io.github.yanncarvalho.rssreader.rss.configuration.DefaultValue.SWAGGER_SECURITY_DESCRIPTION;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Component
public class SwaggerConfig {

   @Value("${spring.application.name}")
   private String title;

   @Value("${application.version}")
   private String version;

   private String securitySchema = "bearerAuth";
   
   @Bean
   OpenAPI customOpenAPI() {
       return new OpenAPI()	.info(new Info().title(title)
                       .contact(new Contact().name("Yann Carvalho")).version(version)
                       .license(new License()
                    		   .name("Apache 2.0")
                    		   	.url(APP_LICENSE)))
        	     .addSecurityItem(new SecurityRequirement()
                         .addList(securitySchema))
                 .components(new Components()
                         .addSecuritySchemes(securitySchema, new SecurityScheme()
                                 .name(securitySchema)
                                 .type(SecurityScheme.Type.HTTP)
                                 .scheme(JWT_BEARER.toLowerCase())
                                 .description(SWAGGER_SECURITY_DESCRIPTION)
                                 .bearerFormat("JWT")));
    }
}