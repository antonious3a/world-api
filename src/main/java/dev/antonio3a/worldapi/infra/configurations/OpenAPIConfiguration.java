package dev.antonio3a.worldapi.infra.configurations;

import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.OAuthFlow;
import io.swagger.v3.oas.models.security.OAuthFlows;
import io.swagger.v3.oas.models.security.Scopes;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {

    @Value("${oauth-flow.token-url}")
    private String tokenUrl;

    @Value("${oauth-flow.refresh-token-url}")
    private String refreshTokenUrl;

    @Value("${oauth-flow.authorization-url}")
    private String authorizationUrl;

    @Bean
    public GroupedOpenApi groupedOpenApi() {
        return GroupedOpenApi.builder()
                .addOpenApiCustomizer(openAPI -> openAPI
                        .info(new Info()
                                .title("World API")
                                .version("0.0.1-SNAPSHOT")
                                .description("World API Description")
                                .summary("World API Summary")
                                .termsOfService("https://www.antonio3a.dev/terms/world-api")
                                .contact(new Contact()
                                        .name("World API")
                                        .email("world-api@antonio3a.dev")
                                        .url("https://www.antonio3a.dev")
                                )
                                .license(new License()
                                        .name("GNU GENERAL PUBLIC LICENSE 3")
                                        .url("https://www.gnu.org/licenses/gpl-3.0.en.html")
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
                                                .refreshUrl(refreshTokenUrl)
                                                .authorizationUrl(authorizationUrl)
                                                .scopes(new Scopes()
                                                        .addString("oidc", "OIDC")
                                                )
                                        )
                                )
                        )
                        .addServersItem(new Server()
                                .url("https://antonio3a.dev/world/api")
                                .description("Online World API")
                        )
                )
                .group("public")
                .pathsToMatch("/world/api/**")
                .build();
    }
}
