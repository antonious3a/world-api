package dev.antonio3a.worldapi.infra.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Value("${env.oauth2.token-url}")
    private String tokenUrl;

    @Value("${env.oauth2.authorization-url}")
    private String authorizationUrl;

    @Bean
    public OpenAPI customOpenAPI(@Value("${info.app.name}") String appName,
                                 @Value("${info.app.description}") String appDescription,
                                 @Value("${info.app.version}") String appVersion) {
        return new OpenAPI()
                .info(new Info()
                        .title(appName)
                        .version(appVersion)
                        .description(appDescription)
                        .summary("World API Summary")
                        .termsOfService("https://www.antonio3a.dev/terms/world-api")
                        .contact(new Contact()
                                .name("World API")
                                .email("world-api@antonio3a.dev")
                                .url("https://www.antonio3a.dev")
                        )
                        .license(new License()
                                .name("MIT License")
                                .url("https://choosealicense.com/licenses/mit/")
                        )
                )
                .schemaRequirement("JWT", new SecurityScheme()
                        .scheme("bearer")
                        .bearerFormat("JWT")
                        .description("JWT Authentication Bearer Token")
                        .type(SecurityScheme.Type.HTTP)
                )
                .schemaRequirement("OAUTH2", new SecurityScheme()
                        .description("OAuth2 Authentication Bearer Token")
                        .type(SecurityScheme.Type.OAUTH2)
                        .flows(new OAuthFlows()
                                .authorizationCode(new OAuthFlow()
                                        .tokenUrl(tokenUrl)
                                        .refreshUrl(tokenUrl)
                                        .authorizationUrl(authorizationUrl)
                                        .scopes(new Scopes()
                                                .addString("openid", "OpenID")
                                                .addString("profile", "Profile")
                                                .addString("email", "E-mail")
                                        )
                                )
                        )
                );
    }
}
